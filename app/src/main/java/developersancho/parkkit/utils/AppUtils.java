package developersancho.parkkit.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import de.cketti.mailto.EmailIntentBuilder;
import developersancho.parkkit.R;

public class AppUtils {

    public static void sendFeedback(Context context) {
        boolean success = EmailIntentBuilder.from(context)
                .to("developersancho@gmail.com")
                .subject(context.getString(R.string.app_name))
                .start();

        if (!success) {
            MessageUtils.showSnackBar(getActivity(context), "İşlem Başarısız.");
        }
    }

    public static void shareApplication(Context context) {
        final String appPackageName = context.getPackageName();

        Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity(context))
                .setType("text/plain")
                .setText(context.getString(R.string.app_google_play_store_link) + appPackageName)
                .getIntent();
        if (shareIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(shareIntent);
        }
    }

    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }

    public static AppCompatActivity getActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof ContextWrapper) {
            if (context instanceof AppCompatActivity) {
                return (AppCompatActivity) context;
            } else {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }

    public static void showGPSDisabledAlertToUser(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.gpsmessage))
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.gpsOK),
                        (dialog, id) -> {
                            Intent callGPSSettingIntent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            context.startActivity(callGPSSettingIntent);
                        });
        /*alertDialogBuilder.setNegativeButton("Cancel",
                (dialog, id) -> dialog.cancel());*/
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    public static void showDataNotFoundAlertToUser(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.datanotfoundmessage))
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.datanotfoundOK),
                        (dialog, id) -> {
                            getActivity(context).finish();
                            getActivity(context).overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        });
        /*alertDialogBuilder.setNegativeButton("Cancel",
                (dialog, id) -> dialog.cancel());*/
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
}
