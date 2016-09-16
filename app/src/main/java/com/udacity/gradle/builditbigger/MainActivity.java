package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ab.displayjokeslib.DisplayJokesActivity;



public class MainActivity extends ActionBarActivity implements JokesEndpointAsyncTask.AsyncResponse {
    ProgressBar mProgressBarMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBarMain=(ProgressBar)findViewById(R.id.progressBarMain);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {

//        JokeTeller jokeTeller=new JokeTeller();
//        Intent intent=new Intent(this, DisplayJokesActivity.class);
//        intent.putExtra(DisplayJokesActivity.KEY_EXTRA_JOKE,jokeTeller.getJoke());
//        startActivity(intent);

        new JokesEndpointAsyncTask(MainActivity.this).execute(MainActivity.this);
        //Toast.makeText(this, jokeTeller.getJoke(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void processToStart() {
        mProgressBarMain.setVisibility(View.VISIBLE);
    }
    @Override
    public void processFinish(String result)
    {
        mProgressBarMain.setVisibility(View.GONE);
        if(result!=null && !result.isEmpty()){
            Intent intent=new Intent(MainActivity.this, DisplayJokesActivity.class);
            intent.putExtra(DisplayJokesActivity.KEY_EXTRA_JOKE,result);
            startActivity(intent);

        }else{
            Toast.makeText(MainActivity.this, R.string.error_fetching_joke,Toast.LENGTH_SHORT).show();
        }
    }


}
