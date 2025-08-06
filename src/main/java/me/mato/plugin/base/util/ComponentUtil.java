package me.mato.plugin.base.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class ComponentUtil {

    public static Component compose(String text, NamedTextColor color, TextDecoration... decorations) {
        Component base = Component.text(text);

        if (color != null) base = base.color(color);

        Set<TextDecoration> decorationSet = decorations.length == 0
                ? EnumSet.noneOf(TextDecoration.class)
                : EnumSet.copyOf(Arrays.asList(decorations));

        if (!decorationSet.contains(TextDecoration.ITALIC)) {
            base = base.decoration(TextDecoration.ITALIC, false);
        }

        for (TextDecoration deco : decorationSet) {
            base = base.decoration(deco, true);
        }

        return base;
    }

    public static String composeToString(String text, NamedTextColor color, TextDecoration... decorations) {
        Component component = compose(text, color, decorations);
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static Component legacy(String legacyText) {
        return LegacyComponentSerializer.legacySection()
                .deserialize(legacyText)
                .decoration(TextDecoration.ITALIC, false);
    }

    // 🔥 Compose encadeado
    public static ComposeBuilder compose() {
        return new ComposeBuilder();
    }

    public static class ComposeBuilder {
        private final TextComponent.Builder builder;

        public ComposeBuilder() {
            this.builder = Component.text();
        }

        public ComposeBuilder text(String text, NamedTextColor color, TextDecoration... decorations) {
            Component component = ComponentUtil.compose(text, color, decorations);
            builder.append(component);
            return this;
        }

        public ComposeBuilder text(Component component) {
            builder.append(component);
            return this;
        }

        public Component build() {
            return builder.build();
        }
    }
}
