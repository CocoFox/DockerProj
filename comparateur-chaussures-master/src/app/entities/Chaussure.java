package app.entities;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Chaussure
 */
public class Chaussure {

    private ImageIcon img = null;
    private String nom;
    private String marque;
    private String couleur;
    private String style;
    private String prix;
    private String quantite;
    private String id;
    private String filename;

    public Chaussure(String id) {
        this.id = id;
    }

    public Chaussure(String[] data) {
        this.id = data[0];
        this.nom = data[1];
        this.couleur = data[2];
        this.marque = data[3];
        this.quantite = data[4];
        this.prix = data[5];
        this.filename = data[6];
        this.style = data[7];
        try {
            this.img = new ImageIcon(ImageIO.read(new File("res/" + data[6] + ".jpg")));
        } catch (Exception e) {
            // this.img = null;
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @return the img
     */
    public ImageIcon getImg() {
        return img;
    }

    /**
     * @return the couleur
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * @return the marque
     */
    public String getMarque() {
        return marque;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the prix
     */
    public String getPrix() {
        return prix;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @return the quantite
     */
    public String getQuantite() {
        return quantite;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return nom + " " + marque;
    }
}