package com.witsdk.wittestapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.witsdk.witcore.RequestListener;
import com.witsdk.witcore.WLog;
import com.witsdk.witcore.Wit;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Wit client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", 1);
            obj.put("title", "foo");
            obj.put("body", "bar");
            obj.put("userId", 1);
        }catch (JSONException e){

            Log.d("JSON", e.toString());
        }
        final Activity activity = this;
        FragmentManager fm = getSupportFragmentManager();
        client = new Wit(this, fm);

        client.request("http://jsonplaceholder.typicode.com/posts", "post", obj, activity, new RequestListener() {
            @Override
            public void onSuccess(JSONObject json, Integer id) {
                Log.d("WIT REQ","RESPONSE post"+ id.toString() +" : " + json.toString());
            }

            @Override
            public void onError(int code, String error) {
                Log.d("WIT REQ","ERROR "+ code +" : " + error);
            }
        });
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        client.onActivityResult(requestCode);
    }

    /* NOTE
    *  This fuction is not triggered by changeDefaultAppSms
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        client.onRequestPermissionsResult();
    }
}
