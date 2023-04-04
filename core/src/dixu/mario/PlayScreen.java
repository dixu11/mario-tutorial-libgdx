package dixu.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dixu.mario.sprites.Mario;

public class PlayScreen implements Screen {
    private final MarioGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Hud hud;
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private Box2DDebugRenderer boxRenderer;
    private World world;
    private Mario mario;

    public PlayScreen(MarioGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        hud = new Hud(game.getBath());
        //rozciaga sie
//        viewport = new StretchViewport(GameParams.WIDTH,GameParams.HEIGHT,camera);
        //pokazuje więcej świata gry
//        viewport = new ScreenViewport(camera);
        //pokazuje ciemne bary
        //zachowuje aspect ratio
        viewport = new FitViewport(GameParams.V_WIDTH/GameParams.PPM, GameParams.V_HEIGHT/GameParams.PPM, camera);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1f/GameParams.PPM);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        world = new World(new Vector2(0, -10), true);
        boxRenderer = new Box2DDebugRenderer();

        mario = new Mario(world);


        //kinematic dont react to velocity
        //ground
        for (RectangleMapObject mapObject : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = mapObject.getRectangle();
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.x + rectangle.width / 2 ) /GameParams.PPM,( rectangle.y + rectangle.height / 2)/GameParams.PPM);
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(rectangle.width/2,rectangle.height/2);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
        //pipes
        for (RectangleMapObject mapObject : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = mapObject.getRectangle();
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.x + rectangle.width / 2 ) /GameParams.PPM,( rectangle.y + rectangle.height / 2)/GameParams.PPM);
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(rectangle.width/2,rectangle.height/2);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
        //coins
        for (RectangleMapObject mapObject : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = mapObject.getRectangle();
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.x + rectangle.width / 2 ) /GameParams.PPM,( rectangle.y + rectangle.height / 2)/GameParams.PPM);
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(rectangle.width/2,rectangle.height/2);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
        //bricks
        for (RectangleMapObject mapObject : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = mapObject.getRectangle();
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.x + rectangle.width / 2 ) /GameParams.PPM,( rectangle.y + rectangle.height / 2)/GameParams.PPM);
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(rectangle.width/2,rectangle.height/2);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        renderer.render();
        boxRenderer.render(world, camera.combined);
        SpriteBatch batch = game.getBath();
        //move to render method
        batch.setProjectionMatrix(hud.getStage().getCamera().combined); // ?
        hud.getStage().draw();
    }

    public void update(float delta) {
        handleInput(delta);
        world.step(1 / 60f, 6, 2);// standard values
        mario.update(delta);
        camera.position.x = mario.getBody().getPosition().x;
        camera.update();
        renderer.setView(camera); // only render what camera shows
    }



    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)||Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.x += 100 * delta;
            mario.getBody().applyLinearImpulse(new Vector2(0,4f),mario.getBody().getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.getBody().getLinearVelocity().x<=2){
            mario.getBody().applyLinearImpulse(new Vector2(0.1f,0),mario.getBody().getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.getBody().getLinearVelocity().x>=-2){
            mario.getBody().applyLinearImpulse(new Vector2(-0.1f,0),mario.getBody().getWorldCenter(),true);
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
