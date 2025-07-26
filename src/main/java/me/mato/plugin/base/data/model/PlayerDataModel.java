package me.mato.plugin.base.data.model;

import java.util.UUID;

public class PlayerDataModel {

    private final UUID uuid;
    private final String name;
    private int level;

    public PlayerDataModel(UUID uuid, String name, int level) {
        this.uuid = uuid;
        this.name = name;
        this.level = level;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
