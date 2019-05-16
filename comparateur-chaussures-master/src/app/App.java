package app;

public class App {
  public static void main(String[] args) {

    try {
      IHM ihm = new IHM("Comparateur de chaussures", 800, 600);
      ihm.setVisible(true);
    } catch (Exception e) {
    }
  }
}