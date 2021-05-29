package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
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

public class MenuScreen implements Screen {
    GravityCrash game;
    private Stage stage;
    private Viewport viewport;
    TextButton newGame, exit, skins;
    Table table;
    Skin skin;
    OrthographicCamera camera;
    Texture fon;
    public MenuScreen(GravityCrash game) {
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
        newGame = new TextButton("LEVELS", skin);
        skins = new TextButton("SKINS", skin);
        exit = new TextButton("EXIT", skin);
        Texture myTexture = new Texture(Gdx.files.internal("saw.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelsScreen(game));
            }
        });
        skins.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SkinsScreen(game));
            }
        });
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(newGame).fillX().width(250).height(100).padBottom(10);
        table.row();
        table.add(skins).fillX().width(250).height(100).padBottom(10);
        table.row();
        table.add(exit).fillX().width(250).height(100);
        table.row();
        stage.addActor(table);
        stage.setViewport(viewport);
    }


    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(fon, 0, 0, 1880, 920);
        game.batch.end();
        stage.act();
        stage.draw();
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
