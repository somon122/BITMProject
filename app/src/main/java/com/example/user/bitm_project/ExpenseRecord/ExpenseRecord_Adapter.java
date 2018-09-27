package com.example.user.bitm_project.ExpenseRecord;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.bitm_project.R;

import java.util.ArrayList;

public class ExpenseRecord_Adapter extends ArrayAdapter {

    ArrayList<ExpenseRecord>expenseRecordList = new ArrayList<>();
    Context mContext;

    public ExpenseRecord_Adapter(@NonNull Context context, @NonNull ArrayList<ExpenseRecord>expenseRecordList) {
        super(context, R.layout.expense_record_show_coustom, expenseRecordList);
        this.expenseRecordList = expenseRecordList;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.expense_record_show_coustom,parent,false);

        TextView expenseRecordDate = convertView.findViewById(R.id.expenseRecordDateShow_id);
        TextView expenseRecordDetails = convertView.findViewById(R.id.expenseRecordDetailsShow_id);
        TextView expenseRecordAmount = convertView.findViewById(R.id.expenseRecordAmountShow_id);

        ExpenseRecord expenseRecord = expenseRecordList.get(position);

        expenseRecordDate.setText(expenseRecord.getDateTime());
        expenseRecordDetails.setText(expenseRecord.getExpenseDetails());
        expenseRecordAmount.setText(expenseRecord.getExpenseAmount());

        return convertView;
    }
}
