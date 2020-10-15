package edu.msu.cassett8.checkers;

import android.content.Context;
import android.graphics.BitmapFactory;



public class GreenChecker extends CheckerPiece {

    /**
     * x location when the piece is placed
     */
    private float startX;

    /**
     * y location when the piece is placed
     */
    private float startY;

    public GreenChecker(Context context, int id, int number) {

        super(context, id, number);
    }
}
