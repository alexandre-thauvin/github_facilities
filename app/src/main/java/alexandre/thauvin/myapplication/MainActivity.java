package alexandre.thauvin.myapplication;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements RequestSubReddit.sendDataResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void sendData(final String result){

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject response = new JSONObject(result);
                }
                catch (org.json.JSONException e){
                    e.getMessage();
                }
                JSONObject data = response.getJSONObject("data");
                JSONArray hotTopics = data.getJSONArray("children");

                for (int i = 0; i < hotTopics.length(); i++) {
                    JSONObject topic = hotTopics.getJSONObject(i).getJSONObject("data");

                    String author = topic.getString("author");
                    String imageUrl = topic.getString("thumbnail");
                    String postTime = topic.getString("created_utc");
                    String rScore = topic.getString("score");
                    String title = topic.getString("title");

                    topicdata.add(new ListData(title, author, imageUrl, postTime, rScore));
                    Log.v(DEBUG_TAG, topicdata.toString());
                }
                //tv.setText(response);
            }
        });
    }
}
