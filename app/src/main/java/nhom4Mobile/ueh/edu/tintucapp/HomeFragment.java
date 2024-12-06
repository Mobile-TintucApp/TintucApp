package nhom4Mobile.ueh.edu.tintucapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private List<Post> postList = new ArrayList<>();
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        FirebaseApp.initializeApp(requireContext());
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.reclyclerview);
        adapter = new NewsAdapter(requireContext(), postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Load data from Firestore
        loadPostsFromFirestore();

        return rootView;
    }

    private void loadPostsFromFirestore() {
        db.collection("posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    error.printStackTrace();
                    return;
                }
                if (snapshots != null) {
                    postList.clear();
                    for (QueryDocumentSnapshot document : snapshots) {
                        Map<String, Object> data = document.getData();
                        Post post = new Post(
                                document.getId(),
                                (String) data.get("title"),
                                (String) data.get("detailContent"),
                                (String) data.get("imageUrl"),
                                (String) data.get("category"),
                                (Boolean) data.get("status")
                        );
                        postList.add(post);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
