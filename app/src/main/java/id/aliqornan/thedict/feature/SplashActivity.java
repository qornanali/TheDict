package id.aliqornan.thedict.feature;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import id.aliqornan.thedict.R;
import id.aliqornan.thedict.data.WordSQLHelper;
import id.aliqornan.thedict.model.Word;

public class SplashActivity extends BaseActivity {

    TextView tvLoadingInfo;
    ProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();

        tvLoadingInfo = findViewById(R.id.text_loading_info);
        myProgressBar = findViewById(R.id.my_progress_bar);

        String firstRun = Hawk.get("first_run");
        if (firstRun != null) {
            if (firstRun != "0") {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SplashActivity.this, getString(R.string.loaded), Toast.LENGTH_LONG).show();
                        openActivity(MainActivity.class, null, true);
                    }
                }, 1500);
            } else {
                new WordsLoader(this).execute();
            }
        } else {
            new WordsLoader(this).execute();
        }

    }

    private void setProgress(String text, Float percent) {
        tvLoadingInfo.setText(String.format("%s\n%.2f", text, percent)+"%");
    }

    private class WordsLoader extends AsyncTask<String, Float, String> {

        Context context;
        WordSQLHelper wordSQLHelper;
        List<Word> words;

        public WordsLoader(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wordSQLHelper = new WordSQLHelper(context);
            words = new ArrayList<>();
        }


        public List<Word> getData(int rawRes, boolean isEnglish) {
            String receiveString = "";
            List<Word> words = new ArrayList<Word>();
            InputStream inputStream = context.getResources().openRawResource(rawRes);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                while ((receiveString = bufferedReader.readLine()) != null) {
                    String[] columns = receiveString.split("[ \t]+");
                    String translation = "";
                    for (int j = 1; j < columns.length; j++) {
                        translation = translation + " " + columns[j];
                    }
                    words.add(new Word(columns[0], isEnglish, translation));
                }
                inputStream.close();
                return words;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            if (values[0] > 0) {
                myProgressBar.setVisibility(View.VISIBLE);
            }
            myProgressBar.setProgress(Math.round(values[0]));
            setProgress(getString(R.string.loading), values[0]);
        }

        @Override
        protected String doInBackground(String... strings) {
            wordSQLHelper.open();
            wordSQLHelper.beginTransaction();
            try {
                words.addAll(getData(R.raw.indonesia_english, false));
                words.addAll(getData(R.raw.english_indonesia, true));
                int n = words.size();
                for (int i = 0; i < n; i++) {
                    wordSQLHelper.insert(words.get(i));
                    publishProgress((i * 100f / n));
                }
                wordSQLHelper.setTransactionSuccess();
            } catch (Exception e) {
                e.printStackTrace();
            }
            wordSQLHelper.endTransaction();
            wordSQLHelper.close();
            return "Executed";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Hawk.put("first_run", "0");
            openActivity(MainActivity.class, null, true);
        }
    }
}
