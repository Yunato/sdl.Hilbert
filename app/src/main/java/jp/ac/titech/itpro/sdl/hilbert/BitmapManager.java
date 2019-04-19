package jp.ac.titech.itpro.sdl.hilbert;

import android.graphics.Bitmap;

public class BitmapManager {
    // true: drawn, false: not drawn
    private Bitmap[] figures;

    public BitmapManager(int max_order) {
        figures = new Bitmap[max_order + 2];
    }

    public boolean wasDrawn(int order){
        return figures[order] != null;
    }

    public Bitmap getFigure(int order){
        return figures[order];
    }

    public void setFigure(int order, Bitmap bitmap){
        figures[order] = bitmap;
    }
}
