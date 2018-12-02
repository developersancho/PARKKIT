package developersancho.parkkit.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.irozon.sneaker.Sneaker;
import com.tapadoo.alerter.Alerter;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import developersancho.parkkit.R;

public class MessageUtils {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@NonNull Context context, @StringRes int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK",
                (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public static void showSnackBar(Activity activity, String message) {
        Snackbar snack = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        view.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));

        TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(activity.getResources().getColor(R.color.whitelilac));

        if (tv != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        snack.show();
    }

    public static void showAlerterNotification(Activity activity, String title, String message) {
        Alerter.create(activity)
                .setTitle(title)
                .setText(message)
                .setTitleTypeface(Typeface.createFromAsset(activity.getAssets(), "font/sourcesanspro_regular.ttf"))
                .setTextTypeface(Typeface.createFromAsset(activity.getAssets(), "font/chalkboard.ttf"))
                .setBackgroundColorRes(R.color.colorAccent)
                .setDuration(4000)
                .setIcon(R.drawable.alerter_ic_notifications)
                .show();
    }

    public static void showSneakerError(Activity activity, String title, String message) {
        Sneaker.with(activity)
                .setTitle(title)
                .setMessage(message)
                .setDuration(4000) // Time duration to show
                .autoHide(true) // Auto hide Sneaker view
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
                .setTypeface(Typeface.createFromAsset(activity.getAssets(), "font/" + "chalkboard.ttf")) // Custom font for title and message
                .sneakError();

    }

    public static void showSneakerSuccess(Activity activity, String title, String message) {
        Sneaker.with(activity)
                .setTitle(title)
                .setMessage(message)
                .setDuration(4000) // Time duration to show
                .autoHide(true) // Auto hide Sneaker view
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
                .setTypeface(Typeface.createFromAsset(activity.getAssets(), "font/" + "chalkboard.ttf")) // Custom font for title and message
                .sneakSuccess();

    }

    public static void showSneakerWarning(Activity activity, String title, String message) {
        Sneaker.with(activity)
                .setTitle(title)
                .setMessage(message)
                .setDuration(4000) // Time duration to show
                .autoHide(true) // Auto hide Sneaker view
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
                .setTypeface(Typeface.createFromAsset(activity.getAssets(), "font/" + "chalkboard.ttf")) // Custom font for title and message
                .sneakWarning();

    }

    public static void showAlertView(AppCompatActivity activity, String title, String message) {
        AlertView alert = new AlertView(title, message, AlertStyle.DIALOG);
        alert.addAction(new AlertAction("OK", AlertActionStyle.POSITIVE, action -> {

        }));
        alert.addAction(new AlertAction("SAVE", AlertActionStyle.NEGATIVE, action -> {

        }));


        alert.show(activity);
    }
}
