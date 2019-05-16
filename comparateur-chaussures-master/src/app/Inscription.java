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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import db.AccesDB;
import helpers.Helpers;
import app.listeners.RetourAction;

/**
 * Inscription
 */
public class Inscription extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField login;
    private JTextField nom;
    private JTextField prenom;
    private JPasswordField pwd;
    private JPasswordField pwd2;

    private JLabel prenomLabel;
    private JLabel nomLabel;
    private JLabel loginLabel;
    private JLabel pwdLabel;
    private JLabel pwdLabel2;
    private JLabel retourLabel;

    private JButton loginButton;
    private JButton exitButton;

    private JPanel nomprenom;
    private JPanel top;
    private JPanel bottom;
    private JPanel retour;

    private JTextField[] fields;
    private JPasswordField[] pwds;

    private AccesDB db;

    private MainPanel parent;

    public Inscription(MainPanel parent) {
        this.parent = parent;
        // champs de connexion
        top = new JPanel();

        // boutons
        bottom = new JPanel();

        // nom prenom
        nomprenom = new JPanel();

        // revenir a l'ecran de connexion
        retour = new JPanel();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        /**
         * titre du component
         */
        TitledBorder title = new TitledBorder("Inscription");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleFont(new Font("sans serif", Font.BOLD, 16));

        setBorder(title);
        setPreferredSize(new Dimension(300, 300));

        top.setLayout(new GridLayout(6, 1));

        prenom = new JTextField();
        nom = new JTextField();
        login = new JTextField();
        pwd = new JPasswordField();
        pwd2 = new JPasswordField();

        fields = new JTextField[] { login, nom, prenom };
        pwds = new JPasswordField[] { pwd, pwd2 };

        nomLabel = new JLabel("Nom");
        prenomLabel = new JLabel("Prénom");
        loginLabel = new JLabel("Identifiant");
        pwdLabel = new JLabel("Mot de passe");
        pwdLabel2 = new JLabel("Confirmez le mot de passe");
        retourLabel = new JLabel("Retour");
        retourLabel.setForeground(Color.blue.darker());
        retourLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retourLabel.addMouseListener(new RetourAction(parent, retourLabel, "connexion"));

        nomLabel.setLabelFor(nom);
        prenomLabel.setLabelFor(prenom);
        loginLabel.setLabelFor(login);
        pwdLabel.setLabelFor(pwd);
        pwdLabel2.setLabelFor(pwd2);

        nomprenom.setLayout(new GridLayout(2, 2, 10, 0));
        nomprenom.add(nomLabel);
        nomprenom.add(prenomLabel);
        nomprenom.add(nom);
        nomprenom.add(prenom);

        top.add(loginLabel);
        top.add(login);
        top.add(pwdLabel);
        top.add(pwd);
        top.add(pwdLabel2);
        top.add(pwd2);

        retour.setLayout(new BorderLayout());
        retour.add(retourLabel, BorderLayout.EAST);

        loginButton = new JButton("S'inscrire");
        exitButton = new JButton("Quitter");
        loginButton.addActionListener(new LoginAction());
        exitButton.addActionListener(new ExitAction());
        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(loginButton);
        bottom.add(exitButton);

        add(nomprenom);
        add(top);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(retour);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(bottom);
    }

    private class LoginAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkValidity()) {
                try {
                    db = new AccesDB();
                    db.newUser(nom.getText(), prenom.getText(), login.getText(),
                            Helpers.SHA1(String.valueOf(pwd.getPassword())));
                    JOptionPane.showMessageDialog(parent, "Vous êtes inscrit ! Veuillez vous connecter.", null,
                            JOptionPane.INFORMATION_MESSAGE);
                    parent.invoke("connexion");
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.close();
                }
            }
        }
    }

    private class ExitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public boolean checkValidity() {
        boolean emptyFields;
        boolean nameNumber;
        boolean username;
        boolean passwords;
        resetFields();

        emptyFields = checkEmptyFields();
        nameNumber = nameContainsNumber();
        username = checkUsername();
        passwords = checkPasswords();

        return emptyFields && nameNumber && username && passwords;
    }

    private void resetFields() {
        // reinitialise les champs à leur état initial (sans tooltip et bordures)
        for (JTextField f : fields) {
            f.setBorder(new JTextField().getBorder());
            f.setToolTipText(null);
        }

        for (JPasswordField f : pwds) {
            f.setBorder(new JPasswordField().getBorder());
            f.setToolTipText(null);
        }
    }

    private boolean checkEmptyFields() {
        // vérifie si les champs sont vides, erreurs si un des champs est vide
        boolean res = true;
        for (JTextField f : fields) {
            if (f.getText().equals("")) {
                f.setBorder(new LineBorder(Color.red));
                f.setToolTipText("⚠ Veuillez renseigner le champ !");
                res = false;
            }
        }

        for (JPasswordField f : pwds) {
            if (String.valueOf(f.getPassword()).equals("")) {
                f.setBorder(new LineBorder(Color.red));
                f.setToolTipText("⚠ Veuillez renseigner le champ !");
                res = false;
            }
        }
        return res;
    }

    private boolean nameContainsNumber() {
        // verifier que le nom et le prenom ne contiennent pas de chiffres
        boolean res = true;
        if (nom.getText().matches(".*\\d+.*")) {
            res = false;
            nom.setBorder(new LineBorder(Color.red));
            nom.setToolTipText("⚠ Les chiffres ne sont pas autorisés !");
        }
        if (prenom.getText().matches(".*\\d+.*")) {
            res = false;
            prenom.setBorder(new LineBorder(Color.red));
            prenom.setToolTipText("⚠ Les chiffres ne sont pas autorisés !");
        }
        return res;
    }

    private boolean checkUsername() {
        // vérifie que le nom d'utilisateur n'est pas utilisé
        boolean res = true;
        try {
            if (!login.getText().equals("")) {
                db = new AccesDB();
                res = db.isUsernameValid(login.getText());
                if (!res) {
                    login.setToolTipText("⚠ Ce nom d'utilisateur est déjà pris !");
                    login.setBorder(new LineBorder(Color.red));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private boolean checkPasswords() {
        // vérifie que les mdp sont identiques
        boolean res = true;
        if (!String.valueOf(pwd.getPassword()).equals(String.valueOf(pwd2.getPassword()))) {
            res = false;
            pwd.setBorder(new LineBorder(Color.red));
            pwd2.setBorder(new LineBorder(Color.red));
            pwd2.setToolTipText("⚠ Les mots de passes ne sont pas identiques !");
        }
        return res;
    }
}