package fr.shams.topquizz.controller;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
/*
On importe les classes de nos différents widgets pour pouvoir les référencer
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    ActivityResultLauncher<Intent> mIntent;


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
                editor.putString(SHARED_PREF_USER_NAME, mUser.getFirstName());
                editor.apply();

                mUser.setFirstName(mNameEditText.getText().toString());
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
                    }
                }
            });

    }




}