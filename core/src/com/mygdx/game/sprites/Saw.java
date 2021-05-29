package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Saw extends  CircleSprite {

    public Saw(int x, int y, int width, int height, Texture texture) {
        super(x - width / 2, y, width, height, texture);
    }
}
