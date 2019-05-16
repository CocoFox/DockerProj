package app.entities;

import java.util.Date;

import db.AccesDB;

/**
 * Reservation
 */
public class Reservation {

    private Client u;
    private Chaussure c;

    private AccesDB db;

    public Reservation(Client u, Chaussure c) {
        this.u = u;
        this.c = c;
    }

    public boolean makeReservation() {
        boolean res = false;
        try {
            db = new AccesDB();
            // if (!db.isReserved(c)) {
                db.newReservation(this);
                res = true;
            // }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return res;
    }

    public void supprimerReservation() {
        try {
            db = new AccesDB();
            db.delReservation(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public String getIDUser() {
        return u.getId();
    }

    public String getIDChaussure() {
        return c.getId();
    }

    /**
     * @return the date
     */
    public Date getDate() {
        try {
            db = new AccesDB();
            return db.getDate(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return null;
    }
}