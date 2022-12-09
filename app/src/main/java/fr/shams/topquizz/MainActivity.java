package fr.shams.topquizz;

import androidx.appcompat.app.AppCompatActivity;
/*
On importe les différentes classes nécessaires à la création et à l'utilisation de nos variables
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
    }
    //Ajout des trois éléments graphique créés précedemment à l'aide de variable
    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
}