package fr.shams.topquizz;

import androidx.appcompat.app.AppCompatActivity;
/*
On importe les classes de nos différents widgets pour pouvoir les référencer
 */
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingTextView =findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);
    }


    //Ajout des trois éléments widgets créés précedemment à l'aide d'attribut de classe
    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;

    //On crée les accesseurs de nos attributs de classe
    public TextView getmGreetingTextView() {
        return mGreetingTextView;
    }

    public EditText getmNameEditText() {
        return mNameEditText;
    }

    public Button getmPlayButton() {
        return mPlayButton;
    }

    //On crée les mutateurs de nos attributs de classe
    public void setmGreetingTextView(TextView mGreetingTextView) {
        this.mGreetingTextView = mGreetingTextView;
    }

    public void setmNameEditText(EditText mNameEditText) {
        this.mNameEditText = mNameEditText;
    }

    public void setmPlayButton(Button mPlayButton) {
        this.mPlayButton = mPlayButton;
    }

}