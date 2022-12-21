package fr.shams.topquizz.activities;

import static fr.shams.topquizz.helpers.SharedPreferencesHelper.SHARED_PREF_USER_SCORE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import fr.shams.topquizz.R;
import fr.shams.topquizz.helpers.SharedPreferencesHelper;
import fr.shams.topquizz.models.Question;
import fr.shams.topquizz.models.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";
    public final static String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    private TextView mQuestionTextView;
    private List<Button> mAnswerButtons;
    private QuestionBank mQuestionBank;
    private int mRemainingQuestionCount;
    private Question mCurrentQuestion;
    private int mScore;
    private boolean mEnableTouchEvents;
    private final SharedPreferencesHelper sharedPrefHelper = new SharedPreferencesHelper(this);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Génération des questions
        mQuestionBank = generateQuestions();

        // Création des attributs reliés au widgets de la page correspondante
        mQuestionTextView =findViewById(R.id.game_activity_textview_question);
        mAnswerButtons = Arrays.asList(
                findViewById(R.id.game_activity_button_1),
                findViewById(R.id.game_activity_button_2),
                findViewById(R.id.game_activity_button_3),
                findViewById(R.id.game_activity_button_4)
        );

        mEnableTouchEvents = true;

        // Détection de tous les boutons de l'applications
        mAnswerButtons.get(0).setOnClickListener(this);
        mAnswerButtons.get(1).setOnClickListener(this);
        mAnswerButtons.get(2).setOnClickListener(this);
        mAnswerButtons.get(3).setOnClickListener(this);

        // On place la question
        mCurrentQuestion = mQuestionBank.getCurrentQuestion();
        displayQuestions(mCurrentQuestion);

        if(savedInstanceState != null){
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        }
        else{
            mScore = 0;
            mRemainingQuestionCount = 3;
        }
    }

    private void displayQuestions(final Question question){
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButtons.get(0).setText(question.getChoiceList().get(0));
        mAnswerButtons.get(1).setText(question.getChoiceList().get(1));
        mAnswerButtons.get(2).setText(question.getChoiceList().get(2));
        mAnswerButtons.get(3).setText(question.getChoiceList().get(3));
    }


    public QuestionBank generateQuestions(){
        Question question1 = new Question(
                getString(R.string.question_1),
                Arrays.asList(
                        getString(R.string.question_1_answer_1),
                        getString(R.string.question_1_answer_2),
                        getString(R.string.question_1_answer_3),
                        getString(R.string.question_1_answer_4)
                ),
                0
        );

        Question question2 = new Question(
                getString(R.string.question_2),
                Arrays.asList(
                        getString(R.string.question_2_answer_1),
                        getString(R.string.question_2_answer_2),
                        getString(R.string.question_2_answer_3),
                        getString(R.string.question_2_answer_4)
                ),
                3
        );

        Question question3 = new Question(
                getString(R.string.question_3),
                Arrays.asList(
                        getString(R.string.question_3_answer_1),
                        getString(R.string.question_3_answer_2),
                        getString(R.string.question_3_answer_3),
                        getString(R.string.question_3_answer_4)
                ),
                3
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3));
    }

    @Override
    public void onClick(View view) {
        int index = mAnswerButtons.indexOf((Button) view);
        String toastText;

        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()) {
            toastText = getString(R.string.answer_correct).toUpperCase();
            mScore ++;
        }
        else {
            toastText = getString(R.string.answer_incorrect).toUpperCase();
        }

        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
        mEnableTouchEvents = false;
        new Handler().postDelayed(() -> {
            mRemainingQuestionCount --;
            if (mRemainingQuestionCount > 0){
                mCurrentQuestion = mQuestionBank.getNextQuestion();
                displayQuestions(mCurrentQuestion);
            }
            else{
               endGame();
            }
            mEnableTouchEvents = true;
        }, 2000);
    }

    private void endGame(){
        // Plus de question, le jeu est fini
        sharedPrefHelper.saveValueInSharedPref(SHARED_PREF_USER_SCORE, String.valueOf(mScore));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.answer_congrats))
                .setMessage(getString(R.string.answer_score, mScore))
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                    setResult(RESULT_OK, intent);
                    finish();
                })
                .create()
                .show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mRemainingQuestionCount);
    }
}