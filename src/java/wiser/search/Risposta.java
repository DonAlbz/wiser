package wiser.search;

import wiser.dao.DataService;

/**
 * Classe che associa ai data service il loro rango secondo la richiesta inviata.
 * @author Flavia Venturelli
 * @version 1.0
 */

public class Risposta {
	
	private double rank;
	private final DataService ds;
	
	public Risposta(DataService servizi, double r)
	{
		ds = servizi;
		rank = r;
	}
	
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}
	public DataService getDs() {
		return ds;
	}
	
	

}
