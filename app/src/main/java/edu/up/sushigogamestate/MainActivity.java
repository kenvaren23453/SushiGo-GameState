package edu.up.sushigogamestate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText textOutput;
    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textOutput = findViewById(R.id.editTextText);
        testButton = findViewById(R.id.runTestButton);

        testButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // Clears the edit text when button is re-pressed
                textOutput.setText("");

                GameState firstInstance = new GameState();

                String firstString = firstInstance.toString();

                // Create a deep copy of first instance and set to player 1 (ID: 0)
                GameState secondInstance = new GameState(firstInstance, 0);

                // Prints out the firstInstance (state) -> Sushi Go Game State
                appendLine("First Instance: \n" + firstString + "\n");

                // Perform (3) Moves total
                appendLine("=== | Starting Game | ===\n");

                // Setup for go here - Depending on if we need it..

                // MOVE 1 ---
                firstInstance.selectCard(0,0); //player 0 selects first card in their hand
                appendLine("Move 1: Player 0 selects first card in their hand.\n");
                appendLine(firstInstance.toString() + "\n"); // Calls toString() after every action

                // MOVE 2 ---
                firstInstance.selectCard(1,0);
                appendLine("Move 2: player one (the second player) selects a card.\n");
                // Action <---
                appendLine(firstInstance.toString() + "\n");

                // MOVE 3 ---
                firstInstance.passHands();
                appendLine("Move 3: Players pass their hands to each other!!.\n");
                // Action <---
                appendLine(firstInstance.toString() + "\n");

                // Finished with all 3 moves
                appendLine("=== | Finished Testing! | ===\n");

                //create a new game gamestate called third instance
                GameState thirdInstance = new GameState();

                // Create a fourth instance which is a copy of third instance, a new game state
                //this is for checking that the copy ctor works correctly
                //it should match second instance, which is also a copy of a new game from same player perspective
                //the rng for the hands is seeded for this so they should match exactly, allowing us to
                //simply compare their toString outputs to make sure they match, verifying copy ctor works
                GameState fourthInstance = new GameState(thirdInstance, 0);



                // Calls toString() on second and third instance
                String secondString = secondInstance.toString();
                String fourthString = fourthInstance.toString();

                appendLine("Second Instance:\n" + secondString + "\n");
                appendLine("Fourth Instance:\n" + fourthString + "\n");

                if(secondString.equals(fourthString))
                {
                    appendLine("Both second and fourth instance match!\n");
                }
                else
                {
                    appendLine("Second and fourth instance do NOT match\n");
                }
            }

        });

    }

    public void appendLine(String text)
    {
        textOutput.append(text);
    }


}