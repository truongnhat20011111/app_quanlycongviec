package com.example.myapplication1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Adapter.RecyclerViewProjectAdapter;
import com.example.myapplication1.Model.Project;
import com.example.myapplication1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class favoriteProjectFragment extends Fragment {


    ArrayList<Project> favoriteProject = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public favoriteProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favoriteProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static favoriteProjectFragment newInstance(String param1, String param2) {
        favoriteProjectFragment fragment = new favoriteProjectFragment();
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
        View view = inflater.inflate(R.layout.fragment_favorite_project, container, false);

        String IDuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("Favorite").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteProject.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Project project = snap.getValue(Project.class);
                    if(project.getIDUserOwn().equals(IDuser)){
                        favoriteProject.add(project);
                    }
                }

                RecyclerView recyclerView = view.findViewById(R.id.recycle_view_show_favorite_project);

                RecyclerViewProjectAdapter adapterFavorite = new RecyclerViewProjectAdapter(getContext(), favoriteProject);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                recyclerView.setAdapter(adapterFavorite);
                recyclerView.hasFixedSize();
                adapterFavorite.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}