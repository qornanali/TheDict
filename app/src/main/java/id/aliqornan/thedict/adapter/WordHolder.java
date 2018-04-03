package id.aliqornan.thedict.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import id.aliqornan.thedict.R;
import id.aliqornan.thedict.model.Word;

/**
 * Created by qornanali on 22/03/18.
 */

public class WordHolder extends BaseHolder<Word> {

    TextView textText, textTranslation;

    public WordHolder(View itemView) {
        super(itemView);
        textText = itemView.findViewById(R.id.text_word_text);
        textTranslation = itemView.findViewById(R.id.text_word_translation);
    }

    public void bind(Context context, int position, Word data) {
        textText.setText(data.getText());
        textTranslation.setText(data.getTranslation());
    }

}
