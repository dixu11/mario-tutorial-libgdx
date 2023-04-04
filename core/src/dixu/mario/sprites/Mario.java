package dixu.mario.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import dixu.mario.GameParams;

public class Mario extends Sprite {
    private World world;
    private Body body;

    public Mario(World world) {
        this.world = world;
        defineMario();
    }

    public void update(float delta){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32/ GameParams.PPM, 100/GameParams.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/GameParams.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

    public Body getBody() {
        return body;
    }
}
