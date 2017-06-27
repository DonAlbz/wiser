/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletPackage;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import wiser.dao.*;
import java.util.*;

import java.io.IOException;
import java.io.PrintWriter;
import javaFunctions.Functions;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import org.hibernate.sql.ordering.antlr.OrderingSpecification;
import org.json.JSONObject;

/**
 *
 * @author Andrea
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    //initiate the DAOclass to get the access to the DB
    DAOclass hibernate;

    //initiate the servlet 
    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        Configuration configuration = new Configuration();
        SessionFactory sf = configuration.configure().buildSessionFactory();
        Session session = sf.openSession();
        hibernate = new DAOclass(session);
    }

    public void destroy() {
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String op = req.getParameter("op");

        if (op.equalsIgnoreCase("getList")) {
            doGetList(req, resp);
        }

        if (op.equalsIgnoreCase("login")) {
            doPostLogin(req, resp);
        }

        if (op.equalsIgnoreCase("registrazione")) {
            doPostRegistrazione(req, resp);
        }
        
        if (op.equalsIgnoreCase("controlloUsername")) {
            doPostControlloUsername(req, resp);
        }
        
        if (op.equalsIgnoreCase("autoc")) {
            doGetAutoc(req, resp);
        }
    }

    private void doGetList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = (String) req.getParameter("search");
        ArrayList<DataService> servicesFiltered = new ArrayList<>();
        ArrayList<DataService> servicesParsed = new ArrayList<>();
        String tagFilter = req.getParameter("tag");
        Integer start = Functions.parseInteger(req.getParameter("start"));
        String catFilter = req.getParameter("filtro");
        boolean isReady = false;
        if ((key != null) && (!key.equalsIgnoreCase("null"))) {
            if (isCategory(key)) {
                catFilter = key;
            } else {
                if (isTag(key)) {
                    tagFilter = key;
                } else {
                    servicesFiltered = getSearchDS(key);
                    req.setAttribute("search", key);
                    isReady = true;
                }
            }
        }
        if ((catFilter != null) && (!catFilter.equalsIgnoreCase("") && (!catFilter.equalsIgnoreCase("null")))) {
            if (!isReady) {
                servicesFiltered = getCategoryDS(catFilter);
                req.setAttribute("filtro", catFilter);
                isReady = true;
            }
        }
            if ((tagFilter != null) && (!tagFilter.equalsIgnoreCase("") && (!tagFilter.equalsIgnoreCase("null")))) {
                if (!isReady) {
                    servicesFiltered = getTagDS(tagFilter);
                    req.setAttribute("tag", tagFilter);
                    isReady = true;
                }
            } else {
                if (!isReady) {
                    servicesFiltered = hibernate.readDataServices();
                    isReady = true;
                }
            }        
        String richiesta = req.getParameter("orderBy");
        if (richiesta==null)
            servicesFiltered.sort((t1,t2) -> t1.getNome().compareTo(t2.getNome()));
        else{
        if(richiesta.equalsIgnoreCase("nome")) {
            servicesFiltered.sort((t1,t2) -> t1.getNome().compareTo(t2.getNome()));
            req.setAttribute("ordinamento", "Ordinamento per nome");
        }
        else if (richiesta.equalsIgnoreCase("utilizziMax")) {   
           servicesFiltered.sort((t1,t2) -> Integer.compare(t1.getNumeroUtilizzi(), t2.getNumeroUtilizzi()));
           Collections.reverse(servicesFiltered); 
           req.setAttribute("ordinamento", "Dai pi&ugrave; ai meno utilizzati");    
        }
        else if (richiesta.equalsIgnoreCase("utilizziMin")) {   
           servicesFiltered.sort((t1,t2) -> Integer.compare(t1.getNumeroUtilizzi(), t2.getNumeroUtilizzi())); 
           req.setAttribute("ordinamento", "Dai meno ai pi&ugrave; utilizzati");    
        }
        else if (richiesta.equalsIgnoreCase("votoMax")) {   
           servicesFiltered.sort((t1,t2) -> Double.compare(t1.getMediaVoti(), t2.getMediaVoti()));
           Collections.reverse(servicesFiltered);
           req.setAttribute("ordinamento", "Dai pi&ugrave; ai meno votati");    
        }
        else if (richiesta.equalsIgnoreCase("votoMin")) {   
           servicesFiltered.sort((t1,t2) -> Double.compare(t1.getMediaVoti(), t2.getMediaVoti()));
           req.setAttribute("ordinamento", "Dai meno ai pi&ugrave; votati");    
        }
  
        
        }
        servicesParsed = Functions.parseDSList(servicesFiltered, start);
        req.setAttribute("servicesDim", servicesFiltered.size());
        ArrayList<Category> categories = hibernate.readCategories();
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/index.jsp");
        req.setAttribute("list", servicesParsed);
        req.setAttribute("cats", categories);
        req.setAttribute("orderBy", richiesta);
        rd.forward(req, resp);
    }

    protected void doPostLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String nome = req.getParameter("username");
        String pass = req.getParameter("password");

        if (validaUtente(nome, pass)) {
            out.println("loggato " + nome);
        } else {
            req.setAttribute("error", "Attenzione! non sei registrato!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    protected void doPostRegistrazione(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String nome = request.getParameter("username");
        String pass = request.getParameter("password");
        String pass_confirm = request.getParameter("password_confirm");

        hibernate.createDeveloper(nome, pass, 0, pass, pass, pass, nome);
        out.println("Registrato!");
    }

    public boolean validaUtente(String name, String password) {
        boolean stato = false;
        String nome1 = name.toLowerCase();
        String pwd1 = password.toLowerCase();
        Set<Sviluppatore> utenti = hibernate.readDevelopers();

        for (Sviluppatore sviluppatore : utenti) {
            if (nome1.equals(sviluppatore.getNome().toLowerCase()) && pwd1.equals(sviluppatore.getPassword().toLowerCase())) {
                stato = true;
            }
        }

        return stato;
    }

    public boolean cercaUtente(String name) {
        boolean stato = false;
        String nome1 = name.toLowerCase();
        Set<Sviluppatore> utenti = hibernate.readDevelopers();

        for (Sviluppatore sviluppatore : utenti) {
            if (nome1.equals(sviluppatore.getNome().toLowerCase())) {
                stato = true;
            }
        }

        return stato;
    }

    public void doPostControlloUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        Boolean utenteGiaPresente = cercaUtente(username);

        resp.getWriter().println(utenteGiaPresente);
    }

    ;
   
    public void doGetAutoc(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String s = (String) req.getParameter("s");
        ArrayList<DataService> services = hibernate.readDataServices();
        ArrayList<Tag> tags = hibernate.readTags();
        ArrayList<Category> categs = hibernate.readCategories();
        Iterator iterService = services.iterator();
        ArrayList<JSONObject> list = new ArrayList<>();
        while (iterService.hasNext()) {
            JSONObject obj = new JSONObject();
            DataService service = (DataService) iterService.next();
            String name = service.getNome();
            if (name.toLowerCase().contains(s.toLowerCase())) {
                obj.put("nome", name);
                list.add(obj);
            }
        }
        Iterator iterTags = tags.iterator();
        while (iterTags.hasNext()) {
            JSONObject obj = new JSONObject();
            Tag tagSel = (Tag) iterTags.next();
            String name = tagSel.getNome();
            if (name.toLowerCase().contains(s)) {
                obj.put("nome", name);
                list.add(obj);
            }
        }     
        Iterator iterCat = categs.iterator();
        while(iterCat.hasNext()) {
            JSONObject obj = new JSONObject();
            Category catSel = (Category) iterCat.next();
            String name = catSel.getNome();
            if (name.toLowerCase().contains(s)) {
                obj.put("nome", name);
                list.add(obj);
            }
        }
        JSONObject toRet = new JSONObject();
        toRet.put("suggerimenti", list);
        PrintWriter out = resp.getWriter();
        out.print(toRet);
    }

    private boolean isCategory(String key) {
        ArrayList<Category> categories = hibernate.readCategories();
        Iterator iterCats = categories.iterator();
        while (iterCats.hasNext()) {
            Category catSel = (Category) iterCats.next();
            if (catSel.getNome().equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<DataService> getCategoryDS(String catName) {
        ArrayList<DataService> services = hibernate.readDataServices();
        ArrayList<DataService> toRet = Functions.filterCategoryDSList(services, catName);
        return toRet;
    }

    private ArrayList<DataService> getSearchDS(String key) {
        ArrayList<DataService> services = hibernate.readDataServices();
        Iterator iterService = services.iterator();
        ArrayList<DataService> toRet = new ArrayList<>();
        while (iterService.hasNext()) {
            DataService service = (DataService) iterService.next();
            if (service.getNome().equalsIgnoreCase(key)) {
                toRet.add(service);
            }
        }
        iterService = services.iterator();
        while (iterService.hasNext()) {
            DataService service = (DataService) iterService.next();
            if (service.getNome().toLowerCase().contains(key.toLowerCase())) {
                if (!service.getNome().equalsIgnoreCase(key)) {
                    toRet.add(service);
                }
            }
        }
        return toRet;
    }

    private boolean isTag(String key) {
        ArrayList<Tag> tags = hibernate.readTags();
        Iterator iterTags = tags.iterator();
        while (iterTags.hasNext()) {
            Tag tagSel = (Tag) iterTags.next();
            if (tagSel.getNome().equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<DataService> getTagDS(String tagName) {
        ArrayList<DataService> services = hibernate.readDataServices();
        ArrayList<DataService> toRet = Functions.filterTagDSList(services, tagName);
        return toRet;
    }

}
