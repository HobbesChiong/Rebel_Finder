package ca.cmpt276.cmpt276assignment3.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;



public class Game {

    int[][] grid; // rows x columns
    int mines;
    int rows;
    int columns;
    ArrayList<int[]> mineLocations = new ArrayList<>();

    public Game(int rows, int columns, int mines){
        grid = new int[rows][columns];

        this.mines = mines;
        this.rows = rows;
        this.columns = columns;
        placeMines();
    }

    private void placeMines(){
        Random r = new Random(); // referring to same obj? need to get a random number and detach from obj
        int c = 1; // count of added mines
        int currRow;
        int currColumn;

        while(c <= mines){

            int[] currMineLocation = new int[2];

            currRow = r.nextInt(rows);
            currColumn = r.nextInt(columns);

            currMineLocation[0] = currRow;
            currMineLocation[1] = currColumn;


            if(!isInMineLocations(currMineLocation)){ // if its not already in the grid add it and increase count
                mineLocations.add(currMineLocation);
                c++;
            }

        }
    }

    public void updateGame(int[] currLocation){

        for (int i = 0 ; i < mineLocations.size(); i++) {
            if (Arrays.equals(mineLocations.get(i), currLocation)) {
                mineLocations.remove(i);
                break;
            }
        }

    }
    public int scan(int row, int column){
        int res = 0;
        int[] currLocation = new int[2];
        // loop over rows and ints
        // loop over row its rows
        for(int i = 0; i < columns; i++){
            currLocation[0] = row;
            currLocation[1] = i;
            if(isInMineLocations(currLocation)){
                res++;
            }
        }
        // loop over its columns
        for(int j = 0; j < rows; j++){
            currLocation[0] = j;
            currLocation[1] = column;
            if(isInMineLocations(currLocation)){
                res++;
            }
        }
        return res;
    }

    public boolean isInMineLocations(int[] currMineLocation){
        if(mineLocations.isEmpty()){
            return false;
        }

        // infinite loop but why?
        for(int[] mine : mineLocations){
            // check if the mine location is already in the grid
            if (mine[0] == currMineLocation[0] && mine[1] == currMineLocation[1]) {
                return true;
            }
        }
        return false;
    }


}
