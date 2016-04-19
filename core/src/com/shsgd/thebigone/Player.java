package com.shsgd.thebigone;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by ryananderson on 4/6/16.
 */
public class Player {
    //float WIDTH = 1, HEIGHT = 1; //meters
    float fixtureWidth = 1, fixtureHeight = 0.5f,
        imageWidth = 1, imageHeight = 1;
    float MOVE_SPEED = 5;
    World world;
    Body body;
    Fixture mainFixture;

    Texture texture;

    boolean moveDown[] = {false, false};
    boolean moveRight[] = {false, false};
    boolean moveUp[] = {false, false};
    boolean moveLeft[] = {false, false};

    public Player(World world, float x, float y) {
        this.world = world;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape box = new PolygonShape(); box.setAsBox(fixtureWidth/2, fixtureHeight/2);
        fixtureDef.shape = box;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        fixtureDef.density = 1.0f;
        mainFixture = body.createFixture(fixtureDef);

        texture = new Texture("character.png");
    }

    public void update(float delta){
        float horizSpeed,  vertSpeed;
        if(moveRight[0]) horizSpeed = MOVE_SPEED;
        else if(moveLeft[0]) horizSpeed = -MOVE_SPEED;
        else horizSpeed = 0;

        if(moveUp[0]) vertSpeed = MOVE_SPEED;
        else if(moveDown[0]) vertSpeed = -MOVE_SPEED;
        else vertSpeed = 0;

        body.setLinearVelocity(horizSpeed, vertSpeed);
    }

    public void moveKeyDown(int direction){
        //System.out.println("moveKeyDown "+direction);
        if(direction==0){
            moveDown[1] = true;
            if(!moveUp[0]) moveDown[0] = true;
        } else if(direction == 1){
            moveRight[1] = true;
            if(!moveLeft[0]) moveRight[0]=true;
        } else if(direction == 2){
            moveUp[1] = true;
            if(!moveDown[0]) moveUp[0] = true;
        } else if(direction == 3){
            moveLeft[1] = true;
            if(!moveRight[0]) moveLeft[0] = true;
        }
    }

    public void moveKeyReleased(int direction){
        if(direction==0){
            moveDown[0] = false; moveDown[1] = false;
            if(moveUp[1]) moveUp[0] = true;
        } else if(direction==1){
            moveRight[0] = false; moveRight[1] = false;
            if(moveLeft[1]) moveLeft[0] = true;
        } else if(direction==2){
            moveUp[0] = false; moveUp[1] = false;
            if(moveDown[1]) moveDown[0] = true;
        } else if(direction==3){
            moveLeft[0] = false; moveLeft[1] =false;
            if(moveRight[1]) moveRight[0] = true;
        }
    }
    public void dispose(){
        texture.dispose();
    }

    public Texture getTexture() {
        return texture;
    }

    public Body getBody() {
        return body;
    }

    public float getFixtureWidth() {
        return fixtureWidth;
    }

    public float getFixtureHeight() {
        return fixtureHeight;
    }

    public float getImageWidth() {
        return imageWidth;
    }

    public float getImageHeight() {
        return imageHeight;
    }
}
