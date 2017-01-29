package swetabh.suman.com.imageloadingapp.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import swetabh.suman.com.imageloadingapp.BuildConfig;
import swetabh.suman.com.imageloadingapp.R;
import swetabh.suman.com.imageloadingapp.data.model.ResponseModel;
import swetabh.suman.com.imageloadingapp.ui.adapter.GridImageAdapter;
import swetabh.suman.com.imageloadingapp.ui.customclass.InfiniteScrollListener;
import swetabh.suman.com.imageloadinglibrary.ImageCache;
import swetabh.suman.com.imageloadinglibrary.ImageFetcher;
import swetabh.suman.com.imageloadinglibrary.Utils;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_RESULT_CODE = 123;
    private Toolbar toolbar;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageGridFragment mFragment;
    private FloatingActionButton fab;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) {
            Utils.enableStrictMode();
        }
        initToolbar();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count % 3 == 0)
                    Toast.makeText(MainActivity.this, "Thinking what to implement on this. :D ", LENGTH_SHORT).show();
                else if (count % 3 == 1)
                    Toast.makeText(MainActivity.this, "Still thinking what to implement :) ", LENGTH_SHORT).show();
                else if (count % 3 == 2)
                    Toast.makeText(MainActivity.this, "I told you I am thinking :D ", LENGTH_SHORT).show();

                count++;
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            RequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_RESULT_CODE);
        }
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            mFragment = new ImageGridFragment();
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frame_content, mFragment, TAG);
            ft.commit();

        }

       /* if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frame_content, new ImageGridFragment(), TAG);
            ft.commit();
        } else {
            RequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_RESULT_CODE);
        }*/
        //initilizeUi();
        //initializeImageLoadingLibrary();
        //setLayoutManagerToRecyclerView();

       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do refreshing
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        if (savedInstanceState == null) {
            callWebService();
        } else {
            adapter.addData(mList);
        }*/
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null) {
            if (mFragment.getCurrentAnimator() != null) {
                mFragment.getCurrentAnimator().cancel();
            } else {
                this.finish();
            }
        } else {
            this.finish();
        }
    }

    private void RequestPermission(@NonNull String permission, int resultCode) {

        ActivityCompat.requestPermissions(this, new String[]{permission}, resultCode);

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_RESULT_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mFragment.callWebService();
            } else {
                RequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_RESULT_CODE);
            }
        }
    }

}
