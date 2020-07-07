package ir.vasl.navigationcomponentimpl.view.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        setUpNavigation();
    }

    private void setUpNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
            navHostFragment.getNavController().addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                    switch (destination.getId()) {
                        case R.id.charactersFragment:
                            toolbar.setTitle("Characters");
                            break;

                        case R.id.locationsFragment:
                            toolbar.setTitle("Locations");
                            break;

                        case R.id.episodesFragment:
                            toolbar.setTitle("Episodes");
                            break;
                    }
                }
            });
        }
    }
}