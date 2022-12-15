package fr.shams.topquizz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import fr.shams.topquizz.R;
import fr.shams.topquizz.model.model.model.User;

public class MainActivity extends AppCompatActivity {


    //Ajout des trois widgets créés précedemment à l'aide d'attribut de classe
    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private User mUser;
    private final static int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final String SHARED_PREF_USER_NAME = "SHARED_PREF_USER_NAME";
    private static final String SHARED_PREF_USER_SCORE = "SHARED_PREF_USER_SCORE";
    ActivityResultLauncher<Intent> mIntent;
    private final static String TAG2 = "AUTRE_SHAMS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingTextView =findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);

        mPlayButton.setEnabled(false);

        String firstName = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_NAME,null);
        int ancienScore = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getInt(SHARED_PREF_USER_SCORE, 0);
        if( firstName != null){
            mGreetingTextView.setText("Welcome Back "+ firstName + "!"+"\n"+"Your last Score was " +
                    ancienScore+", "+"will you do better this time ?" );
            mNameEditText.setText(firstName);
        }

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s,int start, int count, int after){

            }

            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count){

            }

            @Override
            public void afterTextChanged (Editable s){
                //C'est dans cette méthode que l'on regarde si l'utilisateur à écrit
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // L'utilisateur a cliqué
                //On enregistre le prénom de l'utilisateur
                //On lance la seconde page qui contient l'activité de l'application

                //Permet d'enregistrer les informations de l'utilisateur dans uun fichier XML

                SharedPreferences preferences = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SHARED_PREF_USER_NAME, mNameEditText.getText().toString());
                editor.apply();
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                //startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
                mIntent.launch(gameActivityIntent);


                preferences.edit()
                        .putInt(SHARED_PREF_USER_SCORE, mUser.getLastScore())
                        .apply();

            }
        });

    mIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(GAME_ACTIVITY_REQUEST_CODE == result.getResultCode() && RESULT_OK == result.getResultCode()){
                        int score = result.getData().getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
                        mUser.setLastScore(score);
                    }
                }
            });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG2, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG2, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG2, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG2, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG2, "onDestroy() called");
    }
}