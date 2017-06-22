package wiser.main;

/**
 * Classe principale per la gestione dei casi d'uso.
 *
 * @author Flavia Venturelli
 * @version 1.0
 */
import wiser.search.Risposta;
import wiser.search.Richiesta;
import wiser.middleware.similarity.MyUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.dictionary.Dictionary;
import net.didion.jwnl.data.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import wiser.dao.Aggregazione;
import wiser.dao.DAOclass;
import wiser.dao.DataService;
import wiser.dao.Sviluppatore;
import wiser.dao.Tag;
import wiser.middleware.similarity.Similarity;

public class Main {

    private static final String BENVENUTO = "Benvenuto nell'applicazione DS2R, cosa vuoi fare? ";
    private static final String ARRIVEDERCI = "Grazie per aver usato l'applicazione DS2R.";

    private static final String[] VOCI_1 = {"Accedi", "Registrati", "Esci"};

    private static final String[] VOCI_2 = {"Pubblica Data Service", "Pubblica Aggregazione",
        "Cerca Data Service", "Follow", "Logout"};

    private static final String USER = "Username: ";
    private static final String PWD = "Password: ";

    private static final String NOME_DS = "Inserire il nome del data service da pubblicare: ";
    private static final String DESCR_DS = "Inserire una breve descrizione del data service: ";
    private static final String INPUT_DS = "Inserire il tipo dei parametri di ingresso (separati da un trattino -): ";
    private static final String OUTPUT_DS = "Inserire il tipo dei parametri di uscita (separati da un trattino -): ";
    private static final String TAG_DS = "Inserire i tag che descrivono il servizio (separati da /): ";

    private static final String NOME_AGG = "Inserire il nome dell'aggregazione da pubblicare: ";
    private static final String DESCR_AGG = "Inserire una breve descrizione dell'aggregazione: ";
    private static final String DS_AGG = "Inserire gli id dei data service che compongono l'aggregazione (separati da uno spazio): ";
    private static final String VALUT_DS = "E' necessario valutare i data service utilizzati in questa applicazione [1-100]";

    private static final String NOME_DEV = "Inserire il proprio nome: ";
    private static final String PWD_DEV = "Inserire una password: ";
    private static final String AMB_DEV = "Descrivere il proprio ambiente di lavoro: ";
    private static final String AMB_DEV_S = " - struttura (gs-gsm-pb): ";
    private static final String AMB_DEV_C = " - contesto (o-e): ";
    private static final String AMB_DEV_A = " - ampiezza (gp-xx): ";
    private static final String AMB_DEV_M = " - maturita' (e-r): ";
    private static final String ID_DEV = "Memorizzare il proprio id per i futuri accessi: ";

    private static final double SIM_THRESHOLD = 0.45;

    private static final String FOLLOW = "Premere A per aggiungere una relazione, R per rimuovere: ";
    private static final String NEW_FOLLOW = "Inserire l'id dello sviluppatore che si intende seguire: ";
    private static final String OLD_FOLLOW = "Inserire l'id dello sviluppatore che si intende smettere di seguire: ";

    private static final String OP_OK = "Operazione completata!";
    private static final String SUCCESS = "Accesso effettuato correttamente.";
    private static final String FAIL = "Accesso fallito.";

    static SessionFactory sf;
    public static Dictionary dizionario;

    public static void main(String[] args) throws JWNLException, FileNotFoundException, NoSuchAlgorithmException {
        dizionario = caricaDizionario();

        Configuration configuration = new Configuration();
        configuration.configure();
        sf = configuration.buildSessionFactory();

        try (Session session = sf.openSession() // inizializza();
                ) {
            /*
            
             RUNNING EXAMPLE
            
            
             Vector<Sviluppatore> dd = new Vector<Sviluppatore> (DAOclass.readDevelopers(session));
             Sviluppatore requester = DAOclass.readDeveloper(session, (long) 3);
            
             List<Tag> requisiti = new ArrayList<Tag>();
             requisiti.add(DAOclass.readTag(session, (long) 12));
             requisiti.add(DAOclass.readTag(session, (long) 11));
             requisiti.add(DAOclass.readTag(session, (long) 10 ));
            
             Vector<DataService> aggCorr = new Vector<DataService>();
             DataService ds = DAOclass.readDataService(session, (long)1); aggCorr.add(ds);
             ds = DAOclass.readDataService(session, (long)2); aggCorr.add(ds);
            
             Richiesta richiesta = new Richiesta(requester, aggCorr, requisiti);
             Vector<Risposta> vr = ordinaDataServices(richiesta,session);
            
             for(int i=0; i<vr.size();i++)
             {
             System.out.println(i+1+") "+vr.get(i).getDs().getNome()+": "+vr.get(i).getRank());
             }
             */
            System.out.println();
            System.out.println(BENVENUTO);

            long idU = (long) -1;

            int scelta_1 = 1;
            while (scelta_1 != 0) {
                scelta_1 = stampaMenu(VOCI_1);

                switch (scelta_1) {
                    case 1:
                        idU = accedi(session);
                        break;
                    case 2:
                        idU = registrati(session);
                        System.out.println(SUCCESS);
                        break;
                    case 0:
                        idU = -1;
                        break;
                }

                int scelta_2 = 1;

                while (idU != -1 && scelta_2 != 0) {
                    scelta_2 = stampaMenu(VOCI_2);

                    switch (scelta_2) {
                        case 1:
                            pubblicaDS(session);
                            break;

                        case 2:
                            pubblicaAggregazione(idU);
                            break;

                        case 3:
                            String dsInseriti = MyUtil.leggiStringa("ID data service gia' inseriti (es:11-4-5): ");
                            String keywords = MyUtil.leggiStringa("Parole chiave (es: product/age/geography): ");

                            Richiesta request = creaRichiesta(idU, dsInseriti, keywords);
                            ordinaDataServices(request);

                            break;

                        case 4:
                            modificaRelazioni(session, idU);
                            aggiornaDR();
                            break;
                    }
                }
            }
        }
        System.out.println(ARRIVEDERCI);

    }

    private static Dictionary caricaDizionario() throws JWNLException, FileNotFoundException {
        Dictionary wordnet;
        JWNL.initialize(new FileInputStream("conf/file_properties.xml"));
        wordnet = Dictionary.getInstance();

        return wordnet;
    }

