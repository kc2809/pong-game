package com.mygdx.game.screens;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.box2d.Box2dManager;
import com.mygdx.game.core.FrameRate;
import com.mygdx.game.effect.EffectManager;
import com.mygdx.game.object.Ball;
import com.mygdx.game.object.Level;
import com.mygdx.game.object.Player;
import com.mygdx.game.object.PlayerCount;
import com.mygdx.game.object.Trajectory;
import com.mygdx.game.object.Walls;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.VectorUtil;
import com.mygdx.game.world.WorldContactListener;


public class MainGameScreen implements Screen, InputProcessor {
    //    public UIObject uiObject;
    public int ballBeAddedNextRow;
    World world;
//    Box2DDebugRenderer debugRenderer;
    Viewport viewport;
    OrthographicCamera camera;
    Walls walls;
    //    Stage player;
    Level level;
    Player player;
    int fireFlag = 0;
    int count = 0;
    long timeAtFire;
    Trajectory trajectory;
    //
    private WorldContactListener contactListener;
    private EffectManager effectManager;

    public int currentLevel = 1;

    FrameRate frameRate;

    PlayerCount playerCount;

    //UIObject
    public int score;
    public int money;

    //light effect test
    RayHandler handler;
    @Override
    public void show() {
//        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, Constants.VIEWPORT_WIDTH, 50, camera);
        viewport.apply();
        camera.position.set(0, 0, 0);
        world = new World(new Vector2(0, 0), true);

        trajectory = new Trajectory(viewport, 240);

        Gdx.input.setInputProcessor(this);
        initObject();

        ballBeAddedNextRow = 0;

        score = 0;
        money = 0;
    }

    private void initObject() {
        frameRate = new FrameRate();

        handler = new RayHandler(world);
        handler.setCombinedMatrix(camera.combined);
        handler.setShadows(false);

        Filter filter = new Filter();
        filter.groupIndex = 1;
        filter.categoryBits = Constants.WORLD_PHYSIC;
        filter.categoryBits = Constants.BALL_PHYSIC;


        PointLight.setGlobalContactFilter(filter);

        walls = new Walls(world, camera);

        player = new Player(viewport, this, world, handler);
        player.addNewBall();
//        player.addActor(new Ball(world));

        level = new Level(this, viewport, world);

        contactListener = new WorldContactListener(this);
        world.setContactListener(contactListener);

        effectManager = new EffectManager();
        level.addActor(effectManager);

//        uiObject = new UIObject();

        playerCount = new PlayerCount(this);
        level.addActor(playerCount);

//        new ConeLight()
    }

    public void testConLine(float x, float y){
        new ConeLight(handler, 20, Color.WHITE,5, x, y, 0,360 );
    }

    public void setEffectAtPosition(Vector2 position, Color color) {
        effectManager.setEffectAtPosition(position.x, position.y, color);
    }

    public void nextStep() {
        currentLevel++;
        System.out.println("NEXT ROWWWWW: " + player.getActors().size);
        level.moveOneRow();
    }

    public void nextRow() {
//        level.generateNextRow();
//        currentLevel++;
//        System.out.println("NEXT ROWWWWW: " + player.getActors().size);
//        level.moveOneRow();

        //   player.addNewBall();
        player.addBalls(ballBeAddedNextRow);
        // then reset it
        ballBeAddedNextRow = 0;

        //show trajectory again
        trajectory.projected(player.positionToFire.cpy().add(new Vector2(Constants.BALL_WIDTH/2, Constants.BALL_HEIGHT/2)), VectorUtil.VECTOR2_ZERO);
        trajectory.setVisible();
        System.out.println(" after NEXT ROWWWWW: " + player.getActors().size);

        //enable fire flag
        fireFlag = 0;

        //enable player count
        playerCount.setPositionToDraw();
        playerCount.setVisible(true);
    }

    @Override
    public void render(float delta) {
        world.step(1f / 60f, 6, 2);
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        debugRenderer.render(world, camera.combined);

        player.draw();
        player.act(delta);

        level.draw();
        level.act(delta);
        update(delta);

        trajectory.draw();


        Box2dManager.getInstance().destroyBody(world);

//        uiObject.draw();

        frameRate.render();

        handler.updateAndRender();

    }

    private void update(float delta) {
        if (fireFlag == 1) {
            if ((System.currentTimeMillis() - timeAtFire) > 200) {
                Ball b = (Ball) player.getActors().get(count);
                count++;
                b.fireWithVelocity(player.getVelocity());
                if (count == player.getActors().size) {
                    fireFlag = 2;
                    count = 0;
                    playerCount.setVisible(false);
                }
                timeAtFire = System.currentTimeMillis();
            }
        }

        frameRate.update();

    }


    public int getRemainBall() {
        return player.getActors().size - count;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(0, 0, 0);
        walls.setWallPositionByCamera(camera);
        // set init position again.
        player.setInitPositon();
        trajectory.projected(player.positionToFire.cpy().add(new Vector2(Constants.BALL_WIDTH / 2, Constants.BALL_HEIGHT / 2)), VectorUtil.VECTOR2_ZERO);

        frameRate.resize(width, height);

        handler.setCombinedMatrix(camera.combined);
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
        level.dispose();
        player.dispose();

        frameRate.dispose();
        handler.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A) {
            System.out.println("cac");
            player.getActors().get(0).remove();
        }
        if (keycode == Keys.B) {
//            player.addActor(new Ball(world));

        }

        if (keycode == Keys.C) {
//            level.generateNextRow();
        }
        if (keycode == Keys.D) {
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("firee");
        Vector3 worldCoordinate = camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 clickPint = new Vector2(worldCoordinate.x, worldCoordinate.y);


        //
        if (fireFlag != 0) return false;
        fireFlag = 1;
        player.setVelocityWithClickPoint(clickPint);
//        player.resetWhenFireEventFinish();
        trajectory.setInvisible();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 worldCoordinate = camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 clickPint = new Vector2(worldCoordinate.x, worldCoordinate.y);
//        trajectory.projected(player.positionToFire.cpy().add(new Vector2(0.125f, 0.125f)), clickPint);
        trajectory.projected(player.positionToFire.cpy().add(new Vector2(Constants.BALL_WIDTH / 2, Constants.BALL_WIDTH / 2)), clickPint);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
//        Vector3 worldCoordinate = camera.unproject(new Vector3(screenX, screenY, 0));
//        Vector2 clickPint = new Vector2(worldCoordinate.x, worldCoordinate.y);
//        trajectory.projected(player.positionToFire.cpy().add(new Vector2(0.25f, 0.25f)), clickPint);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void test() {
        System.out.println("bach bach");
    }

    public Player getPlayer() {
        return player;
    }

    public void increaseScore(){
        level.increaseScore();
    }

    public void increaseMoeny(){
        level.increaseMoney();
    }
}
