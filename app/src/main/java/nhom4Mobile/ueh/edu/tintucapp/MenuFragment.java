package nhom4Mobile.ueh.edu.tintucapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Khởi tạo các thành phần giao diện
        Button btnMenuDN = view.findViewById(R.id.btn_menuDN);
        Button btnMenuTGS = view.findViewById(R.id.btn_menuTGS);
        Button btnMenuDS = view.findViewById(R.id.btn_menuDS);
        Button btnMenuCD = view.findViewById(R.id.btn_menuCD);
        Button btnMenuGYK = view.findViewById(R.id.btn_menuGYK);
        Button btnMenuTT = view.findViewById(R.id.btn_menuTT);

        // Đặt sự kiện click cho nút "Đăng nhập/ Tạo tài khoản"
        btnMenuDN.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        });

        // Đặt sự kiện click cho nút "Tin gắn sao"
        btnMenuTGS.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Tin_gan_sao_Activity.class);
            startActivity(intent);
        });

        // Đặt sự kiện click cho nút "Tin đọc sau"
        btnMenuDS.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Tin_doc_sau_Activity.class);
            startActivity(intent);
        });

        // Đặt sự kiện click cho nút "Thời tiết"
        btnMenuTT.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ThoitietActivity.class);
            startActivity(intent);
        });

        // Đặt sự kiện click cho nút "Cài đặt"
        btnMenuCD.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Setting.class);
            startActivity(intent);
        });
        // Đặt sự kiện click cho nút "Gửi ý kiến"



        return view;
    }
}