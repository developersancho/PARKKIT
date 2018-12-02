package developersancho.parkkit.ui.gasstation.gasList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.gasstation.Gas;
import developersancho.parkkit.databinding.GasRowItemBinding;
import developersancho.parkkit.ui.base.BaseViewHolder;
import developersancho.parkkit.utils.CommonUtils;

public class GasListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<Gas> gasList = new ArrayList<>();
    private GasAdapterListener listener;
    private LayoutInflater layoutInflater;

    public GasListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        GasRowItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.gas_row_item, parent, false);
        return new GasViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return gasList.size();
    }

    public void setGases(List<Gas> gases) {
        this.gasList = gases;
        notifyDataSetChanged();
    }

    public void setGasAdapterListener(GasAdapterListener listener) {
        this.listener = listener;
    }

    public interface GasAdapterListener {
        void onGasClicked(Gas gas);
    }

    public class GasViewHolder extends BaseViewHolder {
        private GasRowItemBinding binding;

        public GasViewHolder(GasRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(final int position) {
            binding.viewLine.setBackgroundColor(CommonUtils.setRandomMaterialColorByType(mContext, "800"));
            binding.setGas(gasList.get(position));
            binding.executePendingBindings();
            binding.cardGas.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onGasClicked(gasList.get(position));
                }
            });
        }
    }
}