    public static void inizializza() throws JWNLException, FileNotFoundException, NoSuchAlgorithmException {
        try (Session session = sf.openSession()) {
            DAOclass dao = new DAOclass(session);

            dao.createTag("product", "N", (long) 3707459);
            dao.createTag("age", "N", (long) 4868793);
            dao.createTag("distribution", "N", (long) 5658678);
            dao.createTag("gender", "N", (long) 4948193);
            dao.createTag("zip code", "N", (long) 6270450);
            dao.createTag("latitude", "N", (long) 8475515);
            dao.createTag("longitude", "N", (long) 8476652);
            dao.createTag("male", "N", (long) 9487097);
            dao.createTag("female", "N", (long) 9482706);
            dao.createTag("demography", "N", (long) 6071164);
            dao.createTag("sex", "N", (long) 4948193);
            dao.createTag("postal code", "N", (long) 6270450);

            dao.createTag("seller", "N", (long) 10418778);
            dao.createTag("family", "N", (long) 7862221);
            dao.createTag("animal", "N", (long) 15024);
            dao.createTag("temperature", "N", (long) 5655074);
            dao.createTag("weather", "N", (long) 11358426);
            dao.createTag("climate", "N", (long) 14326654);
            dao.createTag("fare", "N", (long) 13136968);
            dao.createTag("express", "N", (long) 1091744);
            dao.createTag("human ecology", "N", (long) 6071164);
            dao.createTag("city", "N", (long) 8406385);
            dao.createTag("sport", "N", (long) 518318);
            dao.createTag("fitness", "N", (long) 14353750);
            dao.createTag("calories", "N", (long) 13545750);
            dao.createTag("birth", "N", (long) 14943296);
            dao.createTag("period", "N", (long) 14914858);
            dao.createTag("death", "N", (long) 7254694);
            dao.createTag("mark", "N", (long) 5666505);
            dao.createTag("statistics", "N", (long) 5940360);
            dao.createTag("university", "N", (long) 8172379);
            dao.createTag("zip", "N", (long) 6270450);
            dao.createTag("sale", "N", (long) 1102627);
            dao.createTag("job", "N", (long) 576963);
            dao.createTag("world", "N", (long) 2450463);
            dao.createTag("population", "N", (long) 13598844);
            dao.createTag("postcode", "N", (long) 6270450);
            dao.createTag("statistical distribution", "N", (long) 5658678);

            Tag t1 = dao.readTag((long) 1);
            Tag t2 = dao.readTag((long) 2);
            Tag t3 = dao.readTag((long) 3);

            Set<Tag> elencoTag1 = new HashSet<>();
            elencoTag1.add(t1);
            elencoTag1.add(t2);
            elencoTag1.add(t3);
            dao.createDataService("s1", "Provides the distribution by age of consumer who bought a given product", elencoTag1, "int-int", "boolean");

            t1 = dao.readTag((long) 1);
            t2 = dao.readTag((long) 3);
            t3 = dao.readTag((long) 4);

            Set<Tag> elencoTag2 = new HashSet<>();
            elencoTag2.add(t1);
            elencoTag2.add(t2);
            elencoTag2.add(t3);
            dao.createDataService("s2", "Provides the distribution by gender of consumer who bought the product", elencoTag2, "char-string", "void");

            t1 = dao.readTag((long) 2);
            t2 = dao.readTag((long) 5);
            t3 = dao.readTag((long) 10);

            Set<Tag> elencoTag3 = new HashSet<>();
            elencoTag3.add(t1);
            elencoTag3.add(t2);
            elencoTag3.add(t3);
            dao.createDataService("s3", "Provides the median age for all people per zip code", elencoTag3, "vector<int>", "double");

            t1 = dao.readTag((long) 2);
            t2 = dao.readTag((long) 10);
            t3 = dao.readTag((long) 6);
            Tag t4 = dao.readTag((long) 7);
            Tag t5 = dao.readTag((long) 8);
            Tag t6 = dao.readTag((long) 9);

            Set<Tag> elencoTag4 = new HashSet<>();
            elencoTag4.add(t1);
            elencoTag4.add(t2);
            elencoTag4.add(t3);
            elencoTag4.add(t4);
            elencoTag4.add(t5);
            elencoTag4.add(t6);
            dao.createDataService("s4", "Provides the median age of male and female people fiven the latitude and longitude values", elencoTag4, "double", "string");

            t1 = dao.readTag((long) 1);
            t2 = dao.readTag((long) 5);
            t3 = dao.readTag((long) 3);

            Set<Tag> elencoTag5 = new HashSet<>();
            elencoTag5.add(t1);
            elencoTag5.add(t2);
            elencoTag5.add(t3);
            dao.createDataService("s5", "Provides the distribution by area (zip code) of consumer who bought the product", elencoTag5, "", "");

            t1 = dao.readTag((long) 1);
            t2 = dao.readTag((long) 3);
            t3 = dao.readTag((long) 13);

            Set<Tag> elencoTag6 = new HashSet<>();
            elencoTag6.add(t1);
            elencoTag6.add(t2);
            elencoTag6.add(t3);
            dao.createDataService("s6", "Provides the distribution of sellers of the product", elencoTag6, "", "");

            t1 = dao.readTag((long) 2);
            t2 = dao.readTag((long) 14);
            t3 = dao.readTag((long) 10);

            Set<Tag> elencoTag7 = new HashSet<>();
            elencoTag7.add(t1);
            elencoTag7.add(t2);
            elencoTag7.add(t3);
            dao.createDataService("s7", "Provides the average age of families that bought the product", elencoTag7, "", "");

            t1 = dao.readTag((long) 15);
            t2 = dao.readTag((long) 6);
            t3 = dao.readTag((long) 7);
            t4 = dao.readTag((long) 16);

            Set<Tag> elencoTag8 = new HashSet<>();
            elencoTag8.add(t1);
            elencoTag8.add(t2);
            elencoTag8.add(t3);
            elencoTag8.add(t4);
            dao.createDataService("s8", "Provides the list of animals living at the latitude, longitude and temperature specified", elencoTag8, "", "");

            t1 = dao.readTag((long) 17);
            t2 = dao.readTag((long) 18);
            t3 = dao.readTag((long) 6);
            t4 = dao.readTag((long) 7);

            Set<Tag> elencoTag9 = new HashSet<>();
            elencoTag9.add(t1);
            elencoTag9.add(t2);
            elencoTag9.add(t3);
            elencoTag9.add(t4);
            dao.createDataService("s9", "Provides the weather information of the place specified by its latitude and longitude", elencoTag9, "", "");

            t1 = dao.readTag((long) 12);
            t2 = dao.readTag((long) 19);
            t3 = dao.readTag((long) 20);

            Set<Tag> elencoTag10 = new HashSet<>();
            elencoTag10.add(t1);
            elencoTag10.add(t2);
            elencoTag10.add(t3);
            dao.createDataService("s10", "Provides the express fares for the postal code specified", elencoTag10, "", "");

            t1 = dao.readTag((long) 21);
            t2 = dao.readTag((long) 4);
            t3 = dao.readTag((long) 22);

            Set<Tag> elencoTag11 = new HashSet<>();
            elencoTag11.add(t1);
            elencoTag11.add(t2);
            elencoTag11.add(t3);
            dao.createDataService("s11", "Provides the distribution by sex of the population living in the city specified", elencoTag11, "", "");

            t1 = dao.readTag((long) 21);
            t2 = dao.readTag((long) 23);
            t3 = dao.readTag((long) 2);

            Set<Tag> elencoTag12 = new HashSet<>();
            elencoTag12.add(t1);
            elencoTag12.add(t2);
            elencoTag12.add(t3);
            dao.createDataService("s12", "Provides the average age of people who practise the specified sport", elencoTag12, "", "");

            t1 = dao.readTag((long) 23);
            t2 = dao.readTag((long) 24);
            t3 = dao.readTag((long) 25);

            Set<Tag> elencoTag13 = new HashSet<>();
            elencoTag13.add(t1);
            elencoTag13.add(t2);
            elencoTag13.add(t3);
            dao.createDataService("s13", "Provides the average number of calories burnt with the specified sport", elencoTag13, "", "");

            t1 = dao.readTag((long) 3);
            t2 = dao.readTag((long) 21);
            t3 = dao.readTag((long) 35);
            t4 = dao.readTag((long) 36);

            Set<Tag> elencoTag14 = new HashSet<>();
            elencoTag14.add(t1);
            elencoTag14.add(t2);
            elencoTag14.add(t3);
            elencoTag14.add(t4);
            dao.createDataService("s14", "Provides the distribution of the world's population", elencoTag14, "", "");

            t1 = dao.readTag((long) 10);
            t2 = dao.readTag((long) 26);
            t3 = dao.readTag((long) 32);
            t4 = dao.readTag((long) 27);

            Set<Tag> elencoTag15 = new HashSet<>();
            elencoTag15.add(t1);
            elencoTag15.add(t2);
            elencoTag15.add(t3);
            elencoTag15.add(t4);
            dao.createDataService("s15", "Provide the numbers of births of the city in the period specified", elencoTag15, "", "");

            t1 = dao.readTag((long) 10);
            t2 = dao.readTag((long) 28);
            t3 = dao.readTag((long) 37);
            t4 = dao.readTag((long) 27);

            Set<Tag> elencoTag16 = new HashSet<>();
            elencoTag16.add(t1);
            elencoTag16.add(t2);
            elencoTag16.add(t3);
            elencoTag16.add(t4);
            dao.createDataService("s16", "Provide the numbers of deaths of the city in the period specified", elencoTag16, "", "");

            t1 = dao.readTag((long) 3);
            t2 = dao.readTag((long) 11);
            t3 = dao.readTag((long) 26);
            t4 = dao.readTag((long) 12);

            Set<Tag> elencoTag17 = new HashSet<>();
            elencoTag17.add(t1);
            elencoTag17.add(t2);
            elencoTag17.add(t3);
            elencoTag17.add(t4);
            dao.createDataService("s17", "Provides the distribution by sex of newborn in the city specified", elencoTag17, "", "");

            t1 = dao.readTag((long) 29);
            t2 = dao.readTag((long) 30);
            t3 = dao.readTag((long) 31);
            t4 = dao.readTag((long) 32);

            Set<Tag> elencoTag18 = new HashSet<>();
            elencoTag18.add(t1);
            elencoTag18.add(t2);
            elencoTag18.add(t3);
            elencoTag18.add(t4);
            dao.createDataService("s18", "Provides the statistic of marks in the university specified", elencoTag18, "", "");

            t1 = dao.readTag((long) 30);
            t2 = dao.readTag((long) 33);
            t3 = dao.readTag((long) 22);
            t4 = dao.readTag((long) 1);

            Set<Tag> elencoTag19 = new HashSet<>();
            elencoTag19.add(t1);
            elencoTag19.add(t2);
            elencoTag19.add(t3);
            elencoTag19.add(t4);
            dao.createDataService("s19", "Provides the statistic of the product's sales in the city specified", elencoTag19, "", "");

            t1 = dao.readTag((long) 11);
            t2 = dao.readTag((long) 38);
            t3 = dao.readTag((long) 33);

            Set<Tag> elencoTag20 = new HashSet<>();
            elencoTag20.add(t1);
            elencoTag20.add(t2);
            elencoTag20.add(t3);
            dao.createDataService("s20", "Provides the distribution of people by sex over job", elencoTag20, "", "");

            double c = 0.5;

            dao.createDeveloper("dev1", new String(MyUtil.digest("dev1")), 1.00, "gms", "e", "n2", "e");
            dao.createDeveloper("dev2", new String(MyUtil.digest("dev2")), 1.00, "gms", "e", "n2", "e");
            dao.createDeveloper("dev3", new String(MyUtil.digest("dev3")), 1.00, "gms", "e", "n1", "r");
            dao.createDeveloper("dev4", new String(MyUtil.digest("dev4")), 1.00, "gms", "e", "n1", "r");
            dao.createDeveloper("dev5", new String(MyUtil.digest("dev5")), 1.00, "gms", "e", "n1", "r");
            dao.createDeveloper("dev6", new String(MyUtil.digest("dev6")), 1.00, "gms", "e", "n1", "r");
            dao.createDeveloper("dev7", new String(MyUtil.digest("dev7")), 1.00, "gms", "e", "gp", "r");
            dao.createDeveloper("dev8", new String(MyUtil.digest("dev8")), 1.00, "gms", "e", "gp", "r");

            dao.createDeveloper("dev9", new String(MyUtil.digest("dev9")), c, "pb", "o", "n3", "r");
            dao.createDeveloper("dev10", new String(MyUtil.digest("dev10")), c, "pb", "o", "gp", "e");
            dao.createDeveloper("dev11", new String(MyUtil.digest("dev11")), c, "pb", "o", "n2", "e");
            dao.createDeveloper("dev12", new String(MyUtil.digest("dev12")), c, "pb", "o", "n3", "r");
            dao.createDeveloper("dev13", new String(MyUtil.digest("dev13")), c, "pb", "o", "n3", "r");
            dao.createDeveloper("dev14", new String(MyUtil.digest("dev14")), c, "pb", "o", "n2", "e");

            dao.updateDeveloperAdd((long) 3, (long) 8);
            dao.updateDeveloperAdd((long) 6, (long) 7);
            dao.updateDeveloperAdd((long) 1, (long) 2);
            dao.updateDeveloperAdd((long) 4, (long) 2);
            dao.updateDeveloperAdd((long) 4, (long) 5);
            dao.updateDeveloperAdd((long) 3, (long) 4);
            dao.updateDeveloperAdd((long) 6, (long) 3);

            dao.updateDeveloperAdd((long) 9, (long) 10);
            dao.updateDeveloperAdd((long) 10, (long) 11);
            dao.updateDeveloperAdd((long) 10, (long) 13);
            dao.updateDeveloperAdd((long) 10, (long) 14);
            dao.updateDeveloperAdd((long) 12, (long) 9);
            dao.updateDeveloperAdd((long) 13, (long) 9);
            dao.updateDeveloperAdd((long) 14, (long) 11);
            dao.updateDeveloperAdd((long) 14, (long) 12);

            Sviluppatore d = dao.readDeveloper((long) 1);
            Set<DataService> elencods1 = new HashSet<>();
            elencods1.add(dao.readDataService((long) 18));
            elencods1.add(dao.readDataService((long) 20));
            dao.createAggregation("g1", "...", d, elencods1);

            d = dao.readDeveloper((long) 2);
            Set<DataService> elencods2 = new HashSet<>();
            elencods2.add(dao.readDataService((long) 8));
            elencods2.add(dao.readDataService((long) 9));
            dao.createAggregation("g2", "...", d, elencods2);

            d = dao.readDeveloper((long) 3);
            Set<DataService> elencods3 = new HashSet<>();
            elencods3.add(dao.readDataService((long) 1));
            elencods3.add(dao.readDataService((long) 10));
            elencods3.add(dao.readDataService((long) 2));
            dao.createAggregation("g3", "...", d, elencods3);

            d = dao.readDeveloper((long) 4);
            Set<DataService> elencods4 = new HashSet<>();
            elencods4.add(dao.readDataService((long) 1));
            elencods4.add(dao.readDataService((long) 2));
            elencods4.add(dao.readDataService((long) 6));
            dao.createAggregation("g4", "...", d, elencods4);

            d = dao.readDeveloper((long) 5);
            Set<DataService> elencods5 = new HashSet<>();
            elencods5.add(dao.readDataService((long) 12));
            elencods5.add(dao.readDataService((long) 13));
            dao.createAggregation("g5", "...", d, elencods5);

            d = dao.readDeveloper((long) 6);
            Set<DataService> elencods6 = new HashSet<>();
            elencods6.add(dao.readDataService((long) 5));
            elencods6.add(dao.readDataService((long) 6));
            dao.createAggregation("g6", "...", d, elencods6);

            d = dao.readDeveloper((long) 7);
            Set<DataService> elencods7 = new HashSet<>();
            elencods7.add(dao.readDataService((long) 14));
            elencods7.add(dao.readDataService((long) 15));
            elencods7.add(dao.readDataService((long) 16));
            dao.createAggregation("g7", "...", d, elencods7);

            d = dao.readDeveloper((long) 8);
            Set<DataService> elencods8 = new HashSet<>();
            elencods8.add(dao.readDataService((long) 14));
            elencods8.add(dao.readDataService((long) 17));
            elencods8.add(dao.readDataService((long) 7));
            dao.createAggregation("g8", "...", d, elencods8);

            d = dao.readDeveloper((long) 9);
            Set<DataService> elencods9 = new HashSet<>();
            elencods9.add(dao.readDataService((long) 1));
            elencods9.add(dao.readDataService((long) 3));
            dao.createAggregation("g9", "...", d, elencods9);

            d = dao.readDeveloper((long) 10);
            Set<DataService> elencods10 = new HashSet<>();
            elencods10.add(dao.readDataService((long) 3));
            elencods10.add(dao.readDataService((long) 12));
            dao.createAggregation("g10", "...", d, elencods10);

            d = dao.readDeveloper((long) 11);
            Set<DataService> elencods11 = new HashSet<>();
            elencods11.add(dao.readDataService((long) 4));
            elencods11.add(dao.readDataService((long) 12));
            dao.createAggregation("g11", "...", d, elencods11);

            d = dao.readDeveloper((long) 10);
            Set<DataService> elencods12 = new HashSet<>();
            elencods12.add(dao.readDataService((long) 11));
            elencods12.add(dao.readDataService((long) 3));
            elencods12.add(dao.readDataService((long) 14));
            dao.createAggregation("g12", "...", d, elencods12);

            d = dao.readDeveloper((long) 13);
            Set<DataService> elencods13 = new HashSet<>();
            elencods13.add(dao.readDataService((long) 9));
            elencods13.add(dao.readDataService((long) 14));
            dao.createAggregation("g13", "...", d, elencods13);

            d = dao.readDeveloper((long) 14);
            Set<DataService> elencods14 = new HashSet<>();
            elencods14.add(dao.readDataService((long) 9));
            elencods14.add(dao.readDataService((long) 19));
            dao.createAggregation("g14", "...", d, elencods14);

            d = dao.readDeveloper((long) 1);
            Set<DataService> elencods15 = new HashSet<>();
            elencods15.add(dao.readDataService((long) 8));
            elencods15.add(dao.readDataService((long) 19));
            dao.createAggregation("g15", "...", d, elencods15);

            d = dao.readDeveloper((long) 2);
            Set<DataService> elencods16 = new HashSet<>();
            elencods16.add(dao.readDataService((long) 14));
            elencods16.add(dao.readDataService((long) 18));
            dao.createAggregation("g16", "...", d, elencods16);

            d = dao.readDeveloper((long) 3);
            Set<DataService> elencods17 = new HashSet<>();
            elencods17.add(dao.readDataService((long) 7));
            elencods17.add(dao.readDataService((long) 12));
            dao.createAggregation("g17", "...", d, elencods17);

            d = dao.readDeveloper((long) 6);
            Set<DataService> elencods18 = new HashSet<>();
            elencods18.add(dao.readDataService((long) 1));
            elencods18.add(dao.readDataService((long) 2));
            elencods18.add(dao.readDataService((long) 6));
            elencods18.add(dao.readDataService((long) 19));
            dao.createAggregation("g18", "...", d, elencods18);

            d = dao.readDeveloper((long) 7);
            Set<DataService> elencods19 = new HashSet<>();
            elencods19.add(dao.readDataService((long) 17));
            elencods19.add(dao.readDataService((long) 20));
            dao.createAggregation("g19", "...", d, elencods19);

            d = dao.readDeveloper((long) 8);
            Set<DataService> elencods20 = new HashSet<>();
            elencods20.add(dao.readDataService((long) 6));
            elencods20.add(dao.readDataService((long) 10));
            elencods20.add(dao.readDataService((long) 19));
            dao.createAggregation("g20", "...", d, elencods20);

            d = dao.readDeveloper((long) 9);
            Set<DataService> elencods21 = new HashSet<>();
            elencods21.add(dao.readDataService((long) 3));
            elencods21.add(dao.readDataService((long) 14));
            elencods21.add(dao.readDataService((long) 15));
            elencods21.add(dao.readDataService((long) 16));
            dao.createAggregation("g21", "...", d, elencods21);

            d = dao.readDeveloper((long) 10);
            Set<DataService> elencods22 = new HashSet<>();
            elencods22.add(dao.readDataService((long) 6));
            elencods22.add(dao.readDataService((long) 13));
            dao.createAggregation("g22", "...", d, elencods22);

            d = dao.readDeveloper((long) 11);
            Set<DataService> elencods23 = new HashSet<>();
            elencods23.add(dao.readDataService((long) 4));
            elencods23.add(dao.readDataService((long) 20));
            dao.createAggregation("g23", "...", d, elencods23);

            d = dao.readDeveloper((long) 4);
            Set<DataService> elencods24 = new HashSet<>();
            elencods24.add(dao.readDataService((long) 11));
            elencods24.add(dao.readDataService((long) 15));
            elencods24.add(dao.readDataService((long) 16));
            dao.createAggregation("g24", "...", d, elencods24);

            d = dao.readDeveloper((long) 12);
            Set<DataService> elencods25 = new HashSet<>();
            elencods25.add(dao.readDataService((long) 1));
            elencods25.add(dao.readDataService((long) 5));
            elencods25.add(dao.readDataService((long) 6));
            dao.createAggregation("g25", "...", d, elencods25);

            d = dao.readDeveloper((long) 14);
            Set<DataService> elencods26 = new HashSet<>();
            elencods26.add(dao.readDataService((long) 5));
            elencods26.add(dao.readDataService((long) 10));
            dao.createAggregation("g26", "...", d, elencods26);

            d = dao.readDeveloper((long) 12);
            Set<DataService> elencods27 = new HashSet<>();
            elencods27.add(dao.readDataService((long) 14));
            elencods27.add(dao.readDataService((long) 20));
            dao.createAggregation("g27", "...", d, elencods27);

            d = dao.readDeveloper((long) 4);
            Set<DataService> elencods28 = new HashSet<>();
            elencods28.add(dao.readDataService((long) 1));
            elencods28.add(dao.readDataService((long) 2));
            dao.createAggregation("g28", "...", d, elencods28);

            d = dao.readDeveloper((long) 5);
            Set<DataService> elencods29 = new HashSet<>();
            elencods29.add(dao.readDataService((long) 2));
            elencods29.add(dao.readDataService((long) 11));
            elencods29.add(dao.readDataService((long) 19));
            dao.createAggregation("g29", "...", d, elencods29);

            d = dao.readDeveloper((long) 1);
            Set<DataService> elencods30 = new HashSet<>();
            elencods30.add(dao.readDataService((long) 3));
            elencods30.add(dao.readDataService((long) 18));
            dao.createAggregation("g30", "...", d, elencods30);

            d = dao.readDeveloper((long) 1);
            Set<DataService> elencods31 = new HashSet<>();
            elencods31.add(dao.readDataService((long) 8));
            elencods31.add(dao.readDataService((long) 9));
            elencods31.add(dao.readDataService((long) 15));
            elencods31.add(dao.readDataService((long) 16));
            dao.createAggregation("g31", "...", d, elencods31);

            d = dao.readDeveloper((long) 11);
            Set<DataService> elencods32 = new HashSet<>();
            elencods32.add(dao.readDataService((long) 15));
            elencods32.add(dao.readDataService((long) 19));
            dao.createAggregation("g32", "...", d, elencods32);

            d = dao.readDeveloper((long) 1);
            Set<DataService> elencods33 = new HashSet<>();
            elencods33.add(dao.readDataService((long) 16));
            elencods33.add(dao.readDataService((long) 19));
            dao.createAggregation("g33", "...", d, elencods33);

            d = dao.readDeveloper((long) 4);
            Set<DataService> elencods34 = new HashSet<>();
            elencods34.add(dao.readDataService((long) 1));
            elencods34.add(dao.readDataService((long) 2));
            elencods34.add(dao.readDataService((long) 5));
            elencods34.add(dao.readDataService((long) 10));
            dao.createAggregation("g34", "...", d, elencods34);

            d = dao.readDeveloper((long) 5);
            Set<DataService> elencods35 = new HashSet<>();
            elencods35.add(dao.readDataService((long) 1));
            elencods35.add(dao.readDataService((long) 2));
            elencods35.add(dao.readDataService((long) 17));
            dao.createAggregation("g35", "...", d, elencods35);

            DataService ds = dao.readDataService((long) 1);
            ds.assegnaVoto(session, 90, dao.readDeveloper((long) 3), dao.readAggregation((long) 3));
            ds.assegnaVoto(session, 87, dao.readDeveloper((long) 4), dao.readAggregation((long) 4));
            ds.assegnaVoto(session, 50, dao.readDeveloper((long) 9), dao.readAggregation((long) 9));
            ds.assegnaVoto(session, 92, dao.readDeveloper((long) 6), dao.readAggregation((long) 18));
            ds.assegnaVoto(session, 53, dao.readDeveloper((long) 12), dao.readAggregation((long) 25));
            ds.assegnaVoto(session, 79, dao.readDeveloper((long) 4), dao.readAggregation((long) 28));
            ds.assegnaVoto(session, 85, dao.readDeveloper((long) 4), dao.readAggregation((long) 34));
            ds.assegnaVoto(session, 81, dao.readDeveloper((long) 5), dao.readAggregation((long) 35));

            ds = dao.readDataService((long) 2);
            ds.assegnaVoto(session, 52, dao.readDeveloper((long) 3), dao.readAggregation((long) 3));
            ds.assegnaVoto(session, 60, dao.readDeveloper((long) 4), dao.readAggregation((long) 4));
            ds.assegnaVoto(session, 45, dao.readDeveloper((long) 6), dao.readAggregation((long) 18));
            ds.assegnaVoto(session, 59, dao.readDeveloper((long) 4), dao.readAggregation((long) 28));
            ds.assegnaVoto(session, 47, dao.readDeveloper((long) 5), dao.readAggregation((long) 29));
            ds.assegnaVoto(session, 55, dao.readDeveloper((long) 4), dao.readAggregation((long) 34));
            ds.assegnaVoto(session, 60, dao.readDeveloper((long) 5), dao.readAggregation((long) 35));

            ds = dao.readDataService((long) 3);
            ds.assegnaVoto(session, 65, dao.readDeveloper((long) 9), dao.readAggregation((long) 9));
            ds.assegnaVoto(session, 91, dao.readDeveloper((long) 10), dao.readAggregation((long) 10));
            ds.assegnaVoto(session, 95, dao.readDeveloper((long) 10), dao.readAggregation((long) 12));
            ds.assegnaVoto(session, 67, dao.readDeveloper((long) 9), dao.readAggregation((long) 21));
            ds.assegnaVoto(session, 20, dao.readDeveloper((long) 1), dao.readAggregation((long) 30));

            ds = dao.readDataService((long) 4);
            ds.assegnaVoto(session, 95, dao.readDeveloper((long) 11), dao.readAggregation((long) 11));
            ds.assegnaVoto(session, 98, dao.readDeveloper((long) 11), dao.readAggregation((long) 23));

            ds = dao.readDataService((long) 5);
            ds.assegnaVoto(session, 50, dao.readDeveloper((long) 6), dao.readAggregation((long) 6));
            ds.assegnaVoto(session, 67, dao.readDeveloper((long) 12), dao.readAggregation((long) 25));
            ds.assegnaVoto(session, 57, dao.readDeveloper((long) 14), dao.readAggregation((long) 26));
            ds.assegnaVoto(session, 55, dao.readDeveloper((long) 4), dao.readAggregation((long) 34));

            ds = dao.readDataService((long) 6);
            ds.assegnaVoto(session, 40, dao.readDeveloper((long) 4), dao.readAggregation((long) 4));
            ds.assegnaVoto(session, 43, dao.readDeveloper((long) 6), dao.readAggregation((long) 6));
            ds.assegnaVoto(session, 51, dao.readDeveloper((long) 6), dao.readAggregation((long) 18));
            ds.assegnaVoto(session, 39, dao.readDeveloper((long) 8), dao.readAggregation((long) 20));
            ds.assegnaVoto(session, 43, dao.readDeveloper((long) 10), dao.readAggregation((long) 22));
            ds.assegnaVoto(session, 10, dao.readDeveloper((long) 12), dao.readAggregation((long) 25));

            ds = dao.readDataService((long) 7);
            ds.assegnaVoto(session, 90, dao.readDeveloper((long) 8), dao.readAggregation((long) 8));
            ds.assegnaVoto(session, 77, dao.readDeveloper((long) 3), dao.readAggregation((long) 17));

            ds = dao.readDataService((long) 8);
            ds.assegnaVoto(session, 31, dao.readDeveloper((long) 2), dao.readAggregation((long) 2));
            ds.assegnaVoto(session, 35, dao.readDeveloper((long) 1), dao.readAggregation((long) 15));
            ds.assegnaVoto(session, 29, dao.readDeveloper((long) 1), dao.readAggregation((long) 31));

            ds = dao.readDataService((long) 9);
            ds.assegnaVoto(session, 70, dao.readDeveloper((long) 2), dao.readAggregation((long) 2));
            ds.assegnaVoto(session, 20, dao.readDeveloper((long) 13), dao.readAggregation((long) 13));
            ds.assegnaVoto(session, 68, dao.readDeveloper((long) 14), dao.readAggregation((long) 14));
            ds.assegnaVoto(session, 69, dao.readDeveloper((long) 1), dao.readAggregation((long) 31));

            ds = dao.readDataService((long) 10);
            ds.assegnaVoto(session, 89, dao.readDeveloper((long) 3), dao.readAggregation((long) 3));
            ds.assegnaVoto(session, 85, dao.readDeveloper((long) 8), dao.readAggregation((long) 20));
            ds.assegnaVoto(session, 91, dao.readDeveloper((long) 14), dao.readAggregation((long) 26));
            ds.assegnaVoto(session, 86, dao.readDeveloper((long) 4), dao.readAggregation((long) 34));

            ds = dao.readDataService((long) 11);
            ds.assegnaVoto(session, 80, dao.readDeveloper((long) 10), dao.readAggregation((long) 12));
            ds.assegnaVoto(session, 95, dao.readDeveloper((long) 4), dao.readAggregation((long) 24));
            ds.assegnaVoto(session, 90, dao.readDeveloper((long) 5), dao.readAggregation((long) 29));

            ds = dao.readDataService((long) 12);
            ds.assegnaVoto(session, 10, dao.readDeveloper((long) 5), dao.readAggregation((long) 5));
            ds.assegnaVoto(session, 30, dao.readDeveloper((long) 10), dao.readAggregation((long) 10));
            ds.assegnaVoto(session, 27, dao.readDeveloper((long) 11), dao.readAggregation((long) 11));
            ds.assegnaVoto(session, 15, dao.readDeveloper((long) 3), dao.readAggregation((long) 17));

            ds = dao.readDataService((long) 13);
            ds.assegnaVoto(session, 46, dao.readDeveloper((long) 5), dao.readAggregation((long) 5));
            ds.assegnaVoto(session, 89, dao.readDeveloper((long) 10), dao.readAggregation((long) 22));

            ds = dao.readDataService((long) 14);
            ds.assegnaVoto(session, 40, dao.readDeveloper((long) 7), dao.readAggregation((long) 7));
            ds.assegnaVoto(session, 45, dao.readDeveloper((long) 8), dao.readAggregation((long) 8));
            ds.assegnaVoto(session, 50, dao.readDeveloper((long) 10), dao.readAggregation((long) 12));
            ds.assegnaVoto(session, 35, dao.readDeveloper((long) 13), dao.readAggregation((long) 13));
            ds.assegnaVoto(session, 48, dao.readDeveloper((long) 2), dao.readAggregation((long) 16));
            ds.assegnaVoto(session, 40, dao.readDeveloper((long) 9), dao.readAggregation((long) 21));
            ds.assegnaVoto(session, 10, dao.readDeveloper((long) 12), dao.readAggregation((long) 27));

            ds = dao.readDataService((long) 15);
            ds.assegnaVoto(session, 60, dao.readDeveloper((long) 7), dao.readAggregation((long) 7));
            ds.assegnaVoto(session, 58, dao.readDeveloper((long) 9), dao.readAggregation((long) 21));
            ds.assegnaVoto(session, 50, dao.readDeveloper((long) 4), dao.readAggregation((long) 24));
            ds.assegnaVoto(session, 57, dao.readDeveloper((long) 1), dao.readAggregation((long) 31));
            ds.assegnaVoto(session, 55, dao.readDeveloper((long) 11), dao.readAggregation((long) 32));

            ds = dao.readDataService((long) 16);
            ds.assegnaVoto(session, 84, dao.readDeveloper((long) 7), dao.readAggregation((long) 7));
            ds.assegnaVoto(session, 87, dao.readDeveloper((long) 9), dao.readAggregation((long) 21));
            ds.assegnaVoto(session, 85, dao.readDeveloper((long) 4), dao.readAggregation((long) 24));
            ds.assegnaVoto(session, 92, dao.readDeveloper((long) 1), dao.readAggregation((long) 31));
            ds.assegnaVoto(session, 88, dao.readDeveloper((long) 1), dao.readAggregation((long) 33));

            ds = dao.readDataService((long) 17);
            ds.assegnaVoto(session, 36, dao.readDeveloper((long) 8), dao.readAggregation((long) 8));
            ds.assegnaVoto(session, 32, dao.readDeveloper((long) 7), dao.readAggregation((long) 19));
            ds.assegnaVoto(session, 30, dao.readDeveloper((long) 5), dao.readAggregation((long) 35));

            ds = dao.readDataService((long) 18);
            ds.assegnaVoto(session, 70, dao.readDeveloper((long) 1), dao.readAggregation((long) 1));
            ds.assegnaVoto(session, 67, dao.readDeveloper((long) 2), dao.readAggregation((long) 16));
            ds.assegnaVoto(session, 71, dao.readDeveloper((long) 1), dao.readAggregation((long) 30));

            ds = dao.readDataService((long) 19);
            ds.assegnaVoto(session, 25, dao.readDeveloper((long) 14), dao.readAggregation((long) 14));
            ds.assegnaVoto(session, 21, dao.readDeveloper((long) 1), dao.readAggregation((long) 15));
            ds.assegnaVoto(session, 30, dao.readDeveloper((long) 6), dao.readAggregation((long) 18));
            ds.assegnaVoto(session, 41, dao.readDeveloper((long) 8), dao.readAggregation((long) 20));
            ds.assegnaVoto(session, 33, dao.readDeveloper((long) 5), dao.readAggregation((long) 29));
            ds.assegnaVoto(session, 27, dao.readDeveloper((long) 11), dao.readAggregation((long) 32));
            ds.assegnaVoto(session, 20, dao.readDeveloper((long) 1), dao.readAggregation((long) 33));

            ds = dao.readDataService((long) 20);
            ds.assegnaVoto(session, 80, dao.readDeveloper((long) 1), dao.readAggregation((long) 1));
            ds.assegnaVoto(session, 89, dao.readDeveloper((long) 7), dao.readAggregation((long) 19));
            ds.assegnaVoto(session, 85, dao.readDeveloper((long) 11), dao.readAggregation((long) 23));
            ds.assegnaVoto(session, 79, dao.readDeveloper((long) 12), dao.readAggregation((long) 27));

            aggiornaDR();
        }
    }

