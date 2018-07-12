package me.gina.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import me.gina.parsetagram.model.Post;

public class TimelineActivity extends AppCompatActivity {

    ArrayList<Post> posts;
    PostsAdapter pAdapter;
    RecyclerView rvPosts;
    private final int REQUEST_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        RecyclerView rvPosts = findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        pAdapter = new PostsAdapter(posts);
        rvPosts.setAdapter(pAdapter);

        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        populateTimeline();

    }

    private void populateTimeline(){

        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();
        postsQuery.orderByDescending("createdAt");

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for (int i = 0; i < objects.size(); i++){
                        Log.d("CreateActivity", "Post[" + i + "] = "
                                + objects.get(i).getCaption() + "\nusername = "
                                + objects.get(i).getUser().getUsername());
                        Post post = new Post();
                        post.setUser(objects.get(i).getUser());
                        post.setImage(objects.get(i).getImage());
                        post.setCaption(objects.get(i).getCaption());
                        posts.add(post);
                        pAdapter.notifyItemInserted(posts.size()-1);
                    }
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
