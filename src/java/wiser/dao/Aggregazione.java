package wiser.dao;

/**
 * This class represents an aggregation of data services.
 *
 * @author Flavia Venturelli
 * @version 1.0
 */
import java.util.*;

public class Aggregazione {

    /**
     * Aggregation identifier.
     */
    private long id;
    /**
     * The list of data services that compose the aggregation.
     */
    private Set<DataService> elencoDS;
    /**
     * The name assigned to the aggregation.
     */
    private String nome;
    /**
     * A textual, human-readable description of the aggregation.
     */
    private String descrizione;
    /**
     * The developer who designed the aggregation.
     */
    private Sviluppatore autore;

    public Aggregazione() {

    }

    public long getId() {
        return id;
    }

    public Sviluppatore getAutore() {
        return autore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Set<DataService> getElencoDS() {
        return elencoDS;
    }

    public void setElencoDS(Set<DataService> eDS) {
        elencoDS = new HashSet<>(eDS);
    }

    public void setDescrizione(String d) {
        descrizione = d;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAutore(Sviluppatore autore) {
        this.autore = autore;
    }

    /**
     * Search the data service specified as input parameter within the aggregation.
     *
     * @param s Data service to search.
     * @return TRUE if the aggregation contains the data service specified as input parameter, otherwise returns FALSE.
     */
    public boolean includeDS(DataService s) {
        java.util.Iterator<DataService> iter = elencoDS.iterator();
        while (iter.hasNext()) {
            DataService ds = iter.next();

            if (Objects.equals(ds.getId(), s.getId())) {
                return true;
            }
        }

        return false;
    }
}