    public static int stampaMenu(String[] voci) {
        System.out.println();
        for (int i = 0; i < voci.length - 1; i++) {
            System.out.println(i + 1 + " - " + voci[i]);
        }

        System.out.println();
        System.out.println("0 - " + voci[voci.length - 1]);

        int scelta = Integer.parseInt(MyUtil.leggiStringa(""));

        return scelta;
    }

    /**
     * Crea la richiesta di ricerca di un nuovo data service con i parametri
     * passati.
     *
     * @param idUtente identificativo dello sviluppatore che effettua la
     * richiesta
     * @param dsInseriti l'elenco di data service gi&agrave; inseriti
     * @param keywords stringa contenente le parole chiave per effettuare la
     * ricerca dell'i-simo data service
     * @return
     */
    public static Richiesta creaRichiesta(Long idUtente, String dsInseriti, String keywords) throws JWNLException, FileNotFoundException {
        Sviluppatore d;
        ArrayList<DataService> aggParziale;
        List<Tag> requisiti;
        try (Session session = sf.openSession()) {
            DAOclass dao = new DAOclass(session);
            d = dao.readDeveloper(idUtente);
            aggParziale = new ArrayList<>();
            String[] idDS = dsInseriti.split("-");
            for (String idDS1 : idDS) {
                DataService ds = dao.readDataService(Long.parseLong(idDS1));
                if (ds != null) {
                    aggParziale.add(ds);
                }
            }
            requisiti = new ArrayList<>();
            String[] kw = keywords.split("/");
            for (String kw1 : kw) {
                Tag temp = disambigua(kw1);
                if (temp != null && MyUtil.cercaElementoT(requisiti, temp) == -1) {
                    requisiti.add(temp);
                }
            }
        }

        return new Richiesta(d, aggParziale, requisiti);
    }

