package com.bartish.oooit.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.bartish.oooit.Main;
import com.bartish.oooit.actors.Item;
import com.bartish.oooit.utils.GameColors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LogoStage extends MyStage {
    private Actor forColor;
    private Group group;
    private Item items[] = {
            new Item(4),
            new Item(2),
            new Item(100),
            new Item(200),
            new Item(300),
            new Item(400)
    };

    public LogoStage(ExtendViewport viewport) {
        super(viewport);
        forColor = new Actor();
        group = new Group();
        group.setSize(360, 360);

        addActor(group);
        for(int i = 0; i < items.length; i++){
            group.addActor(items[i]);

            items[i].addAction(alpha(0));
            items[i].setPosition(i * 60 + 2, (viewport.getWorldHeight() + group.getHeight()) / 2);
            items[i].addAction(delay(i * 0.08f, sequence(
                    parallel(
                            moveTo(items[i].getX(), (group.getHeight() - items[i].getHeight()) / 2,
                                1.25f, Interpolation.exp10Out),
                            alpha(1, 0.5f, Interpolation.pow3Out))
            )));
        }

        forColor.setColor(Color.BLACK);
        forColor.addAction(sequence(color(GameColors.BACK, 3f, Interpolation.exp5Out), delay(0f,run(new Runnable() {
            @Override
            public void run() {
                Main.changeStages();
            }
        }))));

        addActor(forColor);
        forColor.setScale(0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(Gdx.input.isTouched()){
            Main.changeStages();
        }
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(forColor.getColor().r, forColor.getColor().g, forColor.getColor().b, 1);
        super.draw();
    }

    @Override
    public void resize(float worldWidth, float worldHeight){
        group.setPosition((worldWidth - group.getWidth()) / 2, (worldHeight - group.getHeight()) / 2);
    }

}
