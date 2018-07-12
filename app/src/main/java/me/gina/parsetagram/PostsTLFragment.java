package me.gina.parsetagram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import me.gina.parsetagram.model.Post;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostsTLFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class PostsTLFragment extends Fragment {


    private SwipeRefreshLayout swipeContainer;
    ArrayList<Post> posts;
    PostsAdapter pAdapter;
    RecyclerView rvPosts;



    public PostsTLFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_posts_tl, container, false);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline();
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        RecyclerView rvPosts = v.findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        pAdapter = new PostsAdapter(posts);
        rvPosts.setAdapter(pAdapter);

        rvPosts.setLayoutManager(new LinearLayoutManager(v.getContext()));

        populateTimeline();

        return v;
    }



    private void populateTimeline(){

        pAdapter.clear();
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();
        postsQuery.orderByDescending("createdAt");

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    posts.clear();
                    posts.addAll(objects);
                    pAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });

    }
}
