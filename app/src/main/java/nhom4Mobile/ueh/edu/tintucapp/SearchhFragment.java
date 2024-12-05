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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchh, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchAdapter(postList, getContext());
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // Hướng dẫn thêm menu vào Fragment
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option_search, menu); // Đảm bảo menu là option_search.xml
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_search) {
            // Khi người dùng nhấn vào icon search, SearchView sẽ hiện ra
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Cấu hình SearchView
    private void setupSearchView(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPosts(query); // Gọi hàm tìm kiếm khi submit
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    postList.clear();
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    private void searchPosts(String query) {
        db.collection("posts")
                .orderBy("title")
                .startAt(query)
                .endAt(query + "\uf8ff")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        postList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post post = document.toObject(Post.class);
                            // Lọc các trường: category, detail, title Content
                            if (post.getCategory().contains(query) || post.getDetailContent().contains(query) || post.getTitle().contains(query)) {
                                postList.add(post);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("SearchFragment", "Error getting documents: ", task.getException());
                    }
                });
    }
}