package com.mygdx.game.gameObjects.ships.mastership;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.actions.Progress;
import com.mygdx.game.actions.Send;
import com.mygdx.game.gameObjects.stars.Star;
import com.mygdx.game.models.MastershipModel;
import com.mygdx.game.system.Constants;
import com.mygdx.game.system.Point;
import com.mygdx.game.system.TexturesPack;
import com.mygdx.game.system.View;
import com.mygdx.game.textures.focus.FocusTexture;
import com.mygdx.game.textures.progress.ProgressTexture;
import com.mygdx.game.textures.ships.mastership.MastershipTextureFriendly;

// главный корабль дружественный
public class FriendlyMastership extends Mastership {

    private MastershipModel model;
    private View view;
    private View focusView;
    private Send send;
    private Progress progress;

    private Array<Star> stars;
    private Star star;

    private TexturesPack texturesPack;
    private ProgressTexture progressTexture;

    private boolean focusFlag;

    public FriendlyMastership(Star starModel, Array<Star> starModels, FocusTexture focusTexture) {

        this.star = starModel;
        this.stars = starModels;

        texturesPack = new MastershipTextureFriendly();

        view = new View(texturesPack, starModel.getBasicStar().getModel().getCenterPoint(), 0);
        model = new MastershipModel(starModel.getBasicStar().getModel().getCenterPoint(), Constants.Sides.FRIENDLY);

        send = new Send(starModel, this);
        progressTexture = new ProgressTexture(180, 128, Constants.Colors.friendly, 2, 5000);
        progress = new Progress(progressTexture, model.getCenterPoint());

        focusFlag = false;

        focusView = new View(focusTexture, model.getCenterPoint(), 0);
    }

    @Override
    public MastershipModel getModel() {
        return model;
    }

    @Override
    public MastershipModel setModel() {
        return null;
    }

    @Override
    public Send getSend() {
        return send;
    }

    @Override
    public Array<View> getViews() {
        Array<View> views = new Array<View>();

        send.update();
        if (updateProgress()) {
            views.add(progress.getView());
        }


        if (focusFlag)
            views.add(focusView);

        view.update(send.isSande());
        views.add(view);

        return views;
    }

    private boolean updateProgress() {

        if (send.isSande() || star.getBasicStar().getModel().getSide() == Constants.Sides.FRIENDLY) {
            progress.setTime(System.currentTimeMillis());
            progress.setCurrentFrame(0);
            return false;
        }

        if (progress.isDone()) {
            star.getBasicStar().setSide((star.getBasicStar().getModel().getSide() + 1) % 3);
            progress.setCurrentFrame(0);
            progress.setTime(System.currentTimeMillis());
            return false;
        }

        return true;
    }

    @Override
    public View getShipView() {
        return view;
    }

    @Override
    public void onTouch(Point touch) {

        if (focusFlag) {
            if (model.getCenterPoint().inRectRangeThatPoint(touch,
                    view.getFrame().getWidth(), view.getFrame().getHeight())) {
                focusFlag = false;
                return;
            }

            for (int i : star.getBasicStar().getModel().getConnectedStars())
                if (stars.get(i).getBasicStar().getModel().getCenterPoint().inRectRangeThatPoint(touch,
                        view.getFrame().getWidth(), view.getFrame().getHeight())) {
                    send.send(stars.get(i));
                    focusFlag = true;
                    return;
                } else
                    focusFlag = false;

        } else
            focusFlag = model.getCenterPoint().inRectRangeThatPoint(touch,
                    view.getFrame().getWidth(), view.getFrame().getHeight());
    }

    @Override
    public void setCenterPoint(Point centerPoint) {
        view.setRenderPointByCenter(centerPoint);
        view.setOriginPointInCenter();
        model.setCenterPoint(centerPoint);
        progress.setCenterPoint(centerPoint);
        focusView.setRenderPointByCenter(centerPoint);
    }

    @Override
    public Array<Star> getStars() {
        return stars;
    }

    @Override
    public void setStar(Star star) {
        this.star = star;
    }

    public Star getStar(){
        return star;
    }
}
