# WIT Android SDK alpha 0.1.0

WIT Android SDK is a library to give fallback connectivity to mobile Apps.
Using WIT SDK you can do any kind of HTTP Requests.
If the device is offline, the SMS Channel will be used.

Official WIT Android SDK to support:
    - Internet backed HTTP Requests based on okHTTP
    - Offline HTTP Requests via WIT Fallback on SMS
    - Android 5 and 6

## Install

### Setup WIT SDK using Android Studio
The easiest way to install WIT SDK is to use the Android Studio Module import section,
in order to do that:

1) Go to File>New>New Module
2) Select "Import .JAR/.AAR Package" and click next.
3) Enter the path to .aar file and click finish.
4) Go to File>Project Settings (Ctrl+Shift+Alt+S).
5) Under "Modules," in left menu, select "app."
6) Go to "Dependencies tab.
7) Click the green "+" in the upper right corner.
8) Select "Module Dependency"
9) Select the new module from the list.

### Setup WIT SDK using Gradle

1) Clone this repository in your project root folder,
2) In the build.gradle inside your 'app' folder, add:

    dependencies {
        compile fileTree(include: ['*.aar'], dir: 'witsdk')
        ...
        compile project(':witsdk')
    }

3) Inside your settings.gradle add ':witsdk', the file should look like:

    include ':app',':witsdk'

## Use it

### Import the WIT SDK

    import com.witsdk.witcore.Wit;

### Initialize variables

    JSONObject obj = new JSONObject();
    obj.put("id", 1);
    obj.put("title", "foo");
    obj.put("body", "bar");
    obj.put("userId", 1);

    String url = "http://jsonplaceholder.typicsode.com/posts/1";
    String method = "patch";
    final Activity activity = this;

### Initialize WIT and do the Request

    Wit client = new Wit(this);

    client.request(url, method, obj, activity, new RequestListener() {
      @Override
      public void onSuccess(JSONObject json, Integer id) {
        Log.d("WIT REQ","RESPONSE "+ id.toString() +" : " + json.toString());
      }

      @Override
      public void onError(int code, String error) {
        Log.d("WIT REQ","ERROR "+ code +" : " + error);
      }
    });

## Examples

Here some example to get you started on the WIT Android SDK.

### GET Request

            client.request(url, "get", null, activity, new RequestListener() {
                @Override
                public void onSuccess(JSONObject json, Integer id) {
                    Log.d("WIT SDK","GET REQUEST, Response: "+ id.toString() +" : " + json.toString());
                }

                @Override
                public void onError(int code, String error) {
                    Log.d("WIT SDK","GET REQUEST, Error: "+ code +" : " + error);
                }
            });

### POST Request

            client.request("http://jsonplaceholder.typicode.com/posts", "post", obj, activity, new RequestListener() {
                @Override
                public void onSuccess(JSONObject json, Integer id) {
                    Log.d("WIT SDK","GET REQUEST, Response: "+ id.toString() +" : " + json.toString());
                }

                @Override
                public void onError(int code, String error) {
                    Log.d("WIT SDK","GET REQUEST, Error: "+ code +" : " + error);
                }
            });

### PUT Request

            client.request(url, "put", obj, activity, new RequestListener() {
                @Override
                public void onSuccess(JSONObject json, Integer id) {
                    Log.d("WIT SDK","GET REQUEST, Response: "+ id.toString() +" : " + json.toString());
                }

                @Override
                public void onError(int code, String error) {
                    Log.d("WIT SDK","GET REQUEST, Error: "+ code +" : " + error);
                }
            });

### PATCH Request

            client.request(url, "patch", obj, activity, new RequestListener() {
                @Override
                public void onSuccess(JSONObject json, Integer id) {
                    Log.d("WIT SDK","GET REQUEST, Response: "+ id.toString() +" : " + json.toString());
                }

                @Override
                public void onError(int code, String error) {
                    Log.d("WIT SDK","GET REQUEST, Error: "+ code +" : " + error);
                }
            });

### DELETE Request

            client.request(url, "delete", null, activity, new RequestListener() {
                @Override
                public void onSuccess(JSONObject json, Integer id) {
                    Log.d("WIT SDK","GET REQUEST, Response: "+ id.toString() +" : " + json.toString());
                }

                @Override
                public void onError(int code, String error) {
                    Log.d("WIT SDK","GET REQUEST, Error: "+ code +" : " + error);
                }
            });


### License

Copyright (C) WIT Technology, LTD - All Rights Reserved
