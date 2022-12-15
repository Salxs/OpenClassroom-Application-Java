package fr.shams.topquizz.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import fr.shams.topquizz.R;
import fr.shams.topquizz.model.model.model.Question;
import fr.shams.topquizz.model.model.model.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextQuestion;
    private Button mButtonAnswerUn;
    private Button mButtonAnswerDeux;
    private Button mButtonAnswerTrois;
    private Button mButtonAnswerQuatre;
    private QuestionBank mBanqueQuestion = generateQuestions();
    private int mRemainingQuestionCount;
    private Question mCurrentQuestion;
    private int mScore;
    private final static String TAG = "SHAMS";

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    //On crée la clé associé au score du joueur pour le récupérer dans l'activité mainActivity
    public final static String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    private boolean mEnableTouchEvents;


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

        mEnableTouchEvents = true;
        mRemainingQuestionCount = 3;
        mScore = 0;

        //Détection de tous les boutons de l'applications
        mButtonAnswerUn.setOnClickListener(this);
        mButtonAnswerDeux.setOnClickListener(this);
        mButtonAnswerTrois.setOnClickListener(this);
        mButtonAnswerQuatre.setOnClickListener(this);

        //On place la question
        mCurrentQuestion = mBanqueQuestion.getCurrentQuestion();
        displayQuestions(mCurrentQuestion);
        }

    private void displayQuestions(final Question question){
        mTextQuestion.setText(question.getQuestion());
        mButtonAnswerUn.setText(question.getChoiceList().get(0));
        mButtonAnswerDeux.setText(question.getChoiceList().get(1));
        mButtonAnswerTrois.setText(question.getChoiceList().get(2));
        mButtonAnswerQuatre.setText(question.getChoiceList().get(3));
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

    @Override
    public void onClick(View view) {
        int index;
        if(view == mButtonAnswerUn){
            index = 0;
        }
        else if(view == mButtonAnswerDeux){
            index = 1;
        }
        else if(view == mButtonAnswerTrois){
            index = 2;
        }
        else if(view == mButtonAnswerQuatre){
            index = 3;
        }
        else{
            throw new IllegalStateException("Unknown clicked view : "+ view);
        }
        if(index == mBanqueQuestion.getCurrentQuestion().getAnswerIndex()){
            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
            mScore ++;
        }
        else{
            Toast.makeText(this, "INCORRECT", Toast.LENGTH_SHORT).show();
        }
        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRemainingQuestionCount --;
                if(mRemainingQuestionCount > 0){
                    mCurrentQuestion = mBanqueQuestion.getNextQuestion();
                    displayQuestions(mCurrentQuestion);
                }
                else{
                   endGame();
                }
                mEnableTouchEvents = true;
            }
        }, 2000);

    }
    private void endGame(){
        //Plus de question, le jeu est fini
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well Done!")
                .setMessage("Your score is : "+ mScore)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}