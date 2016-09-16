package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.ab.jokes.backend.jokesApi.JokesApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by q4J1X056 on 16-09-2016.
 */

// Help from http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
public class JokesEndpointAsyncTask extends AsyncTask<Context, Void, String> {
    public interface AsyncResponse {
        void processToStart();
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public JokesEndpointAsyncTask(AsyncResponse delegate){
        this.delegate = delegate;
    }
    private static JokesApi jokesApiService = null;
    private Context context;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        delegate.processToStart();
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
        delegate.processFinish(result);
    }
}