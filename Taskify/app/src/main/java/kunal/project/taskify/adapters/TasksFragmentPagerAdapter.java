package kunal.project.taskify.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import kunal.project.taskify.views.ArchivedTaskFragment;
import kunal.project.taskify.views.UpcomingTaskFragment;

public class TasksFragmentPagerAdapter extends FragmentStateAdapter {

    public TasksFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UpcomingTaskFragment();
            case 1:
                return new ArchivedTaskFragment();
        }
        return new UpcomingTaskFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
