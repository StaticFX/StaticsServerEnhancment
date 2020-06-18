package de.staticred.server.chestsystem.util;

import java.util.Collection;

public class Chest {

    private Rarity rarity;
    private Collection<ChestItem> chestItems;
    private String name;


    public Chest(Rarity rarity, Collection<ChestItem> chestItems, String name) {
        this.rarity = rarity;
        this.chestItems = chestItems;
        this.name = name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public Collection<ChestItem> getChestItems() {
        return chestItems;
    }

    public void setChestItems(Collection<ChestItem> chestItems) {
        this.chestItems = chestItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
