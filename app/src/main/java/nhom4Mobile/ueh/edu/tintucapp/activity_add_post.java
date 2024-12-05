package nhom4Mobile.ueh.edu.tintucapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import android.content.Intent;

public class activity_add_post extends AppCompatActivity {

    private EditText etID, etTitle, etDetailContent, etImage, etCategory; // Thêm EditText cho category
    private Button btnSave;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        // Ánh xạ các EditText và Button
        etID = findViewById(R.id.etId);  // ID bài viết
        etTitle = findViewById(R.id.etTitle);
        etDetailContent = findViewById(R.id.etDetailContent);
        etImage = findViewById(R.id.etImage);
        etCategory = findViewById(R.id.etCategory); // Ánh xạ EditText category
        btnSave = findViewById(R.id.btnSave);

        // Khi trang AddPost được mở, lấy ID tiếp theo từ Firestore
        loadNextPostID();

        // Sự kiện nhấn nút "Lưu"
        btnSave.setOnClickListener(v -> savePost());
    }

    // Phương thức để lấy ID bài viết tiếp theo từ Firestore
    private void loadNextPostID() {
        db.collection("posts").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot documents = task.getResult();
                        int nextPostId = documents.size() + 1; // Số lượng bài viết hiện có + 1
                        String postId = String.format("%03d", nextPostId);
                        // Điền ID tiếp theo vào etID
                        etID.setText(String.valueOf("P"+postId));
                    } else {
                        Toast.makeText(this, "Lỗi khi tải dữ liệu bài viết", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Phương thức lưu bài viết
    private void savePost() {
        String id = etID.getText().toString().trim();  // Lấy ID bài viết
        String title = etTitle.getText().toString().trim();
        String detailContent = etDetailContent.getText().toString().trim();
        String image = etImage.getText().toString().trim();
        String category = etCategory.getText().toString().trim(); // Lấy category từ người dùng nhập

        // Kiểm tra dữ liệu nhập
        if (title.isEmpty() || detailContent.isEmpty() || image.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            // Tạo đối tượng Post với ID mới
            Post newPost = new Post(id, title, detailContent, image, category, true);

            // Lưu bài viết vào Firestore
            db.collection("posts")
                    .document(id) // Sử dụng ID bài viết để làm document ID
                    .set(newPost)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Bài viết đã được lưu thành công", Toast.LENGTH_SHORT).show();

                        // Trả lại kết quả cho AdminPage
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish(); // Quay lại AdminPage
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi lưu bài viết", Toast.LENGTH_SHORT).show());
        }
    }
}
