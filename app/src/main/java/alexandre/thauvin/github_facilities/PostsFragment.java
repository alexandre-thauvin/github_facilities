package alexandre.thauvin.github_facilities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PostsFragment extends Fragment implements RequestGithub.sendDataResponse {
    MainActivity activity;
    List<Post> posts;
    RecyclerView recyclerView;

    public PostsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PostsFragment newInstance(String param1, String param2) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_posts, container, false);
        // Inflate the layout for this fragment

        activity = (MainActivity) getActivity();

        Spinner spinner = v.findViewById(R.id.spinner_kind);

        ArrayAdapter<CharSequence> actionAdapter = ArrayAdapter.createFromResource(activity,
                R.array.kind, android.R.layout.simple_spinner_item);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(actionAdapter);

        new RequestGithub.MakeRequestTask(this, activity).execute("onepiece");

        posts = new ArrayList<>();

         recyclerView = v.findViewById(R.id.recycler_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        return v;
    }

    @Override
    public void sendData(final String result) {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject response = new JSONObject(result);
                    JSONArray list = response.getJSONArray("items");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);

                        String url = item.getString("url");
                        String title = item.getString("login");
                        String repo = item.getString("subreddit");

                        posts.add(new Post(title, repo, url));
                    }

                } catch (org.json.JSONException e) {
                    e.getMessage();
                }
                recyclerView.setAdapter(new RecyclerGithub(posts));

                //tv.setText(response);
            }
        });
    }
}
