package faith.stufinite.time2eat;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpAsyncTask extends AsyncTask<Void, Void, String> {
    private String mUrl;
    public boolean check = false;
    public static String result;
    public HttpAsyncTask(String url) {
        mUrl = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        Log.d("in","ini..");
        String resultString = null;
        resultString = getJSON(mUrl);
        return resultString;
    }

    @Override
    protected void onPostExecute(String strings) {
        result = strings;
        super.onPostExecute(strings);
    }

    public String getJSON(String url) {
        HttpURLConnection c = null;
        try {
            Log.d("try","we try");
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.connect();
            int status = c.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    Log.d("Http","we in");
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    result = sb.toString();
                    check = true;
                    return sb.toString();
                default:
                    Log.d("status","well"+status);
            }

        } catch (Exception ex) {
            Log.d("error","fuck");
            return ex.toString();
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    //disconnect error
                }
            }
        }
        return null;
    }

}