package nhom4Mobile.ueh.edu.tintucapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchhFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<Post> postList = new ArrayList<>();
    private FirebaseFirestore db;
    private EditText editSearch;
    private Button btnSearchh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchh, container, false);

        // RecyclerView setup
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Khởi tạo danh sách postList
        postList = new ArrayList<>();
        adapter = new SearchAdapter(postList, getContext());
        recyclerView.setAdapter(adapter);
        // Firebase instance
        db = FirebaseFirestore.getInstance();

        // Find search-related views
        editSearch = view.findViewById(R.id.editSearch);
        btnSearchh = view.findViewById(R.id.btnSearchh);

        // Search button click handler
        btnSearchh.setOnClickListener(v -> {
            String query = editSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                searchPosts(query); // Call search function when search button is clicked
            }
        });
        return view;
    }


    private void searchPosts(String query) {
        Log.d("SearchFragment", "Bắt đầu tìm kiếm với từ khóa: " + query);

        db.collection("posts")
                .orderBy("title") // Có thể thay đổi field nếu cần
                .startAt(query)
                .endAt(query + "\uf8ff")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        postList.clear(); // Xóa kết quả tìm kiếm cũ
                        int foundCount = 0; // Đếm số lượng kết quả tìm thấy

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post post = document.toObject(Post.class);

                            // Kiểm tra xem bài viết có chứa từ khóa không
                            if (post.getCategory().contains(query) ||
                                    post.getDetailContent().contains(query) ||
                                    post.getTitle().contains(query)) {
                                postList.add(post); // Thêm bài viết vào danh sách
                                foundCount++; // Tăng số lượng kết quả tìm thấy
                            }
                        }

                        // Log số lượng bài viết tìm thấy
                        Log.d("SearchFragment", "Tìm thấy " + foundCount + " bài viết.");

                        // Cập nhật giao diện
                        adapter.notifyDataSetChanged();

                        // Thêm log nếu không có kết quả nào
                        if (foundCount == 0) {
                            Log.d("SearchFragment", "Không tìm thấy bài viết nào.");
                        }
                    } else {
                        Log.e("SearchFragment", "Lỗi khi truy vấn: ", task.getException());
                    }
                });
    }

}