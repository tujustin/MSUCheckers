package edu.msu.cassett8.checkers;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Player {

    /**
     * Name of the current player1
     */
    private String playerName;

    /**
     * Color of player
     */
    private String playerColor;

    /**
     * Pointer to owned pieces
     */
    private ArrayList<CheckerPiece> playerPieces;

    /**
     * Player Constructor
     * @param name of player
     * @param color of player
     * @param pieces of player
     */
    public Player(String name, String color, ArrayList<CheckerPiece> pieces){
        playerName = name;
        playerColor = color;
        playerPieces = pieces;
    }

}

