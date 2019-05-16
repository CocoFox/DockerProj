package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import db.AccesDB;
import app.entities.Chaussure;
import app.listeners.RetourAction;

/**
 * Dashboard
 */
public class Dashboard extends JPanel {

    private AccesDB db;
    private JTable tbc = null;
    private JScrollPane tableau = null;
    private JPopupMenu context;
    private JMenuItem supprimer;

    public Dashboard(MainPanel parent) {
        JPanel titlePanel = new JPanel();
        JPanel commandesPanel = new JPanel(new FlowLayout());
        JLabel title = new JLabel("Tableau de bord");
        JLabel retour = new JLabel("Retour");
        JButton ajouter = new JButton("Ajouter une entrée");
        retour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retour.setForeground(Color.blue.darker());
        retour.addMouseListener(new RetourAction(parent, retour, "menuPrincipal"));
        ajouter.addActionListener(new AjouterAction());
        title.setFont(new Font("sans serif", Font.BOLD, 20));

        
        titlePanel.add(title);
        commandesPanel.add(ajouter);
        commandesPanel.add(retour);
        add(titlePanel);
        drawTable();
        add(commandesPanel);
    }

    public void drawTable() {
        try {
            db = new AccesDB();
            tbc = new JTable(new TableauChaussures(db.getResAdmin()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Dimension tailleTableau = new Dimension(tbc.getColumnCount() * 110, tbc.getRowHeight() * tbc.getRowCount());
        context = new JPopupMenu();
        supprimer = new JMenuItem("Supprimer");
        supprimer.addActionListener(new SupprimerAction());
        context.add(supprimer);

        tbc.setAutoCreateRowSorter(true);
        tbc.setPreferredScrollableViewportSize(tailleTableau);
        tbc.addMouseListener(new DashboardPopup());
        if (tableau != null) remove(tableau);
        tableau = new JScrollPane(tbc);
        add(tableau, 1);
        revalidate();
    }

    class DashboardPopup extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            showPopUp(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopUp(e);
        }

        private void showPopUp(MouseEvent e) {
            context.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    class SupprimerAction implements ActionListener {

        private Chaussure c;

        @Override
        public void actionPerformed(ActionEvent e) {
            c = new Chaussure((String) tbc.getValueAt(tbc.getSelectedRow(), 0));
            try {
                db = new AccesDB();
                db.deleteChaussure(c);
                JOptionPane.showMessageDialog(getParent(), "Entrée supprimée avec succès", null, JOptionPane.INFORMATION_MESSAGE);
                drawTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getParent(), "Erreur inconnue", null, JOptionPane.WARNING_MESSAGE);
                ex.printStackTrace();
            } finally {
                db.close();
            }
        }
    }

    class AjouterAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame f = new FenetreAjouter(getParent(), Dashboard.this);
            f.setVisible(true);
        }
    }
}