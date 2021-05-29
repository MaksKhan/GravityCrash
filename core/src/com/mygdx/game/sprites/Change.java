package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Change extends  CircleSprite {
    public boolean draw_change, already_changed;


    public Change(int x, int y, int width, int height, Texture texture) {
        super(x, y, width, height, texture);
        draw_change = true;
        already_changed = false;
    }
}
