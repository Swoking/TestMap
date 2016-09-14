package app.swoking.fr.testmap.Game.Skin;

import java.io.Serializable;

public class Skin implements Serializable {

    private int tete;
    private int cheveux;
    private int peau;
    private int jambe;
    private int chaussure;
    private int sexe;

    public Skin() {
    }

    public int getTete() {
        return tete;
    }

    public void setTete(int tete) {
        this.tete = tete;
    }

    public int getCheveux() {
        return cheveux;
    }

    public void setCheveux(int cheveux) {
        this.cheveux = cheveux;
    }

    public int getPeau() {
        return peau;
    }

    public void setPeau(int peau) {
        this.peau = peau;
    }

    public int getJambe() {
        return jambe;
    }

    public void setJambe(int jambe) {
        this.jambe = jambe;
    }

    public int getChaussure() {
        return chaussure;
    }

    public void setChaussure(int chaussure) {
        this.chaussure = chaussure;
    }

    public int getSexe() {
        return this.sexe;
    }

    public void setSexe(int sexe) {
        this.sexe = sexe;
    }
}
