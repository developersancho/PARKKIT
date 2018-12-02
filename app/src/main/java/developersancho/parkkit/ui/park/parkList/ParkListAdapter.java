package developersancho.parkkit.ui.park.parkList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.parking.Park;
import developersancho.parkkit.databinding.ParkRowItemBinding;
import developersancho.parkkit.ui.base.BaseViewHolder;
import developersancho.parkkit.utils.CommonUtils;

public class ParkListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<Park> parkList = new ArrayList<>();
    private ParkAdapterListener listener;
    private LayoutInflater layoutInflater;

    public ParkListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ParkRowItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.park_row_item, parent, false);
        return new ParkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return parkList.size();
    }

    public void setParks(List<Park> parks) {
        this.parkList = parks;
        notifyDataSetChanged();
    }

    public void setParkAdapterListener(ParkAdapterListener listener) {
        this.listener = listener;
    }

    public interface ParkAdapterListener {
        void onParkClicked(Park park);
    }

    public class ParkViewHolder extends BaseViewHolder {
        private ParkRowItemBinding binding;

        public ParkViewHolder(ParkRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(final int position) {
            binding.viewLine.setBackgroundColor(CommonUtils.setRandomMaterialColorByType(mContext, "800"));
            binding.setPark(parkList.get(position));
            binding.executePendingBindings();
            binding.cardPark.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onParkClicked(parkList.get(position));
                }
            });
        }
    }
}
