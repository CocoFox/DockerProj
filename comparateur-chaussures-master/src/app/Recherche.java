package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import app.entities.Chaussure;
import app.listeners.RetourAction;
import db.AccesDB;
import helpers.Helpers;

/**
 * Recherche
 */
public class Recherche extends JPanel {

    private JPanel comboPanel;
    private JPanel resPanel;
    private JPanel imagePanel;
    private JPanel recherchePanel;
    private JPanel retour;

    private JLabel couleursLabel;
    private JLabel marquesLabel;
    private JLabel styleLabel;
    private JLabel prixLabel;
    private JLabel retourLabel;

    private JButton comparaisonButton;

    private String[] couleurs;
    private String[] marques;
    private String[] styles;

    private JComboBox<String> couleursComboBox;
    private JComboBox<String> marquesComboBox;
    private JComboBox<String> stylesComboBox;
    private JTextField prixIntervalle;

    private AccesDB db;

    private MainPanel parent;

    private ChaussuresGrid cg;

    private TriResultat tr;

    public Recherche(MainPanel parent) {
        this.parent = parent;

        TitledBorder title = new TitledBorder("Les filtres");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleFont(new Font("sans serif", Font.BOLD, 16));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        comboPanel = new JPanel(new GridLayout(2, 4, 10, 0));
        recherchePanel = new JPanel();
        resPanel = new JPanel();
        imagePanel = new JPanel(new FlowLayout());
        retour = new JPanel(new BorderLayout());

        comboPanel.setBorder(title);
        comboPanel.setPreferredSize(new Dimension(500, 75));
        comboPanel.setMaximumSize(comboPanel.getPreferredSize());

        couleursLabel = new JLabel("Couleurs");
        marquesLabel = new JLabel("Marques");
        styleLabel = new JLabel("Styles");
        prixLabel = new JLabel("Prix (min-max)");
        comparaisonButton = new JButton("Lancer la recherche");
        retourLabel = new JLabel("Retour");
        prixIntervalle = new JTextField("0-1000", 8);

        retourLabel.addMouseListener(new RetourAction(parent, retourLabel, "menuPrincipal"));
        comparaisonButton.addActionListener(new ComparaisonAction());

        retourLabel.setForeground(Color.blue.darker());
        retourLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        couleursLabel.setLabelFor(couleursComboBox);
        marquesLabel.setLabelFor(marquesComboBox);
        prixLabel.setLabelFor(prixIntervalle);
        styleLabel.setLabelFor(stylesComboBox);

        try {
            db = new AccesDB();
            couleurs = db.getElementList("couleur");
            marques = db.getElementList("marque");
            styles = db.getElementList("style");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        couleursComboBox = new JComboBox<>(couleurs);
        marquesComboBox = new JComboBox<>(marques);
        stylesComboBox = new JComboBox<>(styles);
        couleursComboBox.insertItemAt("", 0);
        marquesComboBox.insertItemAt("", 0);
        stylesComboBox.insertItemAt("", 0);
        couleursComboBox.setSelectedIndex(0);
        marquesComboBox.setSelectedIndex(0);
        stylesComboBox.setSelectedIndex(0);

        comboPanel.add(couleursLabel);
        comboPanel.add(marquesLabel);
        comboPanel.add(styleLabel);
        comboPanel.add(prixLabel);
        comboPanel.add(couleursComboBox);
        comboPanel.add(marquesComboBox);
        comboPanel.add(stylesComboBox);
        comboPanel.add(prixIntervalle);

        recherchePanel.add(comparaisonButton);

        retour.add(retourLabel, BorderLayout.EAST);

        add(comboPanel);
        add(recherchePanel);
        add(resPanel);
        add(imagePanel);
        add(retour);
    }

    private void generateList() {
        resPanel.removeAll();
        try {
            db = new AccesDB();
            String couleur = (String) couleursComboBox.getSelectedItem();
            String marque = (String) marquesComboBox.getSelectedItem();
            String style = (String) stylesComboBox.getSelectedItem();
            String[] range = Helpers.splitString(prixIntervalle.getText(), "-");
            if (checkIntervalle(range)) {
                // cg = new ChaussuresGrid(db.getRecherche(couleur, marque, style, range[0], range[1]), "Réserver");
                // cg = new ChaussuresGrid(db.getAll(), "Réserver");
                tr = new TriResultat(db.getRecherche(couleur, marque, style, range[0], range[1]), Math.abs(Integer.parseInt(range[0]) - Integer.parseInt(range[1]))/2);
                cg = tr.getChaussuresTri();
                // System.out.println(tr.toString());
                if (cg.isEmpty()) JOptionPane.showMessageDialog(parent, "Pas de résultats trouvés", null, JOptionPane.WARNING_MESSAGE);
                resPanel.add(cg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        
        resPanel.revalidate();
    }

    private boolean checkIntervalle(String[] range) {
        if (range.length != 2 || Integer.parseInt(range[0]) > Integer.parseInt(range[1])) {
            JOptionPane.showMessageDialog(parent, "Veuillez entrer un intervalle de prix valide !", null, JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private class ComparaisonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            generateList();
        }
    }

    private class TriResultat {

        // private String[][] data;
        private ArrayList<String> arr = new ArrayList<>();
        private int prixMoyen;
        private String[][] data;

        public TriResultat(String[][] data, int prixMoyen) {
            this.prixMoyen = prixMoyen;
            this.data = data;
        }
    
        public ChaussuresGrid getChaussuresTri() {
            Helpers.sortByColumn(data, 5, prixMoyen);
            return new ChaussuresGrid(data, "Réserver");
        }
        
        @Override
        public String toString() {
            return arr.toString();
        }
    }
}