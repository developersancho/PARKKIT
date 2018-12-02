package developersancho.parkkit.ui.carservice.carserviceList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.carservice.CarService;
import developersancho.parkkit.databinding.CarServiceRowItemBinding;
import developersancho.parkkit.ui.base.BaseViewHolder;
import developersancho.parkkit.utils.CommonUtils;

public class CarServiceListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<CarService> carServiceList = new ArrayList<>();
    private CarServiceAdapterListener listener;
    private LayoutInflater layoutInflater;

    public CarServiceListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CarServiceRowItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.car_service_row_item, parent, false);
        return new CarServiceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return carServiceList.size();
    }

    public void setCarServices(List<CarService> carServices) {
        this.carServiceList = carServices;
        notifyDataSetChanged();
    }

    public void setCarServiceAdapterListener(CarServiceAdapterListener listener) {
        this.listener = listener;
    }

    public interface CarServiceAdapterListener {
        void onCarServiceClicked(CarService carService);
    }

    public class CarServiceViewHolder extends BaseViewHolder {
        private CarServiceRowItemBinding binding;

        public CarServiceViewHolder(CarServiceRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(final int position) {
            binding.viewLine.setBackgroundColor(CommonUtils.setRandomMaterialColorByType(mContext, "800"));
            binding.setCarservice(carServiceList.get(position));
            binding.executePendingBindings();
            binding.cardCarService.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCarServiceClicked(carServiceList.get(position));
                }
            });
        }
    }
}
