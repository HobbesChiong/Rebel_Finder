package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Welcome Screen activity, first activity that opens when user opens app
public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMainMenuButton();
    }

    private void setupMainMenuButton() {
        Button btn = findViewById(R.id.MainMenubtn);
        btn.setOnClickListener(v -> {
            Intent intent = MainMenu.makeIntent(WelcomeScreen.this);
            startActivity(intent);
        });

    }
}