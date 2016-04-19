package com.shsgd.thebigone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shsgd.thebigone.Main;
import com.shsgd.thebigone.PlayScreenInputProcessor;
import com.shsgd.thebigone.Player;
import com.shsgd.thebigone.Utils.C;


/**
 * Created by ryananderson on 3/26/16.
 */
public class PlayScreen implements Screen{

    private Main game;
    private int myLevelIndex;
    private OrthographicCamera gameCamera = new OrthographicCamera(), b2drCamera = new OrthographicCamera();
    //game camera operates in pixels
    // b2drCamera in meters
    private Viewport b2drViewport, gameViewport;
    private static float zoomFactor = 2.343f;
    private Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    private ShapeRenderer sr = new ShapeRenderer();
    private SpriteBatch sb = new SpriteBatch(), bgsb=new SpriteBatch();
    private PlayScreenInputProcessor inputProcessor;

    //TmxMapLoader tmxMapLoader;
    //TiledMap map;
    //OrthogonalTiledMapRenderer mapRenderer;
    //int[] bgLayers = {0, 1}, foregroundLayers = {2};


    private World world; //Box2D world yay!
    private Player player;
    private Wall wall;

    //input booleans
    public boolean showb2drLines = false; //this gets true in PlayScreenInputProcessor


    public PlayScreen(Main game, int levelIndex){
        //@param levelIndex starts from zero; level1 == index 0

        //System.out.println("A new play screen has been created.");
        this.game = game;
        myLevelIndex = levelIndex;
        //gameCamera.setToOrtho(false, Main.V_WIDTH / zoomFactor, Main.V_HEIGHT / zoomFactor);
        gameViewport = new FitViewport(Main.V_WIDTH / zoomFactor, Main.V_HEIGHT / zoomFactor, gameCamera);
        //b2drCamera.setToOrtho(false, Main.V_WIDTH / zoomFactor / PPM, Main.V_HEIGHT / zoomFactor / PPM);
        b2drViewport = new FitViewport(Main.V_WIDTH/zoomFactor/ C.PPM, Main.V_HEIGHT/zoomFactor/C.PPM, b2drCamera);

        inputProcessor = new PlayScreenInputProcessor(this);

        world = new World(new Vector2(0, 0), true);

        //tmxMapLoader = new TmxMapLoader();
        //map = tmxMapLoader.load("level" + levelIndex + ".tmx");
        //System.out.println("Shifts: "+shifts);
        //mapRenderer = new OrthogonalTiledMapRenderer(map);

        //B2WorldCreator creator = new B2WorldCreator(this, world, map);
        //player = creator.getPlayer();
        player = new Player(world, 0, 0);
        wall = new Wall(world, 0, 2, 3, 1);

    }

    @Override
    public void render(float delta) {
        //This render method will consist of 2 parts
        //1) updating the game state (different method called from this method)
        //2) rendering (drawing to screen)


        //---PART I--- The updating!
        handleInput(delta);
        update(delta);
        updateCameraPosition(delta);

        //---PART II--- The rendering!
        //Clear the game screen with Black
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //mapRenderer.setView(gameCamera);
        //mapRenderer.render(bgLayers);

        //render wall(s)
        sr.setProjectionMatrix(gameCamera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0, 0, 1, 1);
        sr.rect((wall.getBody().getPosition().x-wall.getWidth()/2)*C.PPM, (wall.getBody().getPosition().y-wall.getHeight()/2)*C.PPM,
                wall.getWidth()*C.PPM, wall.getHeight()*C.PPM);
        sr.end();

        //render player
        sb.setProjectionMatrix(gameCamera.combined);
        sb.begin();
        sb.draw(player.getTexture(), (player.getBody().getPosition().x-player.getFixtureWidth()/2) * C.PPM,
                (player.getBody().getPosition().y-player.getFixtureHeight()/2)*C.PPM, player.getImageWidth()*C.PPM, player.getImageHeight()*C.PPM);
        sb.end();

        //mapRenderer.render(foregroundLayers);
        if(showb2drLines)box2DDebugRenderer.render(world, b2drCamera.combined);



    }

    public void update(float delta){

        player.update(delta);
        world.step(1 / 60f, 8, 3);    // 60 FPS
    }

    public void handleInput(float delta){

    }

    public void moveKeyDown(int direction){
        player.moveKeyDown(direction);
    }

    public void moveKeyUp(int direction){
        player.moveKeyReleased(direction);
    }


    public void updateCameraPosition(float delta){
        //REMEMBER b2drCamera uses meters and gameCamera uses pixels
        //in this method, I will update b2drCamera's position in meters and gameCamera
        //will copy it's position in pixels
        b2drCamera.position.set(0, 0, 0);
        b2drCamera.update();

        gameCamera.position.set(b2drCamera.position.x * C.PPM, b2drCamera.position.y * C.PPM, 0);
        gameCamera.update();
    }

    public void restart(){
        //System.out.println("Level restarted");
        game.setScreen(new PlayScreen(game, myLevelIndex));
    }







    @Override
    public void resize(int width, int height) {
        //gameViewport.update((int)(width / zoomFactor), (int)(height / zoomFactor));
        //b2drViewport.update((int) (width / zoomFactor /PPM), (int) (height /zoomFactor/ PPM));

        gameViewport.update(width, height);
        b2drViewport.update(width, height);
        //gameCamera.setToOrtho(false, Main.V_WIDTH / zoomFactor, Main.V_HEIGHT / zoomFactor);
        //b2drCamera.setToOrtho(false, Main.V_WIDTH / zoomFactor / PPM, Main.V_HEIGHT / zoomFactor / PPM);

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
        world.dispose();
        box2DDebugRenderer.dispose();
        sb.dispose();
        bgsb.dispose();
        //map.dispose();
        //mapRenderer.dispose();
        player.dispose();


    }



}
