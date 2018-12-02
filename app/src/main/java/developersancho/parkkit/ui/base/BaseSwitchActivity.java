package developersancho.parkkit.ui.base;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.polyak.iconswitch.IconSwitch;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import developersancho.parkkit.R;
import developersancho.parkkit.utils.AppConstants;

@SuppressLint("Registered")
public abstract class BaseSwitchActivity extends AppCompatActivity implements IconSwitch.CheckedChangeListener,
        ValueAnimator.AnimatorUpdateListener {

    private int[] toolbarColors;
    private int[] statusBarColors;
    private ValueAnimator statusBarAnimator;
    private Interpolator contentInInterpolator;
    private Interpolator contentOutInterpolator;
    private Point revealCenter;
    private Window window;

    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract Toolbar getToolbar();

    public abstract IconSwitch getIconSwitch();

    public abstract @IdRes
    int getContainerViewId();

    //public abstract void changeContentVisibility();

    public abstract Fragment getListFragment();

    public abstract Fragment getMapFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initUI();
        if (savedInstanceState == null)
            changeFragment(getListFragment());
    }

    private void initUI() {
        window = getWindow();
        initColors();
        initAnimationRelatedFields();
        getToolbar();
        getIconSwitch();
        getIconSwitch().setCheckedChangeListener(this);
        updateColors(false);
    }

    public void changeFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out)
                .replace(getContainerViewId(), targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void updateColors(boolean animated) {
        int colorIndex = getIconSwitch().getChecked().ordinal();
        getToolbar().setBackgroundColor(toolbarColors[colorIndex]);
        if (animated) {
            switch (getIconSwitch().getChecked()) {
                case LEFT:
                    statusBarAnimator.reverse();
                    break;
                case RIGHT:
                    statusBarAnimator.start();
                    break;
            }
            revealToolbar();
        } else {
            window.setStatusBarColor(statusBarColors[colorIndex]);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animator) {
        if (animator == statusBarAnimator) {
            int color = (Integer) animator.getAnimatedValue();
            window.setStatusBarColor(color);
        }
    }

    @Override
    public void onCheckChanged(IconSwitch.Checked current) {
        updateColors(true);
        changeContentVisibility();
    }

    private void changeContentVisibility() {
        switch (getIconSwitch().getChecked()) {
            case LEFT:
                changeFragment(getListFragment());
                break;
            case RIGHT:
                changeFragment(getMapFragment());
                break;
        }
    }

    private void revealToolbar() {
        getIconSwitch().getThumbCenter(revealCenter);
        moveFromSwitchToToolbarSpace(revealCenter);
        ViewAnimationUtils.createCircularReveal(getToolbar(),
                revealCenter.x, revealCenter.y,
                getIconSwitch().getHeight(), getToolbar().getWidth())
                .setDuration(AppConstants.DURATION_COLOR_CHANGE_MS)
                .start();
    }

    private void initAnimationRelatedFields() {
        revealCenter = new Point();
        statusBarAnimator = createArgbAnimator(
                statusBarColors[IconSwitch.Checked.LEFT.ordinal()],
                statusBarColors[IconSwitch.Checked.RIGHT.ordinal()]);
        contentInInterpolator = new OvershootInterpolator(0.5f);
        contentOutInterpolator = new DecelerateInterpolator();
    }

    private void initColors() {
        toolbarColors = new int[IconSwitch.Checked.values().length];
        statusBarColors = new int[toolbarColors.length];
        toolbarColors[IconSwitch.Checked.LEFT.ordinal()] = color(R.color.colorPrimary);
        statusBarColors[IconSwitch.Checked.LEFT.ordinal()] = color(R.color.colorPrimary);
        toolbarColors[IconSwitch.Checked.RIGHT.ordinal()] = color(R.color.colorAccent);
        statusBarColors[IconSwitch.Checked.RIGHT.ordinal()] = color(R.color.colorAccent);
    }

    private ValueAnimator createArgbAnimator(int leftColor, int rightColor) {
        ValueAnimator animator = ValueAnimator.ofArgb(leftColor, rightColor);
        animator.setDuration(AppConstants.DURATION_COLOR_CHANGE_MS);
        animator.addUpdateListener(this);
        return animator;
    }

    private void moveFromSwitchToToolbarSpace(Point point) {
        point.set(point.x + getIconSwitch().getLeft(), point.y + getIconSwitch().getTop());
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
}
