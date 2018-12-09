package developersancho.parkkit.ui.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import developersancho.parkkit.R;
import developersancho.parkkit.BR;
import developersancho.parkkit.databinding.ActivityMenuBinding;
import developersancho.parkkit.service.GpsBR;
import developersancho.parkkit.service.InternetBR;
import developersancho.parkkit.ui.base.BaseActivity;
import developersancho.parkkit.ui.menu.navigation.ExpandableListAdapter;
import developersancho.parkkit.ui.menu.navigation.ExpandedMenuModel;
import developersancho.parkkit.ui.menu.navigation.ExtendedSubMenuModel;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppUtils;
import developersancho.parkkit.utils.GpsUtils;
import developersancho.parkkit.utils.NavigationUtils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import static developersancho.parkkit.utils.AppConstants.MAX_SCALE;

public class MenuActivity extends BaseActivity<ActivityMenuBinding, MenuViewModel>
        implements DrawerLayout.DrawerListener {

    private ActivityMenuBinding binding;
    private MenuViewModel viewModel;
    private InternetBR internetBR = new InternetBR();
    private GpsBR gpsBR = new GpsBR();
    private List<ExpandedMenuModel> listDataHeader;
    private ExpandableListAdapter menuAdapter;
    private int lastExpandedPosition = -1;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    public MenuViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.colorBlue);
        binding = getViewDataBinding();
        NavigationUtils.initialize(getApplicationContext(), MenuActivity.this);
        setUpUI();
        registerReceiverInternet();
        registerReceiverGps();
        if (savedInstanceState == null)
            switchFragment(new MenuFragment());
    }

    private void setUpUI() {
        initDrawer();
    }

    private void initDrawer() {
        int i = 0;
        binding.drawerLayout.setScrimColor(0);
        binding.drawerLayout.setStatusBarBackgroundColor(0);
        binding.drawerLayout.setDrawerElevation(0.0f);
        binding.drawerLayout.addDrawerListener(this);
        prepareListData();
        menuAdapter = new ExpandableListAdapter(this, listDataHeader);
        binding.navigationmenu.setGroupIndicator(null);
        binding.navigationmenu.setAdapter(this.menuAdapter);
        while (i < menuAdapter.getGroupCount()) {
            binding.navigationmenu.expandGroup(i);
            i++;
        }
        // 0 dan başlıo sıralama bizde checkin 1. sırada...
        //binding.navigationmenu.collapseGroup(1);
        binding.navigationmenu.setOnGroupCollapseListener(new GroupCollapse());
        binding.navigationmenu.setOnGroupExpandListener(new GroupExpand());

        // child menu
        binding.navigationmenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int pos = ((ExtendedSubMenuModel) ((ExpandedMenuModel) listDataHeader.get(groupPosition)).getSubMenu().get(childPosition)).getClickId();
                //Toast.makeText(MenuActivity.this, String.valueOf(pos) + " group - main", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        // main menu
        binding.navigationmenu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int icon = ((ExpandedMenuModel) listDataHeader.get(groupPosition)).getIconImg();
                if (icon == R.drawable.icon_home) {
                    if (binding.drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer((int) GravityCompat.START);
                    }
                } else if (icon == R.drawable.icon_contact_card) {
                    AppUtils.sendFeedback(MenuActivity.this);
                } else if (icon == R.drawable.icon_share) {
                    AppUtils.shareApplication(MenuActivity.this);
                } else if (icon == R.drawable.icon_rate) {
                    AppUtils.openPlayStoreForApp(MenuActivity.this);
                }
                //Toast.makeText(MainActivity.this, String.valueOf(groupPosition) + " group", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void prepareListData() {
        // Anasayfa
        listDataHeader = new ArrayList<>();
        ExpandedMenuModel expandedMenuModel = new ExpandedMenuModel();
        expandedMenuModel.setIconName(getString(R.string.menu_home));
        expandedMenuModel.setIconImg(R.drawable.icon_home);
        expandedMenuModel.setExpandedIcon(0);
        expandedMenuModel.setAccessibilityId("label_home");
        listDataHeader.add(expandedMenuModel);

        // Bize Ulaşın
        expandedMenuModel = new ExpandedMenuModel();
        expandedMenuModel.setIconName(getString(R.string.menu_contact));
        expandedMenuModel.setIconImg(R.drawable.icon_contact_card);
        expandedMenuModel.setExpandedIcon(0);
        expandedMenuModel.setAccessibilityId("label_contact");
        listDataHeader.add(expandedMenuModel);

        // Uyg. Paylaş
        expandedMenuModel = new ExpandedMenuModel();
        expandedMenuModel.setIconName(getString(R.string.menu_share_app));
        expandedMenuModel.setIconImg(R.drawable.icon_share);
        expandedMenuModel.setExpandedIcon(0);
        expandedMenuModel.setAccessibilityId("label_share_app");
        listDataHeader.add(expandedMenuModel);

        // Uyg. Oyla
        expandedMenuModel = new ExpandedMenuModel();
        expandedMenuModel.setIconName(getString(R.string.menu_rate_app));
        expandedMenuModel.setIconImg(R.drawable.icon_rate);
        expandedMenuModel.setExpandedIcon(0);
        expandedMenuModel.setAccessibilityId("label_rate_app");
        listDataHeader.add(expandedMenuModel);

    }

    public void switchFragment(Fragment fragment) {
        try {
            if (binding.drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
                binding.drawerLayout.closeDrawer((int) GravityCompat.START);
            }
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.setCustomAnimations(R.anim.transculent_enter, R.anim.transculent_exit, R.anim.transculent_pop_enter, R.anim.transculent_pop_exit);
            beginTransaction.replace(R.id.content_frame, fragment);
            beginTransaction.addToBackStack(fragment.getClass().getName());
            beginTransaction.commit();
        } catch (Exception e) {
            Log.d("StackTrace", "Throwable Stack Trace", e);
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MenuActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!GpsUtils.isGpsEnabled(MenuActivity.this)) {
            AppUtils.showGPSDisabledAlertToUser(MenuActivity.this);
        }
        //DebugDB.getAddressLog();
    }

    private void registerReceiverGps() {
        IntentFilter filter = new IntentFilter(AppConstants.GPS_ACTION);
        registerReceiver(gpsBR, filter);
    }

    private void registerReceiverInternet() {
        IntentFilter filter = new IntentFilter(AppConstants.CONNECTIVITY_ACTION);
        registerReceiver(internetBR, filter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        NavigationUtils.setFree();
        super.onDestroy();
        unregisterReceiver(internetBR);
        unregisterReceiver(gpsBR);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        float value = MAX_SCALE - (0.100000024f * slideOffset);
        binding.contentFrame.setScaleX(value);
        binding.contentFrame.setScaleY(value);
        binding.contentFrame.setTranslationX(slideOffset * ((float) drawerView.getWidth()));
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        binding.contentFrame.setScaleX(MAX_SCALE);
        binding.contentFrame.setScaleY(MAX_SCALE);
        binding.contentFrame.setTranslationX(0.0f);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    public DrawerLayout getDrawerLayout() {
        return binding.drawerLayout;
    }

    private class GroupCollapse implements ExpandableListView.OnGroupCollapseListener {
        @Override
        public void onGroupCollapse(int groupPosition) {
            if (((ExpandedMenuModel) listDataHeader.get(groupPosition)).getSubMenu().size() > 0) {
                ((ExpandedMenuModel) listDataHeader.get(groupPosition)).setExpandedIcon(R.drawable.ic_menu_asagi_ok);
                menuAdapter.notifyDataSetChanged();
            }
        }
    }

    private class GroupExpand implements ExpandableListView.OnGroupExpandListener {
        @Override
        public void onGroupExpand(int groupPosition) {
            if (!(lastExpandedPosition == -1 || groupPosition == lastExpandedPosition)) {
                binding.navigationmenu.collapseGroup(lastExpandedPosition);
                for (int i = 0; i < listDataHeader.size(); i++) {
                    if (((ExpandedMenuModel) listDataHeader.get(i)).getSubMenu().size() == 0) {
                        ((ExpandedMenuModel) listDataHeader.get(i)).setExpandedIcon(0);
                    } else {
                        ((ExpandedMenuModel) listDataHeader.get(i)).setExpandedIcon(R.drawable.ic_menu_asagi_ok);
                    }
                }
            }
            if (((ExpandedMenuModel) listDataHeader.get(groupPosition)).isExpanded()) {
                ((ExpandedMenuModel) listDataHeader.get(groupPosition)).setExpandedIcon(R.drawable.ic_menu_asagi_ok);
            } else if (((ExpandedMenuModel) listDataHeader.get(groupPosition)).getSubMenu().size() > 0) {
                ((ExpandedMenuModel) listDataHeader.get(groupPosition)).setExpandedIcon(R.drawable.ic_menu_yukari_ok);
            }
            menuAdapter.notifyDataSetChanged();
            lastExpandedPosition = groupPosition;
        }
    }
}
