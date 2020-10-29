package edu.msu.cassett8.checkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        if(savedInstanceState == null) {
            savedInstanceState = new Bundle();
        }
        Bundle xtras = getIntent().getExtras();
        if (xtras != null) {
            p1 = xtras.getString("playerone");
            p2 = xtras.getString("playertwo");
        }
        getCheckerView().loadInstanceState(savedInstanceState);
    }
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        getCheckerView().saveInstanceState(bundle);
    }

    public void onEndGame(View view) {
        Intent intent = new Intent(this, EndActivity.class);
        Bundle ourBundle = new Bundle();
        ourBundle.putString("playerone", p1);
        ourBundle.putString("playertwo", p2);
        ourBundle.putInt("winner", getCheckerView().getWinner());
        intent.putExtras(ourBundle);
        startActivity(intent);
    }

}