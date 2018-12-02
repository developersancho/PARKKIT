package developersancho.parkkit.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppUtils;
import developersancho.parkkit.utils.GpsUtils;
import developersancho.parkkit.utils.MessageUtils;

public class GpsBR extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AppConstants.GPS_ACTION.equals(intent.getAction())) {
            boolean isGpsEnabled = GpsUtils.isGpsEnabled(context);
            if (!isGpsEnabled)
                AppUtils.showGPSDisabledAlertToUser(context);
        }
    }
}
