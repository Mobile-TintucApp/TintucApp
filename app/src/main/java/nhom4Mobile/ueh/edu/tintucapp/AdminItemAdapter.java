package nhom4Mobile.ueh.edu.tintucapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AdminItemAdapter extends RecyclerView.Adapter<AdminItemAdapter.PostViewHolder> {

    private List<Post> itemList;
    private final OnItemClickListener onItemClickListener;

    // Constructor nhận vào danh sách bài viết và listener
    public AdminItemAdapter(List<Post> itemList, OnItemClickListener onItemClickListener) {
        this.itemList = itemList != null ? itemList : new ArrayList<>(); // Đảm bảo không bị null
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout từ item_post.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = itemList.get(position); // Lấy dữ liệu tại vị trí
        holder.bind(post);  // Gắn dữ liệu vào View
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // PostViewHolder giúp hiển thị thông tin của bài viết
    public class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvId;
        private final TextView tvTitle;
        private final TextView tvCategory;
        private final TextView tvStatus;

        public PostViewHolder(View itemView) {
            super(itemView);

            // Ánh xạ View từ item_post.xml
            tvId = itemView.findViewById(R.id.tvId);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            // Xử lý sự kiện khi click vào item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(itemList.get(position), position);
                }
            });
        }

        // Phương thức gắn dữ liệu vào View
        public void bind(Post post) {
            if (post != null) {
                tvId.setText(post.getId() != null ? post.getId() : "N/A");
                tvTitle.setText(post.getTitle() != null ? post.getTitle() : "No Title");
                tvCategory.setText(post.getCategory() != null ? post.getCategory() : "Unknown");
                tvStatus.setText(post.isStatus() ? "Active" : "Inactive");
            }
        }
    }

    // Interface để xử lý sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClick(Post item, int position);
    }
}

