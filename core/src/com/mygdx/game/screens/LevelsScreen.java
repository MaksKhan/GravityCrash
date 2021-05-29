package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GravityCrash;

public class LevelsScreen implements Screen {


    GravityCrash game;
    private Stage stage;
    private Viewport viewport;
    OrthographicCamera camera;
    TextButton level1, level2, level3, level4, level5, level6, level7, level8, level9, level10;
    private Texture fon;


    public LevelsScreen(GravityCrash game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(1280, 720);
        viewport = new ExtendViewport(1280, 720, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        fon = new Texture("starfield1.png");

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        final Preferences pref = Gdx.app.getPreferences("Preferences");
        level1 = new TextButton("LEVEL 1\n COINS: " + pref.getInteger("Coins_get1"), skin);
        level2 = new TextButton("LEVEl 2\n COINS: " + pref.getInteger("Coins_get2"), skin);
        level3 = new TextButton("LEVEl 3\n COINS: " + pref.getInteger("Coins_get3"), skin);
        level4 = new TextButton("LEVEl 4\n COINS: " + pref.getInteger("Coins_get4"), skin);
        level5 = new TextButton("LEVEl 5\n COINS: " + pref.getInteger("Coins_get5"), skin);
        level6 = new TextButton("COMING SOON", skin);
        level7 = new TextButton("COMING SOON", skin);
        level8 = new TextButton("COMING SOON", skin);
        level9 = new TextButton("COMING SOON", skin);
        level10 = new TextButton("COMING SOON", skin);
        TextButton menu = new TextButton("GO TO MENU", skin);


        Texture myTexture = new Texture(Gdx.files.internal("saw.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);


        if (!pref.contains("Skin")) {
            pref.putString("Skin", "skins/skin1.png");
        }
        level1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pref.putString("last_level", "level1.tmx");
                pref.putInteger("int_last_level", 1);
                pref.flush();
                game.setScreen(new PlayScreen(game, "level1.tmx"));
            }
        });
        level2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pref.putString("last_level", "level2.tmx");
                pref.putInteger("int_last_level", 2);
                pref.flush();
                game.setScreen(new PlayScreen(game, "level2.tmx"));
            }
        });
        level3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pref.putString("last_level", "level3.tmx");
                pref.putInteger("int_last_level", 3);
                pref.flush();
                game.setScreen(new PlayScreen(game, "level3.tmx"));
            }
        });
        level4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pref.putString("last_level", "level4.tmx");
                pref.putInteger("int_last_level", 4);
                pref.flush();
                game.setScreen(new PlayScreen(game, "level4.tmx"));
            }
        });
        level5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pref.putString("last_level", "level5.tmx");
                pref.putInteger("int_last_level", 5);
                pref.flush();
                game.setScreen(new PlayScreen(game, "level5.tmx"));
            }
        });
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        table.add(level1).fillX().width(200).height(100).padBottom(10);
        table.add(level2).fillX().width(200).height(100).padBottom(10);
        table.add(level3).fillX().width(200).height(100).padBottom(10);
        table.add(level4).fillX().width(200).height(100).padBottom(10);
        table.add(level5).fillX().width(200).height(100).padBottom(10);
        table.row();
        table.add(level6).fillX().width(200).height(100).padBottom(10);
        table.add(level7).fillX().width(200).height(100).padBottom(10);
        table.add(level8).fillX().width(200).height(100).padBottom(10);
        table.add(level9).fillX().width(200).height(100).padBottom(10);
        table.add(level10).fillX().width(200).height(100).padBottom(10);
        table.row();

        table.setY(100);
        stage.addActor(table);
        menu.setBounds(580, 180, 200, 80);
        stage.addActor(menu);


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
        fon.dispose();

    }
}
