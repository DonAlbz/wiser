/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFunctions;

import java.util.*;
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


}