    /**
     * Permette di scegliere a quale definizione della stringa passata si fa
     * riferimento.
     *
     * @param daDisambiguare la stringa da disambiguare
     * @return il tag disambiguato
     */
    public static Tag disambigua(String daDisambiguare) throws JWNLException, FileNotFoundException {
        Tag disambiguato = new Tag();
        ArrayList<Synset> significati;
        significati = new ArrayList<>();
        int cont = 1;

        IndexWord indexWord = dizionario.lookupIndexWord(POS.NOUN, daDisambiguare);
        Synset[] senses;

        if (indexWord != null) {
            senses = indexWord.getSenses();

            for (Synset sense : senses) {
                System.out.println(cont + " - " + sense.getGloss());
                significati.add(sense);
                cont++;
            }
        }

        indexWord = dizionario.lookupIndexWord(POS.VERB, daDisambiguare);
        if (indexWord != null) {
            senses = indexWord.getSenses();

            for (Synset sense : senses) {
                System.out.println(cont + " - " + sense.getGloss());
                significati.add(sense);
                cont++;
            }
        }

        indexWord = dizionario.lookupIndexWord(POS.ADJECTIVE, daDisambiguare);
        if (indexWord != null) {
            senses = indexWord.getSenses();

            for (Synset sense : senses) {
                System.out.println(cont + " - " + sense.getGloss());
                significati.add(sense);
                cont++;
            }
        }

        indexWord = dizionario.lookupIndexWord(POS.ADVERB, daDisambiguare);
        if (indexWord != null) {
            senses = indexWord.getSenses();

            for (Synset sense : senses) {
                System.out.println(cont + " - " + sense.getGloss());
                significati.add(sense);
                cont++;
            }
        }

        int d = Integer.parseInt(MyUtil.leggiStringa("Cosa si intende per " + daDisambiguare + "? "));

        disambiguato.setOffset(significati.get(d - 1).getOffset());
        disambiguato.setPos(MyUtil.pos2string(significati.get(d - 1).getPOS()));
        disambiguato.setNome(daDisambiguare);

        System.out.println("hai scelto: " + disambiguato.getNome() + " " + disambiguato.getPos() + " " + disambiguato.getOffset());

        return disambiguato;

    }

