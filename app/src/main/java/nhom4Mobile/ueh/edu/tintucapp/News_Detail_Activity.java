package nhom4Mobile.ueh.edu.tintucapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class News_Detail_Activity extends AppCompatActivity {

    private ImageButton btn_back, btn_fav, btn_save;
    private TextView titleText, categoryText, contentText;
    private ImageView txt_img;
    private boolean isFavorite = false; // Trạng thái lưu tin yêu thích
    private boolean isSaved = false; // Trạng thái lưu tin "save"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        // Liên kết các nút
        btn_back = findViewById(R.id.btnBack);
        btn_fav = findViewById(R.id.btn_fav);
        titleText = findViewById(R.id.txtTitle);
        txt_img = findViewById(R.id.txt_image);
        categoryText = findViewById(R.id.txtStatus);
        contentText = findViewById(R.id.txtBody);
        btn_save = findViewById(R.id.btn_save);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String category = intent.getStringExtra("category");
        String imageUrl = intent.getStringExtra("imageUrl");  // Nhận imageUrl
        String content = intent.getStringExtra("content");

        // Gán dữ liệu vào các TextView
        titleText.setText(title);
        categoryText.setText(category);
        contentText.setText(content);

        // Sử dụng Glide để tải ảnh từ URL vào ImageView
        Glide.with(this)
                .load(imageUrl) // Đưa URL ảnh vào Glide
                .placeholder(R.drawable.ic_launcher_background) // Ảnh placeholder
                .into(txt_img); // Hiển thị ảnh vào ImageView

        // Xử lý nút quay lại
        btn_back.setOnClickListener(v -> onBackPressed());

        // Xử lý nút lưu yêu thích
        btn_fav.setOnClickListener(v -> {
            isFavorite = !isFavorite; // Đổi trạng thái yêu thích
            updateFavoriteIcon(); // Cập nhật màu icon

            // Thêm log để kiểm tra trạng thái
            Log.d("News_Detail_Activity", "Favorite state: " + isFavorite);

            String message = isFavorite ? "Đã lưu vào tin yêu thích" : "Đã bỏ khỏi tin yêu thích";
            Toast.makeText(News_Detail_Activity.this, message, Toast.LENGTH_SHORT).show();
        });

        // Xử lý nút lưu "save"
        btn_save.setOnClickListener(v -> {
            isSaved = !isSaved; // Đổi trạng thái lưu
            updateSaveIcon(); // Cập nhật icon của btn_save

            // Thêm log để kiểm tra trạng thái
            Log.d("News_Detail_Activity", "Saved state: " + isSaved);

            String message = isSaved ? "Đã lưu tin vào danh sách" : "Đã bỏ khỏi danh sách lưu";
            Toast.makeText(News_Detail_Activity.this, message, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            btn_fav.setImageResource(R.drawable.baseline_star_24);  // Đổi icon yêu thích
        } else {
            btn_fav.setImageResource(R.drawable.baseline_star_border_24);  // Đặt lại icon yêu thích
        }
    }

    private void updateSaveIcon() {
        if (isSaved) {
            btn_save.setImageResource(R.drawable.baseline_turned_in_24);  // Đổi icon "save"
        } else {
            btn_save.setImageResource(R.drawable.baseline_turned_in_not_24);  // Đặt lại icon "save"
        }
    }
}
