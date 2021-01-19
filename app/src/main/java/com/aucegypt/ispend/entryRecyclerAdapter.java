package com.aucegypt.ispend;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class entryRecyclerAdapter extends RecyclerView.Adapter<entryRecyclerAdapter.viewHolder> {

    private ArrayList<EntryItem> entryList;

    public entryRecyclerAdapter(ArrayList<EntryItem> entryList)
    {
        this.entryList = entryList;
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        private TextView titleText;
        private TextView notesText;
        private TextView dateTimeText;
        private TextView methodText;
        private TextView valueText;

        public viewHolder(final View view)
        {
            super(view);
            titleText = view.findViewById(R.id.entryTitle);
            notesText = view.findViewById(R.id.entryNotes);
            dateTimeText = view.findViewById(R.id.entryDate);
            methodText = view.findViewById(R.id.entryMethodPay);
            valueText = view.findViewById(R.id.entryValue);
        }

    }

    @NonNull
    @Override
    public entryRecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expenses_list_items, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull entryRecyclerAdapter.viewHolder holder, int position) {
        String title = entryList.get(position).getTitle();
        String notes = entryList.get(position).getNotes();
        String dateTime = entryList.get(position).getDateTime();
        String method = entryList.get(position).getMethod();
        double value = entryList.get(position).getValue();
        String expSave = entryList.get(position).getExpSav();

        holder.titleText.setText(title);
        holder.notesText.setText(notes);
        holder.dateTimeText.setText(dateTime);
        holder.methodText.setText(method);
        holder.valueText.setText(valueOf(value));

        if (expSave == "exp")
            holder.valueText.setTextColor(Color.parseColor("#B55C5C"));
        else if (expSave == "save")
            holder.valueText.setTextColor(Color.parseColor("#5CB56E"));
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
