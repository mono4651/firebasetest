package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeamInfo_2 extends AppCompatActivity {
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_info_2);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userId");
        Bundle bundle = new Bundle();
        bundle.putString("userId", userName);

        InsertTeamInfoFragment fragment = new InsertTeamInfoFragment();
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();

                if (itemId == R.id.menu1) {
                    selectedFragment = new InsertTeamInfoFragment();
                } else if (itemId == R.id.menu2) {
                    selectedFragment = new PlayerInsertFragment();
                } else if (itemId == R.id.menu3) {
                    selectedFragment = new ChkTeamInfoFragment();
                }
                else if (itemId == R.id.menu4) {
                    selectedFragment = new TeamSettingFragment();
                    selectedFragment.setArguments(bundle);

                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        setContentView(R.layout.team_info_2);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu1) {
            openFragment(new InsertTeamInfoFragment());
            return true;
        } else if (itemId == R.id.menu2) {
            openFragment(new PlayerInsertFragment());
            return true;
        } else if (itemId == R.id.menu3) {
            openFragment(new ChkTeamInfoFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openFragment(Fragment fragment) {
        currentFragment = fragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // 추가된 코드: 무조건 프래그먼트를 열도록 수정
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // 프래그먼트를 백 스택에 추가합니다.
        transaction.commit();
    }
}