package developersancho.parkkit.ui.bankatm.bankatmList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.bankatm.BankAtm;
import developersancho.parkkit.databinding.BankAtmRowItemBinding;
import developersancho.parkkit.ui.base.BaseViewHolder;
import developersancho.parkkit.utils.CommonUtils;

public class BankAtmListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<BankAtm> bankAtmList = new ArrayList<>();
    private BankAtmAdapterListener listener;
    private LayoutInflater layoutInflater;

    public BankAtmListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        BankAtmRowItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.bank_atm_row_item, parent, false);
        return new BankAtmViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return bankAtmList.size();
    }

    public void setBankAtms(List<BankAtm> bankAtms) {
        this.bankAtmList = bankAtms;
        notifyDataSetChanged();
    }

    public void setBankAtmAdapterListener(BankAtmAdapterListener listener) {
        this.listener = listener;
    }

    public interface BankAtmAdapterListener {
        void onBankAtmClicked(BankAtm bankAtm);
    }

    public class BankAtmViewHolder extends BaseViewHolder {
        private BankAtmRowItemBinding binding;

        public BankAtmViewHolder(BankAtmRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(final int position) {
            binding.viewLine.setBackgroundColor(CommonUtils.setRandomMaterialColorByType(mContext, "800"));
            binding.setBankatm(bankAtmList.get(position));
            binding.executePendingBindings();
            binding.cardBankAtm.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onBankAtmClicked(bankAtmList.get(position));
                }
            });
        }
    }
}
