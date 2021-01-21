package com.aucegypt.ispend;

import android.graphics.Color;
import android.util.Log;
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
    private entryRecyclerViewClickListener listener;

    public entryRecyclerAdapter(ArrayList<EntryItem> entryList, entryRecyclerViewClickListener listener)
    {
        this.entryList = entryList;
        this.listener = listener;
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView titleText;
        private TextView notesText;
        private TextView dateText;
        private TextView timeText;
        private TextView methodText;
        private TextView valueText;
        private TextView storeText;
        private TextView currencyText;

        public viewHolder(final View view)
        {
            super(view);
            titleText = view.findViewById(R.id.entryTitle);
            notesText = view.findViewById(R.id.entryNotes);
            dateText = view.findViewById(R.id.entryDate);
            timeText = view.findViewById(R.id.entryTime);
            methodText = view.findViewById(R.id.entryMethodPay);
            valueText = view.findViewById(R.id.entryValue);
            storeText = view.findViewById(R.id.entryStore);
            currencyText = view.findViewById(R.id.entryCurrency);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
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
        String date = entryList.get(position).getDate();
        String time = entryList.get(position).getTime();
        String method = entryList.get(position).getMethod();
        double value = entryList.get(position).getValue();
        String expSave = entryList.get(position).getExpSav();
        String store = entryList.get(position).getStore();

        holder.titleText.setText(title);
        holder.notesText.setText(notes);
        holder.dateText.setText(date);
        holder.timeText.setText(time);
        holder.methodText.setText(method);
        holder.storeText.setText(store);

        if (expSave.equals("exp")) {
            holder.valueText.setTextColor(Color.parseColor("#B55C5C"));
            holder.currencyText.setTextColor(Color.parseColor("#B55C5C"));
            value = value * -1.0;
        }
        else if (expSave.equals("save")) {
            holder.valueText.setTextColor(Color.parseColor("#5CB56E"));
            holder.currencyText.setTextColor(Color.parseColor("#5CB56E"));
        }

        holder.valueText.setText(valueOf(value));
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public interface entryRecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
