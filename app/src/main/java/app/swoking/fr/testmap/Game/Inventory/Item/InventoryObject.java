package app.swoking.fr.testmap.Game.Inventory.Item;

import java.util.Objects;

public abstract class InventoryObject {

    protected int     id;
    protected String  name;
    protected String  description;
    protected Objects cible;

    protected abstract int getId();

    protected abstract void setId(int id);

    protected abstract String getName();

    protected abstract void setName(String name);

    protected abstract String getDescription();

    protected abstract void setDescription(String description);

    protected abstract Objects getCible();

    protected abstract void setCible(Objects cible);
}
