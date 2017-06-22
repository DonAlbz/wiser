/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wiser.main;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import wiser.dao.*;

import java.util.*;

/**
 *
 * @author devis
 */
public class WISeRtester {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        SessionFactory sf = configuration.configure().buildSessionFactory();
        Session session = sf.openSession();

        DAOclass dao = new DAOclass(session);

        // print all services and their tags
        System.out.println();
        System.out.println("===================");
        System.out.println("= TAGS BY SERVICE =");
        System.out.println("===================");
        ArrayList<DataService> services = dao.readDataServices();
        Iterator<DataService> iterServ = services.iterator();

        while (iterServ.hasNext()) {
            System.out.println("####################");
            DataService serv = iterServ.next();
            System.out.println("Service " + serv.getNome() + " has been tagged with");
            Set<Tag> tags = serv.getTag();

            Iterator<Tag> iterTag = tags.iterator();
            while (iterTag.hasNext()) {
                System.out.println("- " + iterTag.next().getNome());
            }
        }

        // print all developers and their votes
        System.out.println();
        System.out.println("======================");
        System.out.println("= VOTES BY DEVELOPER =");
        System.out.println("======================");
        Set<Sviluppatore> developers = dao.readDevelopers();
        Iterator<Sviluppatore> iter = developers.iterator();

        while (iter.hasNext()) {
            System.out.println("####################");
            Sviluppatore dev = iter.next();
            System.out.print(dev.getNome());
            ArrayList<Voto> voti = dao.readVoti(dev.getId());

            System.out.println(" assigned " + voti.size() + " grades");

            Iterator<Voto> voto = voti.iterator();
            while (voto.hasNext()) {
                System.out.println("- " + voto.next().getVoto());
            }
        }
    }
}
