package nhom4Mobile.ueh.edu.tintucapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private List<Post> postList;

    public NewsAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_list, parent, false);
        return new NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Post post = postList.get(position);

        // Set the title
        holder.txt_title.setText(post.getTitle());

        // Set the body (use detailContent for now)
        holder.txt_body.setText(post.getDetailContent());

        // Set the category
        holder.txt_category.setText(post.getCategory());

        // Load the image using Glide
        Glide.with(context)
                .load(post.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background) // Replace with actual placeholder
                .into(holder.txt_img);

        // Optional: Handle bookmark button click
        holder.bookmark_button.setOnClickListener(v -> {
            // Handle bookmark action here
        });
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    // Update data in the adapter
    public void update(List<Post> newPostList) {
        postList.clear();
        postList.addAll(newPostList);
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_body, txt_category;
        ImageView txt_img;
        ImageButton bookmark_button;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_body = itemView.findViewById(R.id.txt_body);
            txt_category = itemView.findViewById(R.id.txt_category);
            txt_img = itemView.findViewById(R.id.txt_image);
            bookmark_button = itemView.findViewById(R.id.bookmark_button);
        }
    }

}
