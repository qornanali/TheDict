package id.aliqornan.thedict.feature;

import android.os.Bundle;
import android.widget.TextView;

import id.aliqornan.thedict.R;
import id.aliqornan.thedict.model.Word;

public class DetailActivity extends BaseActivity {

    TextView textText, textTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        actionBar.setTitle(getString(R.string.translation));

        textText = findViewById(R.id.text_word_text);
        textTranslation = findViewById(R.id.text_word_translation);

        Word word = (Word) getIntent().getExtras().getSerializable("word");
        if(word != null){
            textText.setText(word.getText());
            textTranslation.setText(word.getTranslation());
        }
    }
}
