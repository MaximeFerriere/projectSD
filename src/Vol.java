public class Vol {

  private String compagnie;
  private String iataSource;
  private String iataDestination;

  public Vol(String compagnie, String iataSource, String iataDestination) {
    this.compagnie = compagnie;
    this.iataSource = iataSource;
    this.iataDestination = iataDestination;
  }

  public String getCompagnie() {
    return compagnie;
  }

  public void setCompagnie(String compagnie) {
    this.compagnie = compagnie;
  }

  public String getIataSource() {
    return iataSource;
  }

  public void setIataSource(String iataSource) {
    this.iataSource = iataSource;
  }

  public String getIataDestination() {
    return iataDestination;
  }

  public void setIataDestination(String iataDestination) {
    this.iataDestination = iataDestination;
  }

  @Override
  public String toString() {
    return "Vol{" +
        "compagnie='" + compagnie + '\'' +
        ", iataSource='" + iataSource + '\'' +
        ", iataDestination='" + iataDestination + '\'' +
        '}';
  }
}
