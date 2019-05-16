package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.entities.Chaussure;
import app.entities.Client;
import app.entities.Reservation;

/**
 * DataToChaussure
 */
public class ChaussuresGrid extends JPanel {

    private ChaussurePanel[] chaussurePanels;
    private String[][] data;
    private String reserverState;
    private ReservationsGrid rg;

    public ChaussuresGrid(String[][] data, String reserverState) {
        this.data = data;
        this.reserverState = reserverState;
        this.chaussurePanels = new ChaussurePanel[data.length];
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        createPanels();
    }

    public ChaussuresGrid(String[][] data, String reserverState, ReservationsGrid rg) {
        this(data, reserverState);
        this.rg = rg;
    }

    public void createPanels() {
        Chaussure c;
        for (int i = 0; i < data.length; i++) {
            c = new Chaussure(data[i]);
            chaussurePanels[i] = new ChaussurePanel(c);
            add(chaussurePanels[i]);
        }
    }

    public boolean isEmpty() {
        if (data.length == 0) return true;
        return false;
    }

    public class ChaussurePanel extends JPanel {

        private JButton voir;

        private JPanel details;
        private JPanel imagePanel;
        private Chaussure c;
        private JLabel image;

        public ChaussurePanel(Chaussure c) {
            this.c = c;
            setLayout(new FlowLayout());

            JLabel nom = new JLabel("Nom: " + c.getNom());
            JLabel marque = new JLabel("Marque: " + c.getMarque());
            JLabel style = new JLabel("Style: " + c.getStyle());
            JLabel prix = new JLabel("Prix: " + c.getPrix() + "€");
            
            image = new JLabel(c.getImg());
            voir = new JButton("Voir");
            image.setCursor(new Cursor(Cursor.HAND_CURSOR));
            image.addMouseListener(new VoirAction());
            
            imagePanel = new JPanel();
            details = new JPanel();
            details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
            
            voir.addActionListener(new VoirAction());
            
            if (reserverState.equals("Supprimer")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à k:mm");
                Reservation r = new Reservation((Client) EcranConnexion.USER, c);
                JLabel date = new JLabel("Réservation effectuée le " + dateFormat.format(r.getDate()));
                details.add(date);
            }

            details.add(nom);
            details.add(marque);
            details.add(style);
            details.add(prix);
            details.add(Box.createRigidArea(new Dimension(0, 20)));
            details.add(voir);
            imagePanel.add(image);
            add(imagePanel);
            add(details);
        }

        /**
         * @return the c
         */
        public Chaussure getC() {
            return c;
        }

        private class VoirAction extends MouseAdapter implements ActionListener {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                showDetails();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showDetails();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                image.setBorder(BorderFactory.createLineBorder(Color.blue.darker()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                image.setBorder(BorderFactory.createEmptyBorder());
            }

            public void showDetails() {
                JFrame f = new FenetreDetails(c, reserverState, rg);
                f.setVisible(true);
            }
        }
    }

}