package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setupPlayGameButton();
        setupOptionsButton();
        setupHelpButton();
    }

    private void setupHelpButton() {
        Button btn = findViewById(R.id.btnHelp);
        btn.setOnClickListener(v -> {
            Intent intent = HelpScreen.makeIntent(MainMenu.this);
            startActivity(intent);
        });
    }

    private void setupOptionsButton() {
        Button btn = findViewById(R.id.btnOptions);
        btn.setOnClickListener(v -> {
            Intent intent = OptionsScreen.makeIntent(MainMenu.this);
            startActivity(intent);
        });
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, MainMenu.class);
    }


    private void setupPlayGameButton() {
        Button btn = findViewById(R.id.btnPlayGame);
        btn.setOnClickListener(v -> {
            Intent intent = GameActivity.makeIntent(MainMenu.this);
            startActivity(intent);
        });

    }
}