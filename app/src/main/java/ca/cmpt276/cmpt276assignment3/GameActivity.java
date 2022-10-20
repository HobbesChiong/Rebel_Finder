package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ca.cmpt276.cmpt276assignment3.model.Game;

public class GameActivity extends AppCompatActivity {

    // TODO use options to change these values including mines
    private int NUM_ROWS;
    private int NUM_COLS;
    private int MINES;
    private int minesFound = 0;
    private int usedScans = 0;
    Game currGame;

    Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // initialize board size
        int[] boardSize = OptionsScreen.getBoardSize(this);
        NUM_ROWS = boardSize[0];
        NUM_COLS = boardSize[1];
        buttons = new Button[NUM_ROWS][NUM_COLS];
        MINES = OptionsScreen.getMines(this);

        // set times played text view
        TextView timesPlayed = findViewById(R.id.tvTimesPlayed);
        String res ="Times Played: " + OptionsScreen.getTimesPlayed(this);
        timesPlayed.setText(res);


        currGame = new Game(NUM_ROWS,NUM_COLS,MINES); // temp values we'll fix it later
        instantiateTextViews();
        populateButtons();

    }

    private void instantiateTextViews() {
        TextView scansUsed = findViewById(R.id.tvScans);
        scansUsed.setText(R.string.scansUsed);

        TextView foundMines = findViewById(R.id.tvFoundMines);
        String res ="Found 0 out of " + MINES + " Mines";
        foundMines.setText(res);
    }


    // base code comes from this demo video https://www.youtube.com/watch?v=4MFzuP1F-xQ
    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForButtons);
        for (int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for(int col = 0; col < NUM_COLS; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                // this is just for knowing the cords
                // button.setText("" + row + ", " + col);

                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(V -> gridButtonClicked(FINAL_ROW, FINAL_COL));
                tableRow.addView(button);
                buttons[row][col] = button;

            }
        }
    }


    @SuppressLint("SetTextI18n")
    private void gridButtonClicked(int row, int col) {

        Button button = buttons[row][col];

        int[] currButtonLocation = new int[2];
        currButtonLocation[0] = row;
        currButtonLocation[1] = col;

        // lock Button Sizes:
        lockButtonSizes();

        if(currGame.isInMineLocations(currButtonLocation)){ // mine at location
            // plays sound on click
            MediaPlayer mediaPlayerRebel = MediaPlayer.create(this, R.raw.find_rebel_noise);
            mediaPlayerRebel.start();

            // display mine img on button
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.space_robot);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));
            currGame.updateGame(currButtonLocation);

            // loop on rows and columns if u can get tag -1 to it and set text to it
            // button.setTag();

            // loop over its columns
            Button currButton;

            for(int i = 0; i < NUM_COLS; i++){
                currButton = buttons[row][i];
                if(currButton.getTag() != null){
                   currButton.setTag((int) currButton.getTag()-1);
                   int currTag = (int) currButton.getTag();
//                    currTag = currTag - 1;
                    currButton.setText(Integer.toString(currTag));
                }
            }

            // loop over its columns (needs to be amt of row in the grid)
            for(int j = 0; j < NUM_ROWS; j++){
                currButton = buttons[j][col];
                if(currButton.getTag() != null){
                    currButton.setTag((int) currButton.getTag()-1);
                    int currTag = (int) currButton.getTag();
//                    currTag = currTag - 1;
                    currButton.setText(Integer.toString(currTag));

                }
            }

            minesFound++;
            TextView foundMines = findViewById(R.id.tvFoundMines);
            String res = "Found " + minesFound +" out of " + MINES + " Mines";
            foundMines.setText(res);
            if(minesFound == MINES){
                // alert dialog with the win and dismissing sets back to main menu
                FragmentManager manager = getSupportFragmentManager();
                MessageFragment dialog = new MessageFragment();
                dialog.show(manager, "MessageDialog");

                // increase times played
                OptionsScreen.setTimesPlayed(this, OptionsScreen.getTimesPlayed(this)+1);
            }
        }

        else{ // no mine at location
            // plays sound on click
            MediaPlayer mediaPlayerNoRebel = MediaPlayer.create(this, R.raw.scan_sound);
            mediaPlayerNoRebel.start();

            Button currButton = buttons[row][col];

            if(currButton.getTag() == null){ // not scanned yet
                int minesInArea = currGame.scan(row, col);
                button.setText(Integer.toString(minesInArea));
                button.setTag(minesInArea);

                usedScans++;
                TextView scansUsed = findViewById(R.id.tvScans);
                String res = "Scans used: " + usedScans;
                scansUsed.setText(res);
            }

        }

        // change text on button
    }

    private void lockButtonSizes(){
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);

            }
        }
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, GameActivity.class);
    }


}