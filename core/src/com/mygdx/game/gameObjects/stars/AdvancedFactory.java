package com.mygdx.game.gameObjects.stars;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.models.StarModel;
import com.mygdx.game.system.View;

public class AdvancedFactory extends Star {

    @Override
    public StarModel getModel() {
        return null;
    }

    @Override
    public boolean isBlocked() {
        return false;
    }

    @Override
    public void onTouch() {

    }

    @Override
    public Array<View> getViews() {
        return null;
    }
}
