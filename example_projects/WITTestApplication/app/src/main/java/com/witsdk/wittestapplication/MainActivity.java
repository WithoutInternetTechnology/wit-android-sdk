package com.witsdk.wittestapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.witsdk.witcore.RequestListener;
import com.witsdk.witcore.Wit;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

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
        Wit client = new Wit(this);

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
}
