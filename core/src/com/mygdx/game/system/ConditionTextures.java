package com.mygdx.game.system;

public class ConditionTextures {

    private TexturesPack none;
    private TexturesPack friendly;
    private TexturesPack hostile;

    public ConditionTextures(TexturesPack none, TexturesPack friendly, TexturesPack hostile) {

        this.none = none;
        this.friendly = friendly;
        this.hostile = hostile;

    }

    public TexturesPack getTexturesPack(int side) {
        switch (side){
            case Constants.Sides.NONE:
                return none;
            case Constants.Sides.FRIENDLY:
                return friendly;
            case Constants.Sides.HOSTILE:
                return hostile;
            default:
                return null;
        }
    }

    public void dispose(){
        none.dispose();
        friendly.dispose();
        hostile.dispose();
    }
}
