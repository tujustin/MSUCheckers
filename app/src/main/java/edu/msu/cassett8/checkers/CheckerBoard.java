package edu.msu.cassett8.checkers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class CheckerBoard {

    boolean initialized = false;
    final static float SCALE_IN_VIEW = .95f;

    private int puzzleSize;
    private int marginX;
    private int marginY;
    private float scaleFactor;
    private View mCheckersView;


    public ArrayList<CheckerPiece> whitePieces = new ArrayList<CheckerPiece>();
    public ArrayList<CheckerPiece> greenPieces = new ArrayList<CheckerPiece>();
    /**
     * Available moves for the current piece
     */
    public ArrayList<AvailableMove> availableMoves = new ArrayList<AvailableMove>();

    /**
     * Construct Player 1 with name and assign color
     */
    Player playerOne = new Player(MainActivity.getPlayerOne(), "Green", greenPieces);

    /**
     * Construct Player 2 with name and assign color
     */
    Player playerTwo = new Player(MainActivity.getPlayerTwo(), "White", whitePieces);

    public float leftEdge = 0.07f;

    public float rightEdge = 0.9f;

    public float diff = 0.08f;





    /**
     * Completed puzzle bitmap
     */
    private Bitmap boardImage;

    private Paint outlinePaint;

    public CheckerBoard(Context context, View theView){
        mCheckersView = theView;
        boardImage = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.checkersboard);

        //spawn white checkers
        for (int i=0; i<12; i++) {
            whitePieces.add(new WhiteChecker(context,
                    R.drawable.spartan_white,
                    0));
        }

        //spawn green checkers
        for (int i=0; i<12; i++) {
            greenPieces.add(new GreenChecker(context,
                    R.drawable.spartan_green,
                    0));

        }

    }

    public void draw(Canvas canvas) {

        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        if(!initialized)
        {
            setInitialPos(wid, hit);
            initialized=true;
        }

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        puzzleSize = (int)(minDim * SCALE_IN_VIEW);


        // Compute the margins so we center the puzzle
        marginX = (wid - puzzleSize) / 2;
        marginY = (hit - puzzleSize) / 2;

        //
        // Draw the outline of the puzzle
        //

        scaleFactor = (float)puzzleSize / (float)boardImage.getWidth();

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xFF504B);
        outlinePaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(marginX, marginY,
                marginX + puzzleSize, marginY + puzzleSize, outlinePaint);
        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawBitmap(boardImage, 0, 0, null);
        canvas.restore();

        

        //draw all green checker pieces
        for(CheckerPiece piece : greenPieces) {
            piece.draw( canvas, marginX, marginY, puzzleSize, scaleFactor);
        }

        //draw all white checker pieces
        for(CheckerPiece piece : whitePieces) {
            piece.draw( canvas, marginX, marginY, puzzleSize, scaleFactor);
        }

        /**
         * TODO: Add loop to draw all available moves at end of draw
         */
        for (AvailableMove space : availableMoves){
            space.draw(canvas, marginX, marginY, puzzleSize, scaleFactor);
        }


    }

    public boolean onTouchEvent(View view, MotionEvent event) {


        float relX = (event.getX() - marginX) / puzzleSize;
        float relY = (event.getY() - marginY) / puzzleSize;
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY);
        }
        return false;
    }
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for(int p=whitePieces.size()-1; p>=0;  p--) {
            if(whitePieces.get(p).hit(x, y, puzzleSize, scaleFactor)) {
                // We hit a piece!
                findAvailableMoves(whitePieces.get(p), 0);
                //whitePieces.remove(p);
                return true;
            }
        }

        for(int p=greenPieces.size()-1; p>=0;  p--) {
            if(greenPieces.get(p).hit(x, y, puzzleSize, scaleFactor)) {
                // We hit a piece!
                findAvailableMoves(greenPieces.get(p), 1);
                return true;
            }
        }
        for(int p=availableMoves.size()-1; p>=0;  p--) {
            if(availableMoves.get(p).hit(x, y, puzzleSize, scaleFactor)) {
                // We hit a piece!
                for( p=availableMoves.size()-1; p>=0; p--)
                {
                    availableMoves.remove(p);
                }
                mCheckersView.invalidate();
                return true;

            }
        }

        return false;
    }

    public boolean isLeftEdge(float x) {
        if(Math.abs(leftEdge - x ) < diff)
        {
            return true;
        }

        return false;
    }
    public boolean isRightEdge(float x) {
        if(Math.abs(rightEdge - x) < diff)
        {
            return true;
        }

        return false;
    }

    private boolean isJumpable(float x, float y) {
        for(int p=whitePieces.size()-1; p>=0;  p--) {
            if(whitePieces.get(p).hit(x, y, puzzleSize, scaleFactor)) {
                // We hit a piece!
                return true;
            }
        }

        for(int p=greenPieces.size()-1; p>=0;  p--) {
            if(greenPieces.get(p).hit(x, y, puzzleSize, scaleFactor)) {
                // We hit a piece!
                return true;
            }
        }
        return false;
    }

    public void findAvailableMoves(CheckerPiece piece , int type) {
        availableMoves.clear();
        if (type == 0) {
            if (isJumpable(piece.getX() + .125f, piece.getY() + .125f) && isJumpable(piece.getX() - .125f, piece.getY() + .125f)) {
                if (isJumpable(piece.getX() + .25f, piece.getY() + .25f)) {
                    availableMoves.add(new AvailableMove(piece.getX() - .25f,piece.getY() + .25f,piece));
                }
                else if (isJumpable(piece.getX() - .25f, piece.getY() + .25f)) {
                    availableMoves.add(new AvailableMove(piece.getX() + .25f,piece.getY() + .25f,piece));
                }
                else {
                    availableMoves.add(new AvailableMove(piece.getX() + .25f, piece.getY() + .25f, piece));
                    availableMoves.add(new AvailableMove(piece.getX() - .25f, piece.getY() + .25f, piece));
                }
            }
            else if (isJumpable(piece.getX() + .125f, piece.getY() + .125f)) {
                if (!isJumpable(piece.getX() + .25f, piece.getY() + .25f)) {
                    availableMoves.add(new AvailableMove(piece.getX() + .25f, piece.getY() + .25f, piece));
                }
                availableMoves.add(new AvailableMove(piece.getX() - .125f, piece.getY() + .125f, piece));
            }
            else if (isJumpable(piece.getX() - .125f, piece.getY() + .125f)) {
                if (!isJumpable(piece.getX() - .25f, piece.getY() + .25f)) {
                    availableMoves.add(new AvailableMove(piece.getX() - .25f, piece.getY() + .25f, piece));
                }
                availableMoves.add(new AvailableMove(piece.getX() + .125f, piece.getY() + .125f, piece));
            }

            else {
                if (isLeftEdge(piece.getX())) {

                    availableMoves.add(new AvailableMove(piece.getX() + .125f, piece.getY() + .125f, piece));

                }
                if (isRightEdge(piece.getX())) {

                    availableMoves.add(new AvailableMove(piece.getX() - .125f, piece.getY() + .125f, piece));

                }

                if (!isRightEdge(piece.getX()) && !isLeftEdge(piece.getX())) {
                    availableMoves.add(new AvailableMove(piece.getX() + .125f, piece.getY() + .125f, piece));
                    availableMoves.add(new AvailableMove(piece.getX() - .125f, piece.getY() + .125f, piece));
                }
            }

        }

        if (type == 1) {
            if (isJumpable(piece.getX() + .125f, piece.getY() - .125f) && isJumpable(piece.getX() - .125f, piece.getY() - .125f)) {
                if (isJumpable(piece.getX() + .25f, piece.getY() - .25f)) {
                    availableMoves.add(new AvailableMove(piece.getX() - .25f,piece.getY() - .25f,piece));
                }
                else if (isJumpable(piece.getX() - .25f, piece.getY() - .25f)) {
                    availableMoves.add(new AvailableMove(piece.getX() + .25f,piece.getY() - .25f,piece));
                }
                else {
                    availableMoves.add(new AvailableMove(piece.getX() + .25f, piece.getY() - .25f, piece));
                    availableMoves.add(new AvailableMove(piece.getX() - .25f, piece.getY() - .25f, piece));
                }
            }
            else if (isJumpable(piece.getX() + .125f, piece.getY() - .125f)) {
                if (!isJumpable(piece.getX() + .25f, piece.getY() - .25f)) {
                    availableMoves.add(new AvailableMove(piece.getX() + .25f, piece.getY() - .25f, piece));
                }
                availableMoves.add(new AvailableMove(piece.getX() - .125f, piece.getY() - .125f, piece));
            }
            else if (isJumpable(piece.getX() - .125f, piece.getY() - .125f)) {
                if (!isJumpable(piece.getX() - .25f, piece.getY() - .25f)) {
                    availableMoves.add(new AvailableMove(piece.getX() - .25f, piece.getY() - .25f, piece));
                }
                availableMoves.add(new AvailableMove(piece.getX() + .125f, piece.getY() - .125f, piece));
            }

            else {
                if (isLeftEdge(piece.getX())) {

                    availableMoves.add(new AvailableMove(piece.getX() + .125f, piece.getY() - .125f, piece));

                }
                if (isRightEdge(piece.getX())) {

                    availableMoves.add(new AvailableMove(piece.getX() - .125f, piece.getY() - .125f, piece));

                }

                if (!isRightEdge(piece.getX()) && !isLeftEdge(piece.getX())) {
                    availableMoves.add(new AvailableMove(piece.getX() + .125f, piece.getY() - .125f, piece));
                    availableMoves.add(new AvailableMove(piece.getX() - .125f, piece.getY() - .125f, piece));
                }
            }
        }


        mCheckersView.invalidate();



    }

    public void setInitialPos(int wid, int hit)
    {
        //code for setting initial positions of checkers

        //first 12 are white and first 12 are green. May also be other way around. I dont know. x and y range from 0-.95f.

        //example of setting their locations (randomly)
        Random rand = new Random();//remove
        float whiteShift = 0;
        float greenShift = 0;
        int whiteCount = 0;
        int greenCount = 0;

        for(CheckerPiece piece : greenPieces) {
            if (greenCount <= 3) {
                piece.setCords((0.195f * SCALE_IN_VIEW) + greenShift, (0.987f * SCALE_IN_VIEW));
                greenShift = (greenShift + 0.25f);

            }
            if (greenCount == 4){
                greenShift = 0;
            }
            if (greenCount > 3 && greenCount <= 7){
                piece.setCords((0.065f * SCALE_IN_VIEW) + greenShift, (0.857f * SCALE_IN_VIEW));
                greenShift = (greenShift + 0.25f);

            }
            if (greenCount == 7){
                greenShift = 0;
            }
            if (greenCount > 7){
                piece.setCords((0.195f * SCALE_IN_VIEW) + greenShift, (0.725f * SCALE_IN_VIEW));
                greenShift = (greenShift + 0.25f);

            }
            greenCount++;
        }

        for(CheckerPiece piece : whitePieces) {

            if (whiteCount <= 3) {
                piece.setCords((0.065f * SCALE_IN_VIEW) + whiteShift, (0.065f * SCALE_IN_VIEW));
                whiteShift = (whiteShift + 0.25f);

            }
            if (whiteCount == 4){
                whiteShift = 0;
            }
            if (whiteCount > 3 && whiteCount <= 7){
                piece.setCords((0.195f * SCALE_IN_VIEW) + whiteShift, (0.195f * SCALE_IN_VIEW));
                whiteShift = (whiteShift + 0.25f);

            }
            if (whiteCount == 7){
                whiteShift = 0;
            }
            if (whiteCount > 7){
                piece.setCords((0.065f * SCALE_IN_VIEW) + whiteShift, (0.33f * SCALE_IN_VIEW));
                whiteShift = (whiteShift + 0.25f);

            }
            whiteCount++;

        }
    }
}
