package dixu.mario;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.Or;

public class MarioGame extends Game {
    private SpriteBatch batch;
	private BitmapFont font;

    @Override
    public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBath() {
        return batch;
    }
}
