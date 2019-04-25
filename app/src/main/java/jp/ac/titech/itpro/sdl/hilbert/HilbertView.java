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

    private int order = 1;
    private boolean canDraw = false;

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

        // Create canvas object to save
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        savedCanvas = new Canvas(bitmap);
        Canvas[] canvases = { this.canvas, savedCanvas};
        HilbertTurtle[] turtles = { turtle, savedTurtle};

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

        // Draw hilbert
        for(HilbertTurtle tmp : turtles){
            tmp.setPos((w - size + step) / 2, (h + size - step) / 2);
            tmp.setDir(HilbertTurtle.E);
            tmp.draw(order, step, HilbertTurtle.R);
        }
    }

    public void setOrder(int n, boolean canDraw) {
        order = n;
        this.canDraw = canDraw;
        invalidate();
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
