package com.sdi.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.sdi.Earthquake;
import com.sdi.earthquakes.R;
import com.sdi.earthquakes.databinding.EarthquakeItemBinding;

public class EqAdapter extends ListAdapter<Earthquake, EqAdapter.EqViewHolder> {

    public static final DiffUtil.ItemCallback<Earthquake> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Earthquake>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Earthquake oldEq, @NonNull Earthquake newEq) {
                    return oldEq.getId().equals(newEq.getId());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Earthquake oldEq, @NonNull Earthquake newEq) {
                    return oldEq.equals(newEq);
                }
            };

    Context context;
    protected EqAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        void onItemClick(Earthquake earthquake);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EqAdapter.EqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EarthquakeItemBinding binding = EarthquakeItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new EqViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EqAdapter.EqViewHolder holder, int position) {
        Earthquake earthquake = getItem(position);
        holder.bind(earthquake);
    }

    class EqViewHolder extends RecyclerView.ViewHolder{

        private final EarthquakeItemBinding binding;

        public EqViewHolder(@NonNull EarthquakeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Earthquake earthquake) {
            binding.magnitudeText.setText(context.getString(R.string.magnitude_format, earthquake.getMagnitude()));
            binding.placeText.setText(earthquake.getPlace());

            binding.getRoot().setOnClickListener( v -> {
                onItemClickListener.onItemClick(earthquake);
            });

            binding.executePendingBindings();
        }
    }

}
