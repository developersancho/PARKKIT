package developersancho.parkkit.ui.menu.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import developersancho.parkkit.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<ExpandedMenuModel> mListDataHeader;

    public long getChildId(int i, int i2) {
        return (long) i2;
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public ExpandableListAdapter(Context context, List<ExpandedMenuModel> list) {
        this.mContext = context;
        this.mListDataHeader = list;
    }

    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    public int getChildrenCount(int i) {
        return ((ExpandedMenuModel) this.mListDataHeader.get(i)).getSubMenu().size();
    }

    public Object getGroup(int i) {
        return this.mListDataHeader.get(i);
    }

    public Object getChild(int i, int i2) {
        return ((ExpandedMenuModel) this.mListDataHeader.get(i)).getSubMenu().get(i2);
    }

    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        ExpandedMenuModel expandedMenuModel = (ExpandedMenuModel) getGroup(i);
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.nav_menu_item, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.submenu);
        ImageView imageView = (ImageView) view.findViewById(R.id.iconimage);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.img_asagi_yukari_ok);
        textView.setText(expandedMenuModel.getIconName());
        imageView2.setImageResource(expandedMenuModel.getExpandedIcon());
        textView.setContentDescription(expandedMenuModel.getAccessibilityId());
        textView.setTag(expandedMenuModel.getAccessibilityId());
        imageView.setImageResource(expandedMenuModel.getIconImg());
        return view;
    }

    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        ExtendedSubMenuModel extendedSubMenuModel = (ExtendedSubMenuModel) getChild(i, i2);
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.nav_submenu_item, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.submenu);
        textView.setText(extendedSubMenuModel.getIconName());
        textView.setContentDescription(extendedSubMenuModel.getAccessibilityId());
        textView.setTag(extendedSubMenuModel.getAccessibilityId());
        return view;
    }
}
