package wiser.middleware.similarity;

/**
 * Classe di utilit&agrave;.
 * @author Flavia Venturelli
 * @version 1.0
 */

import java.util.*;
import java.security.*;

import net.didion.jwnl.data.POS;
import wiser.dao.DataService;
import wiser.dao.Sviluppatore;
import wiser.dao.Tag;

public class MyUtil {
	
	private static Scanner lettore = new Scanner(System.in);
	
	public static byte[] digest(String pwd) throws NoSuchAlgorithmException
    {
    	MessageDigest md = MessageDigest.getInstance("MD5");
    	
    	md.update(pwd.getBytes()); 
    	byte[] digest = md.digest();
    	 
   	    return digest;
    }
	
	public static int cercaElemento (String[] elenco, String elemento)
	{
		int pos = -1;
		
		for(int i=0; i<elenco.length; i++)
		{
			if(elenco[i].equalsIgnoreCase(elemento))
			{
				pos = i;
				break;
			}
		}
		
		return pos;
	}
	
	public static int cercaElementoT (List<Tag> elenco, Tag t)
	{
		int pos = -1;
		
		for(int i=0; i<elenco.size(); i++)
		{
			Tag temp = elenco.get(i);
			
			if(temp.equal(t))
			{
				pos = i;
				break;
			}
		}
		
		return pos;
	}
	
	public static int cercaElementoD (ArrayList<Sviluppatore> elenco, Long id)
	{
		int pos = -1;
		
		for(int i=0; i<elenco.size(); i++)
		{
			if(elenco.get(i)!=null && elenco.get(i).getId()==id)
			{
				pos = i;
				break;
			}
		}
		
		return pos;
	}
	
    public static int cercaElementoDS (ArrayList<DataService> elenco, Long id)
	{
		int pos = -1;
		
		for(int i=0; i<elenco.size(); i++)
		{
			if(elenco.get(i)!=null && Objects.equals(elenco.get(i).getId(), id))
			{
				pos = i;
				break;
			}
		}
		
		return pos;
	}
	
	public static int contaNull (ArrayList<Sviluppatore> elenco)
	{
		int cont = 0;
		
                cont = elenco.stream().filter((elenco1) -> (elenco1 == null)).map((_item) -> 1).reduce(cont, Integer::sum);
		
		return cont;
	}
	
	public static ArrayList<Sviluppatore> estraiNonNull (ArrayList<Sviluppatore> elenco)
	{
		ArrayList<Sviluppatore> nonNull = new ArrayList<>();
		
                elenco.stream().filter((elenco1) -> (elenco1 != null)).forEach((elenco1) -> {
                    nonNull.add(elenco1);
            });
		
		return nonNull;
	}
	
	public static ArrayList<Sviluppatore> subSet (ArrayList<Sviluppatore> elenco)
	{
		ArrayList<Sviluppatore> sub;
            sub = new ArrayList<>();
		
            for (Sviluppatore elenco1 : elenco) {
                if (elenco1 != null) {
                    sub.add(elenco1);
                } else {
                    return sub;
                }
            }
		
		return sub;
	}
	
	public static double maxArray(double[]array)
	{
		double max = 0.00;
		for(int i=0; i<array.length; i++)
		{
			if(array[i]>max)
			{
				max = array[i];
			}
		}
		return max;
	}
	
	public static ArrayList<Sviluppatore> ordina (int start, ArrayList<Double> elenco, ArrayList<Sviluppatore> elenco2)
	{
		ArrayList<Sviluppatore> ordinato = elenco2;
		for(int k=0; k<elenco.size()-1; k++)
		{
			int i = k+start+1;
			for(int w=k+1; w<elenco.size(); w++)
			{
				int j = start+1+w;
				if(elenco.get(k)<elenco.get(w))
				{
					Sviluppatore temp = ordinato.get(i);
					ordinato.set(i, ordinato.get(j));
					ordinato.set(j, temp);
				}
			}
		}
		
		return ordinato;
	}
	
	
	public static String leggiStringa(String mess) 
	{
		System.out.print(mess);
		lettore = creaScanner();
		return lettore.next();
	}
	
	private static Scanner creaScanner ()
	{
	   Scanner creato = new Scanner(System.in);
	   creato.useDelimiter(System.getProperty("line.separator"));
	   return creato;
	}
	
	public static double[] backwardSubstitution(double[][] matrix, double[]tn)
	{
		double [] x = new double[tn.length];
		for(int i=matrix.length-1; i>=0; i--)
		{
			double sum = 0.00;
			for(int j=i+1; j<matrix.length; j++)
			{
				sum += (matrix[i][j]*x[j]);
			}
			
			x[i] = (tn[i]-sum)/ matrix[i][i];
		}

		return x;
	}
	
	public static double[][] estraiTriangoloSuperiore (double [][] matrix)
	{
		double [][] risultato = matrix;
		for(int i=0; i<matrix.length; i++)
		{
			for(int j=0; j<matrix.length; j++)
			{
				if(i > j)
				{
					risultato[i][j]=0;
				}
			}
		}
		return risultato;
	}
	
	public static void meg(double[][]matrix, double [] tn)
	{
		for(int k=0; k<matrix.length-1; k++)
		{
			for(int i=k+1; i<matrix.length; i++)
			{
				double mik = matrix[i][k]/matrix[k][k];
				for(int j=1; j<matrix.length; j++)
				{
					matrix[i][j] -= mik*matrix[k][j];
				}
				
				tn[i] -= mik*tn[k];
				
			}
		}
	}
	
	/* calcola determinante matrice triangolare */
	public static double calcolaDeterminante(double[][] matrice)
	{
		double det = 1.00;
		
		for(int i=0; i<matrice.length; i++)
		{
			det *= matrice[i][i];
		}
		
		return det;
	}
	
	
	
	public static void stampaMatrice(double[][] matrix)
	{
            for (double[] matrix1 : matrix) {
                for (int ic = 0; ic < matrix1.length; ic++) {
                    System.out.print(matrix1[ic] + " ");
                }
                System.out.println();
            }
	}

	public static int [] vec2arrayInt (ArrayList<Integer> v)
	{
		int [] risultato = new int [v.size()];
		for(int i=0; i<v.size(); i++)
		{
			risultato[i] = v.get(i);
		}
		
		return risultato;
	}
	
	public static double [] vec2arrayDouble (ArrayList<Double> v)
	{
		double [] risultato = new double [v.size()];
		for(int i=0; i<v.size(); i++)
		{
			risultato[i] = v.get(i);
		}
		
		return risultato;
	}
	
	public static String pos2string (POS p)
    {
		String s="";
        if (p.equals(POS.NOUN))
        	s = "N";
        else if (p.equals(POS.VERB))
            	s = "V";
        else if (p.equals(POS.ADJECTIVE))
            	s = "A";
        else if (p.equals(POS.ADVERB))
            	s = "R";

        return s;
    }
}
