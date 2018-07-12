package me.gina.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import me.gina.parsetagram.model.Post;

public class TimelineActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    ArrayList<Post> posts;
    PostsAdapter pAdapter;
    RecyclerView rvPosts;
    private final int REQUEST_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
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


        RecyclerView rvPosts = findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        pAdapter = new PostsAdapter(posts);
        rvPosts.setAdapter(pAdapter);

        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        populateTimeline();

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

    public void onComposeAction(View view) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(TimelineActivity.this, CreateActivity.class);
        startActivity(i); // brings up the second activity
        finish();

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // REQUEST_CODE is defined above
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            // Extract post
//            String postID = data.getStringExtra("postID");
//
//            ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
//// First try to find from the cache and only then go to network
//            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
//// Execute the query to find the object with ID
//            query.getInBackground(postID, new GetCallback<Post>() {
//                public void done(Post item, ParseException e) {
//                    if (e == null) {
//
//                        posts.add(0, item);
//                        pAdapter.notifyItemInserted(0);
//                        rvPosts.scrollToPosition(0);
//                    }
//                }
//            });
//
//        }
//    }
}
