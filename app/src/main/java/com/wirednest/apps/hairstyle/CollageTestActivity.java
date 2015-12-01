package com.wirednest.apps.hairstyle;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.wirednest.apps.hairstyle.fragment.CollageFragment;

import javax.annotation.Nullable;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollageTestActivity extends FragmentActivity {

    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_test);
        ButterKnife.bind(this);

        if (fragmentContainer != null) {
            if (savedInstanceState != null) {
                return;
            }
            CollageFragment dsFragment = new CollageFragment(getBaseContext());
            dsFragment.setArguments(getIntent().getExtras());
//            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, dsFragment).commit();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
