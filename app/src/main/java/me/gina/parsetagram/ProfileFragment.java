package me.gina.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import me.gina.parsetagram.model.Post;


public class ProfileFragment extends Fragment {

    ArrayList<Post> posts;
    ProfAdapter pAdapter;
    RecyclerView rvPosts;
    TextView userName;
    TextView numPosts;
    Button logoutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        rvPosts = view.findViewById(R.id.rvPostsProf);
        logoutButton =  view.findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                final Intent i = new Intent(view.getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        userName = view.findViewById(R.id.profUser);
        numPosts = view.findViewById(R.id.postNums);
        userName.setText(ParseUser.getCurrentUser().getUsername());
        posts = new ArrayList<>();
        pAdapter = new ProfAdapter(posts);
        rvPosts.setAdapter(pAdapter);

        rvPosts.setLayoutManager(new LinearLayoutManager(view.getContext()));


        populateProf();
    }

    private void populateProf(){
        pAdapter.clear();
        final Post.Query postsQuery = new Post.Query().createdBy(ParseUser.getCurrentUser());
        postsQuery.orderByDescending("createdAt");

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> profps, ParseException e) {
                if (e == null){
                    posts.clear();
                    posts.addAll(profps);
                    numPosts.setText(Integer.toString(posts.size()));
                    pAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });


    }
}
