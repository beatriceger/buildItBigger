package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;

import com.example.beatrice.mylibrary.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.backend.MyBean;

import java.io.IOException;

public class EndpointAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    Context context;
    private String result;
    private InterstitialAd interstitialAd;
    private static JokeApi jokeApi = null;

    @Override
    protected String doInBackground(Pair<Context, String>... pairs) {
        if (jokeApi == null) {
            jokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(context.getString(R.string.root_url_api));
            jokeApi = builder.build();
        }
        try {
            return jokeApi.sayHi(new MyBean()).execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public EndpointAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        result = s;
        // Setting the interstitial ad
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                startActivityToDisplayJoke();
            }

            @Override
            public void onAdClosed() {
                startActivityToDisplayJoke();
            }
        });
        AdRequest ar = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(context.getString(R.string.device_id))
                .build();
        interstitialAd.loadAd(ar);
    }


    private void startActivityToDisplayJoke() {
        Intent i = new Intent(context, JokeActivity.class);
        i.putExtra("joke", result);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
