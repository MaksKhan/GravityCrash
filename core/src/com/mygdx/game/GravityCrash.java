package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.PlayScreen;

public class GravityCrash extends Game {
    public SpriteBatch batch;


    @Override
    public void create() {

        batch = new SpriteBatch();
        this.setScreen(new MenuScreen(this));

    }

    @Override
    public void render() {

        super.render();
    }

    @Override
    public void dispose() {

    }

    public void change() {

    }

}
