package demo.solution.ProjectApp.Concept;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import demo.solution.ProjectApp.MainActivity;
import demo.solution.ProjectApp.R;
import demo.solution.ProjectApp.project.Quize.TestFragment;

public class HomeFragment extends Fragment {

    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;
    private Button idHome;
    private ImageView idConcept;
    private ImageView idProject;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        idHome = view.findViewById(R.id.idHome);
        idConcept = view.findViewById(R.id.idConcept);
        idProject = view.findViewById(R.id.idProject);

        // Handle the Home button click to open TestFragment
        idHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment RoomDataBaseFragment = new RoomDataBaseFragment();
                if (getActivity() != null) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, RoomDataBaseFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        // Handle opening the left drawer
        idConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).openLeftDrawer();
                }
            }
        });

        // Handle opening the right drawer
        idProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).openRightDrawer();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentManager = getChildFragmentManager();  // This is the correct fragment manager
    }
}
