package edu.msu.cassett8.checkers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class GreenChecker extends CheckerPiece {

    Context mContext;


    public GreenChecker(Context context, int number) {

        super(context, number);
        mContext = context;
        super.setPiece(BitmapFactory.decodeResource(context.getResources(), R.drawable.spartan_green));
    }

    @Override
    public void draw(Canvas canvas, int marginX, int marginY,
                     int puzzleSize, float scaleFactor) {

        if(super.getKing())
        {
            super.setPiece(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.king_green));
        }
        super.draw(canvas, marginX, marginY, puzzleSize, scaleFactor);
    }
}
