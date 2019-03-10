package uk.team_1.draw_n_shake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        View window = getWindow().getDecorView(); // android host window

        // uses bitwise OR to combine flags
        window.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | // hide nav buttons
                View.SYSTEM_UI_FLAG_FULLSCREEN        // hide status bar
        );

    }
}
