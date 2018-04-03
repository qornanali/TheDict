package id.aliqornan.thedict.feature;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.aliqornan.thedict.R;
import id.aliqornan.thedict.adapter.DefaultRVAdapter;
import id.aliqornan.thedict.adapter.WordHolder;
import id.aliqornan.thedict.data.WordSQLHelper;
import id.aliqornan.thedict.model.Word;
import id.aliqornan.thedict.util.ItemClickListener;

public class MainActivity extends BaseActivity {

    Button btnChangeLang, btnSearch;
    TextView tvLang1, tvLang2;
    EditText etQuery;

    RecyclerView rvWords;

    WordSQLHelper wordSQLHelper;
    DefaultRVAdapter<WordHolder, Word> adapter;
    List<Word> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        words = new ArrayList<>();

        wordSQLHelper = new WordSQLHelper(this);
        rvWords = findViewById(R.id.rv_words);
        tvLang1 = findViewById(R.id.text_lang_1);
        tvLang2 = findViewById(R.id.text_lang_2);
        etQuery = findViewById(R.id.et_query);
        btnSearch = findViewById(R.id.btn_search);
        tvLang2 = findViewById(R.id.text_lang_2);
        btnChangeLang = findViewById(R.id.btn_change_lang);
        btnChangeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvLang1.getText().toString().equals(getString(R.string.english))) {
                    tvLang1.setText(getString(R.string.indonesian));
                    tvLang2.setText(getString(R.string.english));
                } else {
                    tvLang1.setText(getString(R.string.english));
                    tvLang2.setText(getString(R.string.indonesian));
                }
            }
        });

        adapter = new DefaultRVAdapter<WordHolder, Word>(words, this) {
            @Override
            public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new WordHolder(inflate(R.layout.item_list_word, parent, false));
            }
        };
        rvWords.setLayoutManager(new LinearLayoutManager(this));
        rvWords.setNestedScrollingEnabled(false);
        rvWords.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener<Word>() {
            @Override
            public void onItemClick(int position, Word data) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("word", data);
                openActivity(DetailActivity.class, bundle, false);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etQuery.getText().toString();
                if (!query.isEmpty()) {
                    wordSQLHelper.open();
                    words.clear();
                    words.addAll(wordSQLHelper.query(null, WordSQLHelper.WordEntry.COL_NAME_IS_ENGLISH + " = " +
                            (tvLang1.getText().toString().equals(getString(R.string.english)) ? "1" : "0")
                            + " AND " + WordSQLHelper.WordEntry.COL_NAME_TEXT + " LIKE '"+ query+"%'", null));
                    wordSQLHelper.close();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
