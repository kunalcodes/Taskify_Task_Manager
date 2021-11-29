package kunal.project.taskify.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import kunal.project.taskify.R;
import kunal.project.taskify.adapters.TasksFragmentPagerAdapter;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView mTvHomeExHeyUser;
    private String userName;
    private FloatingActionButton mBtnHomeExAdd;
    private ImageView mIvHomeExUser;
    private ViewPager2 mViewPagerTaskList;
    private TabLayout mTabsTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userName = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        initViewsAndClickListeners();
        setFragmentPagerAdapter();
    }

    private void setFragmentPagerAdapter() {
        TasksFragmentPagerAdapter pagerAdapter = new TasksFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        mViewPagerTaskList.setAdapter(pagerAdapter);
        new TabLayoutMediator(mTabsTaskList, mViewPagerTaskList,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("UPCOMING");
                            break;
                        case 1:
                            tab.setText("ARCHIVED");
                            break;
                    }
                }
        ).attach();
    }


    private void initViewsAndClickListeners() {
        mIvHomeExUser = findViewById(R.id.ivHomeExUser);
        mTvHomeExHeyUser = findViewById(R.id.tvHomeExHeyUser);
        mTvHomeExHeyUser.setText("Hey " + userName + ",");
        mBtnHomeExAdd = findViewById(R.id.btnHomeExAdd);
        mBtnHomeExAdd.setOnClickListener(this);
        mIvHomeExUser.setOnClickListener(this);
        mTabsTaskList = findViewById(R.id.tabsTaskList);
        mViewPagerTaskList = findViewById(R.id.viewPagerTaskList);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnHomeExAdd:
                Intent createNewTask = new Intent(HomeActivity.this, CreateNewTaskActivity.class);
                startActivity(createNewTask);
                break;
            case R.id.ivHomeExUser:
//                Intent goToProfile = new Intent(getApplicationContext(), ProfileActivity.class);
//                startActivity(goToProfile);
                break;
        }
    }
}