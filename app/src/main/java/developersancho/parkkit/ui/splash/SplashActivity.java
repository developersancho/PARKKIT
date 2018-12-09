package developersancho.parkkit.ui.splash;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import developersancho.parkkit.BR;
import developersancho.parkkit.R;
import developersancho.parkkit.databinding.ActivitySplashBinding;
import developersancho.parkkit.ui.base.BaseActivity;
import developersancho.parkkit.ui.main.MainActivity;
import developersancho.parkkit.ui.menu.MenuActivity;
import developersancho.parkkit.utils.AppConstants;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements ISplashNavigator {

    private SplashViewModel viewModel;
    private ActivitySplashBinding binding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        viewModel = new SplashViewModel(getApplication());
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = getViewDataBinding();
        viewModel.setNavigator(this);
        setUpPermissions();
    }

    private void setUpPermissions() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // farklı bir thread üzerinde gerçekleştiriyoruz.. main threadde app crash olmasın diye
                            new Handler().postDelayed(() -> {
                                Intent intent = MenuActivity.newIntent(SplashActivity.this);
                                startActivity(intent);
                                finish();
                            }, AppConstants.DELAY_TIME);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Toast.makeText(getApplicationContext(), "Permission Denied!!", Toast.LENGTH_SHORT).show();
                    }
                }).check();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

}
