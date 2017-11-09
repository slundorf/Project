package com.example.simon.galgeleg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import static com.example.simon.galgeleg.MainMenu.logic;

/**
 * Created by Simon on 20-10-2017.
 */

public class Game extends Fragment implements View.OnClickListener {

    private Button gameBut;
    private EditText et;
    private ImageView gameiv;
    private TextView gametv;
    static Integer[] imageIDs = {
            R.drawable.galge,
            R.drawable.forkert1,
            R.drawable.forkert2,
            R.drawable.forkert3,
            R.drawable.forkert4,
            R.drawable.forkert5,
            R.drawable.forkert6,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Game", "the fragment was shown!!");

        View source = inflater.inflate(R.layout.activity_game, container, false);

        gameBut = (Button) source.findViewById(R.id.button4);
        gametv = (TextView) source.findViewById(R.id.gametv);
        gameiv = (ImageView) source.findViewById(R.id.gameiv);
        et = (EditText) source.findViewById(R.id.et);

        startGame();

        gameBut.setOnClickListener(this);

        return source;
    }

    @Override
    public void onClick(View v) {

        if (v == gameBut) {

            String letter = et.getText().toString();
            if (letter.length() != 1 && letter.length() != logic.getOrdet().length()) {
                et.setError("Only write ONE letter or guess the WHOLE word");
                return;
            }
            if (letter.length() == logic.getOrdet().length()) {
                logic.gætOrd(letter);
                updateScreen();
            } else {
                logic.gætBogstav(letter);
                et.setText("");
                et.setError(null);
                updateScreen();
            }
        }

    }


    private void updateScreen() {

        gametv.setText("Guess the word: " + logic.getSynligtOrd());
        if (logic.erSidsteBogstavKorrekt() == true) {

            gametv.append("\n\nYou have " + logic.getAntalForkerteBogstaver() + " wrong:" + logic.getBrugteBogstaver());

        } else if (logic.erSidsteBogstavKorrekt() == false) {

            gametv.append("\n\nYou have " + logic.getAntalForkerteBogstaver() + " wrong:" + logic.getBrugteBogstaver());

            gameiv.setImageResource(imageIDs[logic.getAntalForkerteBogstaver()]);

        }

        if (logic.erSpilletVundet()) {

            gametv.append("\nYou win!");

            endGame();

        }
        if (logic.erSpilletTabt()) {

            gametv.setText("You have lost. The word was : " + logic.getOrdet());

            endGame();
        }
    }

    public void startGame() {
        logic.nulstil();

        gametv.setText("This is the word you have to guess: \n"+logic.getSynligtOrd() +
                "\nWrite the letter or word you wish to guess on underneath and hit 'Guess'.\n");

        et.setHint("Write your letter here.");

        gameiv.setImageResource(imageIDs[logic.getAntalForkerteBogstaver()]);

    }

    private void endGame() {

        EndGame fragment = new EndGame();
        Bundle arguments = new Bundle();
        fragment.setArguments(arguments);

        getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragments, fragment)
                .addToBackStack(null)
                .commit();

    }
}