package edu.msu.cassett8.checkers;

import android.graphics.Canvas;

import java.util.ArrayList;

public class AvailableMove {

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
    private CheckerPiece movePieces;

    /**
     * Available moves for that current piece
     * @param x available move
     * @param y available move
     * @param piece of current move
     */
    public AvailableMove(float x, float y, CheckerPiece piece){
        xCord = x;
        yCord = y;
        movePieces = piece;

    }

    /**
     * Draw function for the player class
     * @param canvas of this
     */
    public void draw(Canvas canvas, float x, float y){

    }


    /**
     * Hit test for current of piece
     * @param x of piece
     * @param y of piece
     */
    public void hitTest(float x, float y){

    }
}
