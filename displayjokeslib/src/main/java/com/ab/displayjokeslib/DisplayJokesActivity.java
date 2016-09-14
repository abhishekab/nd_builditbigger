package com.ab.displayjokeslib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayJokesActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_JOKE="joke";
    private TextView mTextViewJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_jokes);
        mTextViewJoke=(TextView)findViewById(R.id.textViewJoke);
        if(getIntent().hasExtra(KEY_EXTRA_JOKE)) {
            mTextViewJoke.setText(getIntent().getStringExtra(KEY_EXTRA_JOKE));
        }
        else {
            Toast.makeText(this, R.string.cant_fetch_joke,Toast.LENGTH_SHORT).show();
        }
    }
}
