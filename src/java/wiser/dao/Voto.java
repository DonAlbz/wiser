package wiser.dao;

/**
 * This class represents a rating assigned to a data service within a given
 * aggregation.
 *
 * @author Flavia Venturelli
 * @version 1.0
 */
public class Voto {

    /**
     * The IDentifier of the rating.
     */
    private long id;
    /**
     * The rating (within the [0,99] range).
     */
    private int voto;
    /**
     * The developer who assigned the rating.
     */
    private Sviluppatore d;
    /**
     * The aggregation that represents the context of the rating.
     */
    private Aggregazione a;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Sviluppatore getD() {
        return d;
    }

    public void setD(Sviluppatore d) {
        this.d = d;
    }

    public Aggregazione getA() {
        return a;
    }

    public void setA(Aggregazione a) {
        this.a = a;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int v) {
        this.voto = v;
    }
}
