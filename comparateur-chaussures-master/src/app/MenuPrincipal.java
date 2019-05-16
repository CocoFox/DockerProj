package app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.entities.Administrateur;
import app.entities.Client;
import app.entities.Utilisateur;

/**
 * MenuPrincipal
 */
public class MenuPrincipal extends JPanel {

    // client
    private JButton recherche;
    private JButton monCompte;
    private JButton mesReservations;

    // admin
    private JButton dashboard;
    private JButton quitter;
    private JButton seDeconnecter;

    private JLabel welcome;

    private MainPanel parent;

    public MenuPrincipal(MainPanel parent, Utilisateur u) {
        this.parent = parent;
        setLayout(new GridLayout(10, 1, 0, 20));

        seDeconnecter = new JButton("Se deconnecter");
        quitter = new JButton("Quitter");

        seDeconnecter.addActionListener(new DeconnexionAction());
        quitter.addActionListener(new ExitAction());

        welcome = new JLabel();
        welcome.setText("<html>Bonjour <i>" + EcranConnexion.USER + "</i>. Que voulez-vous faire ?</html>");

        add(welcome);
        add(seDeconnecter);
        add(quitter);

        if (u instanceof Client) {
            recherche = new JButton("Recherche");
            mesReservations = new JButton("Mes reservations");
            recherche.addActionListener(new RechercheAction());
            mesReservations.addActionListener(new ReservationsAction());
            add(recherche, 1);
            add(mesReservations, 2);
        }

        if (u instanceof Administrateur) {
            dashboard = new JButton("Tableau de bord");
            dashboard.addActionListener(new DashboardAction());
            add(dashboard, 1);
        }
    }

    private class RechercheAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.invoke("recherche");
        }
    }

    private class ReservationsAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.invoke("reservations");
        }
    }

    private class DeconnexionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.invoke("connexion");
        }
    }

    private class ExitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class DashboardAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.invoke("dashboard");
        }
    }
}