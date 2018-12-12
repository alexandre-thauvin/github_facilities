package alexandre.thauvin.github_facilities;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PostsFragment extends Fragment implements RequestGithub.sendDataResponse {
    MainActivity activity;
    List<Post> posts;
    RecyclerView recyclerView;
    Spinner spinner;

    public PostsFragment() {
        // Required empty public constructor
    }

    private OnFragmentInteractionListener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_posts, container, false);
        // Inflate the layout for this fragment

        activity = (MainActivity) getActivity();

        spinner = v.findViewById(R.id.spinner_kind);

        ArrayAdapter<CharSequence> actionAdapter = ArrayAdapter.createFromResource(activity,
                R.array.kind, android.R.layout.simple_spinner_item);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(actionAdapter);

        final SearchView searchView = v.findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                List<String> params = new ArrayList<>(Arrays.asList(query.split(" ")));
                params.add(0, spinner.getSelectedItem().toString());
                new RequestGithub.MakeRequestTask(PostsFragment.this, activity).execute(params);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

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

                if (result != null) {

                    try {
                        JSONObject response = new JSONObject(result);
                        Log.d("JSOOON", result);
                        JSONArray list = response.getJSONArray("items");

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject item = list.getJSONObject(i);

                            String url;
                            String owner = "";
                            String title;

                            switch (spinner.getSelectedItem().toString()) {
                                case "users":
                                    url = item.getString("html_url");
                                    title = item.getString("login");
                                    break;
                                default:
                                    JSONObject ownerObject = item.getJSONObject("owner");
                                    url = item.getString("html_url");
                                    title = item.getString("name");
                                    owner = ownerObject.getString("login");
                                    break;

                            }
                            posts.add(new Post(title, owner, url));
                        }

                    } catch (org.json.JSONException e) {
                        e.getMessage();
                    }
                    recyclerView.setAdapter(new RecyclerGithub(posts, mListener));

                    Toast.makeText(activity, "finished", Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String string);
    }
}
