package converter;

import java.io.Serializable;
import java.util.List;

public class Monnaie implements Serializable{

    private List<String> pays;
    private String nom;
    private String code;
    private float taux; // Le taux de change de l'euro vers cette monnaie

    public Monnaie(List<String> pays, String nom, String code, float taux) {
        this.pays = pays;
        this.nom = nom;
        this.code = code;
        this.taux = taux;
    }

    public List<String> getPays() {
        return pays;
    }

    public String getNom() {
        return nom;
    }

    public String getCode() {
        return code;
    }

    public float getTaux() {
        return taux;
    }
    
    
}
