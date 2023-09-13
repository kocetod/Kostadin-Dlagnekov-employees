package com.project.employees;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.project.employees.dto.PairEmployee;
import com.project.employees.listviewadapter.EmployeeAdapter;

import java.util.List;

public class ResultFragment extends Fragment {
    private CsvDataManager loadCSV;
    private DateManager dateManager;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        ListView resultList = view.findViewById(R.id.result_list);
        Button backButton = view.findViewById(R.id.back_btn);

        loadCSV = new CsvDataManager();
        dateManager = new DateManager();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String file_path = bundle.getString("file_path");
            List<PairEmployee> pairEmployeeList = dateManager.matchingDays(loadCSV.pairCommonProjects(file_path));
            EmployeeAdapter adapter = new EmployeeAdapter(getContext(), pairEmployeeList);
            resultList.setAdapter(adapter);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragmentResult();
            }
        });

        return view;
    }

    private void closeFragmentResult(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .remove(this)
                .commit();
    }
}