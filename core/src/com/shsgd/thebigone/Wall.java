package com.shsgd.thebigone;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by ryananderson on 4/6/16.
 */
public class Wall  {
    //Represents a solid wall

    private World world;
    private Body body;
    private Fixture fixture;

    private static BodyDef bodyDef;
    private static FixtureDef fixtureDef;
    private static PolygonShape rectangle;

    private float width, height;

    static {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        rectangle = new PolygonShape();

        fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.friction = 0.0f;

    }

    public Wall(World world, float x, float y, float width, float height) {
        this.world = world;
        this.width = width;
        this.height = height;

        //make box2d body and fixture
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        //shape
        rectangle.setAsBox(width/2, height/2);
        fixtureDef.shape = rectangle;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

    }

    public void dispose(){
        rectangle.dispose();
    }

    public Body getBody() {
        return body;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
