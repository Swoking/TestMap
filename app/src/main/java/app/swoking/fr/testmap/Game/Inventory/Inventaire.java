package app.swoking.fr.testmap.Game.Inventory;

import java.io.Serializable;
import java.util.HashMap;

import app.swoking.fr.testmap.Game.Inventory.Item.InventoryObject;

public class Inventaire implements Serializable {

    private HashMap<Integer, Integer> inventoryObjects;

    public Inventaire() {
    }

    public void add(InventoryObject object){

    }

    public void use(InventoryObject object){

    }

    public HashMap<Integer, Integer> getInventoryObjects() {
        return inventoryObjects;
    }

    public void setInventoryObjects(HashMap<Integer, Integer> inventoryObjects) {
        this.inventoryObjects = inventoryObjects;
    }

}
