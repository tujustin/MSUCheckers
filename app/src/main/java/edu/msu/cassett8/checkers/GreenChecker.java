package edu.msu.cassett8.checkers;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class GreenChecker extends CheckerPiece {



    public GreenChecker(Context context, int id, int number) {

        super(context, id, number);
    }

    @Override
    public void AcceptVisitor(CheckerVisitor Visitor)
    {
        Visitor.visitedGreen();
    }

}
