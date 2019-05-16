package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.entities.Client;
import app.listeners.RetourAction;
import db.AccesDB;

/**
 * ReservationsGrid
 */
public class ReservationsGrid extends JPanel {

    private MainPanel parent;
    private AccesDB db;
    private ChaussuresGrid chaussuresGrid = null;

    public ReservationsGrid(MainPanel parent) {
        this.parent = parent;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel titre = new JLabel("Mes Réservations");
        JPanel retour = new JPanel(new FlowLayout());
        JLabel retourLabel = new JLabel("Retour");
        retourLabel.setForeground(Color.blue.darker());
        retourLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        titre.setFont(new Font("sans serif", Font.BOLD, 18));
        
        
        retourLabel.addMouseListener(new RetourAction(parent, retourLabel, "menuPrincipal"));
        retour.add(retourLabel);
        add(titre);
        add(Box.createRigidArea(new Dimension(0, 20)));
        drawReservationsGrid();
        add(retour);
    }

    public void drawReservationsGrid() {
        if (chaussuresGrid != null) remove(chaussuresGrid);
        try {
            db = new AccesDB();
            chaussuresGrid = new ChaussuresGrid(db.getReservations((Client) EcranConnexion.USER), "Supprimer", this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        add(chaussuresGrid, 2);
        revalidate();
    }
}