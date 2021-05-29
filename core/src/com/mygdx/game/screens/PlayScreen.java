package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Hero;
import com.mygdx.game.GravityCrash;
import com.mygdx.game.sprites.Change;
import com.mygdx.game.sprites.Coin;
import com.mygdx.game.sprites.JumpBlock;
import com.mygdx.game.sprites.Portal;
import com.mygdx.game.sprites.Saw;
import com.mygdx.game.tools.MyGesture;
import com.mygdx.game.tools.WorldContactListener;
import com.mygdx.game.tools.WorldCreator;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    public boolean gameOver = false;
    private final Viewport gameViewport;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    public World world;
    public Hero hero;
    public boolean normal_gravity = true;
    GestureDetector gestureDetector;
    public boolean flight_high_jump = false;
    MyGesture myGesture;
    TiledMap map;
    WorldCreator worldCreator;
    Texture jumpBlock = new Texture("jump.png");

    Texture starfield = new Texture("starfield.png");
    Sprite stasik;
    Preferences prefs;
    public ArrayList<Saw> saws;
    public ArrayList<JumpBlock> jumpblocks;
    public ArrayList<Change> changes;
    boolean check_saws = true;
    boolean check_jumpBlocks = true;
    boolean check_changes = true;
    public boolean in_left_flight = false;
    public final short WORLD_WIDTH = 16, WORLD_HEIGHT = 12;
    float k, k1;
    Music music;
    public Sound soundCoin, soundGravity, soundJump, soundJumpBlock, soundDeath;
    private Box2DDebugRenderer b2dr;


    GravityCrash game;


    public PlayScreen(GravityCrash game, String mapName) {
        this.game = game;
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.gameViewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        map = new TmxMapLoader().load(mapName);

    }


    @Override
    public void show() {
        myGesture = new MyGesture(this);
        b2dr = new Box2DDebugRenderer();
        gestureDetector = new GestureDetector(myGesture);
        Gdx.input.setInputProcessor(gestureDetector);
        prefs = Gdx.app.getPreferences("Preferences");
        if (!prefs.contains("Skin")) {
            prefs.putString("Skin", "skins/skin1.png");
            prefs.flush();
        }
        stasik = new Sprite(new Texture(prefs.getString("Skin")));
        stasik.setBounds(1, 1, 0.9f, 0.9f);
        stasik.setOrigin(0.45f, 0.45f);


        renderer = new OrthogonalTiledMapRenderer(map, 1f / ((TiledMapTileLayer) map.getLayers().get(0)).getTileWidth()); //один тайл равен единичному отрезку на координатной прямой экрана

        world = new World(new Vector2(0, -22), false);

        hero = new Hero(world, 10, 3);
        worldCreator = new WorldCreator(world, map);
        hero.b2body.setLinearVelocity(new Vector2(10, 0));
        hero.b2body.setLinearVelocity(new Vector2(10, 0));

        world.setContactListener(new WorldContactListener(this));

        saws = (ArrayList<Saw>) worldCreator.saws.clone();
        jumpblocks = (ArrayList<JumpBlock>) worldCreator.jumpblocks.clone();
        changes = (ArrayList<Change>) worldCreator.changes.clone();
        k = 5;
        k1 = 5;
        music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
        music.play();
        soundCoin = Gdx.audio.newSound(Gdx.files.internal("music/coin.mp3"));
        soundGravity = Gdx.audio.newSound(Gdx.files.internal("music/gravity.mp3"));
        soundJump = Gdx.audio.newSound(Gdx.files.internal("music/jump.mp3"));
        soundJumpBlock = Gdx.audio.newSound(Gdx.files.internal("music/jumpBlock.mp3"));
        soundDeath = Gdx.audio.newSound(Gdx.files.internal("music/death.mp3"));
    }


    @Override
    public void render(float delta) {
        b2dr.render(world, camera.combined);
        if (hero.b2body.getPosition().x >= 565) {
            int k = 0;
            for (Coin i : worldCreator.coins.keySet()) {
                if (!worldCreator.coins.get(i)) {
                    prefs.putBoolean("Coin " + (prefs.getInteger("int_last_level") + i.getNumber()), true);
                    k++;
                }
            }
            prefs.putInteger("Coins_get" + prefs.getInteger("int_last_level"), k);
            prefs.putInteger("all_coins", k);


            prefs.flush();
            myDispose();
            game.setScreen(new PassingScreen(game));
        }
        if (gameOver) {
            soundDeath.play();
            game.setScreen(new DeathScreen(game));
            myDispose();
        }
        update();
        if (hero.b2body.getLinearVelocity().x < 10f) {
            hero.b2body.applyLinearImpulse(new Vector2(1f, 0), hero.b2body.getWorldCenter(), true);
        }
        if (hero.original_mehanic) {


            if (hero.b2body.getLinearVelocity().y == 0) {
                flight_high_jump = false;
            }
            if (normal_gravity && hero.b2body.getLinearVelocity().y < 0) {
                hero.check = true;
            } else if (!normal_gravity && hero.b2body.getLinearVelocity().y > 0) {
                hero.check = true;
            }
            if (hero.b2body.getLinearVelocity().y == 0) {
                hero.in_flight = false;
            }

            if (hero.check) {
                hero.landing(normal_gravity);
            }

            if (Gdx.input.isTouched() && hero.b2body.getLinearVelocity().y == 0 && !hero.collides && hero.b2body.getLinearVelocity().x > 8) {
                soundJump.play();
                hero.jump(normal_gravity);
            }
        } else {
            normal_gravity = true;
            world.setGravity(new Vector2(0, -22));
            if (Gdx.input.isTouched()) {
                hero.b2body.applyForceToCenter(0, 60f, true);
            }
        }
        if (in_left_flight) {
            hero.b2body.applyForceToCenter(new Vector2(-1500, 0), true);
            in_left_flight = false;
        }

        game.batch.setProjectionMatrix(camera.combined); //рисует только то, что видно в камере
        Gdx.gl.glClearColor(0, 0.05f, 0.1f, 0f);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        k += 0.02f;
        k1 += 0.02f;
        if (k > 30) {
            k -= 26;
            k1 -= 26;
        }
        game.batch.draw(starfield, hero.b2body.getPosition().x - k, 0, WORLD_WIDTH + 10, WORLD_HEIGHT);
        game.batch.draw(starfield, hero.b2body.getPosition().x + 26 - k1, 0, WORLD_WIDTH + 10, WORLD_HEIGHT);


        stasik.setX(hero.b2body.getPosition().x - 0.45f);
        stasik.setY(hero.b2body.getPosition().y - 0.45f);
        stasik.draw(game.batch);
        if (normal_gravity) {
            stasik.rotate(-10);
        } else {
            stasik.rotate(10);
        }
        for (Portal i : worldCreator.portals) {
            if ((int) hero.b2body.getPosition().x == (int) i.getX() && (int) hero.b2body.getPosition().y == (int) i.getY()) {
                hero = new Hero(world, (int) worldCreator.portals.get(worldCreator.portals.indexOf(i) + 1).getX() + 3, (int) worldCreator.portals.get(worldCreator.portals.indexOf(i) + 1).getY());
            }
        }


        if (check_jumpBlocks) {
            for (JumpBlock i : jumpblocks) {
                if (hero.b2body.getPosition().x - i.getX() > 10) {
                    worldCreator.jumpblocks.remove(i);
                }
                if (Math.abs(i.getX() - hero.b2body.getPosition().x) < 30) {
                    game.batch.draw(jumpBlock, i.getX(), i.getY(), 1, 1);
                }

            }
            //check_jumpBlocks = true;
            jumpblocks = (ArrayList<JumpBlock>) worldCreator.jumpblocks.clone();
        }
        if (check_saws) {
            //check_saws = false;
            for (Saw i : saws) {
                if (hero.b2body.getPosition().x - i.getX() > 10) {
                    worldCreator.saws.remove(i);
                }
                if (Math.abs(i.getX() - hero.b2body.getPosition().x) < 30) {
                    i.getSprite().draw(game.batch);
                    i.getSprite().rotate(6);
                }

            }

            saws = (ArrayList<Saw>) worldCreator.saws.clone();
        }

        for (Coin i : worldCreator.coins.keySet()) {

            if (worldCreator.coins.get(i) && !prefs.getBoolean("Coin " + (prefs.getInteger("int_last_level")) + i.getNumber())) {
                i.getSprite().draw(game.batch);
            }
            if ((int) hero.b2body.getPosition().x == (int) i.getX() && (int) hero.b2body.getPosition().y == (int) i.getY()) {
                soundCoin.play();
                worldCreator.coins.put(i, false); // больше не отрисовывает

            }
        }
        if (check_changes) {
            //check_changes = false;
            for (Change i : changes) {
                if ((int) hero.b2body.getPosition().x == (int) i.getX() && (int) hero.b2body.getPosition().y == (int) i.getY() && !i.already_changed) {
                    if (!hero.original_mehanic) {
                        normal_gravity = true;
                        world.setGravity(new Vector2(0, -22));
                        hero.original_mehanic = true;
                    } else if (hero.original_mehanic = true) {
                        normal_gravity = true;
                        world.setGravity(new Vector2(0, -22));
                        hero.original_mehanic = false;
                    }
                    i.draw_change = false;
                    i.already_changed = true;
                }
                if (hero.b2body.getPosition().x - i.getX() > 10) {
                    worldCreator.saws.remove(i);
                }
                if (Math.abs(i.getX() - hero.b2body.getPosition().x) < 30 && i.draw_change) {
                    i.getSprite().draw(game.batch);
                }

            }

            changes = (ArrayList<Change>) worldCreator.changes.clone();
        }


        game.batch.end();
        renderer.render();


        camera.position.x = hero.b2body.getPosition().x + 8;


    }

    private void update() {
        world.step(1.0f / 60.0f, 6, 2);
        camera.update();
        renderer.setView(camera);
    }


    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
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
        world.dispose();
        map.dispose();
        renderer.dispose();

        game.batch.dispose();
        game.dispose();
        music.dispose();
        starfield.dispose();
        hero.dispose();
        soundDeath.dispose();
        soundJumpBlock.dispose();
        soundJump.dispose();
        soundGravity.dispose();
        soundCoin.dispose();
    }
    public void myDispose() {
        music.dispose();
        starfield.dispose();
        music.dispose();
        soundDeath.dispose();
        soundJumpBlock.dispose();
        soundJump.dispose();
        soundGravity.dispose();
        soundCoin.dispose();
    }
}
