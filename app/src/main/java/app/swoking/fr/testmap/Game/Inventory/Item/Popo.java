package app.swoking.fr.testmap.Game.Inventory.Item;

import java.util.Objects;

public class Popo extends InventoryObject {

    public Popo() {
        setId(0);
        setName("Popo");
        setDescription("Régénére la vie");
    }

    @Override
    protected int getId() {
        return super.id;
    }

    @Override
    protected void setId(int id) {
        super.id = id;
    }

    @Override
    protected String getName() {
        return super.name;
    }

    @Override
    protected void setName(String name) {
        super.name = name;
    }

    @Override
    protected String getDescription() {
        return super.description;
    }

    @Override
    protected void setDescription(String description) {
        super.description = description;
    }
    @Override
    protected Objects getCible() {
        return super.cible;
    }

    @Override
    protected void setCible(Objects cible) {
        super.cible = cible;
    }
}
