package uk.team_1.draw_n_shake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

    private Canvas canvas; // drawing surface
    /*
    - bitmap image for canvas
    - needed for drawing
    - stored as member as canvas has no getter
    */
    private Bitmap canvasBM;

    // no default constructors
    public CanvasView(Context context) { super(context); }
    public CanvasView(Context context, AttributeSet attrs) { super(context, attrs); }

    private void init(DisplayMetrics metrics) {
        Log.d("team_1/setup", "init() called");

        canvasBM = Bitmap.createBitmap(
                metrics.widthPixels,    // screen width
                metrics.heightPixels,   // screen width
                Bitmap.Config.ARGB_8888 // recomended bitmap config
        );

        canvas = new Canvas(canvasBM); // create canvas with our bitmap
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("team_1/update", "onDraw() called");
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.d("team_1/event", "onTouchEvent() called");

        float x = e.getX();
        float y = e.getY();

        switch(e.getAction()) { // TODO: add functionality
            case MotionEvent.ACTION_DOWN:
                Log.d("team_1/event", "ACTION_DOWN() triggered");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("team_1/event", "ACTION_MOVE() triggered");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("team_1/event", "ACTION_UP() triggered");
                break;
        }

        return true; // TODO: maybe return something relevant
    }
}
