package wiser.dao;

/**
 * Utility class to manage interactions with the database.
 *
 * @author Flavia Venturelli, Devis Bianchini.
 * @version 1.1
 */
import java.sql.ResultSet;
import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class DAOclass {

    /**
     * The session opened on the database.
     */
    private final Session session;
    private Object statement;

    public DAOclass(Session s) {
        this.session = s;
    }

    /**
     * Search ratings assigned by a developer.
     *
     * @param iddev Developer ID.
     * @return The list of ratings.
     */
    public ArrayList<Voto> readVoti(Long iddev) {
        ArrayList<Voto> vv;
        vv = new ArrayList<>();
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Query query = this.session.createQuery("from Voto where id_dev=" + iddev);
        List<Voto> list = query.list();
        java.util.Iterator<Voto> iter = list.iterator();
        while (iter.hasNext()) {
            vv.add(iter.next());
        }

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return vv;
    }

    /**
     * Save in a persistent way a tag on the database.
     *
     * @param n The name of the tag.
     * @param p Part of speech the semantically enriches the tag (noun N, verb
     * C, adjective A, adverb R).
     * @param o The ID of the Wordnet synset the semantically disambiguated tag
     * belongs to.
     */
    public void createTag(String n, String p, Long o) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Tag t = new Tag();
        t.setNome(n);
        t.setOffset(o);
        t.setPos(p);

        this.session.save(t);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

    }

    /**
     * Read a Tag from the database.
     *
     * @param id The ID of Tag to be read.
     * @return The Tag retrieved from the database.
     */
    public Tag readTag(Long id) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Tag t = (Tag) this.session.get(Tag.class, id);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return t;
    }

    /**
     * Save in a persistent way a data service on the database.
     *
     * @param nome Data service name.
     * @param descrizione Data service description.
     * @param specifiche Set of Tags that describe the data service.
     * @param input Input parameters (comma separated, each of them expressed
     * through the semantic type of the parameter).
     * @param output Output parameters (comma separated, each of them expressed
     * through the semantic type of the parameter).
     */
    public void createDataService(String nome, String descrizione, Set<Tag> specifiche, String input, String output) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        DataService ds = new DataService();

        ds.setNome(nome);
        ds.setDescrizione(descrizione);
        ds.setTag(specifiche);
        ds.setNumeroUtilizzi(0);
        ds.setInput(input);
        ds.setOutput(output);
        ds.setVoti(new HashSet<>());

        this.session.save(ds);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }
    }

    /**
     * Read a data service from the database.
     *
     * @param id ID of data service to read.
     * @return The retrieved data service.
     */
    public DataService readDataService(Long id) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        DataService ds = (DataService) session.get(DataService.class, id);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return ds;
    }

    /**
     * Search for data service presenting the maximum value of the product between average rating and number of uses; return the computed value.
     *
     * @return Maximum computed value.
     */
    public double readDataServiceMax() {
        double max = 0;
        ArrayList<Double> nprimo;
        nprimo = new ArrayList<>();

        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }
        Query query;
        query = this.session.createQuery("select ds from DataService ds");
        List<DataService> risultato = query.list();
        java.util.Iterator<DataService> i = risultato.iterator();

        while (i.hasNext()) {
            DataService ds = i.next();
            double avg = ds.mediaVoti();
            nprimo.add(avg * ds.getNumeroUtilizzi());
        }

        for (Double nprimo1 : nprimo) {
            if (nprimo1 > max) {
                max = nprimo1;
            }
        }

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return max;
    }

    /**
     * Read all data service from the database.
     *
     * @return The list of retrieved data services.
     */
    public ArrayList<DataService> readDataServices() {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        List<DataService> elenco = this.session.createQuery("select ds from DataService ds").list();
        Set<DataService> ds = new HashSet<>(elenco);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return new ArrayList<>(ds);
    }

    /**
     * Update number of uses of a data service.
     *
     * @param id ID of data service to be updated.
     * @param incrementoUsi Number of uses to increment.
     */
    private void updateDataService(Long id, int incrementoUsi) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        DataService daAggiornare = new DataService();

        Query query = this.session.createQuery("FROM DataService WHERE id=" + id);
        List<DataService> list = query.list();
        java.util.Iterator<DataService> iter = list.iterator();
        while (iter.hasNext()) {
            daAggiornare = iter.next();
        }

        daAggiornare.setNumeroUtilizzi(daAggiornare.getNumeroUtilizzi() + incrementoUsi);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }
    }

    /**
     * Update data service by assigning a rating.
     *
     * @param id ID of data service to be rated.
     * @param d The developer who assigns the rating.
     * @param a The aggregation where the data service has been used and with respect to data service is being rated.
     * @param v The new rating.
     */
    public void updateDataService(Long id, Sviluppatore d, Aggregazione a, int v) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Voto voto = new Voto();
        voto.setA(a);
        voto.setD(d);
        voto.setVoto(v);

        this.session.save(voto);

        DataService daAggiornare = new DataService();

        Query query = this.session.createQuery("FROM DataService WHERE id=" + id);
        List<DataService> list = query.list();
        java.util.Iterator<DataService> iter = list.iterator();
        while (iter.hasNext()) {
            daAggiornare = iter.next();
        }

        Set<Voto> voti = daAggiornare.getVoti();
        voti.add(voto);
        daAggiornare.setVoti(voti);

        this.session.update(d);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }
    }

    /**
     * Save in a persistent way a new developer in the database.
     *
     * @param nome The name of new developer.
     * @param pwd The password of new developer.
     * @param cred Initial credibility of the new developer.
     * @param s The topology of the social network the developer belongs to (gs - gms - pb).
     * @param c The context in which the developer is acting (o - e).
     * @param a The width of the domain in which the developer is acting (gp - {n1 n2 n3 ..}).
     * @param m The maturity of the domain in which the developer is acting (e - r).
     * @return ID of the newly created developer.
     */
    public long createDeveloper(String nome, String pwd, double cred, String s, String c, String a, String m) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Sviluppatore d = new Sviluppatore();

        d.setNome(nome);
        d.setPassword(pwd);
        d.setElencoDR(new HashSet<>());
        d.setFollowed(new HashSet<>());
        d.setCredibilita(cred);

        Ambiente ambiente = new Ambiente();
        ambiente.setStruttura(s);
        ambiente.setContesto(c);
        ambiente.setAmpiezza(a);
        ambiente.setMaturita(m);

        this.session.save(ambiente);

        d.setAmbiente(ambiente);
        this.session.save(d);

        List<Long> elenco = this.session.createQuery("select MAX(id) as idmax from Sviluppatore").list();
        java.util.Iterator<Long> i = elenco.iterator();
        long id = (long) 0;
        while (i.hasNext()) {
            id = i.next();
            break;
        }

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return id;
    }

    /**
     * Read a developer from the database.
     *
     * @param id ID of developer to be read.
     * @return The retrieved developer.
     */
    public Sviluppatore readDeveloper(Long id) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Sviluppatore d = (Sviluppatore) session.get(Sviluppatore.class, id);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return d;
    }

    /**
     * Read the followers of a developer in the social network.
     *
     * @param d The developer whose followers are searched for.
     * @return The list of retrieved followers.
     */
    public Set<Sviluppatore> readFollowers(Sviluppatore d) {

        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Set<Sviluppatore> followers = new HashSet<>();

        Query query = this.session.createQuery("select d from Sviluppatore d "
                + "inner join d.followed fed where fed.id=" + d.getId());
        List<Sviluppatore> list = query.list();
        java.util.Iterator<Sviluppatore> i = list.iterator();
        while (i.hasNext()) {
            followers.add(i.next());
        }

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return followers;
    }

    /**
     * Read all developers with a given domain width.
     *
     * @param a The Ambient with required width.
     * @return The list of retrieved developers.
     */
    public Set<Sviluppatore> readDevelopers_amp(Ambiente a) {

        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Query query = this.session.createQuery("select d from Sviluppatore d "
                + "inner join d.ambiente a where a.ampiezza='" + a.getAmpiezza() + "'");
        List<Sviluppatore> elenco = query.list();

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        Set<Sviluppatore> ed = new HashSet<>(elenco);
        return ed;
    }

    /**
     * Read all developers within an social network with a given domain topology.
     *
     * @param a The Ambient with required topology.
     * @return The list of retrieved developers.
     */
    public Set<Sviluppatore> readDevelopers_strut(Ambiente a) {

        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Query query = this.session.createQuery("select d from Sviluppatore d "
                + "inner join d.ambiente a where a.struttura='" + a.getStruttura() + "'");
        List<Sviluppatore> elenco = query.list();

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        Set<Sviluppatore> ed = new HashSet<>(elenco);
        return ed;
    }

    /**
     * Read all developers from the database.
     *
     * @return The list of retrieved developers.
     */
    public Set<Sviluppatore> readDevelopers() {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        List<Sviluppatore> elenco = this.session.createQuery("select d from Sviluppatore d").list();
        Set<Sviluppatore> developers = new HashSet<>(elenco);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return developers;
    }

    /**
     * Add a new social relationship to a developer.
     *
     * @param idDaAgg The developer to modify.
     * @param idNuovoFollow ID of the developer towards whom the new social relationship is set.
     */
    public void updateDeveloperAdd(Long idDaAgg, Long idNuovoFollow) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Sviluppatore daAggiornare = new Sviluppatore();

        Query query = this.session.createQuery("FROM Sviluppatore WHERE id=" + idDaAgg);
        List<Sviluppatore> list = query.list();
        java.util.Iterator<Sviluppatore> iter = list.iterator();
        while (iter.hasNext()) {
            daAggiornare = iter.next();
        }

        Sviluppatore nuovoFollowed = new Sviluppatore();

        Query query2 = this.session.createQuery("FROM Sviluppatore WHERE id=" + idNuovoFollow);
        List<Sviluppatore> list2 = query2.list();
        java.util.Iterator<Sviluppatore> iter2 = list2.iterator();
        while (iter2.hasNext()) {
            nuovoFollowed = iter2.next();
        }

        daAggiornare.aggiungiFollow(nuovoFollowed);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }
    }

    /**
     * Remove a social relationship from a developer.
     *
     * @param idDaAgg The developer to modify.
     * @param idNuovoFollow ID of the developer for whom the social relationship is removed.
     */
    public void updateDeveloperRem(Long idDaAgg, Long idExFollow) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Sviluppatore daAggiornare = new Sviluppatore();

        Query query = this.session.createQuery("FROM Sviluppatore WHERE id=" + idDaAgg);
        List<Sviluppatore> list = query.list();
        java.util.Iterator<Sviluppatore> iter = list.iterator();
        while (iter.hasNext()) {
            daAggiornare = iter.next();
        }

        Sviluppatore nuovoFollowed = new Sviluppatore();

        Query query2 = this.session.createQuery("FROM Sviluppatore WHERE id=" + idExFollow);
        List<Sviluppatore> list2 = query2.list();
        java.util.Iterator<Sviluppatore> iter2 = list2.iterator();
        while (iter2.hasNext()) {
            nuovoFollowed = iter2.next();
        }

        daAggiornare.rimuoviFollow(nuovoFollowed);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }
    }

    /**
     * Save in persistent way a new aggregation on the database.
     *
     * @param nome The name of the new aggregation.
     * @param descr The description of the new aggregation.
     * @param autore The developer who has designed the aggregation.
     * @param elencoDS The list of data services that compose the new aggregation.
     * @return The ID of the created aggregation.
     */
    public long createAggregation(String nome, String descr, Sviluppatore autore, Set<DataService> elencoDS) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Aggregazione a = new Aggregazione();

        a.setNome(nome);
        a.setDescrizione(descr);
        a.setAutore(autore);
        a.setElencoDS(elencoDS);

        java.util.Iterator<DataService> i = elencoDS.iterator();
        while (i.hasNext()) {
            DataService temp = i.next();
            updateDataService(temp.getId(), 1);
        }

        this.session.save(a);

        long id = (long) 0;

        List<Long> elenco = this.session.createQuery("select MAX(id) as idmax from Aggregazione").list();
        java.util.Iterator<Long> j = elenco.iterator();
        while (j.hasNext()) {
            id = j.next();
            break;
        }

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return id;
    }

    /**
     * Read all aggregations from the database.
     *
     * @return The list of retrieved aggregations.
     */
    public ArrayList<Aggregazione> readAggregation() {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        List<Aggregazione> elenco = this.session.createQuery("select a from Aggregazione a").list();
        ArrayList<Aggregazione> aggregazioni;
        aggregazioni = new ArrayList<>(elenco);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return aggregazioni;
    }

    /**
     * Read all aggregations that contain the specified data service.
     *
     * @param ds Data service to be found in the retrieved aggregations.
     * @return The list of retrieved aggregations.
     */
    public ArrayList<Aggregazione> readAggregation(DataService ds) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        ArrayList<Aggregazione> aggregazioni;
        aggregazioni = new ArrayList<>();

        Query query = this.session.createQuery("select a from Aggregazione a "
                + "inner join a.elencoDS eds where eds.id=" + ds.getId());
        List<Aggregazione> list = query.list();
        java.util.Iterator<Aggregazione> i = list.iterator();
        while (i.hasNext()) {
            aggregazioni.add(i.next());
        }

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return aggregazioni;
    }

    /**
     * Read an aggregation from the database.
     *
     * @param id ID of the aggregation.
     * @return The retrieved aggregation.
     */
    public Aggregazione readAggregation(Long id) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Aggregazione a = (Aggregazione) this.session.get(Aggregazione.class, id);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return a;
    }

    /**
     * Read aggregations designed by a given developer.
     *
     * @param d The developer who designed the aggregation to search.
     * @return The list of retrieved aggregations.
     */
    public Set<Aggregazione> readAggregation(Sviluppatore d) {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Set<Aggregazione> aggregazioni = new HashSet<>();

        Query query = this.session.createQuery("select a from Aggregazione a "
                + "inner join a.autore d where d.id=" + d.getId());
        List<Aggregazione> list = query.list();
        java.util.Iterator<Aggregazione> i = list.iterator();
        while (i.hasNext()) {
            aggregazioni.add(i.next());
        }

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return aggregazioni;
    }

    /**
     * Update the rank of a developer with respect to another ones.
     *
     * @param id ID of the developer whose rank must be updated.
     * @param idrispettoA ID of the reference developer.
     * @param dr The new value of the rank.
     */
    public void updateDRrelativo(Long id, Long idrispettoA, double dr) {
        Sviluppatore dra = readDeveloper(idrispettoA);
        Sviluppatore d = new Sviluppatore();

        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Query query = this.session.createQuery("from Sviluppatore WHERE id=" + id);
        List<Sviluppatore> list = query.list();
        java.util.Iterator<Sviluppatore> iter = list.iterator();
        while (iter.hasNext()) {
            d = iter.next();
            break;
        }

        CoppiaDR cdr = null;

        Set<CoppiaDR> scdr = d.getElencoDR();
        java.util.Iterator<CoppiaDR> j = scdr.iterator();
        while (j.hasNext()) {
            CoppiaDR temp = j.next();
            if (temp.getRispettoA().getId() == idrispettoA) {
                cdr = temp;
                cdr.setDr(dr);
                break;
            }
        }

        if (cdr == null) {
            cdr = new CoppiaDR();
            cdr.setDr(dr);
            cdr.setRispettoA(dra);

            d.aggiungiDR(cdr);
        }

        this.session.save(cdr);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }
    }
    
   // /*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/
    
        public ArrayList<Tag> readTags() {
        boolean outerTransaction = true;

        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        List<Tag> elenco = this.session.createQuery("select ds from Tag ds").list();
        Set<Tag> ds = new HashSet<>(elenco);
        
 

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }

        return new ArrayList<>(ds);
    }

    /**
    * Add a data service to an aggregation
    *
    * @param id ID of the aggregation.
    * @param s data service
    * @return TRUE  if the data service is added, otherwise FALSE        
    */

    public boolean addDsToAggregation(Long id, DataService s) {
        boolean outerTransaction = true;
        boolean toReturn;
        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Aggregazione a = (Aggregazione) this.session.get(Aggregazione.class, id);

        toReturn = a.addDS(s);    

        //incremento utilizzi data service
        updateDataService(s.getId(), 1);

        this.session.save(a);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }
        return toReturn;
    }
        
        
     /**
    * remove a data service to an aggregation
    *
    * @param id ID of the aggregation.
    * @param s data service
    * @return TRUE  if the data service is added, otherwise FALSE        
    */

    public boolean removeDsToAggregation(Long id, DataService s) {
        boolean outerTransaction = true;
        boolean toReturn;
        // if the transaction is already active, this means that has been 
        // opened by the invoking method
        if (!this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
            outerTransaction = false;
        }

        Aggregazione a = (Aggregazione) this.session.get(Aggregazione.class, id);

        toReturn = a.removeDS(s);    

        //incremento utilizzi data service
        updateDataService(s.getId(), 1);

        this.session.save(a);

        // if the transaction has been opened by the invoking method, 
        // it is that method that is in charge of commit/abort
        if (!outerTransaction) {
            this.session.getTransaction().commit();
        }
        return toReturn;
    }
        
        
        
}


