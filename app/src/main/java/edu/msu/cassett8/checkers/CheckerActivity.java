package edu.msu.cassett8.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CheckerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkers_view);
    }

    public void onEndGame(View view) {
        Intent intent = new Intent(this, EndActivity.class);
        startActivity(intent);
    }

}