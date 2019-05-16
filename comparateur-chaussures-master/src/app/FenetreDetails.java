package app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import app.entities.Chaussure;
import app.entities.Client;
import app.entities.Reservation;



/**
 * FenetreDetails
 */
public class FenetreDetails extends JFrame {

    private JButton reserver;
    private Chaussure chaussure;
    private String reserverState;
    private ReservationsGrid rg;

    public FenetreDetails(Chaussure chaussure, String reserverState, ReservationsGrid rg) {
        this.chaussure = chaussure;
        this.reserverState = reserverState;
        this.rg = rg;

        pack();
        setSize(300, 400);
        setLocationRelativeTo(null);
        JPanel master = new JPanel();
        JPanel details = new JPanel();
        JPanel reserverPanel = new JPanel();
        JLabel image = new JLabel(chaussure.getImg());
        JLabel nom = new JLabel("Nom: " + chaussure.getNom());
        JLabel marque = new JLabel("Marque: " + chaussure.getMarque());
        JLabel style = new JLabel("Style: " + chaussure.getStyle());
        JLabel prix = new JLabel("Prix: " + chaussure.getPrix() + "€");
        JLabel quantite = new JLabel("Quantité: " + chaussure.getQuantite());
        
        reserver = new JButton(reserverState);
        reserver.addActionListener(new ReservationAction());

        master.setLayout(new BoxLayout(master, BoxLayout.Y_AXIS));
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        reserverPanel.setLayout(new FlowLayout());

        details.add(image);
        details.add(Box.createRigidArea(new Dimension(0, 20)));
        details.add(nom);
        details.add(marque);
        details.add(style);
        details.add(prix);
        details.add(quantite);
        reserverPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        reserverPanel.add(reserver);
        master.add(details);
        master.add(reserverPanel);
        add(master);
    }

    private class ReservationAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: debug remettre EcranConnexion.USER
            Reservation r = new Reservation((Client) EcranConnexion.USER, chaussure);
            if (e.getActionCommand().equals("Réserver")) {
                if (chaussure.getQuantite().equals("0")) {
                    JOptionPane.showMessageDialog(null, "Article en rupture de stock", null, JOptionPane.WARNING_MESSAGE);
                } else if (!chaussure.getQuantite().equals("0") && r.makeReservation()) {
                    JOptionPane.showMessageDialog(null, "Réservation effectuée avec succès", null, JOptionPane.INFORMATION_MESSAGE);
                    FenetreDetails.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Article déjà réservé", null, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                r.supprimerReservation();
                JOptionPane.showMessageDialog(null, "Suppression effectuée avec succès", null, JOptionPane.INFORMATION_MESSAGE);
                FenetreDetails.this.dispose();
                rg.drawReservationsGrid();
            }
        }
    }
}