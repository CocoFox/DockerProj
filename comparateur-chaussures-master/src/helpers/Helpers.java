package helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;

import db.AccesDB;;

/**
 * Helpers
 */
public class Helpers {

    public static String filename(String produit) {
        AccesDB db;
        try {
            db = new AccesDB();
            return db.getFilename(produit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] splitString(String s, String divider) {
        return s.split(divider);
    }

    public static String SHA1(String input) {

        try {
            // choisi l'algorithme
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // calcule l'empreinte du message, retourne array de bytes
            byte[] empreinte = md.digest(input.getBytes());

            // signum representation ?
            BigInteger no = new BigInteger(1, empreinte);
            String chiffre = no.toString(16);

            while (chiffre.length() < 32) {
                chiffre = "0" + chiffre;
            }

            return chiffre;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sortByColumn(String[][] arr, int col, int ref) {
        Arrays.sort(arr, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                if (Math.abs(Float.parseFloat(o1[col])-ref) > Math.abs(Float.parseFloat(o2[col])-ref))
                    return 1;
                return -1;
            }
        });
    }
}