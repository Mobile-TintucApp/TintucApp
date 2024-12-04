package nhom4Mobile.ueh.edu.tintucapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditPostActivity extends AppCompatActivity {

    private EditText etID, etTitle, etDetailContent, etImage, etCategory;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        // Ánh xạ các EditText và Button
        etID = findViewById(R.id.etId);
        etTitle = findViewById(R.id.etTitle);
        etDetailContent = findViewById(R.id.etDetailContent);
        etImage = findViewById(R.id.etImage);
        etCategory = findViewById(R.id.etCategory);
        btnSave = findViewById(R.id.btnSave);

        // Nhận dữ liệu từ AdminPage
        Intent intent = getIntent();
        postId = intent.getStringExtra("postId"); // Gán giá trị cho postId
        etID.setText(postId); // Hiển thị ID bài viết
        etTitle.setText(intent.getStringExtra("postTitle"));
        etDetailContent.setText(intent.getStringExtra("postDetailContent"));
        etImage.setText(intent.getStringExtra("postImage"));
        etCategory.setText(intent.getStringExtra("postCategory"));

        // Sự kiện nhấn nút "Lưu"
        btnSave.setOnClickListener(v -> savePost());
    }

    private void savePost() {
        String title = etTitle.getText().toString().trim();
        String detailContent = etDetailContent.getText().toString().trim();
        String image = etImage.getText().toString().trim();
        String category = etCategory.getText().toString().trim();

        // Kiểm tra dữ liệu nhập
        if (postId == null || postId.isEmpty()) {
            Toast.makeText(this, "Không thể xác định bài viết cần chỉnh sửa", Toast.LENGTH_SHORT).show();
            return;
        }

        if (title.isEmpty() || detailContent.isEmpty() || image.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật bài viết trên Firestore
        db.collection("posts").document(postId)
                .update("title", title, "detailContent", detailContent, "imageUrl", image, "category", category)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Bài viết đã được cập nhật", Toast.LENGTH_SHORT).show();
                    // Trả lại kết quả cho AdminPage
                    setResult(RESULT_OK);
                    finish(); // Quay lại AdminPage
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi cập nhật bài viết", Toast.LENGTH_SHORT).show());
    }
}
