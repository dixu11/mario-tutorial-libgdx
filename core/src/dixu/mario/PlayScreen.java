package dixu.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
    private MarioGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

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
        viewport = new FitViewport(GameParams.V_WIDTH, GameParams.V_HEIGHT, camera);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        renderer.render();
        SpriteBatch batch = game.getBath();
        //move to render method
        batch.setProjectionMatrix(hud.getStage().getCamera().combined); // ?
        hud.getStage().draw();
    }

    public void update(float delta) {
        handleInput(delta);
        camera.update();
        renderer.setView(camera); // only render what camera shows
    }

    private void handleInput(float delta) {
        if (Gdx.input.isTouched()) {
            camera.position.x += 100 * delta;
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
