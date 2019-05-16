package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import app.entities.Chaussure;
import app.entities.Client;
import app.entities.Reservation;

/**
 * ConnexionDB
 */
public class AccesDB {
    private ResultSet rs = null;
    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement ps = null;

    public AccesDB() throws Exception {
        String url = "jdbc:mysql://localhost/Comparateur?useSSL=false&allowMultiQueries=true";
        String username = "wadii";
        String pwd = "123";
        con = DriverManager.getConnection(url, username, pwd);
    }

    /**
     * @return the rs
     */
    public String[][] getAll() throws Exception {

        stmt = con.createStatement();
        rs = stmt.executeQuery("select * from Chaussures;");
        int c = rs.getMetaData().getColumnCount();
        String[][] res = new String[getRowCount()][c];

        return toTable(res, rs, c);
    }

    public String[][] getResAdmin() throws Exception {

        stmt = con.createStatement();
        rs = stmt.executeQuery("select ID, nom, couleur, marque, style, quantite, prix from Chaussures;");
        int c = rs.getMetaData().getColumnCount();
        String[][] res = new String[getRowCount()][c];

        return toTable(res, rs, c);
    }

    private String[][] toTable(String[][] res, ResultSet rs, int column) throws SQLException {
        int row = 0;
        while (rs.next()) {
            for (int i = 0; i < column; i++) {
                res[row][i] = rs.getString(i + 1);
            }
            row++;
        }
        return res;
    }

    public String[][] getRecherche(String couleur, String marque, String style, String prixMin, String prixMax)
            throws Exception {
        ps = buildQuery(couleur, marque, style, prixMin, prixMax);
        rs = ps.executeQuery();
        int column = rs.getMetaData().getColumnCount();
        String[][] res = new String[getRowCount()][column];
        return toTable(res, rs, column);
    }

    private PreparedStatement buildQuery(String couleur, String marque, String style, String prixMin, String prixMax)
            throws Exception {
        String couleurCondition = "";
        String marqueCondition = "";
        String styleCondition = "";
        int numConditions = 0;
        int i = 0; int j = 0; int k = 0;

        if (!couleur.equals("")) {
            couleurCondition = "couleur=? and ";
            numConditions++;
            i = numConditions;
        }
        if (!marque.equals("")) {
            marqueCondition = "marque=? and ";
            numConditions++;
            j = numConditions;
        }
        if (!style.equals("")) {
            styleCondition = "style=? and ";
            numConditions++;
            k = numConditions;
        }

        String query = "select * from Chaussures where " + couleurCondition
                + marqueCondition + styleCondition + "prix between ? and ?;";
        ps = con.prepareStatement(query);
        if (i != 0) ps.setString(i, couleur);
        if (j != 0) ps.setString(j, marque);
        if (k != 0) ps.setString(k, style);
        ps.setString(numConditions+1, prixMin);
        ps.setString(numConditions+2, prixMax);
        return ps;
    }

    public String[][] getReservations(Client u) throws Exception {
        String query = "select ch.* from Chaussures ch, Reservations r where r.ID_client=? and ch.ID=r.ID_chaussure;";
        ps = con.prepareStatement(query);
        ps.setString(1, u.getId());
        rs = ps.executeQuery();
        int column = rs.getMetaData().getColumnCount();
        String[][] res = new String[getRowCount()][column];
        return toTable(res, rs, column);
    }

    public Date getDate(Reservation r) throws Exception {
        String query = "select date_reservation from Reservations where ID_client=? and ID_chaussure=?;";
        ps = con.prepareStatement(query);
        ps.setString(1, r.getIDUser());
        ps.setString(2, r.getIDChaussure());
        rs = ps.executeQuery();
        rs.next();
        return rs.getTimestamp(1);
    }

    private int getRowCount() throws Exception {
        rs.last();
        int count = rs.getRow();
        rs.beforeFirst();
        return count;
    }

    public boolean getIdAndPwd(String id, String pwd, String type) throws Exception {
        String query = "select nom, prenom from " + type + " where identifiant=? and mdp=?;";
        ps = con.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, pwd);
        rs = ps.executeQuery();
        return rs.next() ? true : false;
    }

    public boolean isUsernameValid(String username) throws Exception {
        String query = "select exists(select 1 from Clients where identifiant=?);";
        ps = con.prepareStatement(query);
        ps.setString(1, username);
        rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) == 0 ? true : false;
    }

    public void newUser(String nom, String prenom, String login, String pwd) throws Exception {
        String query = "insert into Clients (nom, prenom, identifiant, mdp) values(?, ?, ?, ?);";
        ps = con.prepareStatement(query);
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setString(3, login);
        ps.setString(4, pwd);
        ps.executeUpdate();
    }

    public void newReservation(Reservation r) throws Exception {
        String query = "insert into Reservations (ID_client, ID_chaussure) values (?, ?); update Chaussures set quantite=quantite-1 where ID=? and quantite > 0;";
        ps = con.prepareStatement(query);
        ps.setString(1, r.getIDUser());
        ps.setString(2, r.getIDChaussure());
        ps.setString(3, r.getIDChaussure());
        ps.executeUpdate();
    }

    public void setChaussure(Chaussure c) throws Exception {
        String query = "insert into Chaussures (nom, couleur, marque, quantite, prix, filename, style) values (?, ?, ?, ?, ?, ?, ?);";
        ps = con.prepareStatement(query);
        ps.setString(1, c.getNom());
        ps.setString(2, c.getCouleur());
        ps.setString(3, c.getMarque());
        ps.setString(4, c.getQuantite());
        ps.setFloat(5, Float.parseFloat(c.getPrix()));
        ps.setString(6, c.getFilename());
        ps.setString(7, c.getStyle());
        ps.executeUpdate();
    }

    public void delReservation(Reservation r) throws Exception {
        String query = "delete from Reservations where ID_client=? and ID_chaussure=?; update Chaussures set quantite=quantite+1 where ID=?;";
        ps = con.prepareStatement(query);
        ps.setString(1, r.getIDUser());
        ps.setString(2, r.getIDChaussure());
        ps.setString(3, r.getIDChaussure());
        ps.executeUpdate();
    }

    public void deleteChaussure(Chaussure c) throws Exception {
        String query = "delete from Chaussures where ID=?;";
        ps = con.prepareStatement(query);
        ps.setString(1, c.getId());
        ps.executeUpdate();
    }

    public boolean isReserved(Chaussure c) throws Exception {
        String query = "select exists(select 1 from Reservations where ID_chaussure=?);";
        ps = con.prepareStatement(query);
        ps.setString(1, c.getId());
        rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) == 1 ? true : false;
    }

    public String[] getInfosUser(String username, String type) throws Exception {
        String query = "select ID, nom, prenom from " + type + " where identifiant=?;";
        String[] res = new String[3];
        ps = con.prepareStatement(query);
        ps.setString(1, username);
        rs = ps.executeQuery();
        rs.next();
        res[0] = rs.getString(1);
        res[1] = rs.getString(2);
        res[2] = rs.getString(3);
        return res;
    }

    public String[] getElementList(String col) throws Exception {
        int i = 0;
        ps = con.prepareStatement("select distinct " + col + " from Chaussures order by " + col + ";");
        rs = ps.executeQuery();
        String[] res = new String[getRowCount()];
        while (rs.next()) {
            res[i] = rs.getString(1);
            i++;
        }
        return res;
    }

    public String getFilename(String produit) throws Exception {
        ps = con.prepareStatement("select filename from Chaussures where nom=?;");
        ps.setString(1, produit);
        rs = ps.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (con != null) {
                con.close();
            }

            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {

        }
    }

}