package edu.msu.cassett8.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndActivity extends AppCompatActivity {

    TextView playerWinner;
    TextView playerLoser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        playerWinner = (TextView)findViewById(R.id.playerWinner);
        playerLoser = (TextView)findViewById(R.id.playerLoser);

        Bundle bundle = getIntent().getExtras();
        String pOne = bundle.getString("playerone");
        String pTwo = bundle.getString("playertwo");

        playerWinner.setText(pOne + " is the winner!");
        playerLoser.setText(pTwo + " is the loser!");

    }

    public void onRestartGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}