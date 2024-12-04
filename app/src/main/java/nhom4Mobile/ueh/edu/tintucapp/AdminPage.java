package nhom4Mobile.ueh.edu.tintucapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminItemAdapter adapter;
    private List<Post> itemList;
    private Button btnAdd, btnEdit, btnDelete, logout;
    private Post selectedPost;
    private int selectedPosition = -1; // Vị trí của item được chọn

    // Khai báo FirebaseFirestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        // Ánh xạ RecyclerView và Button
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        logout = findViewById(R.id.logoutAdBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // Cấu hình LayoutManager

        // Khởi tạo adapter và gán vào RecyclerView
        itemList = new ArrayList<>();
        adapter = new AdminItemAdapter(itemList, (item, position) -> {
            selectedPost = item;
            selectedPosition = position;
            Toast.makeText(this, "Đã chọn: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        // Tải dữ liệu từ Firestore khi trang mở
        loadDataFromFirestore();

        // Sự kiện cho nút "Thêm"
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(AdminPage.this, activity_add_post.class);
            startActivityForResult(intent, 1); // Gọi activity_add_post và đợi kết quả
        });

        // Sự kiện cho nút "Sửa"
        btnEdit.setOnClickListener(v -> {
            if (selectedPost != null) {
                Intent intent = new Intent(AdminPage.this, EditPostActivity.class);
                // Truyền thông tin bài viết qua Intent
                intent.putExtra("postId", selectedPost.getId());
                intent.putExtra("postTitle", selectedPost.getTitle());
                intent.putExtra("postDetailContent", selectedPost.getDetailContent());
                intent.putExtra("postImage", selectedPost.getImageUrl());
                intent.putExtra("postCategory", selectedPost.getCategory());
                intent.putExtra("postStatus", selectedPost.isStatus());
                startActivityForResult(intent, 2); // Gọi activity chỉnh sửa bài viết
            } else {
                Toast.makeText(this, "Vui lòng chọn bài viết cần sửa", Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(v -> {
            if (selectedPost != null) {
                // Cập nhật trạng thái của bài viết thành false (đánh dấu là đã xóa)
                deletePost(selectedPost.getId());
            } else {
                Toast.makeText(this, "Vui lòng chọn bài viết cần xóa", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Sau khi quay lại từ activity_add_post, tải lại dữ liệu từ Firestore
            loadDataFromFirestore();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            // Sau khi quay lại từ EditPostActivity, tải lại dữ liệu từ Firestore
            loadDataFromFirestore();
        }
    }

    private void loadDataFromFirestore() {
        // Truy vấn collection "posts" từ Firestore
        db.collection("posts") // Lấy collection "posts" từ Firestore
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        itemList.clear(); // Xóa dữ liệu cũ trước khi thêm mới

                        // Lấy danh sách bài viết từ Firestore
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();  // Lấy ID của bài viết
                            String title = document.getString("title");
                            String detailContent = document.getString("detailContent");
                            String imageUrl = document.getString("imageUrl");
                            String category = document.getString("category");
                            boolean status = document.getBoolean("status");

                            // Tạo đối tượng Post và thêm vào danh sách
                            Post post = new Post(id, title, detailContent, imageUrl, category, status);
                            itemList.add(post);
                        }

                        // Cập nhật RecyclerView
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Lỗi khi tải dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void deletePost(String postId) {
        // Cập nhật trạng thái của bài viết thành false (xóa)
        db.collection("posts").document(postId)
                .update("status", false)  // Cập nhật trạng thái bài viết thành false
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Bài viết đã được xóa", Toast.LENGTH_SHORT).show();
                    loadDataFromFirestore(); // Tải lại dữ liệu sau khi xóa
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi xóa bài viết", Toast.LENGTH_SHORT).show());
    }
}
