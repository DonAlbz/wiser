package wiser.middleware.similarity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.relationship.Relationship;
import net.didion.jwnl.data.relationship.RelationshipFinder;
import net.didion.jwnl.data.relationship.RelationshipList;
import net.didion.jwnl.dictionary.Dictionary;
import org.hibernate.Session;
import wiser.dao.DataService;
import wiser.dao.Tag;
import wiser.dao.Aggregazione;
import wiser.dao.DAOclass;
import wiser.dao.Sviluppatore;
import wiser.main.Main;

/**
 * Classe che contiene tutti i metodi per il calcolo di similarity.
 *
 * @author Flavia Venturelli, Devis Bianchini
 * @version 1.0
 */
public class Similarity {

    public static int calcolaDistanzaLevenshtein(String s, String t) {
        int d[][];     // matrice
        int n;         // lunghezza di s
        int m;         // lunghezza di t
        int i;         // iterazioni su s
        int j;         // iterazioni su t
        char s_i;      // i-esimo carattere di s
        char t_j;      // j-esimo carattere di t
        int costo;
        n = s.length(); // n conterr� il num di caratteri di s
        m = t.length(); // m conterr� il num di caratteri di t

        // se la stringa sorgente � vuota
        if (n == 0) {
            // la distanza � il num di chr della dest.
            return m;
        }

        // se la stringa destinazione � vuota
        if (m == 0) {
            // la distanza � il num di chr della sorg.
            return n;
        }

        // creo la matrice (n+1)*(m+1)
        d = new int[n + 1][m + 1];
        // la prima riga della mat. conterr� le distanze da 0 a n
        // la distanza 1 � associata al primo chr della stringa, 0 al vuoto
        // Nel caso del nostro esempio:

        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // esamino ogni carattere di s (i da 1 a n)
        for (i = 1; i <= n; i++) {
            s_i = s.charAt(i - 1);
            // Esamino ogni carattere di t (j da 1 a m)
            for (j = 1; j <= m; j++) {
                t_j = t.charAt(j - 1);
                // Se l'i-esimo elemento di s � uguale al j-esimo elemento di t
                if (s_i == t_j) {
                    // il costo � 0
                    costo = 0;
                } else { // altrimenti il costo � 1
                    costo = 1;
                }

                // imposto la cella d[i][j] scegliendo il valore minimo tra:
                //   - la cella immediatamente superiore + 1
                //   - la cella immediatamente a sinistra + 1
                //   - la cella diagonalmente in alto a sinistra pi� il costo
                d[i][j] = getMinimo(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + costo);
            }

        }

        // una volta completate tutte le iterazioni la cella
        // d[n][m] contiene la distanza finale tra la stringa
        // sorgente e quella di destianzione
        return d[n][m];

    }

    public static float normalizzaDistanza(int lengthDatabaseString, int lengthRequestString, int distance) {
        float distanceNorm = (float) 0;
        if (lengthDatabaseString > lengthRequestString) {
            distanceNorm = (float) (distance / (float) (lengthDatabaseString));
        }

        if (lengthDatabaseString < lengthRequestString) {
            distanceNorm = (float) (distance / (float) (lengthRequestString));
        }

        if (lengthRequestString == lengthDatabaseString) {
            distanceNorm = (float) (distance / (float) (lengthDatabaseString));
        }

        return distanceNorm;
    }

    private static int getMinimo(int a, int b, int c) {
        int min;
        // imposto il minimo uguale ad a
        min = a;
        // se il parametro b � minore del minimo
        if (b < min) {
            // il nuovo minimo sar� il parametro b
            min = b;
        }
        // se il parametro c � minore del minimo
        if (c < min) {
            // il nuovo minimo sar� il parametro c
            min = c;
        }

        return min;
    }

