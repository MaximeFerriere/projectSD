import java.util.Objects;

public class Aeroport {

  private String iata;
  private String nom;
  private String ville;
  private String pays;
  private double longitude;
  private double latitude;
  private double minDistance = Double.MAX_VALUE;


  public Aeroport(String iata, String nom, String ville, String pays, double longitude,
      double latitude) {
    this.iata = iata;
    this.nom = nom;
    this.ville = ville;
    this.pays = pays;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public double getMinDistance() {
    return minDistance;
  }

  public void setMinDistance(double minDistance) {
    this.minDistance = minDistance;
  }

  public String getIata() {
    return iata;
  }

  public void setIata(String iata) {
    this.iata = iata;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public String getPays() {
    return pays;
  }

  public void setPays(String pays) {
    this.pays = pays;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((iata == null) ? 0 : iata.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Aeroport other = (Aeroport) obj;
    if (iata == null) {
      if (other.iata != null)
        return false;
    } else if (!iata.equals(other.iata))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Aeroport{" +
        "iata='" + iata + '\'' +
        ", nom='" + nom + '\'' +
        ", ville='" + ville + '\'' +
        ", pays='" + pays + '\'' +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        '}';
  }
}
