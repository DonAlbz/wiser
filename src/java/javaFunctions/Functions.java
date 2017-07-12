/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFunctions;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import wiser.dao.*;

/**
 *
 * @author Mattia
 */
public class Functions {

    public final static Integer DS_PER_PAGINA = 8;
    
    public static Integer parseInteger(String s) {
        Integer start;
        if (s == null) {
            start = 0;
        } else {
            start = Integer.parseInt(s);
        }
        return start;
    }

    public static ArrayList<DataService> parseDSList(ArrayList<DataService> list, Integer start) {
        ArrayList<DataService> toRet = new ArrayList<>();
        for (int i = start; i < start + DS_PER_PAGINA; i++) {
            if (i < list.size()) {
                toRet.add(list.get(i));
            }
        }
        return toRet;
    }

    public static Integer numberOfPages(Integer servicesDim) {
        Integer numPages;
        if (servicesDim % DS_PER_PAGINA != 0) {
            numPages = servicesDim / DS_PER_PAGINA + 1;
        } else {
            numPages = servicesDim / DS_PER_PAGINA;
        }
        return numPages;
    }

    public static ArrayList<DataService> filterCategoryDSList(ArrayList<DataService> list, String filter) {
        ArrayList<DataService> services = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DataService service = list.get(i);
            Set<Category> catlist = service.getCategory();
            Object[] listaDiArray = catlist.toArray();
            for (int j = 0; j < listaDiArray.length; j++) {
                Category cat = (Category) listaDiArray[j];
                if (filter.equalsIgnoreCase(cat.getNome())) {
                    services.add(list.get(i));
                }
            }
        }
        return services;
    }

    public static ArrayList<DataService> filterTagDSList(ArrayList<DataService> list, String filter) {
        ArrayList<DataService> services = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DataService service = list.get(i);
            Set<Tag> taglist = service.getTag();
            Object[] listaDiArray = taglist.toArray();
            for (int j = 0; j < listaDiArray.length; j++) {
                Tag tag = (Tag) listaDiArray[j];
                if (filter.equalsIgnoreCase(tag.getNome())) {
                    services.add(list.get(i));
                }
            }
        }
        return services;
    }

    public static ArrayList<DataService> orderDSList(ArrayList<DataService> list, String filter, HttpServletRequest req) {
        if ((filter == null) || (filter.equalsIgnoreCase("null"))) {
            list.sort((t1, t2) -> t1.getNome().compareTo(t2.getNome()));
        } else {
            if (filter.equalsIgnoreCase("nome")) {
                list.sort((t1, t2) -> t1.getNome().compareTo(t2.getNome()));
                req.setAttribute("ordinamento", "nome");
            } else if (filter.equalsIgnoreCase("utilizziMax")) {
                list.sort((t1, t2) -> Integer.compare(t1.getNumeroUtilizzi(), t2.getNumeroUtilizzi()));
                Collections.reverse(list);
                req.setAttribute("ordinamento", "utilizziMax");
            } else if (filter.equalsIgnoreCase("utilizziMin")) {
                list.sort((t1, t2) -> Integer.compare(t1.getNumeroUtilizzi(), t2.getNumeroUtilizzi()));
                req.setAttribute("ordinamento", "utilizziMin");
            } else if (filter.equalsIgnoreCase("votoMax")) {
                list.sort((t1, t2) -> Double.compare(t1.getMediaVoti(), t2.getMediaVoti()));
                Collections.reverse(list);
                req.setAttribute("ordinamento", "votoMax");
            } else if (filter.equalsIgnoreCase("votoMin")) {
                list.sort((t1, t2) -> Double.compare(t1.getMediaVoti(), t2.getMediaVoti()));
                req.setAttribute("ordinamento", "votoMin");
            }
            else
            {
                 list.sort((t1, t2) -> t1.getNome().compareTo(t2.getNome()));
            }
        }
        return list;
    }
}
