package me.gina.parsetagram;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.action_tl:
                    case R.id.action_create:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Get image from:")
                                .setItems(R.array.image_choice_array, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        switch (which){
                                            case 0: //camera i
                                                Intent i = new Intent(MainActivity.this, CreateActivity.class);
                                                startActivity(i); // brings up the second activity
                                                finish();

                                            case 1: //open gallery
                                                Intent x = new Intent(MainActivity.this, CreateActivity.class);
                                                startActivity(x); // brings up the second activity
                                                finish();


                                        }
                                    }
                                });
                        builder.show();

                }
                return true;
            }
        });

    }






}
