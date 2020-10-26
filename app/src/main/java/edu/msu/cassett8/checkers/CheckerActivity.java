package edu.msu.cassett8.checkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckerActivity extends AppCompatActivity {

    private Bundle bundle;

    private CheckersView getCheckerView() {
        return this.findViewById(R.id.checkersView);
    }
    TextView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkers_view);


        getCheckerView().loadInstanceState(savedInstanceState);
    }
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        getCheckerView().saveInstanceState(bundle);
    }

    public void onEndGame(View view) {
        Intent intent = new Intent(this, EndActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}