    /**
     * Computes the similarity between an aggregation and the set of services
     * specified as second input parameter.
     *
     * @param agg The target aggregation to compare.
     * @param ds The set of services.
     * @return The computed similarity.
     * @throws FileNotFoundException
     * @throws JWNLException
     */
    public static double aggSim(Aggregazione agg, ArrayList<DataService> ds) throws FileNotFoundException, JWNLException {
        double result;
        List<DataService> g = (ArrayList<DataService>) agg.getElencoDS();
        List<DataService> gr = new ArrayList<>(ds);

        List<DataService> q;
        List<DataService> p;

        if (gr.size() <= g.size()) {
            q = gr;
            p = g;
        } else {
            q = g;
            p = gr;
        }

        double[][] matrix = new double[q.size()][p.size()];
        for (int i = 0; i < q.size(); i++) {
            DataService qi = q.get(i);
            for (int j = 0; j < p.size(); j++) {
                DataService pj = p.get(j);
                List<Tag> lt = new ArrayList<>(pj.getTag());
                matrix[i][j] = Similarity.simTag(qi, lt);
            }
        }

        double sum = HungarianAlgorithm.hgAlgorithm(matrix, "max");
        result = (2.0 * sum) / (double) (q.size() + p.size());

        return result;
    }

    /**
     * Computes the tag-based similarity between a data service and the set of
     * tags specified as second input parameter.
     *
     * @param service The target data service to compare.
     * @param requisiti The set of tags.
     * @return The computed value.
     * @throws FileNotFoundException
     * @throws JWNLException
     */
    public static double simTag(DataService service, List<Tag> requisiti) throws FileNotFoundException, JWNLException {
        double result;
        List<Tag> t = (ArrayList<Tag>) service.getTag();

        List<Tag> q;
        List<Tag> p;

        if (requisiti.size() <= t.size()) {
            q = requisiti;
            p = t;
        } else {
            q = t;
            p = requisiti;
        }
        int distance;

        double[][] matrix = new double[q.size()][p.size()];

        for (int i = 0; i < q.size(); i++) {
            // if tag has not been disambiguated with WordNet, Levenshtein distance is used
            if (q.get(i).getPos().equals("ND")) {
                for (int j = 0; j < p.size(); j++) {
                    distance = Similarity.calcolaDistanzaLevenshtein(q.get(i).getNome().toLowerCase(), p.get(j).getNome().toLowerCase());
                    matrix[i][j] = (double) ((double) (1) - (double) Similarity.normalizzaDistanza(q.get(i).getNome().length(), p.get(j).getNome().length(), distance));//qui ci metto Levenstain 
                }
            } else // if tag has been disambiguated with WordNet ..
            {
                for (int j = 0; j < p.size(); j++) {
                    if (p.get(j).getPos().equals("ND")) // Levenshtein distance is used, because the other tag has not been disambiguated
                    {
                        distance = Similarity.calcolaDistanzaLevenshtein(q.get(i).getNome().toLowerCase(), p.get(j).getNome().toLowerCase());
                        matrix[i][j] = (double) ((double) (1) - (double) Similarity.normalizzaDistanza(q.get(i).getNome().length(), p.get(j).getNome().length(), distance));
                    } else // all tags have been disambiguated
                    {
                        matrix[i][j] = Similarity.tagAffinity(q.get(i), p.get(j), Main.dizionario);
                    }
                }
            }

        }

        double sum = HungarianAlgorithm.hgAlgorithm(matrix, "max");
        result = (2.0 * sum) / (double) (q.size() + p.size());

        return result;
    }

    /**
     * Compute the overall similarity between a data service specified as input
     * parameter and the request.
     *
     * @param service The target data service.
     * @param aggCorr Data services already inserted in the aggregation under
     * development, to be used together with the target service.
     * @param requisiti Tags specified in the request.
     * @param s The session opened on the database.
     * @return The computed service similarity.
     * @throws FileNotFoundException
     * @throws JWNLException
     */
    public static double calcolaSim(DataService service, ArrayList<DataService> aggCorr, List<Tag> requisiti, Session s) throws FileNotFoundException, JWNLException {
        double omegas = 0.50;

        double st = Similarity.simTag(service, requisiti);
        double sa = Similarity.simAgg(service, aggCorr, s);

        double sim = omegas * st + (1 - omegas) * sa;
        return sim;
    }

