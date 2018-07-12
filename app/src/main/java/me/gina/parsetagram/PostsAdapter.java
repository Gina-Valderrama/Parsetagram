package me.gina.parsetagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import me.gina.parsetagram.model.Post;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView captionTextView;
        public com.parse.ParseImageView imageIV;
        public TextView tvTime;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tvUsername);
            captionTextView = itemView.findViewById(R.id.tvCaption);
            imageIV =  itemView.findViewById(R.id.ivImage);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }



    private List<Post> mPosts;

    public PostsAdapter(List<Post> posts) {
        mPosts = posts;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        Post post = mPosts.get(index);
        holder.nameTextView.setText(post.getUser().getUsername());
        holder.captionTextView.setText(post.getCaption());

        String postTime = getRelativeTimeAgo(post.getCreatedAt());

        holder.tvTime.setText(postTime);

        holder.imageIV.setParseFile(post.getImage());
        holder.imageIV.loadInBackground();

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }


    public String getRelativeTimeAgo(Date date) {
        long dateMillis = date.getTime();
        return DateUtils.getRelativeTimeSpanString(dateMillis,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}