    public static ArrayList<Risposta> ordinaDataServices(Richiesta r) throws FileNotFoundException, JWNLException {
        Session session = sf.openSession();
        DAOclass dao = new DAOclass(session);

        ArrayList<Risposta> risp = new ArrayList<>();
        ArrayList<DataService> servizi = dao.readDataServices();

        ArrayList<DataService> filtro;
        filtro = new ArrayList<>();
        ArrayList<Double> vettRho = new ArrayList<>();
        ArrayList<Double> vettSim;
        vettSim = new ArrayList<>();

        for (DataService ds : servizi) {
            if (MyUtil.cercaElementoDS(r.getAgg(), ds.getId()) == -1) {
                double sim = Similarity.calcolaSim(ds, r.getAgg(), r.getReq(), session);
                if (sim >= SIM_THRESHOLD) {
                    double rho = Similarity.calcolaRho(r.getDev(), ds, session);
                    filtro.add(ds);
                    vettRho.add(rho);
                    vettSim.add(sim);
                }
            }
        }

        double maxRho = MyUtil.maxArray(MyUtil.vec2arrayDouble(vettRho));

        for (int i = 0; i < filtro.size(); i++) {
            DataService temp = filtro.get(i);
            double rho = vettRho.get(i) / maxRho;
            double sim = vettSim.get(i);

            double rank = 2 * rho * sim / (rho + sim);

            risp.add(new Risposta(temp, rank));
            //System.out.println("* "+temp.getNome()+" sim: "+sim+" rho: "+rho+" -> "+rank);

        }

        /*ordinamento */
        for (int j = 0; j < risp.size() - 1; j++) {
            for (int k = j; k < risp.size(); k++) {
                if (risp.get(j).getRank() < risp.get(k).getRank()) {
                    Risposta temp = risp.get(j);
                    risp.set(j, risp.get(k));
                    risp.set(k, temp);
                }
            }
        }

        return risp;
    }