    /**
     * Computes the similarity between data services specified as input
     * parameter and all the aggregations stored within the data layer
     * associated to a given data service.
     *
     * @param service The specified data service, whose aggregations have to be
     * considered.
     * @param aggCorr The set of data services to be considered for the
     * comparison.
     * @param s The session opened on the database.
     * @return The computed value.
     * @throws FileNotFoundException
     * @throws JWNLException
     */
    private static double simAgg(DataService service, ArrayList<DataService> aggCorr, Session s) throws FileNotFoundException, JWNLException {
        double sa = 0;

        ArrayList<Aggregazione> aggregazioni = (new DAOclass(s)).readAggregation(service);

        for (Aggregazione a : aggregazioni) {
            double as = Similarity.aggSim(a, aggCorr);
            sa += as;
        }

        sa /= aggregazioni.size();

        return sa;
    }

    /**
     * Computes term affinity between two tags according to the WordNet thesaurus.
     *
     * @param t1 The first compared tag.
     * @param t2 The second compared tag.
     * @param wordnet The thesaurus adopted for the comparison.
     * @return The computed value.
     * @throws JWNLException
     * @throws FileNotFoundException
     * @throws IndexOutOfBoundsException
     */
    public static double tagAffinity(Tag t1, Tag t2, Dictionary wordnet) throws JWNLException, FileNotFoundException, IndexOutOfBoundsException {
        double ta;
        int diffLivelli;

        if (Objects.equals(t1.getOffset(), t2.getOffset()) && t1.getPos().equals(t2.getPos())) {
            ta = 1.0;
        } else {
            diffLivelli = (Integer) Similarity.trovaRelazione(t1, t2, wordnet);
            if (diffLivelli != 0) {
                ta = Math.pow((float) 0.8, diffLivelli);
            } else // if diffLivelli is equal to zero, then two terms do not have any relationship in the thesaurus
            {
                ta = 0;
            }
        }

        return ta;
    }

    /**
     * Search for the number of hypernymy/hyponymy relationships that relate two tags within a terminological taxonomy.
     *
     * @param t1 The first considered tag.
     * @param t2 The second considered tag.
     * @param wordnetpass The taxonomy adopted for the comparison.
     * @return The number of levels.
     * @throws JWNLException
     * @throws FileNotFoundException
     * @throws IndexOutOfBoundsException
     */
    public static int trovaRelazione(Tag t1, Tag t2, Dictionary wordnetpass) throws JWNLException, FileNotFoundException, IndexOutOfBoundsException {
        int num = 0;
        Dictionary wordnet = wordnetpass;

        if (wordnet != null) {
            // retrieving the parts of speech
            POS p1 = Similarity.string2pos(t1.getPos());
            POS p2 = Similarity.string2pos(t2.getPos());

            // returning synsets
            Synset s1 = wordnet.getSynsetAt(p1, t1.getOffset());
            Synset s2 = wordnet.getSynsetAt(p2, t2.getOffset());

            // methods to compute the distance
            PointerType tipo;
            // the type of pointer depends on the type of synset
            tipo = PointerType.HYPERNYM;
            if (p1 == POS.ADJECTIVE && p2 == POS.ADJECTIVE) {
                tipo = PointerType.SIMILAR_TO;
            }

            try {
                RelationshipList list = RelationshipFinder.getInstance().findRelationships(s1, s2, tipo);

                Relationship rel = list.getShallowest();
                num = rel.getDepth();
            } catch (java.lang.IndexOutOfBoundsException ex) {
                num = 0;
            }
        }

        return num;
    }

    /**
     * Convert a string to the corresponding part of speech.
     *
     * @return ADVERB, NOUN, VERB, ADJECTIVE
     */
    public static POS string2pos(String pos) {
        POS p = null;
        switch (pos) {
            case "N":
                p = POS.NOUN;
                break;
            case "V":
                p = POS.VERB;
                break;
            case "A":
                p = POS.ADJECTIVE;
                break;
            case "R":
                p = POS.ADVERB;
                break;
        }

        return p;
    }

