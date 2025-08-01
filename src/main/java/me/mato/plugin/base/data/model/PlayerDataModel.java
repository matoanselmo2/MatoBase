package me.mato.plugin.base.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public record PlayerDataModel(UUID uuid, String name, int level) {
}
