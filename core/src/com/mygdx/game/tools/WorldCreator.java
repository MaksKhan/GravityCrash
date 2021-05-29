package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.sprites.Change;
import com.mygdx.game.sprites.Coin;
import com.mygdx.game.sprites.JumpBlock;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.mygdx.game.sprites.Portal;
import com.mygdx.game.sprites.Saw;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class WorldCreator {
    public final ArrayList<Portal> portals;

    public ArrayList<JumpBlock> jumpblocks;
    public ArrayList<Saw> saws;
    public ArrayList<Change> changes;
    public HashMap<Coin, Boolean> coins;
    private final float PIXELS = 128f;
    Texture saw = new Texture("saw.png");
    Texture coin = new Texture("coin.png");
    Texture change = new Texture("change.png");
    Preferences prefs;


    public WorldCreator(World world, TiledMap map) {


//пробное создание земли
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        CircleShape circleShape = new CircleShape();


        //перенос ground
        /*for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() / PIXELS + rect.getWidth() / PIXELS / 2, rect.getY() / PIXELS + rect.getHeight() / PIXELS / 2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / PIXELS, rect.getHeight() / 2 / PIXELS);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("ground");
        }
        */

        //перенос платформ
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() / PIXELS + rect.getWidth() / PIXELS / 2, rect.getY() / PIXELS + rect.getHeight() / PIXELS / 2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / PIXELS, rect.getHeight() / 2 / PIXELS);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("platforms");

        }
        //перенос треугольников
        float[] a = new float[6];
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(PolygonMapObject.class)) {
            PolygonMapObject polygonMapObject = (PolygonMapObject) object;
            Polygon polygon = polygonMapObject.getPolygon();

            int k = 0;
            for (float i : polygon.getVertices()) {
                i /= PIXELS;
                a[k] = i;
                k++;
            }
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(polygon.getX() / PIXELS, polygon.getY() / PIXELS);
            body = world.createBody(bdef);
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(a);
            body.createFixture(polygonShape, 0.0f).setUserData("rectangle");
            polygonShape.dispose();
        }
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() / PIXELS + rect.getWidth() / PIXELS / 2, rect.getY() / PIXELS + rect.getHeight() / PIXELS / 2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / PIXELS, rect.getHeight() / 2 / PIXELS);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("small_triangles");
        }
        jumpblocks = new ArrayList<>();
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            jumpblocks.add(new JumpBlock(rect.x / PIXELS, rect.y / PIXELS));
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() / PIXELS + rect.getWidth() / PIXELS / 2, rect.getY() / PIXELS + rect.getHeight() / PIXELS / 2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / PIXELS, rect.getHeight() / 2 / PIXELS);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("jump");
        }
        saws = new ArrayList<>();
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse circle = ((EllipseMapObject) object).getEllipse();
            saws.add(new Saw((int) (circle.x / PIXELS), (int) (circle.y / PIXELS), (int) (circle.width / PIXELS), (int) (circle.height / PIXELS), saw));
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(circle.x / PIXELS, circle.y / PIXELS + circle.height / PIXELS / 2);
            new CircleShape();
            body = world.createBody(bdef);
            circleShape.setRadius(circle.height / PIXELS / 2 - 0.1f);
            fdef.shape = circleShape;
            body.createFixture(fdef).setUserData("saw");
        }
        coins = new HashMap<>(); // значние в хэшмапе: true - отрисовывать, false - нет
        prefs = Gdx.app.getPreferences("Preferences");

        int k = 1;
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse circle = ((EllipseMapObject) object).getEllipse();
            coins.put(new Coin((int) (circle.x / PIXELS), (int) (circle.y / PIXELS), (int) (circle.width / PIXELS), (int) (circle.height / PIXELS), coin, k), true);
            if (!prefs.contains("Coin " + (prefs.getInteger("int_last_level")) + k)) {
                prefs.putBoolean("Coin " + (prefs.getInteger("int_last_level")) + k, false);
            }
            k++;
        }
        prefs.flush();
        changes = new ArrayList<>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(EllipseMapObject.class)) {

            Ellipse circle = ((EllipseMapObject) object).getEllipse();
            changes.add(new Change((int) (circle.x / PIXELS), (int) (circle.y / PIXELS), (int) (circle.width / PIXELS), (int) (circle.height / PIXELS), change));
        }
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() / PIXELS + rect.getWidth() / PIXELS / 2, rect.getY() / PIXELS + rect.getHeight() / PIXELS / 2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / PIXELS, rect.getHeight() / 2 / PIXELS);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("left");
        }
        portals = new ArrayList<>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse circle = ((EllipseMapObject) object).getEllipse();
            portals.add(new Portal((int) (circle.x / PIXELS), (int) (circle.y / PIXELS), (int) (circle.width / PIXELS), (int) (circle.height / PIXELS), coin)); //картинга тут из-за шаблона CircleSprite
        }
        Collections.sort(portals, new Comparator<Portal>() {
            @Override
            public int compare(Portal portal, Portal t1) {
                return (int) (portal.getX() - t1.getX());
            }
        });
    }
}
