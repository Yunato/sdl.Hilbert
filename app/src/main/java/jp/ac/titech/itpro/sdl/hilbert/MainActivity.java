package jp.ac.titech.itpro.sdl.hilbert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static int MAX_ORDER = 9;
    public static final String EXTRA_STRING_ORDER =
            "jp.ac.titech.itpro.sdl.hilbert.EXTRA_STRING_KEY";
    private int order = 1;
    private boolean isFist = true;
    private BitmapManager manager;

    private TextView orderView;
    private HilbertView hilbertView;
    private Button decButton;
    private Button incButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderView = findViewById(R.id.order_view);
        hilbertView = findViewById(R.id.hilbert_view);
        decButton = findViewById(R.id.dec_button);
        incButton = findViewById(R.id.inc_button);

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assertTrue(order > 1, "A room to decrement order should exist");
                if(!manager.wasDrawn(order)){
                    manager.setFigure(order, hilbertView.getBitmap());
                }
                order--;
                display();
            }
        });
        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assertTrue(order < MAX_ORDER, "A room to increment order should exist");
                if(!manager.wasDrawn(order)){
                    manager.setFigure(order, hilbertView.getBitmap());
                }
                order++;
                display();
            }
        });

        if (savedInstanceState != null) {
            order = savedInstanceState.getInt(EXTRA_STRING_ORDER);
        }
        manager = new BitmapManager(MAX_ORDER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        display();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_STRING_ORDER, order);
    }

    private void display() {
        orderView.setText(getString(R.string.order_view_format, order));
        if(!isFist){
            if(manager.wasDrawn(order)){
                hilbertView.setBitmap(manager.getFigure(order), true);
            }else{
                hilbertView.setBitmap(manager.getFigure(order - 1), false);
            }
        }
        hilbertView.setOrder(order);
        decButton.setEnabled(order > 1);
        incButton.setEnabled(order < MAX_ORDER);
        isFist = false;
    }

    public static void assertTrue(boolean f, String message) {
        if (BuildConfig.DEBUG && !f) {
            throw new AssertionError(message);
        }
    }
}
