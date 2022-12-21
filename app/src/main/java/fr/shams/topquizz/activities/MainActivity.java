package fr.shams.topquizz.activities;

import static fr.shams.topquizz.helpers.SharedPreferencesHelper.SHARED_PREF_USER_NAME;
import static fr.shams.topquizz.helpers.SharedPreferencesHelper.SHARED_PREF_USER_SCORE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import fr.shams.topquizz.R;
import fr.shams.topquizz.helpers.SharedPreferencesHelper;
import fr.shams.topquizz.models.User;

public class MainActivity extends AppCompatActivity {

    private final SharedPreferencesHelper sharedPrefHelper = new SharedPreferencesHelper(this);
    private EditText mNameEditText;
    private Button mPlayButton;
    private final User mUser = new User();
    ActivityResultLauncher<Intent> mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ajout des trois widgets créés précedemment à l'aide d'attribut de classe
        TextView greetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);

        mPlayButton.setEnabled(false);

        mUser.setFirstName(sharedPrefHelper.getValueFromSharedPref(SHARED_PREF_USER_NAME, null));
        mUser.setLastScore(Integer.parseInt(sharedPrefHelper.getValueFromSharedPref(SHARED_PREF_USER_SCORE, "0")));
        if (mUser.getFirstName() != null) {
            greetingTextView.setText(getString(R.string.welcome_back, mUser.getFirstName(), mUser.getLastScore()));
            mNameEditText.setText(mUser.getFirstName());
        }

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //C'est dans cette méthode que l'on regarde si l'utilisateur à écrit
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });

        mPlayButton.setOnClickListener(view -> {
            // L'utilisateur a cliqué
            //On enregistre le prénom de l'utilisateur
            //On lance la seconde page qui contient l'activité de l'application

            //Permet d'enregistrer les informations de l'utilisateur dans uun fichier XML


            sharedPrefHelper.saveValueInSharedPref(SHARED_PREF_USER_NAME, mNameEditText.getText().toString());
            sharedPrefHelper.saveValueInSharedPref(SHARED_PREF_USER_SCORE, String.valueOf(mUser.getLastScore()));

            Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
            mIntent.launch(gameActivityIntent);
        });

        mIntent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        int score = result.getData().getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
                        mUser.setLastScore(score);
                    }
                });
    }

}