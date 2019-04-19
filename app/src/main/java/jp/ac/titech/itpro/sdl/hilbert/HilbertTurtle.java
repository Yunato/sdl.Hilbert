package jp.ac.titech.itpro.sdl.hilbert;

public class HilbertTurtle extends Turtle {

    public HilbertTurtle(Drawer drawer) {
        super(drawer);
    }

    public void draw(int order, double step, double turn) {
        if (order == 1) {
            drawOneOrder(order, step, turn);
        } else {
            drawMultipleOrder(order, step, turn);
        }
    }

    public void drawOneOrder(int order, double step, double turn) {
        if (order > 0) {
            rotate(-turn);
            drawOneOrder(order - 1, step, -turn);
            forward(step);
            rotate(turn);
            drawOneOrder(order - 1, step, turn);
            forward(step);
            drawOneOrder(order - 1, step, turn);
            rotate(turn);
            forward(step);
            drawOneOrder(order - 1, step, -turn);
            rotate(-turn);
        }
    }

    public void drawMultipleOrder(int order, double step, double turn) {
        rotate(-turn);
        drawOneOrder(order - 1, step, -turn);
    }
}
