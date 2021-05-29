package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;


public class Hero extends Sprite {
    public World world;
    public Body b2body;
    public boolean in_flight;
    public boolean check = false;

    int jump_force, landing_force;
    public boolean collides = false;
    public boolean original_mehanic = true;


    public Hero(World world, int x, int y) {
        this.world = world;
        defineHero(x, y);
    }

    private void defineHero(int x, int y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;


        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.45f);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("hero");
    }

    public void jump(boolean normal_gravity) {
        if (normal_gravity) {
            jump_force = 900; // прыгает
        } else {
            jump_force = -900;
        }
        b2body.applyForceToCenter(0, jump_force, true); //проходит 4 блока
        check = true;
    }

    public void landing(boolean normal_gravity) {
        if (normal_gravity) {
            landing_force = -56; // 40
        } else {
            landing_force = 56;
        }
        if (b2body.getLinearVelocity().y != 0) {
            b2body.applyForceToCenter(new Vector2(0, landing_force), true);
        } else {
            check = false;

        }
    }

    public void high_jump(boolean normal_gravity) {
        if (normal_gravity) {
            jump_force = 1509; // проходит 7 блоков, но с доп.прыжком 10 блоков
        } else {
            jump_force = -1509;
        }
        b2body.applyForceToCenter(0, jump_force, true);
        check = true;
    }

    public void dispose() {
        world.dispose();
    }
}
