package edu.msu.cassett8.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class EndActivity extends AppCompatActivity {

    TextView playerWinner;
    TextView playerLoser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        playerWinner = (TextView)findViewById(R.id.playerWinner);
        playerLoser = (TextView)findViewById(R.id.playerLoser);

        String pOne = "Player One";
        String pTwo = "Player Two";
        Bundle bundle = getIntent().getExtras();
        int winner = 2;
        if(bundle!=null) {
            pOne = bundle.getString("playerone");
            pTwo = bundle.getString("playertwo");
            winner = bundle.getInt("winner");
        }

        String isWinner = getString(R.string.isWinner);
        String isLoser = getString(R.string.isLoser);
        String winmsg;
        String losemsg;
        if(winner == 2)
        {
            winmsg = pTwo + " " +isWinner;
            losemsg = pOne + " " + isLoser;
        }
        else
        {
            winmsg = pOne + " " +isWinner;
            losemsg = pTwo + " " + isLoser;
        }
        playerWinner.setText(winmsg);
        playerLoser.setText(losemsg);

    }

    public void onRestartGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}