package com.liam191.clockr;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liam191.clockr.clocking.Clocking;

import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Order clockings
//       - User sets chronological or reverse-chronological ordering
// TODO: Refactor into separate classes.
public class ClockingAdapter extends RecyclerView.Adapter<ClockingAdapter.ClockingViewHolder> {
    private List<Clocking> clockingList;

    public ClockingAdapter(List<Clocking> clockingList) {
        this.clockingList = clockingList;
    }

    public void updateClockingList(List<Clocking> clockingList){
        this.clockingList = clockingList;
        Logger.getAnonymousLogger().log(Level.INFO,"### updateClockingList > "+clockingList);
        notifyDataSetChanged();
    }

    public class ClockingViewHolder extends RecyclerView.ViewHolder {
        public TextView clockingTag;
        public TextView clockingLabel;
        public TextView clockingDescription;
        public TextView clockingStartDate;
        public TextView clockingDuration;
        public ImageView icon;
        public View iconLine;

        public ClockingViewHolder(View itemView) {
            super(itemView);
            this.clockingTag = itemView.findViewById(R.id.tag);
            this.clockingLabel = itemView.findViewById(R.id.label);
            this.clockingDescription = itemView.findViewById(R.id.description);
            this.clockingStartDate = itemView.findViewById(R.id.start_time);
            this.clockingDuration = itemView.findViewById(R.id.duration);
            this.icon = itemView.findViewById(R.id.icon);
            this.iconLine = itemView.findViewById(R.id.icon_line);
        }
    }

    @NonNull
    @Override
    public ClockingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClockingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.clocking_listitem, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ClockingViewHolder holder, int position) {
        Clocking clocking = clockingList.get(position);
        holder.clockingLabel.setText(clocking.label());
        //TODO: Set tag name here
        holder.clockingStartDate.setText(clocking.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        //TODO: Fix i18n warning about concatenating strings in text fields
        //TODO: Format based on hours and minutes
        //      - A duration of 43 minutes would be "43m"
        //      - A duration of 2 hours and 38 minutes would be "2h38m" rather than "258m"
        holder.clockingDuration.setText(clocking.durationInMinutes().toMinutes() +"m");

        if(clocking.description().equals("")){
            // Hide this view if there's nothing to display.
            holder.clockingDescription.setVisibility(View.GONE);
        } else {
            holder.clockingDescription.setText(clocking.description());
        }

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.clockingTag.setTextColor(color);
        holder.icon.setColorFilter(color);
        holder.iconLine.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return clockingList.size();
    }
}
