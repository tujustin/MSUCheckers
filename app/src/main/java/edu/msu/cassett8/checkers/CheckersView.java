package edu.msu.cassett8.checkers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class CheckersView extends View {

    private CheckerBoard board;


    public CheckersView(Context context)
    {
        super(context);
        init(null, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return board.onTouchEvent(this, event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public CheckersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

    }

    public void saveInstanceState(Bundle bundle) {
        board.saveInstanceState(bundle);
    }
    public void loadInstanceState(Bundle bundle) {
        board.loadInstanceState(bundle, getContext());
    }
    public CheckersView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
    public int getTurn()
    {
        return board.getTurn();
    }

    public void setP1(String p1){
        board.setPlayerOne(p1);
    }
    public void setP2(String p2){
        board.setPlayerTwo(p2);
    }

    public int getWinner()
    {
        return board.getWinner();
    }
    public void changeTurn()
    {
        board.changeTurn();
    }
    public boolean getisEnd()
    {
        return board.getisEnd();
    }
    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        board = new CheckerBoard(getContext(), this);
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CheckersView, defStyle, 0);

        setId(R.id.checkersView);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        board.draw(canvas);
    }

}
