package edu.msu.cassett8.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static String playerOne, playerTwo;

    EditText player1;
    EditText player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onStartCheckers(View view) {
        player1 = (EditText) findViewById(R.id.player1);
        player2 = (EditText) findViewById(R.id.player2);
        playerOne = player1.getText().toString();
        playerTwo = player2.getText().toString();
        //Check if the names are empty or not
        if(playerOne.equals("") && playerTwo.equals("")){
            new AlertDialog.Builder(view.getContext())
                    .setTitle(R.string.nothing)
                    .setMessage(R.string.emptytext)
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
                    .show();
        }
        else{
            Bundle bundle = new Bundle();
            bundle.putString("playerone", playerOne);
            bundle.putString("playertwo", playerTwo);
            ///For checker activity class
            Intent intent = new Intent(this, CheckerActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    /**
     * Get's the player1's name and passes it player class
     * @return playerOne
     */
    public static String getPlayerOne() {
        return playerOne;
    }

    /**
     * Get's the player2's name and passes it player class
     * @return playerTwo
     */
    public static String getPlayerTwo() {
        return playerTwo;
    }

}