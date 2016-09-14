package app.swoking.fr.testmap.Game;

import java.io.Serializable;

public class Character implements Serializable {

    private String     name;
    private int        xp;
    private int        lvl;

    //private Succes succes;

    public Character() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
}
