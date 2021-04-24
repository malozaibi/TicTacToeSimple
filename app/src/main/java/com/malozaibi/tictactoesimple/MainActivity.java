package com.malozaibi.tictactoesimple;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Resources res; // resource object
    // all buttons
    ImageButton btn1;
    ImageButton btn2;
    ImageButton btn3;
    ImageButton btn4;
    ImageButton btn5;
    ImageButton btn6;
    ImageButton btn7;
    ImageButton btn8;
    ImageButton btn9;

    // check how many boxes are full - for Tie
    int numberOfClicks = 0;


    // determine the current player
    boolean playingNow = true; // true is O and false is X

    // the main Matrix where the data is stored
    String[][] ticTac = {{"","",""},{"","",""},{"","",""}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // android things
        setContentView(R.layout.activity_main);

        // get the resources - the images
        res = getResources();

        // linking the button the the buttons in the UI
        btn1 = (ImageButton) findViewById(R.id.i1);
        btn2 = (ImageButton) findViewById(R.id.i2);
        btn3 = (ImageButton) findViewById(R.id.i3);
        btn4 = (ImageButton) findViewById(R.id.i4);
        btn5 = (ImageButton) findViewById(R.id.i5);
        btn6 = (ImageButton) findViewById(R.id.i6);
        btn7 = (ImageButton) findViewById(R.id.i7);
        btn8 = (ImageButton) findViewById(R.id.i8);
        btn9 = (ImageButton) findViewById(R.id.i9);


    }


    public void buttonClicked(View v) {

        // get the button which is clicked
        ImageButton btn = (ImageButton) v;

        // return id of the button 0-8
        int btnId = Integer.parseInt(btn.getTag().toString());

        // get the row and col from the id
        int row = btnId / 3; // 0, 1 or 2
        int col = btnId % 3; // 0, 1 or 2

        // get the state
        String playerSymbol = playingNow ? "O" : "X";

        // quit this function if it is already clicked
        if (!ticTac[row][col].equals(""))
            return;

        // if clicked for the first time continue and,
        // put the symbol in the multiDimensional array
        ticTac[row][col] = playerSymbol;

        // set the background to the
        setBg(btn, playerSymbol);

        // check the horizontal and vertical possible wins of the current spot only
        boolean horizontalWin = true;
        boolean verticalWin = true;
        for (int i = 0; i < 3 ;i++) {
            if (!ticTac[i][col].equals(playerSymbol)) {
                horizontalWin = false;
            }
            if (!ticTac[row][i].equals(playerSymbol)) {
                verticalWin = false;
            }
        }

        // check if winner
        boolean winner = horizontalWin || verticalWin;

        // if not winner check diagonal (/) (\) possibility
        if (!winner) {
            // if it is in the diagonal (/) area check it
            if (row == col) {
                if (ticTac[0][0].equals(ticTac[1][1]) && ticTac[0][0].equals(ticTac[2][2]))
                    winner = true;
            }
            // if it is in the diagonal (\) area check it
            if (row + col == 2) {
                if (ticTac[0][2].equals(ticTac[1][1]) && ticTac[2][0].equals(ticTac[1][1]))
                    winner = true;
            }
        }

        // creating alert
        AlertDialog.Builder bd = new AlertDialog.Builder(this);

        // set the button of the alert and link it ot the reset function
        // if the 'replay :)' is clicked the game will be reset
        bd.setPositiveButton("replay :)",(dialog, which) -> resetGame())
            .setCancelable(false); // disabling clicking outside the alert

        // if the player is winner
        if (winner) {
            // display winner message
            bd.setMessage("The winnier is " + playerSymbol)
                    // choose the image
                    .setIcon(playerSymbol.equals("O") ? R.drawable.o : R.drawable.x)
                    .setTitle("Congratulations!")
                    .show();

            return; // quit
        }

        // if not winner
        playingNow = !playingNow ; // toggle the true and false - O and X (change player)
        numberOfClicks++; // increment valid clicks count


        // reset if no one has won
        if (numberOfClicks >= 9) { // all boxes are full
            // show alert and reset
            bd.setMessage("No winner!")
                    .setTitle("Tie!")
                    .show();
        }

    }



    // reset function to reinitialize the variables
    void resetGame() {
        // reset background to white for all buttons
        setZeroBg(btn2);
        setZeroBg(btn3);
        setZeroBg(btn4);
        setZeroBg(btn1);
        setZeroBg(btn5);
        setZeroBg(btn6);
        setZeroBg(btn7);
        setZeroBg(btn8);
        setZeroBg(btn9);


        // reinitialize the main variables
        numberOfClicks = 0;
        playingNow = true;
        ticTac = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};

    }

    // function to set the background of the button
    void setBg(ImageButton i, String s) {
        if (s.equals("O")) // O
            i.setBackground(res.getDrawable(R.drawable.o));
        else // X
            i.setBackground(res.getDrawable(R.drawable.x));
    }

    // function to set the background to clear white
    void setZeroBg(ImageButton i) {
        i.setBackground(res.getDrawable(R.color.white));
    }
}