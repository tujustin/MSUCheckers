package edu.msu.cassett8.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckerActivity extends AppCompatActivity {

    private Bundle bundle;

    TextView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkers_view);

        playerView = (TextView)findViewById(R.id.playerView);

        bundle = getIntent().getExtras();
        Bundle bundle = getIntent().getExtras();
        String pOne = bundle.getString("playerone");
        String pTwo = bundle.getString("playertwo");

    }

    public void onEndGame(View view) {
        Intent intent = new Intent(this, EndActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}