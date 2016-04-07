package com.shsgd.thebigone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by ryananderson on 4/6/16.
 */
public class PlayScreen implements Screen{

    private static final int PPM = 64;

    private Main game;
    private MyInputProcessor inputProcessor;
    private SpriteBatch sb = new SpriteBatch();
    private ShapeRenderer sr = new ShapeRenderer();
    private OrthographicCamera gameCamera, b2drCamera;
    private Viewport gameViewport, b2drViewport;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Player player;
    private Wall wall;

    public PlayScreen(Main game) {
        this.game = game;
        inputProcessor = new MyInputProcessor(this);
        gameCamera = new OrthographicCamera();
        b2drCamera = new OrthographicCamera();
        gameViewport = new FitViewport(Main.V_WIDTH, Main.V_HEIGHT, gameCamera);
        b2drViewport = new FitViewport(Main.V_WIDTH/PPM, Main.V_HEIGHT/PPM, b2drCamera);
        world = new World(new Vector2(0,0), true); // a new box2d world with zero gravity! (this is an overhead rpg)
        box2DDebugRenderer = new Box2DDebugRenderer();

        player = new Player(world, 0, 0);
        wall = new Wall(world, 0, -2, 2, 0.5f);


    }

    @Override
    public void render(float delta) {

        //There are two parts of the render method:
        //1) The UPDATING of the game properties (a different method, but called from the render method)
        //2) the Actual RENDERING of the game onto the screen

        //---PART I--- the Updating!
        handleInput(delta);
        updateCameras(delta);
        updateGame(delta);

        //---PART II--- the Rendering!
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        //sr.setColor(1, 1, 1, 1);
        //sr.rect(0, 0, Main.V_WIDTH, Main.V_HEIGHT);
        sr.end();

        box2DDebugRenderer.render(world, b2drCamera.combined);
        //gameCamera.combined.scl(1 / PPM);

    }

    public void updateGame(float delta){
        player.update(delta);
        world.step(1 / 60f, 6, 2);
    }

    public void updateCameras(float delta){
        //b2drCamera.position.set(player.body.getPosition().x,player.body.getPosition().y,0);
        //gameCamera.position.set(player.body.getPosition().x*PPM,player.body.getPosition().y*PPM,0);
        b2drCamera.position.set(0,0,0);
        gameCamera.position.set(0 * PPM, 0 * PPM, 0);
    }

    public void handleInput(float delta){

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        b2drViewport.update(width, height);
    }

    public void moveKeyDown(int direction){
        player.moveKeyDown(direction);
    }

    public void moveKeyUp(int direction){
        player.moveKeyReleased(direction);
    }

    @Override
    public void show() {

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
        sb.dispose();
        sr.dispose();

    }
}
