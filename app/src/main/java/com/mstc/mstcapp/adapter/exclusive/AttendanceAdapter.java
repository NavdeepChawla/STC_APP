package com.mstc.mstcapp.adapter.exclusive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.exclusive.AttendanceObject;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceView> {
    private List<AttendanceObject> attendanceObjects_list;
    public static int mExpandedPosition=-1;
    public static int previousExpandedPosition=-1;

    public AttendanceAdapter(List<AttendanceObject> attendanceObjects_list) {
        this.attendanceObjects_list=attendanceObjects_list;
    }

    @NonNull
    @Override
    public AttendanceView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance,parent,false);
        return new AttendanceView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceView holder, final int position) {
        //String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        holder.attendanceTitle.setText(attendanceObjects_list.get(position).getAttendanceTitle());
        List<String> contents=attendanceObjects_list.get(position).getAttendanceContent();
        int size=contents.size();
        holder.attendanceContent.setText(getData(size,contents));
        final boolean isExpanded = position==mExpandedPosition;
        holder.attendanceContent.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        if (isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });

    }
    private String getData(int n,List<String> arr) {
        StringBuilder names= new StringBuilder();
        for(int i=0;i<n;i++){
            if(i<n-1)
                names.append(arr.get(i)).append("\n");
            else
                names.append(arr.get(i));
        }
        return names.toString();
    }

    @Override
    public int getItemCount() {
        return attendanceObjects_list.size();
    }

    public static class AttendanceView extends RecyclerView.ViewHolder{

        TextView attendanceTitle,attendanceContent;

        public AttendanceView(@NonNull View itemView) {
            super(itemView);
            attendanceTitle=itemView.findViewById(R.id.attendance_title);
            attendanceContent=itemView.findViewById(R.id.attendance_content);

            attendanceContent.setVisibility(View.GONE);

        }
    }
}
