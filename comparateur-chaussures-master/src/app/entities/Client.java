package app.entities;

/**
 * Client
 */
public class Client extends Utilisateur {

    public Client(String id) {
        super(id);
    }

    public Client(String id, String nom, String prenom, String identifiant) {
        super(id, nom, prenom, identifiant);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}