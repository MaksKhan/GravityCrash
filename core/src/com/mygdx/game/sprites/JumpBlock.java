package com.mygdx.game.sprites;


public class JumpBlock {

    float x, y;

    public JumpBlock(float x, float y) {
        System.out.println(x + " tut " + y);
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }
}
