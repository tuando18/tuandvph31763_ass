package com.dovantuan.tuandvph31763_ass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.dovantuan.tuandvph31763_ass.Fragment.Frag_Home;
import com.dovantuan.tuandvph31763_ass.Fragment.Frag_Introduce;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final int FRAG_HOME = 0;
    static final int FRAG_GIOITHIEU = 1;
    int mCurrentFrag;
    DrawerLayout mDrawer;
    Toolbar mToobar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = findViewById(R.id.layout_drawer);
        mToobar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToobar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToobar, R.string.open, R.string.close);
        mDrawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        navigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new Frag_Home());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            if (mCurrentFrag != FRAG_HOME) {
                replaceFragment(new Frag_Home());
                mCurrentFrag = FRAG_HOME;
            }
        } else if (id == R.id.gioithieu) {
            if (mCurrentFrag != FRAG_GIOITHIEU) {
                replaceFragment(new Frag_Introduce());
                mCurrentFrag = FRAG_GIOITHIEU;
            }
        } else if (id == R.id.logout) {
            thongbao();
        }
        getSupportActionBar().setTitle(item.getTitle());
        mDrawer.close();
        return true;
    }

    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.commit();
    }

    void thongbao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thông Báo");
        builder.setIcon(R.drawable.thongbao);
        builder.setMessage("Bạn có muốn đăng xuất hay không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}