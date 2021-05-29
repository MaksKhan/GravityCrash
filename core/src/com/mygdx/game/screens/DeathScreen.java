package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GravityCrash;


public class DeathScreen implements Screen {

    GravityCrash game;
    private Stage stage;
    private Viewport viewport;
    Skin skin;

    OrthographicCamera camera;
    Preferences prefs;
    private Table table;
    Texture fon;
    Music music;

    public DeathScreen(GravityCrash game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(1280, 720);
        viewport = new ExtendViewport(1280, 720, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        fon = new Texture("starfield1.png");
        skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));


        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        TextButton newGame = new TextButton("TRY AGAIN", skin);
        TextButton menu = new TextButton("GO TO MENU", skin);
        prefs = Gdx.app.getPreferences("Preferences");
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new PlayScreen(game, prefs.getString("last_level")));
            }
        });
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        table.add(newGame).fillX().width(250).height(100).padBottom(10);
        table.row();
        table.add(menu).fillX().width(250).height(100).padBottom(10);

        stage.addActor(table);


    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(fon, 0, 0, 1880, 920);
        game.batch.end();
        stage.draw();
        stage.act();
        game.batch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void resize(int i, int i1) {
        viewport.update(i, i1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.dispose();
        game.batch.dispose();
        stage.dispose();
        music.dispose();
        fon.dispose();
    }
}
