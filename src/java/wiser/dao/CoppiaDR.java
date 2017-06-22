package wiser.dao;

/**
 * This class represents the rank of developer with respect to a different one
 * in his/her social network.
 *
 * @author Flavia Venturelli
 * @version 1.0
 */
public class CoppiaDR {

    /**
     * The IDentifier of the instance of this class.
     */
    private long id;
    /**
     * The developer with respect to which the rank is computed.
     */
    private Sviluppatore rispettoA;
    /**
     * The rank value.
     */
    private double dr;

    public Sviluppatore getRispettoA() {
        return rispettoA;
    }

    public void setRispettoA(Sviluppatore rispettoA) {
        this.rispettoA = rispettoA;
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
