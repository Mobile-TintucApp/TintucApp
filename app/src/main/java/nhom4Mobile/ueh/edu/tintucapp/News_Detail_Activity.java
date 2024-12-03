package nhom4Mobile.ueh.edu.tintucapp;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class News_Detail_Activity extends AppCompatActivity {

    private ImageButton btn_back, btn_fav;
    private boolean isFavorite = false; // Trạng thái lưu tin yêu thích

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_detail);

        // Xử lý phần padding với hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Liên kết các nút
        btn_back = findViewById(R.id.btn_back);
        btn_fav = findViewById(R.id.btn_fav);

        // Xử lý nút quay lại
        btn_back.setOnClickListener(v -> onBackPressed());

        // Xử lý nút lưu yêu thích
        btn_fav.setOnClickListener(v -> {
            isFavorite = !isFavorite; // Đổi trạng thái yêu thích
            updateFavoriteIcon(); // Cập nhật màu icon
            String message = isFavorite ? "Đã lưu vào tin yêu thích" : "Đã bỏ khỏi tin yêu thích";
            Toast.makeText(News_Detail_Activity.this, message, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateFavoriteIcon() {
        Drawable drawable = btn_fav.getDrawable();
        if (isFavorite) {
            // Chuyển icon sang màu vàng
            ColorFilter yellowFilter = new PorterDuffColorFilter(
                    getResources().getColor(android.R.color.holo_orange_light),
                    PorterDuff.Mode.SRC_IN);
            drawable.setColorFilter(yellowFilter);
        } else {
            // Khôi phục icon về màu gốc
            drawable.clearColorFilter();
        }
    }
}
