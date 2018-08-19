package com.mygdx.game.screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.box2d.Box2dManager;
import com.mygdx.game.core.FrameRate;
import com.mygdx.game.effect.EffectManager;
import com.mygdx.game.object.Ball;
import com.mygdx.game.object.Level;
import com.mygdx.game.object.Player;
import com.mygdx.game.object.PlayerCount;
import com.mygdx.game.object.Trajectory;
import com.mygdx.game.object.Walls;
import com.mygdx.game.sound.SoundManager;
import com.mygdx.game.storage.MyPreference;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.VectorUtil;
import com.mygdx.game.world.WorldContactListener;

import static com.mygdx.game.util.Constants.SPEED;


public class MainGameScreen implements Screen, InputProcessor {
    MyGdxGame game;
    public int ballBeAddedNextRow;
    World world;
    public int currentLevel;
    OrthographicCamera camera;
    Walls walls;
    public Level level;
    Player player;
    public int power;
    public boolean paused;
    long timeAtFire;

    //Trajectory
    Trajectory trajectory;
    //
    private WorldContactListener contactListener;
    private EffectManager effectManager;
    int fireFlag;
    PlayerCount playerCount;

    //UIObject
    public int score;
    public int money;
    int count;
    //light effect
    RayHandler handler;
    FrameRate frameRate;
    private int powerTimes;

    public boolean isSoundOn;

    private Vector2 touchDown;
    //    Box2DDebugRenderer debugRenderer;
    Viewport viewport;
    InputMultiplexer multiplexer;

    public MainGameScreen(MyGdxGame game, OrthographicCamera camera, Viewport viewport) {
        initGameState();
//        debugRenderer = new Box2DDebugRenderer();
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;

        viewport.apply();
        camera.position.set(0, 0, 0);

        trajectory = new Trajectory(viewport, 240);

        initObject();

        ballBeAddedNextRow = 0;
    }

    private void initGameState() {
        paused = false;
        fireFlag = 0;
        count = 0;
        currentLevel = 1;
        score = 0;
//        money = 0;
        money = MyPreference.getInstance().getMoney();
        isSoundOn = MyPreference.getInstance().isSoundOn();
        power = 1;
    }

    public void onceMoreTime() {
        paused = false;
        fireFlag = 0;
        count = 0;
        playerCount.setPositionToDraw();
        playerCount.setVisible(true);
    }

    @Override
    public void show() {
        paused = false;
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(level);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        setColorPlayer();
        money = MyPreference.getInstance().getMoney();
    }

    private void initObject() {
        world = new World(new Vector2(0, 0), true);
        frameRate = new FrameRate();

        handler = new RayHandler(world);
        handler.setCombinedMatrix(camera);
        handler.setShadows(false);

        Filter filter = new Filter();
        filter.groupIndex = 1;
        filter.categoryBits = Constants.WORLD_PHYSIC;
        filter.categoryBits = Constants.BALL_PHYSIC;

        PointLight.setGlobalContactFilter(filter);

        walls = new Walls(world, camera);

        player = new Player(viewport, this, world, handler);
        player.addNewBall();
        level = new Level(this, viewport, world);

        contactListener = new WorldContactListener(this);
        world.setContactListener(contactListener);

        effectManager = new EffectManager();
        level.addActor(effectManager);

//        arrow = new Arrow();
        playerCount = new PlayerCount(this);
        level.addActor(playerCount);
//        level.addActor(arrow);
    }

    public void setEffectAtPosition(Vector2 position, Color color) {
        effectManager.setEffectAtPosition(position.x, position.y, color);
    }

    public void nextStep() {
        currentLevel++;
        System.out.println("NEXT ROWWWWW: " + player.getActors().size);
        setPowerPlayer();
        level.moveOneRow();
    }

