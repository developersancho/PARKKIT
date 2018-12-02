package developersancho.parkkit.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import androidx.databinding.BindingAdapter;

public class BindingUtils {

    /*@BindingAdapter({"adapter"})
    public static void addParkItems(RecyclerView recyclerView, List<Park> parks) {
        ParkListAdapter adapter = (ParkListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(parks);
        }
    }*/

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }

    @BindingAdapter("distance")
    public static void setText(TextView view, double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        view.setText(String.valueOf(df.format(Double.valueOf(value))) + " km");
    }

}
