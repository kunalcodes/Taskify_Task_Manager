package kunal.project.taskify.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kunal.project.taskify.R;
import kunal.project.taskify.TaskModel;
import kunal.project.taskify.adapters.UpComingTaskAdapter;
import kunal.project.taskify.utils.TaskItemClickListener;

public class UpcomingTaskFragment extends Fragment implements TaskItemClickListener {

    private ArrayList<TaskModel> taskList = new ArrayList<>();
    private RecyclerView recyclerViewUpcoming;
    private String mUid;
    private UpComingTaskAdapter taskAdapter;
    private TextView mTvHomeFetchingData;
    private DatabaseReference mNode;
    private String mTaskId;

    public UpcomingTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mNode = FirebaseDatabase.getInstance().getReference("Users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewsAndClickListeners(view);
        setRecyclerViewAdapter();
        mTvHomeFetchingData.setVisibility(View.VISIBLE);
        buildRecyclerViewData();
    }



    private void setRecyclerViewAdapter() {
        taskAdapter = new UpComingTaskAdapter(taskList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewUpcoming.setAdapter(taskAdapter);
        recyclerViewUpcoming.setLayoutManager(linearLayoutManager);
    }

    private void buildRecyclerViewData() {
        mNode.child(mUid).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskList.clear();
                String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd @hh:mm a"));
                for (DataSnapshot taskDataSnapshot : snapshot.getChildren()) {
                    if (taskDataSnapshot.getValue() != null) {
                        TaskModel taskModel = taskDataSnapshot.getValue(TaskModel.class);
                        if (taskModel.getDate().compareTo(localDateTime)>=0){
                            taskList.add(taskModel);
                            Collections.sort(taskList, new Comparator<TaskModel>() {
                                @Override
                                public int compare(TaskModel o1, TaskModel o2) {
                                    return o1.getDate().compareToIgnoreCase(o2.getDate());
                                }
                            });
                        }
                    }
                }
                mTvHomeFetchingData.setVisibility(View.INVISIBLE);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mTvHomeFetchingData.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initViewsAndClickListeners(View view) {
        mTvHomeFetchingData = view.findViewById(R.id.tvHomeFetchingDataUpcoming);
        recyclerViewUpcoming = view.findViewById(R.id.recyclerViewUpcoming);
    }

    @Override
    public void onDeleteClicked(int position) {
        mTaskId = taskList.get(position).getTaskId();
        mNode.child(mUid).child(mTaskId).removeValue();
    }

    @Override
    public void onUpdateClicked(int position) {
        TaskModel taskModel = taskList.get(position);
        Intent editTask = new Intent(getContext(), EditTaskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("taskmodel", taskModel);
        editTask.putExtras(bundle);
        startActivity(editTask);
    }
}