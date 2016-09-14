package app.swoking.fr.testmap.Game;

public class Niveau {

    private int Level;
    private int maxXP;

    private static Niveau[] lvls;

    public Niveau(int level, int maxXP) {
        this.Level = level;
        this.maxXP = maxXP;
    }

    public Niveau() {
        lvls    = new Niveau[10];
        lvls[0] = new Niveau(1, 1000);
        lvls[1] = new Niveau(1, 2000);
        lvls[2] = new Niveau(1, 3000);
        lvls[3] = new Niveau(1, 4000);
        lvls[4] = new Niveau(1, 5000);
        lvls[5] = new Niveau(1, 6000);
        lvls[6] = new Niveau(1, 7000);
        lvls[7] = new Niveau(1, 8000);
        lvls[8] = new Niveau(1, 9000);
        lvls[9] = new Niveau(1, 10000);
    }

    public Niveau getNiveau(int index) {
        return lvls[index];
    }

    public int getLevel() {
        return Level;
    }

    public int getMaxXP() {
        return maxXP;
    }
}
