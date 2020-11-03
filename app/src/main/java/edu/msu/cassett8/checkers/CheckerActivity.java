package edu.msu.cassett8.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckerActivity extends AppCompatActivity {

    String p1 = null;
    String p2 = null;
    int winner = 1;
    private CheckersView getCheckerView() {
        return this.findViewById(R.id.checkersView);
    }
    TextView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.checkers_view);

        Bundle xtras = getIntent().getExtras();
        if (xtras != null) {
            p1 = xtras.getString("playerone");
            p2 = xtras.getString("playertwo");
        }
        if(savedInstanceState == null) {
            savedInstanceState = new Bundle();
            onStartGame(getCheckerView());
        }
        getCheckerView().loadInstanceState(savedInstanceState);
        getCheckerView().setP1(p1);
        getCheckerView().setP2(p2);
        boolean checkStart = savedInstanceState.getBoolean("started");
        TextView textView = (TextView) findViewById(R.id.turnText);
        String playersTurn = null;
        if(getCheckerView().getTurn() == 1)
        {
            playersTurn = p1;
        }
        else {
            playersTurn = p2;
        }
        String msg = getString(R.string.turn) + " " + playersTurn + " " + getString(R.string.turnC);
        textView.setText(msg);



    }
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        getCheckerView().saveInstanceState(bundle);
    }

    public void onStartGame(View view){
        String msg = getString(R.string.controlsDesc) + " " + p1 + " " + getString(R.string.greenPlayer) + " " + p2 + " " + getString(R.string.whitePlayer) + " " + p1 + " " + getString(R.string.first);
        TextView textView = (TextView) findViewById(R.id.turnText);
        String playersTurn = null;
        String msg2 = getString(R.string.turn) + " " + p1 + " " + getString(R.string.turnC);
        textView.setText(msg2);
        new AlertDialog.Builder(view.getContext())
                .setTitle(R.string.instructions)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
    }


    public void Done(View view){
        if(getCheckerView().getisEnd())
        {
            Intent intent = new Intent(this, EndActivity.class);
            Bundle ourBundle = new Bundle();
            ourBundle.putString("playerone", p1);
            ourBundle.putString("playertwo", p2);
            ourBundle.putInt("winner", getCheckerView().getWinner());
            intent.putExtras(ourBundle);
            startActivity(intent);
        }
        else {
            getCheckerView().changeTurn();
            TextView textView = (TextView) findViewById(R.id.turnText);
            String playersTurn = null;
            if(getCheckerView().getTurn() == 1)
            {
                playersTurn = p1;
            }
            else {
                playersTurn = p2;
            }
            String msg = getString(R.string.turn) + " " + playersTurn + " " + getString(R.string.turnC);
            textView.setText(msg);
        }
    }

    public void Resign(View view)
    {
        Intent intent = new Intent(this, EndActivity.class);
        Bundle ourBundle = new Bundle();
        ourBundle.putString("playerone", p1);
        ourBundle.putString("playertwo", p2);
        int winner = (getCheckerView().getTurn() == 1) ? 2 : 1;
        ourBundle.putInt("winner", winner);
        intent.putExtras(ourBundle);
        startActivity(intent);
    }

}