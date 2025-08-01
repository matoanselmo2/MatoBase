package me.mato.plugin.base.api.model;

import java.util.UUID;

public record PlayerDataModel(UUID uuid, String name, int level) {
}