    /**
     * Computes the value that summarizes the experience of all the developers
     * ho used a data service specified as input parameter.
     *
     * @param requester The developer who required the computation.
     * @param service The service with respect to which developers experience
     * have to be computed.
     * @param s The session opened on the database.
     * @return The computed value.
     */
    public static double calcolaRho(Sviluppatore requester, DataService service, Session s) {
        ArrayList<Sviluppatore> vd = service.trovaUtilizzatori(s);
        double drm = Similarity.calcolaDRmedio(service, vd, requester, s);
        double rho;

        double n = service.getNumeroUtilizzi() * service.mediaVoti();
        double nprimo = n / (new DAOclass(s)).readDataServiceMax();

        if (drm >= nprimo) {
            rho = n * drm / (0.58 * n + (1 - 0.58) * drm);
        } else {
            rho = n * drm / (0.58 * drm + (1 - 0.58) * n);
        }

        return rho;
    }

    /**
     * Computes the average rank of developers who used the data service
     * specified as input parameter.
     *
     * @param service The considered data service.
     * @param vd The list of developers who used the service.
     * @param requester The developer who issued the request, with respect to
     * this developer the relative component of the rank is computed.
     * @param s The session opened on the database.
     * @return The computed value.
     */
    public static double calcolaDRmedio(DataService service, ArrayList<Sviluppatore> vd, Sviluppatore requester, Session s) {
        double drm = 0;

        for (Sviluppatore vd1 : vd) {
            s.update(vd1);
            Sviluppatore d = vd1;
            int n = d.findUtilizzi(service, s);
            double dru = d.findDR(requester);
            drm += dru * n;
        }

        return drm /= service.getNumeroUtilizzi();
    }

    /**
     * Computes the rank of a developer with respect to another one, both
     * specified as input parameters.
     *
     * @param developer The first developer, whose rank has to be computed.
     * @param rispA The other developer, with respect to whom the rank must be
     * computed.
     * @param s The session opened on the database.
     * @return The computed rank.
     */
    public static double calcolaDR(Sviluppatore developer, Sviluppatore rispA, Session s) {
        double dr;

        if (!(developer.getAmbiente().getStruttura().equals("pb")) && !(rispA.getAmbiente().getStruttura().equals("pb"))) {
            dr = Similarity.calcolaDRconCondizioni(developer, rispA, s);
        } else {
            dr = Similarity.calcolaDRconFormula(developer, rispA, s);
        }

        return dr;

    }

    /**
     * Computes the rank of a developer (by applying some conditions) with
     * respect to another one, both specified as input parameters.
     *
     * @param developer The first developer, whose rank has to be computed.
     * @param rispA The other developer, with respect to whom the rank must be
     * computed.
     * @param s The session opened on the database.
     * @return The computed rank.
     */
    public static double calcolaDRconCondizioni(Sviluppatore developer, Sviluppatore rispA, Session s) {
        double dr = 0.00;
        ArrayList<Sviluppatore> antenati = new ArrayList<>();
        antenati = rispA.trovaAntentati(antenati);

        int presente = MyUtil.cercaElementoD(antenati, developer.getId());
        if (presente != -1) {
            if (developer.getAmbiente().getStruttura().equals("gs") && rispA.getAmbiente().getStruttura().equals("gs")) {
                ArrayList<Sviluppatore> elenco = MyUtil.estraiNonNull(antenati);
                int pos = MyUtil.cercaElementoD(elenco, developer.getId());
                dr = (elenco.size() - pos) / elenco.size();
            }

            if (developer.getAmbiente().getStruttura().equals("gms") && rispA.getAmbiente().getStruttura().equals("gms")) {
                ArrayList<Sviluppatore> pari;
                pari = new ArrayList<>();
                int lastnull = -1;

                for (int i = 0; i < antenati.size(); i++) {
                    Sviluppatore dev = antenati.get(i);

                    if (dev != null) {
                        pari.add(dev);
                    } else {
                        if (pari.size() != 1) {
                            ArrayList<Double> drs = new ArrayList<>();
                            for (Sviluppatore pari1 : pari) {
                                drs.add(Similarity.calcolaDRconFormula(pari1, rispA, s));
                            }

                            antenati = MyUtil.ordina(lastnull, drs, antenati);
                        }
                        pari = new ArrayList<>();
                        lastnull = i;

                    }
                }

                ArrayList<Sviluppatore> elenco = MyUtil.estraiNonNull(antenati);
                double pos = (double) MyUtil.cercaElementoD(elenco, developer.getId());
                dr = (elenco.size() - pos) / elenco.size();
            }
        } else {
            dr = Similarity.calcolaDRconFormula(developer, rispA, s);
        }

        return dr;
    }

