package app;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.entities.Chaussure;
import db.AccesDB;

/**
 * FenetreAjouter
 */
public class FenetreAjouter extends JFrame {

    private Dashboard d;
    public FenetreAjouter(Container parent, Dashboard d) {
        this.d = d;

        pack();
        setSize(300, 300);
        setLocationRelativeTo(parent);
        JPanel panel = new AjouterPanel();
        add(panel);
    }

    class AjouterPanel extends JPanel {

        private JTextField nomField;
        private JTextField couleurField;
        private JTextField marqueField;
        private JTextField quantiteField;
        private JTextField prixField;
        private JTextField styleField;

        private AccesDB db;

        private AjouterPanel() {
            JPanel panel = new JPanel();
            JPanel boutonPanel = new JPanel();
            panel.setLayout(new GridLayout(6, 2, -40, 10));

            JLabel nomLabel = new JLabel("Nom");
            JLabel couleurLabel = new JLabel("Couleur");
            JLabel marqueLabel = new JLabel("Marque");
            JLabel quantiteLabel = new JLabel("Quantité");
            JLabel prixLabel = new JLabel("Prix");
            JLabel styleLabel = new JLabel("Style");
            nomField = new JTextField(12);
            couleurField = new JTextField();
            marqueField = new JTextField();
            quantiteField = new JTextField();
            prixField = new JTextField();
            styleField = new JTextField();
            nomField = new JTextField("CONVERSE", 12);
            couleurField = new JTextField("Bleue");
            marqueField = new JTextField("All Stars");
            quantiteField = new JTextField("15");
            prixField = new JTextField("60");
            styleField = new JTextField("Tennis");
            
            JButton valider = new JButton("Valider");
            valider.addActionListener(new AjouterChaussureAction());

            nomLabel.setLabelFor(nomField);
            couleurLabel.setLabelFor(couleurField);
            marqueLabel.setLabelFor(marqueField);
            quantiteLabel.setLabelFor(quantiteField);
            prixLabel.setLabelFor(prixField);
            styleLabel.setLabelFor(styleField);

            panel.add(nomLabel);
            panel.add(nomField);
            panel.add(couleurLabel);
            panel.add(couleurField);
            panel.add(marqueLabel);
            panel.add(marqueField);
            panel.add(quantiteLabel);
            panel.add(quantiteField);
            panel.add(prixLabel);
            panel.add(prixField);
            panel.add(styleLabel);
            panel.add(styleField);
            boutonPanel.add(valider);

            add(panel);
            add(boutonPanel);
        }

        public class AjouterChaussureAction implements ActionListener {

            private Chaussure c;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c = new Chaussure(new String[]{ "", nomField.getText(), couleurField.getText(),
                        marqueField.getText(), quantiteField.getText(), prixField.getText(),
                        nomField.getText().toLowerCase().replace(" ", ""), styleField.getText() });
                    db = new AccesDB();
                    db.setChaussure(c);
                    JOptionPane.showMessageDialog(getParent(), "Entrée ajoutée avec succès", null, JOptionPane.INFORMATION_MESSAGE);
                    d.drawTable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.close();
                }
            }
        }
    }
}
