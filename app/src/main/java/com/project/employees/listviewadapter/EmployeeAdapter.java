package com.project.employees.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.project.employees.dto.PairEmployee;
import com.project.employees.R;

import java.util.List;

public class EmployeeAdapter extends BaseAdapter {
    private Context context;
    private List<PairEmployee> pairEmployeeList;

    public EmployeeAdapter(Context context, List<PairEmployee> pairEmployeeList) {
        this.context = context;
        this.pairEmployeeList = pairEmployeeList;
    }

    @Override
    public int getCount() {
        return pairEmployeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return pairEmployeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.employee_item, parent, false);
        }

        PairEmployee pairEmployee = (PairEmployee) getItem(position);

        TextView firstEmployee = convertView.findViewById(R.id.empl_one_id);
        TextView secondEmployee = convertView.findViewById(R.id.empl_two_id);
        TextView projectId = convertView.findViewById(R.id.project_id);
        TextView daysWorking = convertView.findViewById(R.id.days_working);

        // Настройте текстовете на TextViews, използвайки данните от pairEmployee
        firstEmployee.setText(String.valueOf(pairEmployee.getFirstEmployee().getEmpID()));
        secondEmployee.setText(String.valueOf(pairEmployee.getSecondEmployee().getEmpID()));
        projectId.setText(String.valueOf(pairEmployee.getProjectId()));
        daysWorking.setText(String.valueOf(pairEmployee.getDaysWorked()));
        return convertView;
    }
}

