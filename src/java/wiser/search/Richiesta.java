package wiser.search;

/**
 * Classe che rappresenta la richiesta di uno sviluppatore che sta progettando un'aggregazione e cerca un nuovo data service.
 * @author Flavia Venturelli
 * @version 1.0
 */

import java.util.*;
import wiser.dao.DataService;
import wiser.dao.Sviluppatore;
import wiser.dao.Tag;

public class Richiesta {
	
	private final Sviluppatore dev;
	private final ArrayList<DataService> agg;
	private final List<Tag> req;

	public Richiesta(Sviluppatore d, ArrayList<DataService> a, List<Tag> t)
	{
		dev = d;
		agg = a;
		req = t;
	}

	public Sviluppatore getDev() {
		return dev;
	}

	public ArrayList<DataService> getAgg() {
		return agg;
	}

	public List<Tag> getReq() {
		return req;
	}
	
	
}
