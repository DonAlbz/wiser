package wiser.middleware.similarity;

/**
 * Classe di utilit&agrave; che implementa l'algoritmo kmeans++.
 *
 * @author Flavia Venturelli
 * @version 1.0
 */
import java.util.*;

public class Kmeans {

    // with careful seeding

    private static float[] seeding(int[] voti, int numCluster) {
        int dim = voti.length;
        Random rand = new Random();
        int min = 0;
        int randomCenter = rand.nextInt((dim - min)) + min;
        float[] centri = new float[numCluster];
        centri[0] = voti[randomCenter];
        //System.out.println("centro numero 0: "+centri[0]);
        for (int i = 1; i < numCluster; i++) {
            centri[i] = -1;
        }

        for (int i = 1; i < centri.length; i++) {
            double[] probabilities = new double[dim];
            double sommaDistanze2 = 0;
            for (int j = 0; j < dim; j++) {
                float dist = calcolaDistanzaMinima(voti[j], centri);
                double dist2 = Math.pow(dist, 2);
                sommaDistanze2 += dist2;
                probabilities[j] = dist2;
            }

            //cerca la probabilita max
            int pos = 0;
            double max = 0;
            for (int j = 0; j < probabilities.length; j++) {
                double prob = probabilities[j] / sommaDistanze2;
                if (prob > max) {
                    pos = j;
                    max = prob;
                }
            }

            centri[i] = voti[pos];
            //System.out.println("centro numero "+i+": "+centri[i]);
        }

        return centri;
    }

    private static float calcolaDistanzaMinima(int voto, float[] centri) {
        ArrayList<Float> dist;
        dist = new ArrayList<>();
        for (int i = 0; i < centri.length && centri[i] != -1; i++) {
            float diff = voto - centri[i];
            if (diff < 0) {
                diff = -diff;
            }
            dist.add(diff);
        }

        float min = Float.MAX_VALUE;

        for (Float dist1 : dist) {
            if (dist1 < min) {
                min = dist1;
            }
        }

        return min;
    }

    public static float findCentroid(int[] voti, int numCluster) {
        int maxsiz = voti.length;
        int dim = voti.length;

        int[][] d1 = new int[numCluster][dim];

        // inizializza d1 con tutti 0
        for (int i = 0; i < numCluster; i++) {
            for (int j = 0; j < dim; j++) {
                d1[i][j] = 0;
            }
        }

        int pos = 0;
        float m1[] = new float[numCluster];
        float avg[] = seeding(voti, numCluster);

        for (int k = 0; k < maxsiz; k++) {
            for (int j = 0; j < numCluster; j++) {
                m1[j] = avg[j] - voti[k]; // calcola la distanza tra ogni voto e la media di ogni cluster
                if (m1[j] < 0) {
                    m1[j] = -m1[j]; // cambia segno -> ABS
                }
            }

            // cerca il cluster a distanza minima
            float min = m1[0];
            for (int i = 0; i < numCluster; i++) {
                if (min >= m1[i]) {
                    min = m1[i];
                    pos = i;
                }
            }

            // colloca il voto c-simo nel cluster piu' vicino (alla c-sima colonna -> c'e' solo un voto per colonna)
            d1[pos][k] = voti[k];
        }

        int[] numElem = new int[numCluster];
        float avg2[] = new float[numCluster]; // contiene le medie dei cluster
        // conta il numero di elementi presenti in ogni cluster
        for (int i = 0; i < numCluster; i++) {
            avg2[i] = 0;
            numElem[i] = 0;
            for (int j = 0; j < dim; j++) {
                if (d1[i][j] != 0) {
                    numElem[i]++;
                }
                avg2[i] += d1[i][j];
            }

            avg2[i] /= numElem[i];
        }

        // cerca il cluster con media maggiore
        int centroid = 0;
        int max = 0;
        for (int c = 0; c < numCluster; c++) {
            int temp = numElem[c];
            if (temp > max) {
                max = temp;
                centroid = c;
            }
        }

		//System.out.println("Centroid Max = "+avg2[centroid]);
        //System.out.println("Max = "+max);
        return avg2[centroid];
    }
}
