package dixu.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
    private MarioGame game;
    private Texture texture;
    private OrthographicCamera camera;
    private Viewport viewport;

    public PlayScreen(MarioGame game) {
        this.game = game;
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        camera = new OrthographicCamera();
        //rozciaga sie
//        viewport = new StretchViewport(GameParams.WIDTH,GameParams.HEIGHT,camera);
        //pokazuje więcej świata gry
//        viewport = new ScreenViewport(camera);
        //pokazuje ciemne bary
        //zachowuje aspect ratio
        viewport = new FitViewport(GameParams.WIDTH,GameParams.HEIGHT,camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        SpriteBatch batch = game.getBath();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture,0,0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
