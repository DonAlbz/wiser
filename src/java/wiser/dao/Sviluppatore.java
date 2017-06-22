package wiser.dao;

/**
 * This class represents a developer.
 *
 * @author Flavia Venturelli
 * @version 1.0
 */
import java.util.*;
import org.hibernate.Session;
 
public class Sviluppatore {

    /**
     * The IDentifier of the developer.
     */
    private long id;
    /**
     * Developer's name.
     */
    private String nome;
    /**
     * Developer's password.
     */
    private String password;
    /**
     * The social environment within which a developer operates.
     */
    private Ambiente ambiente;
    /**
     * The set of developer's followers.
     */
    private Set<Sviluppatore> followed;
    /**
     * A set of relative ranks of this developer with respect to other ones.
     */
    private Set<CoppiaDR> elencoDR;
    /**
     * Developer's credibility (belonging to the [0,1] range).
     */
    private double credibilita;

    public Sviluppatore() {

    }

    public long getId() {
        return id;
    }

    public Set<CoppiaDR> getElencoDR() {
        return elencoDR;
    }

    public void setElencoDR(Set<CoppiaDR> elencoDR) {
        this.elencoDR = elencoDR;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFollowed(Set<Sviluppatore> followed) {
        this.followed = followed;
    }

    public String getNome() {
        return nome;
    }

    public Set<Sviluppatore> getFollowed() {
        return followed;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void aggiungiDR(CoppiaDR cdr) {
        elencoDR.add(cdr);
    }

    public void aggiungiFollow(Sviluppatore d) {
        followed.add(d);
    }

    public void rimuoviFollow(Sviluppatore d) {
        followed.remove(d);
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente a) {
        this.ambiente = a;
    }

    public double getCredibilita() {
        return credibilita;
    }

    public void setCredibilita(double c) {
        this.credibilita = c;
    }

    /**
     * Find the developer's rank with respect to the developer specified as input parameter.
     *
     * @param d The reference developer to compute the rank.
     * @return The computed developer's rank.
     */
    public double findDR(Sviluppatore d) {
        double dr = 0;
        java.util.Iterator<CoppiaDR> iter = elencoDR.iterator();
        while (iter.hasNext()) {
            CoppiaDR ci = iter.next();

            if (ci.getRispettoA().getId() == d.getId()) {
                dr = ci.getDr();
                break;
            }
        }

        return dr;
    }

    /**
     * Find developers directly or indirectly followed by the current one; this method can be invoked only if developers' social network does not present cycles.
     *
     * @param antenati Developers who have been already found (this method is recursive).
     * @return The list of developers who have been found.
     */
    public ArrayList<Sviluppatore> trovaAntentati(ArrayList<Sviluppatore> antenati) {
        java.util.Iterator<Sviluppatore> iter = followed.iterator();

        while (iter.hasNext()) {
            Sviluppatore d = iter.next();
            antenati.add(d);
        }

        // NULL divide depth levels
        iter = followed.iterator();
        if (iter.hasNext()) {
            antenati.add(null);
        }

        while (iter.hasNext()) {
            Sviluppatore d = iter.next();
            d.trovaAntentati(antenati);
        }

        return antenati;
    }

    /**
     * Return the number of times the developer used the data service specified as input parameter.
     *
     * @param ds The data service.
     * @param s La sessione aperta con il database.
     * @return Il valore cercato.
     */
    public int findUtilizzi(DataService ds, Session s) {
        int n = 0;

        Set<Aggregazione> aa = (new DAOclass(s)).readAggregation(this);
        java.util.Iterator<Aggregazione> i = aa.iterator();
        while (i.hasNext()) {
            Aggregazione a = i.next();
            boolean temp = a.includeDS(ds);
            if (temp) {
                n++;
            }
        }

        return n;
    }

}
