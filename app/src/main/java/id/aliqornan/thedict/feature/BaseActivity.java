package id.aliqornan.thedict.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.orhanobut.hawk.Hawk;

import id.aliqornan.thedict.R;

/**
 * Created by qornanali on 27/03/18.
 */

public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        Hawk.init(this).build();
    }

    public void openActivity(Class c, Bundle bundle, boolean isFinish) {
        Intent i = new Intent(this, c);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        this.startActivity(i);
        if (isFinish) {
            this.finish();
        }
    }
}
