package developersancho.parkkit.ui.bankatm.bankatmList;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import developersancho.parkkit.BR;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.bankatm.BankAtm;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentBankAtmListBinding;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppLogger;
import developersancho.parkkit.utils.AppUtils;
import developersancho.parkkit.utils.NetworkUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankAtmListFragment extends BaseFragment<FragmentBankAtmListBinding, BankAtmListViewModel>
        implements IBankAtmListNavigator, BankAtmListAdapter.BankAtmAdapterListener {

    private FragmentBankAtmListBinding binding;
    private BankAtmListViewModel viewModel;
    private BankAtmListAdapter adapter;
    private UserLocation userLocation;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bank_atm_list;
    }

    @Override
    public BankAtmListViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this).get(BankAtmListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        adapter = new BankAtmListAdapter(getContext());
        adapter.setBankAtmAdapterListener(this::onBankAtmClicked);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
        subscribeToBankAtmList();
    }

    private void setUp() {
        binding.recyclerViewBankAtm.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewBankAtm.setHasFixedSize(true);
        binding.recyclerViewBankAtm.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewBankAtm.setAdapter(adapter);
    }

    private void subscribeToBankAtmList() {
        userLocation = viewModel.getUserLocation();
        String distance = AppConstants.DISTANCE;
        String lat = String.valueOf(userLocation.getLatitude());
        String lng = String.valueOf(userLocation.getLongitude());

        if (NetworkUtils.isNetworkConnected(getContext())) {
            viewModel.fetchBankAtmInsertRoom(distance, lat, lng).observe(this, bankAtms -> {
                adapter.setBankAtms(bankAtms);
            });
        } else {
            viewModel.fetchBankAtmFromRoom().observe(this, bankAtms -> {
                adapter.setBankAtms(bankAtms);
            });
        }

    }

    @Override
    public void onBankAtmClicked(BankAtm bankAtm) {
        Toast.makeText(getContext(), "Address: " + bankAtm.getAddress(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getContext(), "HATA : " + throwable.getMessage(), Toast.LENGTH_LONG).show();
        AppLogger.e(throwable.getMessage());
    }

    @Override
    public void showNotFoundMessage() {
        AppUtils.showDataNotFoundAlertToUser(getContext());
    }
}
