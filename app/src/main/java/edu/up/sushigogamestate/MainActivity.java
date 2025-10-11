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

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Clears first line -> setText
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
                appendLine("Move 3: Can be anything.\n");
                // Action <---
                appendLine(firstString + "\n");

                // Finished with all 3 moves
                appendLine("=== | Finished Testing! | ===\n");

                // Create a third instance (object of GameState)
                GameState thirdInstance = new GameState();

                // Calls toString() on second and third instance
                String secondInstant = secondInstance.toString();
                String thirdInstant = thirdInstance.toString();

                appendLine("Second Instance:\n" + secondInstant + "\n");
                appendLine("Third Instance:\n" + thirdInstant + "\n");

                if(secondInstant.equals(thirdInstant))
                {
                    appendLine("Both second and third instance match!\n");
                }
                else
                {
                    appendLine("Second and third instance do NOT match\n");
                }
            }

        });

    }

    public void appendLine(String text)
    {
        textOutput.append(text);
    }


}