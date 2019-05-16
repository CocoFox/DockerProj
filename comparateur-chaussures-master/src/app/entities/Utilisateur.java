package app.entities;

/**
 * Utilisateur
 */
public abstract class Utilisateur  {

    private String id;
    private String nom;
    private String prenom;
    private String identifiant;

    public Utilisateur(String id) {
        this.id = id;
    }

    public Utilisateur(String id, String nom, String prenom, String identifiant) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.identifiant = identifiant;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the identifiant
     */
    public String getIdentifiant() {
        return identifiant;
    }

    @Override
    public String toString() {
        return prenom;
    }
}