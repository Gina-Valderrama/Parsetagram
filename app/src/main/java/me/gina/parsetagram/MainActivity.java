package me.gina.parsetagram;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import me.gina.parsetagram.model.Post;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    ArrayList<Post> posts;
    PostsAdapter pAdapter;
    RecyclerView rvPosts;
    private final int REQUEST_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.placeholder, new PostsTLFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.action_tl:
                        FragmentTransaction f1 = getSupportFragmentManager().beginTransaction();
                        // Replace the contents of the container with the new fragment
                        f1.replace(R.id.placeholder, new PostsTLFragment());
                        // or ft.add(R.id.your_placeholder, new FooFragment());
                        // Complete the changes added above
                        f1.commit();
                        return true;

                    case R.id.action_create:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Get image from:")
                                .setItems(R.array.image_choice_array, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        switch (which){
                                            case 0: //camera i
                                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                                CreateFragment cFrag = CreateFragment.newInstance(1, "imageType");
                                                ft.replace(R.id.placeholder, cFrag);
                                                ft.commit();

                                            case 1: //open gallery
                                                FragmentTransaction f2 = getSupportFragmentManager().beginTransaction();

                                                CreateFragment cFrag2 = CreateFragment.newInstance(2, "imageType");
                                                f2.replace(R.id.placeholder, cFrag2);
                                                f2.commit();

                                        }
                                    }
                                });
                        builder.show();
                        return true;


                }
                return true;
            }
        });

    }





}
