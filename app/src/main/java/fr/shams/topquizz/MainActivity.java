package fr.shams.topquizz;

import androidx.appcompat.app.AppCompatActivity;
/*
On importe les classes de nos différents widgets pour pouvoir les référencer
 */
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    //Ajout des trois éléments widgets créés précedemment à l'aide d'attribut de classe
    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingTextView =findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);

        mPlayButton.setEnabled(false);

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s,int start, int count, int after){

            }

            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count){

            }

            @Override
            public void afterTextChanged (Editable s){
                //C'est dans cette méthode que l'on regarde si l'utilisateur à écrire
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // L'utilisateur à cliqué
            }
        });

    }




}