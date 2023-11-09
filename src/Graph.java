import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
/*
@author Maxime Ferriere && Dogukan Gunduz
 */
public class Graph {

  private HashMap<String, Aeroport> aeroport = new HashMap<>();
  private HashMap<Aeroport, Set<Vol>> volAeroport = new HashMap<>(); //liste adjacent


  public Graph(File aeroports, File vols) {
    try {

      FileReader fr = new FileReader(aeroports);
      FileReader fr2 = new FileReader(vols);

      // Créer l'objet BufferedReader
      BufferedReader br = new BufferedReader(fr);
      BufferedReader br2 = new BufferedReader(fr2);

      String line;

      while ((line = br.readLine()) != null) {

        String[] donneAeroport = line.split(",");

        Aeroport ae = new Aeroport(donneAeroport[0], donneAeroport[1], donneAeroport[2],
            donneAeroport[3], Double.parseDouble(donneAeroport[4]),
            Double.parseDouble(donneAeroport[5]));

        aeroport.put(donneAeroport[0], ae);
        volAeroport.put(ae, new HashSet<>());

      }
      fr.close();

      while ((line = br2.readLine()) != null) {

        String[] donneVols = line.split(",");

        Vol vl = new Vol(donneVols[0], donneVols[1], donneVols[2]);

        Aeroport ae = aeroport.get(donneVols[1]);
        volAeroport.get(ae).add(vl);

      }
      fr2.close();


    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void calculerItineraireMinimisantNombreVol(String src, String dest) {


    HashMap<String, Vol> mesOrigines = new HashMap<>();
    Set<String> mesPassages = new HashSet<>();
    Deque<String> quiFirst = new ArrayDeque<>();

    quiFirst.add(src);
    mesPassages.add(src);

    Boolean trouve = false;

    while (!trouve && !quiFirst.isEmpty()) {
      String vol = quiFirst.pop();
      Aeroport ae = aeroport.get(vol);

      for (Vol v : volAeroport.get(ae)
      ) {

        String d = v.getIataDestination();

        if (!mesPassages.contains(d)) {
          mesPassages.add(d);
          quiFirst.add(d);
          mesOrigines.put(d, v);
        }//fin if

        if (d.equals(dest)) {
          trouve = true;
        }//fin if

      }//fin for


    } //fin while

    ArrayList<Vol> leCheminLePlusCourtInverse = new ArrayList<>();
    String d = dest;

    while (!d.equals(src)) {

      Vol v = mesOrigines.get(d);
      leCheminLePlusCourtInverse.add(v);
      d = v.getIataSource();

    } //fin while
    double distanceFinal=0.0;
    String sortietemp ="";
    Collections.reverse(leCheminLePlusCourtInverse);
    for (Vol v : leCheminLePlusCourtInverse
    ) {
      Aeroport aeroportsrc = aeroport.get(v.getIataSource());
      Aeroport aeroportdest = aeroport.get(v.getIataDestination());
      double dist = Util.distance(aeroportsrc.getLatitude(), aeroportsrc.getLongitude(), aeroportdest.getLatitude(), aeroportdest.getLongitude());
      distanceFinal+=dist;
      sortietemp += "Vol [source=" + aeroportsrc.getNom() + ", destination=" + aeroportdest.getNom() + ", airline=" +
          v.getCompagnie() + "; distance=" +
          dist + "]\n";
    } //fin for
    System.out.println(distanceFinal);
    System.out.println(sortietemp); //fin for


  }

  public void calculerItineraireMiniminantDistance(String src, String dest) {

    for (Map.Entry<String, Aeroport> set :
        aeroport.entrySet()) {
      set.getValue().setMinDistance(Double.MAX_VALUE);
    }

    HashMap<Aeroport, Vol> mesOrigines = new HashMap<>();


    Comparator<Aeroport> com = new MyComparator();

    TreeSet<Aeroport> quiFirst = new TreeSet<>(com);
    Set<Aeroport> finito = new HashSet<>();

    Aeroport src1 = aeroport.get(src);
    src1.setMinDistance(0.0);

    quiFirst.add(src1);
    boolean firstDest = false;

    while (!firstDest && !quiFirst.isEmpty()) {

      Aeroport ae = aeroport.get(quiFirst.pollFirst().getIata());
      firstDest = ae.getIata() == dest;
      finito.add(ae);

      for (Vol v : volAeroport.get(ae)) {

        Aeroport aDest = aeroport.get(v.getIataDestination());

        if (!finito.contains(aDest)) {
          Double distance = Util.distance(ae.getLatitude(), ae.getLongitude(), aDest.getLatitude(),
              aDest.getLongitude()) + ae.getMinDistance();
          Double val = aDest.getMinDistance();
          if (val == null || val > distance) {

            if(!(val==null)) {
              quiFirst.remove(aDest);
            }
            aDest.setMinDistance(distance);
            aDest.setMinDistance(distance);
            quiFirst.add(aDest);
            mesOrigines.put(aDest, v);

          }//fin if

        }//fin if

      }//fin for
    }//fin while

    ArrayList<Vol> leCheminLePlusCourtInverse = new ArrayList<>();
    String d = dest;

    while (!d.equals(src)) {

      Aeroport ae = aeroport.get(d);
      Vol v = mesOrigines.get(ae);
      leCheminLePlusCourtInverse.add(v);
      d = v.getIataSource();

    } //fin while
    String sortietemp ="";
    Collections.reverse(leCheminLePlusCourtInverse);
    for (Vol v : leCheminLePlusCourtInverse
    ) {
      Aeroport aeroportsrc = aeroport.get(v.getIataSource());
      Aeroport aeroportdest = aeroport.get(v.getIataDestination());
      double dist = Util.distance(aeroportsrc.getLatitude(), aeroportsrc.getLongitude(), aeroportdest.getLatitude(), aeroportdest.getLongitude());
      sortietemp += "Vol [source=" + aeroportsrc.getNom() + ", destination=" + aeroportdest.getNom() + ", airline=" +
          v.getCompagnie() + "; distance=" +
          dist + "]\n";
    } //fin for
    System.out.println(aeroport.get(dest).getMinDistance());
    System.out.println(sortietemp);
  }//fin fonction

}

class MyComparator implements Comparator<Aeroport> {

  @Override
  public int compare(Aeroport o1, Aeroport o2) {

    if (o1.getMinDistance() != o2.getMinDistance()) {
        return Double.compare(o1.getMinDistance(), o2.getMinDistance());
      } else {
        return o1.getIata().compareTo(o2.getIata());
      }
  }
}