    /* da invocare quando la rete cambia (cioe' nuove relazioni di follow) */
    public static void aggiornaDR() {

        Session session = sf.openSession();
        DAOclass dao = new DAOclass(session);
        Set<Sviluppatore> elencoSviluppatori = dao.readDevelopers();
        java.util.Iterator<Sviluppatore> i = elencoSviluppatori.iterator();

        while (i.hasNext()) {
            Sviluppatore risp = i.next();
            ArrayList<Sviluppatore> vd;
            vd = new ArrayList<>(elencoSviluppatori);
            double[] vettDR = new double[vd.size() - 1];
            int cont = 0;

            System.out.print(risp.getId() + " ");

            for (Sviluppatore dj : vd) {
                if (risp.getId() != dj.getId()) {
                    vettDR[cont] = Similarity.calcolaDR(dj, risp, session);
                    cont++;
                }
            }

            double drMax = MyUtil.maxArray(vettDR);

            cont = 0;
            for (Sviluppatore temp : vd) {
                if (temp.getId() != risp.getId()) {
                    double dr = 0.00;
                    if (drMax != 0) {
                        dr = vettDR[cont] / drMax;
                        cont++;
                    }
                    dao.updateDRrelativo(temp.getId(), risp.getId(), dr);
                }
            }

        }

        System.out.println();
    }

