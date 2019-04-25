package jp.ac.titech.itpro.sdl.hilbert;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class HilbertView extends View {

    private Paint paint = new Paint();

    private Canvas canvas;
    private Canvas savedCanvas;
    private Bitmap bitmap;
    private Bitmap editedBitmap;

    private int order = 1;
    private boolean isFirst = true;
    private boolean wasDrawn = false;

    private HilbertTurtle turtle = new HilbertTurtle(new Turtle.Drawer() {
        @Override
        public void drawLine(double x0, double y0, double x1, double y1) {
            canvas.drawLine((float) x0, (float) y0, (float) x1, (float) y1, paint);
        }
    });

    private HilbertTurtle savedTurtle = new HilbertTurtle(new Turtle.Drawer() {
        @Override
        public void drawLine(double x0, double y0, double x1, double y1) {
            savedCanvas.drawLine((float) x0, (float) y0, (float) x1, (float) y1, paint);
        }
    });

    public Canvas getCanvas() {
        return canvas;
    }

    public HilbertView(Context context) {
        this(context, null);
    }

    public HilbertView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HilbertView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        int w = canvas.getWidth();
        int h = canvas.getHeight();
        // Create canvas object to save
        editedBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        savedCanvas = new Canvas(editedBitmap);
        Canvas[] canvases = { this.canvas, savedCanvas};

        // Draw background
        paint.setColor(Color.DKGRAY);
        for(Canvas tmp : canvases){
            tmp.drawRect(0, 0, w, h, paint);
        }

        // Set param
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        int size = Math.min(w, h);
        double step = (double) size / (1 << order);
        int x, y;
        int width, height;
        width = height = (int)step;
        x = (w - size + width) / 2;
        y = (h + size - width) / 2;

        if(isFirst){
            drawHilbert(w, h, size, step);
            trimBitmap(x, y - height, width, height);
            bitmap = editedBitmap;
            isFirst = false;
        }else if(wasDrawn){
            drawBitmap(x, y - height);
        }else{
            drawPreBitmap();
            bitmap = editedBitmap;
        }
    }

    public void setOrder(int n) {
        order = n;
        invalidate();
    }

    public void setBitmap(Bitmap bitmap, boolean wasDrawn){
        this.bitmap = bitmap;
        this.wasDrawn = wasDrawn;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    private void drawHilbert(int w, int h, int size, double step){
        savedCanvas = new Canvas(editedBitmap);
        HilbertTurtle[] turtles = { turtle, savedTurtle};
        for(HilbertTurtle tmp : turtles){
            tmp.setPos((w - size + step) / 2, (h + size - step) / 2);
            tmp.setDir(HilbertTurtle.E);
            tmp.draw(order, step, HilbertTurtle.R);
        }
    }

    private void drawPreBitmap(){
    }

    private void drawBitmap(int x, int y){
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    private void trimBitmap(int x, int y, int width, int height){
        editedBitmap = Bitmap.createBitmap(editedBitmap, x, y, width, height, null, true);
    }
}
