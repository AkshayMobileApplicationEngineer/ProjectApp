
package demo.solution.ProjectApp;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;


import demo.solution.ProjectApp.Concept.HomeFragment;
import demo.solution.ProjectApp.Concept.NotificationFragment;
import demo.solution.ProjectApp.Concept.ProfileFragment;
import demo.solution.ProjectApp.Concept.RazorpayFragment;
import demo.solution.ProjectApp.Concept.RetrofitFragment;
import demo.solution.ProjectApp.project.ELearnigApp.ELearningFragment;
import demo.solution.ProjectApp.project.Quize.UI.QuizFragment;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationViewL;
    private NavigationView navigationViewR;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationViewL = findViewById(R.id.nav_viewL);
        navigationViewR = findViewById(R.id.nav_viewR);
        fragmentManager = getSupportFragmentManager();

        // Set up navigation view listeners
        setupNavigationViewListener(navigationViewL);
        setupNavigationViewListener(navigationViewR);

        // Load the default fragment if no saved instance state exists
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }

    private void setupNavigationViewListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    fragment = new HomeFragment();
                } else if (itemId == R.id.nav_profile) {
                    fragment = new ProfileFragment();
                } else if (itemId == R.id.nav_notifications) {
                    fragment = new NotificationFragment();
                } else if (itemId == R.id.nav_quiz) {
                    fragment = new QuizFragment();
                }
                else if(itemId == R.id.nav_Retrofit) {
                fragment=new RetrofitFragment();
                }
                else if(itemId == R.id.nav_Razorpay) {
                    fragment=new RazorpayFragment();
                } else if (itemId==R.id.nav_ELeaning) {
                    fragment= new ELearningFragment();
                } else {
                    return false;
                }
                if (fragment != null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

                // Close the appropriate drawer
                drawerLayout.closeDrawer(navigationView);

                return true;
            }
        });
    }
}