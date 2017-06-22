package wiser.dao;

/**
 * This class represents a data service.
 *
 * @author Flavia Venturelli
 * @version 1.0
 */
import wiser.middleware.similarity.MyUtil;
import wiser.middleware.similarity.Kmeans;
import java.util.*;
import org.hibernate.Session;
import wiser.middleware.similarity.Similarity;

public class DataService {

    /**
     * The IDentifier of the data service.
     */
    private Long idDS;
    /**
     * The name assigned to the data service.
     */
    private String nome;
    /**
     * The textual, human-readable description of the data service.
     */
    private String descrizione;
    /**
     * A set of (semantic) tags used to label the data service.
     */
    private Set<Tag> tag;
    /**
     * The number of times data service has been used in aggregations (i.e., the number of aggregations that contain the data service).
     */
    private int numeroUtilizzi;
    /**
     * Input parameters (comma separated, each of them expressed through the semantic type of the parameter).
     */
    private String input;
    /**
     * Output parameters (comma separated, each of them expressed through the semantic type of the parameter).
     */
    private String output;
    /**
     * The list of ratings assigned to the data service.
     */
    private Set<Voto> voti;

    private static final int MAX_VOTO = 100;

    public DataService() {

    }

    public Long getId() {
        return idDS;
    }

    public void setId(Long id) {
        this.idDS = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Set<Tag> getTag() {
        return tag;
    }

    public void setTag(Set<Tag> tag) {
        this.tag = tag;
    }

    public int getNumeroUtilizzi() {
        return numeroUtilizzi;
    }

    public void setNumeroUtilizzi(int numeroUtilizzi) {
        this.numeroUtilizzi = numeroUtilizzi;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Set<Voto> getVoti() {
        return voti;
    }

    public void setVoti(Set<Voto> v) {
        this.voti = v;
    }

    /**
     * Finds developers who used the service.
     *
     * @param s The session opened on the database database.
     * @return The list of searched developers.
     */
    public ArrayList<Sviluppatore> trovaUtilizzatori(Session s) {
        ArrayList<Sviluppatore> utilizzatori;
        utilizzatori = new ArrayList<>();

        ArrayList<Aggregazione> elencoAgg = (new DAOclass(s)).readAggregation(this);
        java.util.Iterator<Aggregazione> i = elencoAgg.iterator();
        while (i.hasNext()) {
            Aggregazione a = i.next();
            Sviluppatore d = a.getAutore();
            if (MyUtil.cercaElementoD(utilizzatori, d.getId()) == -1) {
                utilizzatori.add(d);
            }
        }

        return utilizzatori;
    }

    /**
     * Associates a rating to the data service and updates credibility of the developers who assigned the rating; the credibility is not modified if the rating is the first one assigned within a given width of the domain.
     *
     * @param s The session opened on the database database.
     * @param v The assigned rating.
     * @param d The developer who assigns the rating.
     * @param a Aggregation with reference to which rating assignment has been performed.
     */
    public void assegnaVoto(Session s, int v, Sviluppatore d, Aggregazione a) {

        ArrayList<Integer> vv;
        vv = new ArrayList<>();
        java.util.Iterator<Voto> i = voti.iterator();

        while (i.hasNext()) {
            Voto vi = i.next();
            if (vi.getD().getAmbiente().getAmpiezza().equals(d.getAmbiente().getAmpiezza())) {
                vv.add(vi.getVoto());
            }
        }

        int[] votes = MyUtil.vec2arrayInt(vv);

        if (votes.length != 0) {
            int n_cluster = (int) Math.ceil(Math.sqrt((double) votes.length / 2));
            if (n_cluster != 0) {
                double kmean = Kmeans.findCentroid(votes, n_cluster);
                double diff = Math.abs(v - kmean);
                if (d.getAmbiente().getContesto().equals("o")) {
                    Similarity.aggiornaCredibilita(d, s, diff / 100);
                }
            }
        }

        (new DAOclass(s)).updateDataService(idDS, d, a, v);
    }

    /**
     * Computes average rating assigned to a service.
     *
     * @return The computed average in the range [0,1].
     */
    public double mediaVoti() {
        double avg = 0.00;

        java.util.Iterator<Voto> i = voti.iterator();
        while (i.hasNext()) {
            Voto vi = i.next();
            avg += vi.getVoto();

        }

        avg = avg / voti.size() / MAX_VOTO;

        return avg;
    }
    
   // /*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/
    
    public int getParteInteraVoti(double media) {
        double mediaNormalizzata = (media*MAX_VOTO)/20;
        int parteIntera = (int)mediaNormalizzata;
        return parteIntera;
    }
    
    public double getParteDecimaleVoti(double media) {
        double mediaNormalizzata = (media*MAX_VOTO)/20;
        int parteIntera = (int)mediaNormalizzata;
        double parteDecimale = mediaNormalizzata - parteIntera;
        return parteDecimale;
    }
}
