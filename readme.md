![alt tag](https://raw.githubusercontent.com/WithoutInternetTechnology/wit-android-sdk/master/android-sdk%20banner.png)

# WIT Android SDK *alpha* 0.1.0

*Please be patient :) this is a very early SDK*

WIT Android SDK is a library to give fallback connectivity to mobile Apps.
Using WIT SDK you can do any kind of HTTP Requests.
If the device is offline, the SMS Channel will be used.

Official WIT Android SDK to support:

- Internet backed HTTP Requests based on okHTTP
- Offline HTTP Requests via WIT Fallback on SMS
- Android 5 and 6

## Something to keep in mind

In this phase WIT SDK supports "generic requests" meaning that
you can throw(1) any kind of data to any url of choice which can result
in a big payload being sent via SMS.
Our system take care of compressing and optimizing the request you are doing,
but this doesn't mean that the payload will be short,
what we suggest is to take a look at your endpoints and simplify your data where you can.
This will have a big impact on the speed of the communications between the offline 
device and the server.

Here at [WIT Technology](https://www.witsdk.com) we offer a premium support to any company who want to improve
the speed of the requests and to lower the prices for the data transportation.
Your endpoints will remain the same so you don't have to modify any code to take
advantage of this.

1) we support all HTTP Requests Verbs -> get|post|put|patch|delete


## Contact US

For any technical issue create it [here](https://github.com/WithoutInternetTechnology/wit-android-sdk/issues) or contact us at davide [ at ] witsdk.com.
For business operations, and to know more about our Premium Support write to alessio [ at ] witsdk.com.


# Install

### Prerequisites

WIT SDK needs [okHttp3](https://github.com/square/okhttp) to work.

### Setup WIT SDK using Android Studio
The easiest way to install WIT SDK is to use the Android Studio Module import section,
in order to do that:

- Clone this repository in your computer
- Go to File>New>New Module
- Select "Import .JAR/.AAR Package" and click next.
- Enter the path to .aar file and click finish.
- Go to File>Project Settings (Ctrl+Shift+Alt+S).
- Under "Modules," in left menu, select "app."
- Go to "Dependencies tab.
- Click the green "+" in the upper right corner.
- Select "Module Dependency"
- Select the new module from the list.


### Setup WIT SDK using Gradle

1. Clone this repository in your project root folder,
2. In the build.gradle inside your 'app' folder, add WIT SDK and okHttp3:

```java
    dependencies {
        compile fileTree(include: ['*.aar'], dir: 'witsdk')
 
        ...
        compile 'com.squareup.okhttp3:okhttp:3.4.2'
        compile project(':witsdk')
    }
```

3. Inside your settings.gradle add ':witsdk', the file should look like:

```java
    include ':app',':witsdk'
```

## Use it

### Import the WIT SDK

    import com.witsdk.witcore.Wit;

### Initialize variables
```java
    JSONObject obj = new JSONObject();
    obj.put("id", 1);
    obj.put("title", "foo");
    obj.put("body", "bar");
    obj.put("userId", 1);

    String url = "http://jsonplaceholder.typicsode.com/posts/1";
    String method = "patch";
    final Activity activity = this;
```

### Initialize WIT and do the Request

```java
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
```
## Examples

Here some example to get you started on the WIT Android SDK.

### GET Request
```java
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
```

### POST Request
```java
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
```
### PUT Request
```java
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
```
### PATCH Request
```java
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
```
### DELETE Request
```java
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
```

### License

Copyright (C) WIT Technology, LTD - All Rights Reserved
