package com.mygdx.game.tools;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import com.mygdx.game.screens.PlayScreen;

public class WorldContactListener implements ContactListener {

    PlayScreen playScreen;

    public WorldContactListener(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if ((fixtureA.getUserData() == "hero" && (fixtureB.getUserData() == "rectangle" || fixtureB.getUserData() == "saw")) || ((fixtureA.getUserData() == "small_rectangle" || fixtureB.getUserData() == "saw") && fixtureB.getUserData() == "hero")) { ;
            playScreen.gameOver = true;
        }
        if ((fixtureA.getUserData() == "hero" && (fixtureB.getUserData() == "rectangle" || fixtureB.getUserData() == "saw" || fixtureB.getUserData() == "small_triangles")) || ((fixtureA.getUserData() == "rectangle" || fixtureA.getUserData() == "saw" || fixtureA.getUserData() == "small_triangles") && fixtureB.getUserData() == "hero")) {
            playScreen.gameOver = true;
        }
        if ((fixtureA.getUserData() == "hero" && fixtureB.getUserData() == "platforms") || (fixtureA.getUserData() == "platforms" && fixtureB.getUserData() == "hero")) {
            playScreen.hero.b2body.setLinearVelocity(new Vector2(10, 0));
        }
        if ((fixtureA.getUserData() == "hero" && fixtureB.getUserData() == "platforms") || (fixtureA.getUserData() == "platforms" && fixtureB.getUserData() == "hero")) {
            playScreen.hero.b2body.setLinearVelocity(new Vector2(10, 0));
        }
        if (fixtureA.getUserData() == "hero" && fixtureB.getUserData() == "jump") {
            playScreen.soundJumpBlock.play();
            playScreen.hero.high_jump(playScreen.normal_gravity);
            playScreen.hero.check = true;
            playScreen.hero.in_flight = true;
        } else if (fixtureA.getUserData() == "jump" && fixtureB.getUserData() == "hero") {
            playScreen.soundJumpBlock.play();
            playScreen.hero.high_jump(playScreen.normal_gravity);
            playScreen.hero.check = true;
            playScreen.hero.in_flight = true;
        }
        if (fixtureA.getUserData() == "hero" && fixtureB.getUserData() == "left") {
            playScreen.in_left_flight = true;
        }
        if (fixtureA.getUserData() == "left" && fixtureB.getUserData() == "hero") {
            playScreen.in_left_flight = true;
        }

    }


    @Override
    public void endContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getUserData() == "hero" && fixtureB.getUserData() == "jump" && !playScreen.hero.in_flight) {
            playScreen.hero.high_jump(playScreen.normal_gravity);
            playScreen.hero.check = true;

        } else if (fixtureA.getUserData() == "jump" && fixtureB.getUserData() == "hero" && !playScreen.hero.in_flight) {

            playScreen.hero.high_jump(playScreen.normal_gravity);
            playScreen.hero.check = true;

        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getUserData() == "hero" && fixtureB.getUserData() == "left") {
            playScreen.in_left_flight = true;
        }
        if (fixtureA.getUserData() == "left" && fixtureB.getUserData() == "hero") {
            playScreen.in_left_flight = true;
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
