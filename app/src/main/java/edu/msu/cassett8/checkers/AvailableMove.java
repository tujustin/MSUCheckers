package edu.msu.cassett8.checkers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class AvailableMove {

    final private Paint linePaint;
    /**
     * x coordinates for move
     */
    private float xCord;
    /**
     * y coordinates for move
     */
    private float yCord;

    /**
     * Pointer to piece
     */
    private CheckerPiece movePiece;

    /**
     * Available moves for that current piece
     * @param x available move
     * @param y available move
     * @param piece of current move
     */
    public AvailableMove(float x, float y, CheckerPiece piece){
        xCord = x;
        yCord = y;
        movePiece = piece;
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFF504B);
        linePaint.setStrokeWidth(3);

    }

    /**
     * Draw function for the player class
     * @param canvas of this
     */
    public void draw(Canvas canvas, float marginX, float marginY, float puzzleSize, float scaleFactor){
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + xCord * puzzleSize, marginY + yCord * puzzleSize);

        // Scale it to the right size
        canvas.scale(scaleFactor/1.5f, scaleFactor/1.5f);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(movePiece.getWidth() / 2f, -movePiece.getHeight() / 2f);

        // Draw the bitmap

        float squareSize = movePiece.getWidth();
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(3);

        canvas.drawRect(xCord-squareSize, yCord, xCord , yCord+squareSize, linePaint);
        canvas.restore();

    }


    /**
     * Hit test for current of piece
     * @param x of piece
     * @param y of piece
     */
    public void hitTest(float x, float y){

    }
}