    /**
     * Computes the rank of a developer (by applying a formula) with respect to
     * another one, both specified as input parameters.
     *
     * @param developer The first developer, whose rank has to be computed.
     * @param rispA The other developer, with respect to whom the rank must be
     * computed.
     * @param s The session opened on the database.
     * @return The computed rank.
     */
    public static double calcolaDRconFormula(Sviluppatore developer, Sviluppatore rispA, Session s) {
        double dr;
        Set<Sviluppatore> elenco = (new DAOclass(s)).readDevelopers();
        ArrayList<Sviluppatore> dev = new ArrayList<>(elenco);

        double[][] matrix = new double[dev.size()][dev.size()];
        double[] terminiNoti = new double[dev.size()];

        for (int ir = 0; ir < dev.size(); ir++) {
            Sviluppatore d = dev.get(ir);

            double alpha;

            if (d.getAmbiente().getAmpiezza().equals("gp")) {
                alpha = 0.10;
            } else {
                if (d.getAmbiente().getMaturita().equals("e")) {
                    alpha = 0.70;
                } else {
                    alpha = 0.80;
                }
            }

            // niche that is not among the niches of the considered developer are penalised
            int dd;
            if (d.getAmbiente().getAmpiezza().equals("gp") || !(rispA.getAmbiente().getAmpiezza().equals(d.getAmbiente().getAmpiezza()))) {
                dd = (new DAOclass(s)).readDevelopers().size();
            } else {
                dd = (new DAOclass(s)).readDevelopers_amp(d.getAmbiente()).size();
            }

            int w = (new DAOclass(s)).readAggregation(d).size();

            terminiNoti[ir] = (1 - alpha) / dd * w;

            for (int ic = 0; ic < dev.size(); ic++) {
                if (ic == ir) {
                    matrix[ir][ic] = 1.00;
                } else {
                    matrix[ir][ic] = 0.00;
                }
            }

            Set<Sviluppatore> followers = (new DAOclass(s)).readFollowers(d);

            java.util.Iterator<Sviluppatore> iter = followers.iterator();

            double wi;

            while (iter.hasNext()) {
                Sviluppatore f = iter.next();
                int pos = MyUtil.cercaElementoD(dev, f.getId());

                if (!(f.getAmbiente().getContesto().equals("e"))) {
                    wi = -alpha / f.getFollowed().size() * f.getCredibilita();
                } else {
                    wi = -alpha / f.getFollowed().size() * 1;
                }

                matrix[ir][pos] = wi;
            }

        }

        MyUtil.meg(matrix, terminiNoti);
        MyUtil.estraiTriangoloSuperiore(matrix);
        double[] ris = MyUtil.backwardSubstitution(matrix, terminiNoti);
        int pos = MyUtil.cercaElementoD(dev, developer.getId());
        dr = ris[pos];

        return dr;
    }

    /**
     * Update credibility of a developer after assigning a rating.
     *
     * @param developer The developer whose credibility must be updated.
     * @param s The session opened on the database.
     * @param diff The difference between assigned rating and the majority of
     * ratings assigned by others.
     */
    public static void aggiornaCredibilita(Sviluppatore developer, Session s, double diff) {
        int totvoti = (new DAOclass(s)).readVoti(developer.getId()).size();
        double credibilita = developer.getCredibilita();
        developer.setCredibilita((credibilita * totvoti + (1 - diff)) / (totvoti + 1));
    }
}
