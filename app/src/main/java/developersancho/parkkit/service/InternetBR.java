package developersancho.parkkit.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import developersancho.parkkit.R;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.MessageUtils;
import developersancho.parkkit.utils.NetworkUtils;

public class InternetBR extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AppConstants.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean isConnected = NetworkUtils.isNetworkConnected(context);
            Activity activity = (Activity) context;
            //AppCompatActivity appactivity = (AppCompatActivity) context;
            if (isConnected) {
                //MessageUtils.showAlerterNotification(activity, "SUCCESS", "ÇEVRİMİÇİ");
            } else {
                MessageUtils.showSneakerError(activity, "ERROR", activity.getResources().getString(R.string.offline));
            }
        }
    }
}
