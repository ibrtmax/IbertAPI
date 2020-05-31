package me.ibrt.universal.objects;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextBuilder {

    public TextComponent text;

    public TextBuilder(String text) {
        this.text = new TextComponent(text);
    }

    public TextBuilder addHover(String text, HoverEvent.Action action) {
        this.text.setHoverEvent(new HoverEvent(action, new ComponentBuilder(text).create()));
        return this;
    }

    public TextBuilder addClick(String text, ClickEvent.Action action) {
        this.text.setClickEvent(new ClickEvent(action, text));
        return this;
    }

    public TextBuilder addExtra(TextBuilder tb) {
        this.text.addExtra(tb.text);
        return this;
    }

    public TextComponent build() {
        return this.text;
    }
}
