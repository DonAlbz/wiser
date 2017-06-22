package wiser.dao;

/**
 * This class represents a tag assigned to a data service.
 *
 * @author Flavia Venturelli, Devis Bianchini
 * @version 1.0
 */

public class Tag {

    /**
     * The IDentifier of the tag.
     */
    private long id;
    /**
     * The name of the tag (i.e., the term). 
     */
    private String nome;
    /**
     * Part of speech the semantically enriches the tag (noun N, verb C, adjective A, adverb R).
     */
    private String pos;
    /**
     * The ID of the Wordnet synset the semantically disambiguated tag belongs to.
     */
    private long offset;

    public Tag() {

    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Perform a comparison between the current tag and the ones specified as input parameter.
     *
     * @param t2 The tag to be compared with the current one.
     * @return TRUE if tags are the same, FALSE otherwise.
     */
    public boolean equal(Tag t2) {
        return offset == t2.getOffset() && pos.equals(t2.getPos()) && nome.equals(t2.getNome());
    }
}
