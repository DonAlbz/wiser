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
    }

       private void doGetList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<DataService> services = hibernate.readDataServices();
        ArrayList<Tag> tags = hibernate.readTags();
        Integer start = Functions.parseInteger(req.getParameter("start"));
        String tagFilter = req.getParameter("filtro");
        ArrayList<DataService> servicesParsed;
        if ((tagFilter != null)&&(!tagFilter.equalsIgnoreCase("")&&(!tagFilter.equalsIgnoreCase("null")))) {
            ArrayList<DataService> servicesFiltered = Functions.filterDSList(services, tagFilter);
            servicesParsed = Functions.parseDSList(servicesFiltered, start);
            req.setAttribute("servicesDim", servicesFiltered.size());
            req.setAttribute("filtro", tagFilter);
        } else {
            servicesParsed = Functions.parseDSList(services, start);
            req.setAttribute("servicesDim", services.size());
        }
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/index.jsp");
        req.setAttribute("list", servicesParsed);
        req.setAttribute("tags", tags);
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
            if (nome1.equals(sviluppatore.getNome().toLowerCase()) ) {
                stato = true;
            }
        }

        return stato;
    }
    
    
    
    

   public void doPostControlloUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        Boolean utenteGiaPresente = cercaUtente(username);
        
        resp.getWriter().println(utenteGiaPresente);
    };
    
    
}
