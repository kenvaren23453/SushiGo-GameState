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
        testButton = findViewById(R.id.button);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Method for OnClick functionality
                testButtonRun();
            }

            // ok

        });

    }

    public void appendLine(String text)
    {
        textOutput.append(text);
    }

    public void testButtonRun()
    {

        // Clears first line -> setText
        textOutput.setText("");

        GameState firstInstance = new GameState();

        String firstInstant = firstInstance.toString();

        // Create a deep copy of first instance and set to player 1 (ID: 0)
        GameState secondInstance = new GameState(firstInstance, 0);

        // Prints out the firstInstance (state) -> Sushi Go Game State
        appendLine("First Instance: \n" + firstInstant + "\n");

        // Perform (3) Moves total
        appendLine("=== | Starting Game | ===\n");

        // Setup for go here - Depending on if we need it..

        // MOVE 1 ---
        appendLine("Move 1: Can be anything.\n");
        // firstInstance.selectCard() --> Action
        appendLine(firstInstant + "\n"); // Calls toString() after every action

        // MOVE 2 ---
        appendLine("Move 2: Can be anything.\n");
        // Action <---
        appendLine(firstInstant + "\n");

        // MOVE 3 ---
        appendLine("Move 3: Can be anything.\n");
        // Action <---
        appendLine(firstInstant + "\n");

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

}