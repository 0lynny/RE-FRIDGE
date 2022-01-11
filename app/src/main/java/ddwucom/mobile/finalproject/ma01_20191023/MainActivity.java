package ddwucom.mobile.finalproject.ma01_20191023;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        mBottomNV = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentHome()).commit();

        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentHome()).commit();
                        break;
                    case R.id.navigation_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentFoodInfo()).commit();
                        break;
                    case R.id.navigation_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentShop()).commit();
                        break;
                    case R.id.navigation_4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentInsertFood()).commit();
                        break;
                    case R.id.navigation_5:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentOnline()).commit();
                        break;

                }
                return true;
            }
        });

    }

}