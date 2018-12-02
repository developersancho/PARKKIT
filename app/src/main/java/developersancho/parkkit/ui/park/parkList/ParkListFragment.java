package developersancho.parkkit.ui.park.parkList;


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
import developersancho.parkkit.data.model.api.parking.Park;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentParkListBinding;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppLogger;
import developersancho.parkkit.utils.AppUtils;
import developersancho.parkkit.utils.NetworkUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParkListFragment extends BaseFragment<FragmentParkListBinding, ParkListViewModel>
        implements IParkListNavigator, ParkListAdapter.ParkAdapterListener {

    private FragmentParkListBinding binding;
    private ParkListAdapter adapter;
    private ParkListViewModel viewModel;
    private UserLocation userLocation;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_park_list;
    }

    @Override
    public ParkListViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(ParkListViewModel.class);
        return viewModel;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        adapter = new ParkListAdapter(getContext());
        adapter.setParkAdapterListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
        subscribeToParkList();
    }


    private void setUp() {
        binding.recyclerViewPark.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPark.setHasFixedSize(true);
        binding.recyclerViewPark.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewPark.setAdapter(adapter);
    }

    private void subscribeToParkList() {
        userLocation = viewModel.getUserLocation();
        String distance = AppConstants.DISTANCE;
        String lat = String.valueOf(userLocation.getLatitude());
        String lng = String.valueOf(userLocation.getLongitude());

        if (NetworkUtils.isNetworkConnected(getContext())) {
            viewModel.fetchParksInsertRoom(distance, lat, lng).observe(this, parks -> {
                adapter.setParks(parks);
            });
        } else {
            viewModel.fetchParksFromRoom().observe(this, parks -> {
                adapter.setParks(parks);
            });
        }

    }

    @Override
    public void onParkClicked(Park park) {
        Toast.makeText(getContext(), "Adress : " + park.getAddress(), Toast.LENGTH_LONG).show();
    }
}
