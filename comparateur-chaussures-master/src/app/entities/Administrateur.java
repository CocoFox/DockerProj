package app.entities;

/**
 * Administrateur
 */
public class Administrateur extends Utilisateur {
    
    public Administrateur(String id) {
        super(id);
    }

    public Administrateur(String id, String nom, String prenom, String identifiant) {
        super(id, nom, prenom, identifiant);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}