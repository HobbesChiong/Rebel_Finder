package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import ca.cmpt276.cmpt276assignment3.model.Game;

public class GameActivity extends AppCompatActivity {

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;
    Game currGame;

    Button[][] buttons = new Button[NUM_ROWS][NUM_COLS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        currGame = new Game(5,6,2); // temp values we'll fix it later
        populateButtons();
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
                button.setText("" + col + ", " + row);

                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View V){
                        gridButtonClicked(FINAL_ROW, FINAL_COL);

                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;

            }
        }
    }


    private void gridButtonClicked(int row, int col) {

        Toast.makeText(this, "Button clicked: " + col + ", " + row, Toast.LENGTH_SHORT).show();
        Button button = buttons[row][col];

        int[] currButtonLocation = new int[2];
        currButtonLocation[0] = row;
        currButtonLocation[1] = col;

        // lock Button Sizes:
        lockButtonSizes();

        // scale image to button (do this if its a mine)
        // if(button cords in mine locations) update the game to remove it and also decrease revealed buttons counts, then display image
        // option 1 loop through rows and columns check if button has tag (null if not revealed yet)  then set text to -1 and set that object to -1 (probably should use object Integer
        // else scan and find display number of mines
        //

        if(currGame.isInMineLocations(currButtonLocation)){
            // display mine img on button
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.space_robot);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

        }
        else{ // no mine at location
            int temp = currGame.scan(row, col);
            button.setText(Integer.toString(temp));
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