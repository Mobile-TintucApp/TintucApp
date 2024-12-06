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
        db.collection("posts")
                .orderBy("title") // Can change this to a field you want to sort by
                .startAt(query)
                .endAt(query + "\uf8ff") // Firebase range query for search
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        postList.clear(); // Clear previous search results
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post post = document.toObject(Post.class);
                            // Filter posts based on title, category, or content
                            if (post.getCategory().contains(query) ||
                                    post.getDetailContent().contains(query) ||
                                    post.getTitle().contains(query)) {
                                postList.add(post); // Add matching post to list
                            }
                        }
                        adapter.notifyDataSetChanged(); // Notify adapter of data change
                    } else {
                        Log.e("SearchFragment", "Error getting documents: ", task.getException());
                    }
                });
    }
}