package me.gina.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

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
//
//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                populateTimeline();
//                swipeContainer.setRefreshing(false);
//            }
//        });
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//
//        RecyclerView rvPosts = findViewById(R.id.rvPosts);
//        posts = new ArrayList<>();
//        pAdapter = new PostsAdapter(posts);
//        rvPosts.setAdapter(pAdapter);
//
//        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_favorites:
                        Intent i = new Intent(TimelineActivity.this, CreateActivity.class);
                        startActivity(i); // brings up the second activity
                        finish();
                        return true;
                }
                return true;
            }
        });

//        populateTimeline();

    }

//
//
//    private void populateTimeline(){
//
//        pAdapter.clear();
//        final Post.Query postsQuery = new Post.Query();
//        postsQuery.getTop().withUser();
//        postsQuery.orderByDescending("createdAt");
//
//        postsQuery.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> objects, ParseException e) {
//                if (e == null){
//                   posts.clear();
//                   posts.addAll(objects);
//                   pAdapter.notifyDataSetChanged();
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }

//    public void onComposeAction(View view) {
//        // first parameter is the context, second is the class of the activity to launch
//        Intent i = new Intent(TimelineActivity.this, CreateActivity.class);
//        startActivity(i); // brings up the second activity
//        finish();
//
//    }




}
