package app;

import java.lang.reflect.Method;

import javax.swing.JPanel;

import app.entities.Administrateur;
import app.entities.Client;

/**
 * MainPanel
 */
public class MainPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private EcranConnexion ec;

    private MenuPrincipal menu;
    private Inscription insc;
    private Recherche rech;
    private ReservationsGrid res;
    private Dashboard dash;

    public MainPanel() {
        connexion();
        // menuPrincipal();
        // inscription();
        // recherche();
        // reservations();
    }

    public void recherche() {
        rech = new Recherche(this);
        add(rech);
    }

    public void menuPrincipal() {
        // menu = new MenuPrincipal(this, new Administrateur("1"));
        menu = new MenuPrincipal(this, EcranConnexion.USER);
        add(menu);
    }

    public void inscription() {
        insc = new Inscription(this);
        add(insc);
    }

    public void connexion() {
        ec = new EcranConnexion(this);
        add(ec);
    }

    public void reservations() {
        res = new ReservationsGrid(this);
        add(res);
    }

    public void dashboard() {
        dash = new Dashboard(this);
        add(dash);
    }

    public void invoke(String name) {
        try {
            Method method = MainPanel.class.getDeclaredMethod(name);
            removeAll();
            invalidate();
            method.invoke(this);
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}