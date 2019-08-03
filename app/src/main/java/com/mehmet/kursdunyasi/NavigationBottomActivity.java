package com.mehmet.kursdunyasi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mehmet.kursdunyasi.Fragments.LessonsFragment;
import com.mehmet.kursdunyasi.Fragments.MainPageFragment;
import com.mehmet.kursdunyasi.Fragments.ExamsFragment;
import com.mehmet.kursdunyasi.Fragments.ProfileFragment;

public class NavigationBottomActivity extends AppCompatActivity {

    private ChangeFragment changeFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment.change(new MainPageFragment());
                    return true;

                case R.id.navigation_exams:
                    changeFragment.change(new ExamsFragment());
                    return true;

                case R.id.navigation_notifications:
                    return true;

                case R.id.navigation_mylessons:
                    changeFragment.change(new LessonsFragment());
                    return true;

                case R.id.navigation_profile:
                    changeFragment.change(new ProfileFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bottom);

        changeFragment=new ChangeFragment(NavigationBottomActivity.this);
        changeFragment.change(new MainPageFragment());

        /*changeFragment=new ChangeFragment(NavigationBottomActivity.this);
        changeFragment.change(new ExamsFragment());*/

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
