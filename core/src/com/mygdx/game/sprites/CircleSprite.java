package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CircleSprite {
    Sprite sprite;
    float x, y;
    int width, height, number;
    public boolean get;
    public CircleSprite (int x, int y, int width, int height, Texture texture) {
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.get = false;
        sprite.setBounds(x, y, width, height);
        sprite.setOrigin(width / 2f, height / 2f);
    }


    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setGet(boolean get) {
        this.get = get;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
