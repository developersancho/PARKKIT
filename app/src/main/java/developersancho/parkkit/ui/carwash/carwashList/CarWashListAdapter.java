package developersancho.parkkit.ui.carwash.carwashList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.carwash.CarWash;
import developersancho.parkkit.databinding.CarWashRowItemBinding;
import developersancho.parkkit.ui.base.BaseViewHolder;
import developersancho.parkkit.utils.CommonUtils;

public class CarWashListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<CarWash> carWashList = new ArrayList<>();
    private CarWashAdapterListener listener;
    private LayoutInflater layoutInflater;

    public CarWashListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CarWashRowItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.car_wash_row_item, parent, false);
        return new CarWashViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return carWashList.size();
    }

    public void setCarWashes(List<CarWash> carWashes) {
        this.carWashList = carWashes;
        notifyDataSetChanged();
    }

    public void setCarWashAdapterListener(CarWashAdapterListener listener) {
        this.listener = listener;
    }

    public interface CarWashAdapterListener {
        void onCarWashClicked(CarWash carWash);
    }

    public class CarWashViewHolder extends BaseViewHolder {
        private CarWashRowItemBinding binding;

        public CarWashViewHolder(CarWashRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(final int position) {
            binding.viewLine.setBackgroundColor(CommonUtils.setRandomMaterialColorByType(mContext, "800"));
            binding.setCarwash(carWashList.get(position));
            binding.executePendingBindings();
            binding.cardCarWash.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCarWashClicked(carWashList.get(position));
                }
            });
        }
    }
}
