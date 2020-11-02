package edu.msu.cassett8.checkers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class AvailableMove {

    final private Paint linePaint;
    /**
     * x coordinates for move
     */
    private float x;
    /**
     * y coordinates for move
     */
    private float y;

    /**
     * Pointer to piece
     */
    private CheckerPiece piece;

    private CheckerPiece JumpedPiece = null;

    /**
     * Available moves for that current piece
     * @param x available move
     * @param y available move
     * @param piece of current move
     */
    public AvailableMove(float x, float y, CheckerPiece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFF504B);
        linePaint.setStrokeWidth(3);

    }

    public AvailableMove(float x, float y, CheckerPiece piece, CheckerPiece jumpedPiece){
        this.x = x;
        this.y = y;
        this.piece = piece;
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFF504B);
        linePaint.setStrokeWidth(3);
        this.JumpedPiece = jumpedPiece;

    }


    /**
     * Draw function for the player class
     * @param canvas of this
     */
    public void draw(Canvas canvas, float marginX, float marginY, float puzzleSize, float scaleFactor){
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * puzzleSize, marginY + y * puzzleSize);

        // Scale it to the right size
        canvas.scale(scaleFactor/1.5f, scaleFactor/1.5f);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(piece.getWidth() / 2f, -piece.getHeight() / 2f);

        // Draw the bitmap

        float squareSize = piece.getWidth();
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(3);

        canvas.drawRect(x -squareSize, y, x, y +squareSize, linePaint);
        canvas.restore();

    }


    /**
     * Hit test for the checker pieces when the users touch the screen
     * @param testX
     * @param testY
     */
    public boolean hit(float testX, float testY,
                       int puzzleSize, float scaleFactor) {

        float width = piece.getPiece().getWidth();
        float height = piece.getPiece().getHeight();
        // Make relative to the location and size to the piece size
        int pX = (int)((testX - x) * puzzleSize / scaleFactor) +
                piece.getPiece().getWidth() / 2;
        int pY = (int)((testY - y) * puzzleSize / scaleFactor) +
                piece.getPiece().getHeight() / 2;

        if(pX < 0 || pX >= piece.getPiece().getWidth() ||
                pY < 0 || pY >= piece.getPiece().getHeight()) {
            return false;
        }
        piece.setCords(x,y);
        return true;
    }

    public float getX() {return x;}
    public float getY() {return y;}
    public CheckerPiece getJumpedPiece()
    {
        return JumpedPiece;
    }
}
