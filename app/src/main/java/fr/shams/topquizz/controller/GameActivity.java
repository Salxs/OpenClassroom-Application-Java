package fr.shams.topquizz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

import fr.shams.topquizz.R;
import fr.shams.topquizz.model.model.model.Question;
import fr.shams.topquizz.model.model.model.QuestionBank;

public class GameActivity extends AppCompatActivity {

    private TextView mTextQuestion;
    private Button mButtonAnswerUn;
    private Button mButtonAnswerDeux;
    private Button mButtonAnswerTrois;
    private Button mButtonAnswerQuatre;
    private QuestionBank mBanqueQuestion = generateQuestions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Création des attributs reliés au widgets de la page correspondante


        mTextQuestion=findViewById(R.id.game_activity_textview_question);
        mButtonAnswerUn=findViewById(R.id.game_activity_button_1);
        mButtonAnswerDeux=findViewById(R.id.game_activity_button_2);
        mButtonAnswerTrois=findViewById(R.id.game_activity_button_3);
        mButtonAnswerQuatre=findViewById(R.id.game_activity_button_4);


    }


    public QuestionBank generateQuestions(){
        Question question1 = new Question(
                "Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"
                ),
                0
        );

        Question question2 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );

        Question question3 = new Question(
                "What is the house of The Simpsons?",
                Arrays.asList(
                        "42",
                        "101",
                        "666",
                        "742"
                ),
                3
        );

        return new QuestionBank(Arrays.asList(question1,question2,question3));

    }
}