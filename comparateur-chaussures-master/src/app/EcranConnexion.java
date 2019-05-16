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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import db.AccesDB;
import app.entities.Administrateur;
import app.entities.Client;
import app.entities.Utilisateur;
import helpers.Helpers;

/**
 * EcranConnexion
 */
public class EcranConnexion extends JPanel {

    private static final long serialVersionUID = 1L;
    public static Utilisateur USER = null;
    
    private JPanel top;
    private JPanel bottom;
    private JPanel inscription;
    private JPanel choix;

    private JTextField login;
    private JPasswordField pwd;
    private JLabel loginLabel;
    private JLabel pwdLabel;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel nouveauCompte;

    private JRadioButton admin;
    private JRadioButton client;

    private String[] infosUser = new String[3];

    private AccesDB db;


    private MainPanel parent;

    public EcranConnexion(MainPanel parent) {
        this.parent = parent;
        // champs de connexion
        top = new JPanel();

        // inscription
        inscription = new JPanel();

        // boutons
        bottom = new JPanel();

        // choix
        choix = new JPanel(new FlowLayout());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /**
         * titre du component
         */
        TitledBorder title = new TitledBorder("Écran de connexion");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleFont(new Font("sans serif", Font.BOLD, 16));

        setBorder(title);

        top.setLayout(new GridLayout(4, 1));

        login = new JTextField();
        pwd = new JPasswordField();
        loginLabel = new JLabel("Identifiant");
        pwdLabel = new JLabel("Mot de passe");
        pwd.addActionListener(new LoginAction());
        nouveauCompte = new JLabel("S'inscrire ?");
        nouveauCompte.setForeground(Color.blue.darker());
        nouveauCompte.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nouveauCompte.addMouseListener(new InscriptionAction());

        loginLabel.setLabelFor(login);
        pwdLabel.setLabelFor(pwd);
        top.add(loginLabel);
        top.add(login);
        top.add(pwdLabel);
        top.add(pwd);

        inscription.setLayout(new BorderLayout());
        inscription.add(nouveauCompte, BorderLayout.EAST);

        admin = new JRadioButton("Administrateur");
        client = new JRadioButton("Client");
        client.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(admin);
        group.add(client);
        choix.add(admin);
        choix.add(client);

        loginButton = new JButton("Se connecter");
        exitButton = new JButton("Quitter");
        loginButton.addActionListener(new LoginAction());
        exitButton.addActionListener(new ExitAction());
        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(loginButton);
        bottom.add(exitButton);

        add(top);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(inscription);
        add(choix);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(bottom);
    }

    private class LoginAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (client.isSelected())
                isUtilisateur("Clients");
            if (admin.isSelected())
                isUtilisateur("Administrateurs");
        }
    }

    private class ExitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);

        }
    }

    private class InscriptionAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            parent.invoke("inscription");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            nouveauCompte.setText("S'inscrire ?");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            nouveauCompte.setText("<html><a href=''>S'inscrire ?</a></html>");
        }
    }

    private void isUtilisateur(String type) {
        String chiffre = Helpers.SHA1(String.valueOf(pwd.getPassword()));
        boolean res;
        try {
            db = new AccesDB();
            res = db.getIdAndPwd(login.getText(), chiffre, type);
            if (res) {
                infosUser = db.getInfosUser(login.getText(), type);
                if (type.equals("Administrateurs"))
                    USER = new Administrateur(infosUser[0], infosUser[1], infosUser[2], login.getText());
                if (type.equals("Clients")) {
                    USER = new Client(infosUser[0], infosUser[1], infosUser[2], login.getText());
                }
                System.out.println(USER instanceof Administrateur);
                parent.invoke("menuPrincipal");
            } else {
                JOptionPane.showMessageDialog(parent, "Identifiant ou mot de passe erroné", null,
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}