package edu.msu.cassett8.checkers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class CheckersView extends View {

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private CheckerBoard board;

    public CheckersView(Context context)
    {
        super(context);
        init(null, 0);
    }

    public CheckersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CheckersView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        board = new CheckerBoard(getContext());
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CheckersView, defStyle, 0);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        board.draw(canvas);
    }

}
