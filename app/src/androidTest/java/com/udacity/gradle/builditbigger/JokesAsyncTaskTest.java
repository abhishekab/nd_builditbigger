package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by q4J1X056 on 16-09-2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class JokesAsyncTaskTest {


    @Test
    public void testVerifyJokeAsyncTask() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        JokesEndpointAsyncTask asyncTaskTest=new JokesEndpointAsyncTask(new JokesEndpointAsyncTask.AsyncResponse() {
        @Override
        public void processToStart() {

        }
        @Override
        public void processFinish(String result) {
            assertNotNull("Fail, Response is null", result);
            assertTrue("Fail, Response is empty", !result.isEmpty());
            signal.countDown();
        }
    });
        asyncTaskTest.execute(InstrumentationRegistry.getTargetContext());

        signal.await();

    }

}
