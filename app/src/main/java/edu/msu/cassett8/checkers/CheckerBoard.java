package edu.msu.cassett8.checkers;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import androidx.fragment.app.DialogFragment;
import java.util.Random;

public class CheckerBoard {

    boolean initialized = false;
    final static float SCALE_IN_VIEW = .95f;

    private int puzzleSize;
    private int marginX;
    private int marginY;
    private float scaleFactor;
    private View mCheckersView;
    private Player playerOne;
    private Player playerTwo;
    private boolean isEnd= false;
    private int winner =0;
    private boolean popupShown = false;
    private int turn = 1;

    private ArrayList<WhiteChecker> whitePieces = new ArrayList<WhiteChecker>();
    private ArrayList<GreenChecker> greenPieces = new ArrayList<GreenChecker>();
    /**
     * Available moves for the current piece
     */
    private ArrayList<AvailableMove> availableMoves = new ArrayList<AvailableMove>();

    private final String White_Location="WhiteChecker.locations";
    private final String Green_Location="GreenChecker.locations";
    private final String Green_IDs="GreenChecker.ids";
    private final String White_IDs="WhiteChecker.ids";
    /**
     * Save the puzzle to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {

        float [] wlocations = new float[whitePieces.size() * 2];
        int [] wids = new int[whitePieces.size()];
        float [] glocations = new float[greenPieces.size() * 2];
        int [] gids = new int[greenPieces.size()];

        for(int i=0;  i<whitePieces.size(); i++) {
            CheckerPiece piece = whitePieces.get(i);
            wlocations[i*2] = piece.getX();
            wlocations[i*2+1] = piece.getY();
            wids[i] = piece.getID();
        }
        for(int i=0;  i<greenPieces.size(); i++) {
            CheckerPiece piece = greenPieces.get(i);
            glocations[i*2] = piece.getX();
            glocations[i*2+1] = piece.getY();
            gids[i] = piece.getID();
        }

        bundle.putFloatArray(White_Location, wlocations);
        bundle.putIntArray(White_IDs,  wids);
        bundle.putFloatArray(Green_Location, glocations);
        bundle.putIntArray(Green_IDs,  gids);
        bundle.putInt("turn", turn);
        bundle.putInt("winner", winner);
        bundle.putBoolean("gameState", isEnd);


    }
    public void loadInstanceState(Bundle bundle, Context context) {

        if(bundle!=null) {
            turn = bundle.getInt("turn");
            winner = bundle.getInt("winner");
            isEnd = bundle.getBoolean("gameState");
            float[] wlocations = bundle.getFloatArray(White_Location);
            int[] wids = bundle.getIntArray(White_IDs);
            float[] glocations = bundle.getFloatArray(Green_Location);
            int[] gids = bundle.getIntArray(Green_IDs);

            ArrayList<WhiteChecker> whitePieces = new ArrayList<WhiteChecker>();
            ArrayList<GreenChecker> greenPieces = new ArrayList<GreenChecker>();

            if (gids != null) {
                for (int i = 0; i < gids.length; i++) {

                    GreenChecker piece = new GreenChecker(context, R.drawable.spartan_green, gids[i]);
                    piece.setCords(glocations[i * 2], glocations[i * 2 + 1]);
                    greenPieces.add(piece);
                }
                this.greenPieces = greenPieces;
            }
            if (wids != null) {
                for (int i = 0; i < wids.length; i++) {

                    WhiteChecker piece = new WhiteChecker(context, R.drawable.spartan_white, gids[i]);
                    piece.setCords(wlocations[i * 2], wlocations[i * 2 + 1]);
                    whitePieces.add(piece);
                }
                this.whitePieces = whitePieces;
            }
            initialized=true;

        }

    }



    public ArrayList<GreenChecker> getGreenPieces() {
        return greenPieces;
    }

    /**
     * Construct Player 1 with name and assign color
     */



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
                    i));
        }

        //spawn green checkers
        for (int i=0; i<12; i++) {
            greenPieces.add(new GreenChecker(context,
                    R.drawable.spartan_green,
                    i));

        }


        playerOne = new Player(MainActivity.getPlayerOne());

        /**
         * Construct Player 2 with name and assign color
         */
        playerTwo = new Player(MainActivity.getPlayerTwo());
    }

    public void draw(Canvas canvas) {

        if(isEnd = true)
        {
            return;
        }
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

        if (isEnd)
        {
            return false;
        }

        float relX = (event.getX() - marginX) / puzzleSize;
        float relY = (event.getY() - marginY) / puzzleSize;
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                boolean result = onTouched(relX, relY);
                if(isEnd)
                {
                    if(!popupShown)
                    {
                        // The puzzle is done
                        // Instantiate a dialog box builder
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(view.getContext());

                        // Parameterize the builder

                        builder.setTitle(R.string.endGame);
                        int id = 0;
                        if(winner==1){id=R.string.player1Win;}else {id=R.string.player2Win;}
                        builder.setMessage(id);
                        builder.setPositiveButton(android.R.string.ok, null);

                        // Create the dialog box and show it
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        popupShown=true;
                    }
                }
                return result;
        }
        return false;
    }
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        if(turn == 2) {

            for (int p = whitePieces.size() - 1; p >= 0; p--) {
                if (whitePieces.get(p).hit(x, y, puzzleSize, scaleFactor)) {
                    // We hit a piece!
                    findAvailableMoves(whitePieces.get(p), 0);
                    //whitePieces.remove(p);
                    return true;
                }
            }
        }

        if (turn == 1) {


            for (int p = greenPieces.size() - 1; p >= 0; p--) {
                if (greenPieces.get(p).hit(x, y, puzzleSize, scaleFactor)) {
                    // We hit a piece!
                    findAvailableMoves(greenPieces.get(p), 1);
                    return true;
                }
            }
        }
        for(int p=availableMoves.size()-1; p>=0;  p--) {
            if(availableMoves.get(p).hit(x, y, puzzleSize, scaleFactor)) {
                // We hit a piece!
                AvailableMove i = availableMoves.get(p);
                CheckerPiece jumped = i.getJumpedPiece();

                if(jumped !=null)
                {
                    if(turn == 1)
                    {
                        for (int j = whitePieces.size(); j>=0; j--)
                        {
                            if (whitePieces.get(j).equals(jumped))
                            {
                                whitePieces.remove(p);
                                if (whitePieces.isEmpty())
                                {
                                    isEnd = true;
                                    winner=1;
                                }
                                break;
                            }
                        }
                    }
                    else if (turn == 2)
                    {
                        for (int j = greenPieces.size(); j>=0; j--)
                        {
                            if (greenPieces.get(j).equals(jumped))
                            {
                                greenPieces.remove(p);
                                if (greenPieces.isEmpty())
                                {
                                    isEnd=true;
                                    winner=2;
                                }
                                break;
                            }
                        }

                    }
                }
                for( p=availableMoves.size()-1; p>=0; p--)
                {
                    availableMoves.remove(p);
                }

                if (turn == 1) { turn = 2;} else { turn = 1;}

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
