package edu.msu.cassett8.checkers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CheckerPiece {

    private Bitmap piece;

    /**
     * x location.
     * We use relative x locations in the range 0-1 for the center
     * of the puzzle piece.
     */
    private float x = 0;

    /**
     * y location
     */
    private float y = 0;

    private int mNum; //number identifier for checker piece

    public CheckerPiece(Context context, int id, int number) {

        //based on checkernumber
        piece = BitmapFactory.decodeResource(context.getResources(), id);
        mNum = number;
    }
    /**
     * Draw the puzzle piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param puzzleSize Size we draw the puzzle in pixels
     * @param scaleFactor Amount we scale the puzzle pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int puzzleSize, float scaleFactor) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * puzzleSize, marginY + y * puzzleSize);

        // Scale it to the right size
        canvas.scale(scaleFactor/1.5f, scaleFactor/1.5f);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(piece, 0, 0, null);

        canvas.restore();
    }

    public void setCords(float xCord, float yCord){
        x= xCord;
        y= yCord;
    }

    /**
     * Hit test for the checker pieces when the users touch the screen
     * @param testX
     * @param testY
     */
    public boolean hit(float testX, float testY,
                       int puzzleSize, float scaleFactor) {

        // Make relative to the location and size to the piece size
        int pX = (int)((testX - x) * puzzleSize / scaleFactor) +
                piece.getWidth() / 2;
        int pY = (int)((testY - y) * puzzleSize / scaleFactor) +
                piece.getHeight() / 2;

        if(pX < 0 || pX >= piece.getWidth() ||
                pY < 0 || pY >= piece.getHeight()) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }
    public Bitmap getPiece(){
        return piece;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getWidth()
    {
        return piece.getWidth();
    }
    public float getHeight()
    {
        return piece.getHeight();
    }
}
