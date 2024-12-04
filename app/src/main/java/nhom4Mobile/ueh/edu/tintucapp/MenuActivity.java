package nhom4Mobile.ueh.edu.tintucapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Khai bao
        Button btnTinDocSau = findViewById(R.id.btn_menuDS);
        Button btnTinGanSao = findViewById(R.id.btn_menuTGS);
        Button btnDangNhap = findViewById(R.id.btn_menuDN);
        Button btnCaiDat = findViewById(R.id.btn_menuCD);
        Button btnGuiYKien = findViewById(R.id.btn_menuGYK);
        Button btnThoiTiet = findViewById(R.id.btn_menuTT);

        //Xử lý sự kiện các nút
        btnTinDocSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Activity TinDocSauActivity
                Intent intent = new Intent(MenuActivity.this, Tin_doc_sau_Activity.class);
                startActivity(intent);
            }
        });

        btnTinGanSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Activity TinDocSauActivity
                Intent intent = new Intent(MenuActivity.this, Tin_gan_sao_Activity.class);
                startActivity(intent);
            }
        });

        btnThoiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Activity TinDocSauActivity
                Intent intent = new Intent(MenuActivity.this, ThoitietActivity.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Activity TinDocSauActivity
                Intent intent = new Intent(MenuActivity.this, Login.class);
                startActivity(intent);
            }
        });

    }
}