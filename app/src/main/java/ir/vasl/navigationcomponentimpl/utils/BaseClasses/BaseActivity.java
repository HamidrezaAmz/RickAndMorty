package ir.vasl.navigationcomponentimpl.utils.BaseClasses;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutResourceId();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //  Fixed Portrait orientation
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()
                && getSupportFragmentManager().getFragments().size() <= 1)
            finishAffinity();
        super.onBackPressed();
    }

}