    public void nextRow() {
        if (isSoundOn) SoundManager.instance.playLevelUpSound();
        level.increaseScore();
        //   player.addNewBall();
        player.addBalls(ballBeAddedNextRow);
        // then reset it
        ballBeAddedNextRow = 0;

        //show trajectory again
        trajectory.projected(player.positionToFire.cpy().add(new Vector2(Constants.BALL_WIDTH/2, Constants.BALL_HEIGHT/2)), VectorUtil.VECTOR2_ZERO);
        trajectory.setVisible(true);
        System.out.println(" after NEXT ROWWWWW: " + player.getActors().size);

        //enable fire flag
        fireFlag = 0;

        //enable player count
        playerCount.setPositionToDraw();
        playerCount.setVisible(true);
//        if(level.checkGameOver()){
//            gameOver();
//        }
    }

    @Override
    public void render(float delta) {
        if (paused) return;
        world.step(1f / 60f, 6, 2);
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        debugRenderer.render(world, camera.combined);

        player.act(delta);
        player.draw();

        level.act(delta);
        level.draw();

        update(delta);

        trajectory.draw();

        Box2dManager.getInstance().inActiveBodies(world);
        Box2dManager.getInstance().activeBodies(world);

        frameRate.render();
        handler.updateAndRender();

    }

    private void update(float delta) {
        if (paused) return;
        if (fireFlag == 1) {
            if ((System.currentTimeMillis() - timeAtFire) > 80) {
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
        frameRate.resize(width, height);
        handler.setCombinedMatrix(camera);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {
        paused = true;
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
            level.reset();
        }
        if (keycode == Keys.D) {
            gameOver();
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    private boolean isValidVelocity(Vector2 velocity) {
        return velocity.angle() > 7 && velocity.angle() < 173;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinate = camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 clickPint = new Vector2(worldCoordinate.x, worldCoordinate.y);
        System.out.println("down: " + clickPint);
        touchDown = clickPint;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (fireFlag != 0) return false;
        System.out.println("firee");
        Vector3 worldCoordinate = camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 touchUp = new Vector2(worldCoordinate.x, worldCoordinate.y);
        //if ball is process or touch up point >= touch down point then do nothing
        if (fireFlag != 0 || touchUp.y >= touchDown.y) return false;
        Vector2 velocity = touchDown.sub(touchUp).nor().scl(SPEED);
        if (!isValidVelocity(velocity)) return false;
        fireFlag = 1;
        player.fire(velocity);
        trajectory.setVisible(false);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (fireFlag != 0) return false;
        Vector3 worldCoordinate = camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 touchDragPoint = new Vector2(worldCoordinate.x, worldCoordinate.y);
        Vector2 velocity = touchDown.cpy().sub(touchDragPoint).nor();
        trajectory.setVisible(isValidVelocity(velocity));
        trajectory.projected(player.centerPosition(), velocity);
        playerCount.rotateArrow(velocity.angle());
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public void increaseMoeny(){
        level.increaseMoney();
    }

    public void setColorPlayer() {
        player.setColorBalls(MyPreference.getInstance().getCurrentColor());
    }

    public void setPowerPlayer() {
        if (power == 2) {
            if (--powerTimes == 0) {
                power = 1;
                player.setDistance(power);
            }
        }

        if (currentLevel % 10 == 0) {
            power = 2;
            powerTimes = 2;
            player.setDistance(power);
        }
    }


    public void gameOver() {
        MyPreference.getInstance().setMoney(money);
        if (score > MyPreference.getInstance().getHighestScore()) {
            MyPreference.getInstance().setHighestScore(score);
        }
        game.changeGameOverScreen();
    }

    public void reset(){
        walls.setWallPositionByCamera(camera);
        // set init position again.
//        player.setInitPositon();
        trajectory.projected(player.positionToFire.cpy().add(new Vector2(Constants.BALL_WIDTH / 2, Constants.BALL_HEIGHT / 2)), VectorUtil.VECTOR2_ZERO);
        level.reset();
        player.reset();
        playerCount.setPositionToDraw();
        playerCount.setVisible(true);
//        arrow.setPositionByPlayer(player.positionToFire);
        initGameState();
        level.generateNextStep();
        ballBeAddedNextRow = 0;
    }

    public void pauseScreen() {
        this.pause();
        game.changePausedScreen();
    }

    public void replay() {
        onceMoreTime();
        level.oneMoreTime();
    }
}
