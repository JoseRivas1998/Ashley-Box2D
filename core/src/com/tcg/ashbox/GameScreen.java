package com.tcg.ashbox;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tcg.ashbox.controller.KeyboardController;
import com.tcg.ashbox.entity.components.*;
import com.tcg.ashbox.entity.systems.CollisionSystem;
import com.tcg.ashbox.entity.systems.PhysicsDebugSystem;
import com.tcg.ashbox.entity.systems.PhysicsSystem;
import com.tcg.ashbox.entity.systems.PlayerControlSystem;
import com.tcg.ashbox.physics.B2dContactListener;

public class GameScreen implements Screen {

    private KeyboardController keyboardController;
    private World world;
    private SpriteBatch sb;
    private OrthographicCamera cam;
    private PooledEngine engine;

    @Override
    public void show() {
        keyboardController = new KeyboardController();
        world = new World(new Vector2(0, -10f), true);
        world.setContactListener(new B2dContactListener());

        float pixelsPerMeter = 32.0f;
        float worldWidth = Gdx.graphics.getWidth() / pixelsPerMeter;
        float worldHeight = Gdx.graphics.getHeight() / pixelsPerMeter;

        cam = new OrthographicCamera(worldWidth, worldHeight);
        cam.position.set(worldWidth / 2f, worldHeight / 2f, 0);
        cam.update();
        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

        engine = new PooledEngine();
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, cam));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(keyboardController));


        createPlayer();
        createPlatform(2, 2);
        createPlatform(3, 4);
        createPlatform(5, 2);
        createPlatform(5, 4);

        createFloor();

        Gdx.input.setInputProcessor(keyboardController);

    }

    private void createPlatform(float x, float y) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBody = engine.createComponent(B2dBodyComponent.class);
        b2dBody.body = createBox(x, y, 3f, 0.2f, false);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.Type.SCENERY;
        b2dBody.body.setUserData(entity);

        entity.add(b2dBody);
        entity.add(type);

        engine.addEntity(entity);

    }

    private void createFloor() {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBody = engine.createComponent(B2dBodyComponent.class);
        b2dBody.body = createBox(0, 0, 100, 0.2f, false);
        TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
        typeComponent.type = TypeComponent.Type.SCENERY;
        b2dBody.body.setUserData(entity);

        entity.add(b2dBody);
        entity.add(typeComponent);

        engine.addEntity(entity);
    }

    private void createPlayer() {

        Entity entity = engine.createEntity();

        B2dBodyComponent b2dBody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);

        b2dBody.body = createOval(10, 1, 1, true);

        position.position.set(10, 10, 0);
        type.type = TypeComponent.Type.PLAYER;
        stateCom.set(StateComponent.State.NORMAL);
        b2dBody.body.setUserData(entity);

        entity.add(b2dBody);
        entity.add(position);
        entity.add(player);
        entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);

        engine.addEntity(entity);

    }

    private Body createBox(float x, float y, float w, float h, boolean dynamic) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = dynamic ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;

        boxBodyDef.position.x = x;
        boxBodyDef.position.y = y;
        boxBodyDef.fixedRotation = true;

        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(w / 2f, h / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = poly;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.0f;

        boxBody.createFixture(fixtureDef);
        poly.dispose();
        return boxBody;

    }

    private Body createOval(float x, float y, float w, boolean dynamic){
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = dynamic ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;

        boxBodyDef.position.x = x;
        boxBodyDef.position.y = y;
        boxBodyDef.fixedRotation = true;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(w /2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0f;

        boxBody.createFixture(fixtureDef);
        circleShape.dispose();

        return boxBody;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
