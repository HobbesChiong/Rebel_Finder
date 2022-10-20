package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        createMinesRadioButtons();
        createBoardSizeRadioButtons();
        createResetButton();

    }

    private void createResetButton() {
        Button btn = findViewById(R.id.btnResetStats);

        btn.setOnClickListener(view -> setTimesPlayed(getApplicationContext(), 0));


    }

    private void createBoardSizeRadioButtons() {
        RadioGroup group = findViewById(R.id.radio_group_boardSize);

        int[] num_rows = getResources().getIntArray(R.array.number_of_rows);
        int[] num_cols = getResources().getIntArray(R.array.number_of_columns);

        for(int i = 0; i < 3; i++){
            int numRow = num_rows[i];
            int numCol = num_cols[i];

            RadioButton btn = new RadioButton(this);
            btn.setTextColor(Color.WHITE);
            String res = numRow + " rows X " + numCol + " columns";
            btn.setText(res);

            btn.setOnClickListener(v -> saveBoardSize(numRow,numCol));

            // add to radio group
            group.addView(btn);

            int[] savedBoardSize = getBoardSize(this);
            if(numRow == savedBoardSize[0] && numCol == savedBoardSize[1]){
                btn.setChecked(true);
            }
        }
    }

    private void saveBoardSize(int rows, int cols) {
        SharedPreferences prefs = this.getSharedPreferences("OptionsPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Num rows", rows);
        editor.putInt("Num cols", cols);
        editor.apply();

    }

    static public int[] getBoardSize(Context c){
        int[] res = new int[2];

        SharedPreferences prefs = c.getSharedPreferences("OptionsPrefs", MODE_PRIVATE);

        int defaultRows = c.getResources().getInteger(R.integer.default_num_rows);
        int defaultCols = c.getResources().getInteger(R.integer.default_num_columns);

        res[0] = prefs.getInt("Num rows",defaultRows);
        res[1] = prefs.getInt("Num cols",defaultCols);
        return res;
    }

    // code based of https://www.youtube.com/watch?v=_yaP4etGKlU
    private void createMinesRadioButtons() {
        RadioGroup group = findViewById(R.id.radio_group_mines);

        int[] num_mines = getResources().getIntArray(R.array.number_of_mines);

        for (int numMine : num_mines) {
            RadioButton btn = new RadioButton(this);
            btn.setTextColor(Color.WHITE);
            String res = numMine + " mines";
            btn.setText(res);

            // set on click callbacks
            btn.setOnClickListener(v -> saveMines(numMine));

            // add to radio group
            group.addView(btn);

            // select default button:
            if (numMine == getMines(this)) {
                btn.setChecked(true);
            }
        }

    }

    private void saveMines(int numMine) {
        SharedPreferences prefs = this.getSharedPreferences("OptionsPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Num mines",numMine);
        editor.apply();

    }
    static public int getMines(Context c){
        SharedPreferences prefs = c.getSharedPreferences("OptionsPrefs", MODE_PRIVATE);

        int defaultMines = c.getResources().getInteger(R.integer.default_num_mines);

        return prefs.getInt("Num mines", defaultMines);
    }

    static public void setTimesPlayed(Context c, int timesPlayed){
        SharedPreferences prefs = c.getSharedPreferences("OptionsPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("times played",timesPlayed);
        editor.apply();
    }

    static public int getTimesPlayed(Context c){
        SharedPreferences prefs = c.getSharedPreferences("OptionsPrefs", MODE_PRIVATE);

        return prefs.getInt("times played", 0);
    }


    public static Intent makeIntent(Context context){
        return new Intent(context, OptionsScreen.class);
    }


}