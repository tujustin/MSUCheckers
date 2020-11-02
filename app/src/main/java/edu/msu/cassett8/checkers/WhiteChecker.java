package edu.msu.cassett8.checkers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class WhiteChecker extends CheckerPiece {

    Context mContext;


    public WhiteChecker(Context context, int number) {

        super(context, number);
        mContext = context;
        super.setPiece(BitmapFactory.decodeResource(context.getResources(), R.drawable.spartan_white));
    }


    @Override
    public void draw(Canvas canvas, int marginX, int marginY,
                     int puzzleSize, float scaleFactor) {

        if(super.getKing())
        {
            super.setPiece(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.king_white));
        }
        super.draw(canvas, marginX, marginY, puzzleSize, scaleFactor);
    }

    @Override
    public void AcceptVisitor(CheckerVisitor Visitor)
    {
        Visitor.visitedWhite();
    }



}
