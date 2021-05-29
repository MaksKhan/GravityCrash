package com.mygdx.game.tools;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.PlayScreen;

public class MyGesture implements GestureDetector.GestureListener {
    PlayScreen playScreen;

    public MyGesture(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        if (velocityY < 0 && ((playScreen.hero.check && playScreen.hero.b2body.getLinearVelocity().y > 0 && !playScreen.hero.in_flight)) && playScreen.normal_gravity) {
            playScreen.normal_gravity = false;
            playScreen.soundGravity.play();
            playScreen.world.setGravity(new Vector2(0, 22));
        } else if ((velocityY > 0 && (playScreen.hero.check && playScreen.hero.b2body.getLinearVelocity().y < 0 && !playScreen.hero.in_flight)) && !playScreen.normal_gravity) {
            playScreen.normal_gravity = true;
            playScreen.soundGravity.play();
            playScreen.world.setGravity(new Vector2(0, -22));
        }
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }


    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
