package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Coin extends  CircleSprite {
    int number;
    public Coin(int x, int y, int width, int height, Texture texture, int number) {
        super(x, y, width, height, texture);
        this.number = number;
    }

    public String getNumber() {
        return Integer.toString(number);
    }
}