    public static void pubblicaDS(Session s) throws FileNotFoundException, JWNLException {
        String nomeDS = MyUtil.leggiStringa(NOME_DS);
        String descrDS = MyUtil.leggiStringa(DESCR_DS);
        String input = MyUtil.leggiStringa(INPUT_DS);
        String output = MyUtil.leggiStringa(OUTPUT_DS);

        String tags = MyUtil.leggiStringa(TAG_DS);
        String[] elenco = tags.split("/");
        Set<Tag> tagDisambiguati = new HashSet<>();

        for (String elenco1 : elenco) {
            Tag temp = disambigua(elenco1);
            tagDisambiguati.add(temp);
        }

        Session session = sf.openSession();

        DAOclass dao = new DAOclass(session);

        dao.createDataService(nomeDS, descrDS, tagDisambiguati, input, output);

        System.out.println();
        System.out.println(OP_OK);
        System.out.println();
    }

    public static void pubblicaAggregazione(Long idd) throws FileNotFoundException, JWNLException {
        Session session = sf.openSession();
        DAOclass dao = new DAOclass(session);

        String nomeAgg = MyUtil.leggiStringa(NOME_AGG);
        String descrAgg = MyUtil.leggiStringa(DESCR_AGG);
        ArrayList<DataService> dsAll = dao.readDataServices();

        System.out.println();

        for (DataService temp : dsAll) {
            System.out.println(temp.getId() + " - " + temp.getNome());
        }

        System.out.println();
        String dataServices = MyUtil.leggiStringa(DS_AGG);
        String[] elenco = dataServices.split(" ");
        Set<DataService> ddss = new HashSet<>();

        for (String elenco1 : elenco) {
            DataService temp = dao.readDataService(Long.parseLong(elenco1));
            ddss.add(temp);
        }

        long ida = dao.createAggregation(nomeAgg, descrAgg, dao.readDeveloper(idd), ddss);
        Aggregazione a = dao.readAggregation(ida);
        Sviluppatore d = dao.readDeveloper(idd);

        System.out.println(VALUT_DS);

        for (String elenco1 : elenco) {
            DataService temp = dao.readDataService(Long.parseLong(elenco1));
            int voto = Integer.parseInt(MyUtil.leggiStringa(" - " + temp.getNome() + ": "));
            temp.assegnaVoto(session, voto, d, a);
        }

        System.out.println();
        System.out.println(OP_OK);
        System.out.println();
    }

