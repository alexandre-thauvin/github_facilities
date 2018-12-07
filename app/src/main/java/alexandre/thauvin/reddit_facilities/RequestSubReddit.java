package alexandre.thauvin.reddit_facilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestSubReddit {

    public interface sendDataResponse {
        void sendData(String str);
    }

    public static class MakeRequestTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        private sendDataResponse dataInterface;
        private WeakReference<Context> contextRef;
        private ProgressDialog dialog;

        public MakeRequestTask(sendDataResponse inter, Context context){
            dataInterface = inter;
            contextRef = new WeakReference<>(context);
            dialog = new ProgressDialog(context);
        }


        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Request request = new Request.Builder()
                    .url("https://www.reddit.com/r/" + params[0] + "/.json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            catch (java.io.IOException e){
                Log.d("requestfailure", "get: " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(String response) {
            // TODO: check this.exception
            // TODO: do something with the feed
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dataInterface.sendData(response);
        }
    }
}
