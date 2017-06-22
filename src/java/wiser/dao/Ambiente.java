package wiser.dao;

/**
 * This class represents the social environment within which a developer
 * operates, described through the structure of the social network (simple
 * hierarchical - gs, multi-specialized hierarchical - gms, peer-based - pb),
 * the context (open - o, enterprise-wide - e), the extent of the domain
 * (general purpose - gp, niche - {n1 n2 n3 ..}), the maturity of the domain
 * (expansion - e, standard - r).
 *
 * @author Flavia Venturelli
 * @version 1.0
 */
public class Ambiente {

    /**
     * The identifier of the object.
     */
    private long id;
    /**
     * The structure of the social network (simple hierarchical - gs,
     * multi-specialized hierarchical - gms, peer-based - pb).
     */
    private String struttura;
    /**
     * The context (open - o, enterprise-wide - e).
     */
    private String contesto;
    /**
     * The extent of the domain (general purpose - gp, niche - {n1 n2 n3 ..}).
     */
    private String ampiezza;
    /**
     * The maturity of the domain (expansion - e, standard - r).
     */
    private String maturita;

    public Ambiente() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStruttura() {
        return struttura;
    }

    public void setStruttura(String struttura) {
        this.struttura = struttura;
    }

    public String getContesto() {
        return contesto;
    }

    public void setContesto(String contesto) {
        this.contesto = contesto;
    }

    public String getAmpiezza() {
        return ampiezza;
    }

    public void setAmpiezza(String ampiezza) {
        this.ampiezza = ampiezza;
    }

    public String getMaturita() {
        return maturita;
    }

    public void setMaturita(String maturita) {
        this.maturita = maturita;
    }

}