    public static long registrati(Session s) throws NoSuchAlgorithmException {

        Session session = sf.openSession();
        DAOclass dao = new DAOclass(session);
        String nomeDev = MyUtil.leggiStringa(NOME_DEV);
        String pwd = MyUtil.leggiStringa(PWD_DEV);

        String digestpwd = new String(MyUtil.digest(pwd));

        System.out.println();
        System.out.println(AMB_DEV);
        String sDev = MyUtil.leggiStringa(AMB_DEV_S);
        String cDev = MyUtil.leggiStringa(AMB_DEV_C);
        String aDev = MyUtil.leggiStringa(AMB_DEV_A);
        String mDev = MyUtil.leggiStringa(AMB_DEV_M);
        long id = dao.createDeveloper(nomeDev, digestpwd, 0.5, sDev, cDev, aDev, mDev);

        System.out.println();
        System.out.println(ID_DEV + id);
        System.out.println(OP_OK);
        System.out.println();

        return id;
    }

    public static void modificaRelazioni(Session s, Long idd) {

        Session session = sf.openSession();
        DAOclass dao = new DAOclass(session);
        char ar = MyUtil.leggiStringa(FOLLOW).toCharArray()[0];

        // aggiunta
        if (ar == 'A') {
            Set<Sviluppatore> seguiti = dao.readDeveloper(idd).getFollowed();

            Set<Sviluppatore> elenco = dao.readDevelopers();
            java.util.Iterator<Sviluppatore> i = elenco.iterator();

            System.out.println();
            while (i.hasNext()) {
                Sviluppatore temp = i.next();
                if (temp.getId() != idd && !seguiti.contains(temp)) {
                    System.out.println(temp.getId() + " " + temp.getNome());
                }
            }

            System.out.println();
            String nuovo = MyUtil.leggiStringa(NEW_FOLLOW);

            dao.updateDeveloperAdd(idd, Long.parseLong(nuovo));
        }

        // rimozione
        if (ar == 'R') {
            Set<Sviluppatore> seguiti = dao.readDeveloper(idd).getFollowed();
            java.util.Iterator<Sviluppatore> i = seguiti.iterator();

            System.out.println();
            while (i.hasNext()) {
                Sviluppatore temp = i.next();
                System.out.println(temp.getId() + " " + temp.getNome());
            }

            System.out.println();
            String nuovo = MyUtil.leggiStringa(OLD_FOLLOW);

            dao.updateDeveloperRem(idd, Long.parseLong(nuovo));
        }

        System.out.println();
        System.out.println(OP_OK);
        System.out.println();
    }

    public static long accedi(Session s) throws NoSuchAlgorithmException {

        Session session = sf.openSession();
        DAOclass dao = new DAOclass(session);
        String id = MyUtil.leggiStringa(USER);
        String pwd = MyUtil.leggiStringa(PWD);

        String digestpwd = new String(MyUtil.digest(pwd));

        long ris = (long) -1;

        Sviluppatore d = dao.readDeveloper(Long.parseLong(id));
        if (d != null) {
            s.update(d);
            if (d.getPassword().equals(digestpwd)) {
                System.out.println();
                System.out.println(SUCCESS);
                System.out.println();

                ris = Long.parseLong(id);
            } else {
                System.out.println();
                System.out.println(FAIL);
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println(FAIL);
            System.out.println();
        }

        return ris;
    }

}
