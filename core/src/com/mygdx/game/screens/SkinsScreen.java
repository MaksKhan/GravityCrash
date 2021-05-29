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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GravityCrash;

public class SkinsScreen implements Screen {
    public short WORLD_WIDTH = 1000, WORLD_HEIGHT = 1000;

    GravityCrash game;
    private Stage stage;
    private Viewport viewport;
    Skin skin;
    OrthographicCamera camera;
    Preferences prefs;
    private Table table;
    Texture fon;
    TextButton menu;

    public SkinsScreen(GravityCrash game) {
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
        prefs = Gdx.app.getPreferences("Preferences");

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        Texture myTexture = new Texture(Gdx.files.internal("skins/skin1.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton skin1 = new ImageButton(myTexRegionDrawable);
        myTexture = new Texture(Gdx.files.internal("skins/skin2.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton skin2 = new ImageButton(myTexRegionDrawable);
        myTexture = new Texture(Gdx.files.internal("skins/skin3.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton skin3 = new ImageButton(myTexRegionDrawable);
        myTexture = new Texture(Gdx.files.internal("skins/skin4.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton skin4 = new ImageButton(myTexRegionDrawable);
        myTexture = new Texture(Gdx.files.internal("skins/skin5.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton skin5 = new ImageButton(myTexRegionDrawable);

        System.out.println(prefs.getInteger("all_coins"));
        TextButton textskin1 = new TextButton("You have: " + prefs.getInteger("Coins_get1") + "\nYou need: 0", skin);
        TextButton textskin2 = new TextButton("You have: " + prefs.getInteger("Coins_get1") + "\nYou need: 3", skin);
        TextButton textskin3 = new TextButton("You have: " + prefs.getInteger("Coins_get1") + "\nYou need: 6", skin);
        TextButton textskin4 = new TextButton("You have: " + prefs.getInteger("Coins_get1") + "\nYou need: 9", skin);
        TextButton textskin5 = new TextButton("You have: " + prefs.getInteger("Coins_get1") + "\nYou need: 12", skin);

        menu = new TextButton("GO TO MENU", skin);

        prefs = Gdx.app.getPreferences("Preferences");
        textskin1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.putString("Skin", "skins/skin1.png");
                prefs.flush();

            }
        });
        textskin2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getInteger("all_coins") >= 3) {
                    prefs.putString("Skin", "skins/skin2.png");
                    prefs.flush();
                }
            }
        });
        textskin3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getInteger("all_coins") >= 6) {
                    prefs.putString("Skin", "skins/skin3.png");
                    prefs.flush();
                }
            }
        });
        textskin4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getInteger("all_coins") >= 9) {

                    prefs.putString("Skin", "skins/skin4.png");
                    prefs.flush();
                }
            }
        });
        textskin5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getInteger("all_coins") >= 12) {

                    prefs.putString("Skin", "skins/skin5.png");
                    prefs.flush();
                }
                System.out.println(prefs.getString("Skin"));
            }
        });
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        table.add(skin1).fillX().width(250).height(100).padBottom(10);
        table.add(skin2).fillX().width(250).height(100).padBottom(10);
        table.add(skin3).fillX().width(250).height(100).padBottom(10);
        table.add(skin4).fillX().width(250).height(100).padBottom(10);
        table.add(skin5).fillX().width(250).height(100).padBottom(10);

        table.row();
        table.add(textskin1).fillX().width(250).height(100).padBottom(10);
        table.add(textskin2).fillX().width(250).height(100).padBottom(10);
        table.add(textskin3).fillX().width(250).height(100).padBottom(10);
        table.add(textskin4).fillX().width(250).height(100).padBottom(10);
        table.add(textskin5).fillX().width(250).height(100).padBottom(10);
        table.row();
        stage.addActor(table);
        menu.setBounds(555, 75, 250, 100);
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
        skin.dispose();
    }
}