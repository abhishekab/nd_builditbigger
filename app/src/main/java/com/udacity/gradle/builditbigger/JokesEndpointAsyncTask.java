package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ab.displayjokeslib.DisplayJokesActivity;
import com.ab.jokes.backend.jokesApi.JokesApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by q4J1X056 on 16-09-2016.
 */
public class JokesEndpointAsyncTask extends AsyncTask<Context, Void, String> {
    private static JokesApi jokesApiService = null;
    private Context context;
    private ProgressBar progressBar;
    JokesEndpointAsyncTask(ProgressBar progressBarMain)
    {
        this.progressBar=progressBarMain;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progressBar!=null)
        {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String doInBackground( Context... params) {
        context = params[0];

        if(jokesApiService == null) {  // Only do this once
            JokesApi.Builder builder = new JokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(context.getString(R.string.endpoint_url))
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            jokesApiService = builder.build();
        }



        try {
            return jokesApiService.getJoke().execute().getJoke();
        } catch (IOException e) {
             e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
        }
        if(result!=null && !result.isEmpty()){
            Intent intent=new Intent(context, DisplayJokesActivity.class);
        intent.putExtra(DisplayJokesActivity.KEY_EXTRA_JOKE,result);
        context.startActivity(intent);

        }else{
            Toast.makeText(context, R.string.error_fetching_joke,Toast.LENGTH_SHORT).show();
        }


    }
}