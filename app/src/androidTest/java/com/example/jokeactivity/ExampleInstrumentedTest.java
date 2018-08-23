package com.example.jokeactivity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.MainActivityFragment;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);

//    @Before
//    public void init(){
//        mActivity.getActivity().getFragmentManager().beginTransaction();
//    }

    @Test
    public void mainActivityTest(){
        Espresso.onView(ViewMatchers.withId(R.id.tv)).check(ViewAssertions
        .matches(ViewMatchers.withText("")));

        final CountDownLatch signal = new CountDownLatch(1);
        try {
            AsyncTask<Pair<Context, String>, Void, String> task = new AsyncTask<Pair<Context, String>, Void, String>() {
                private MyApi myApiService = null;
                private Context context;

                @Override
                protected String doInBackground(Pair<Context, String>... params) {
                    if(myApiService == null) {  // Only do this once
                        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                                new AndroidJsonFactory(), null)
                                // options for running against local devappserver
                                // - 10.0.2.2 is localhost's IP address in Android emulator
                                // - turn off compression when running against local devappserver
                                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                    @Override
                                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                        abstractGoogleClientRequest.setDisableGZipContent(true);
                                    }
                                });
                        // end options for devappserver

                        myApiService = builder.build();
                    }

                    context = params[0].first;
                    String name = params[0].second;

                    try {
                        return myApiService.sayHi(name).execute().getData();
                    } catch (IOException e) {
                        return "";
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    assertTrue(!s.isEmpty());
                    signal.countDown();
                    Log.d("EXAMPLE", s);
                }
            };
            task.execute(new Pair<Context, String>(getContext(), "Nume"));
            signal.await();
        }
        catch (InterruptedException e) {
        }

    }
}
