package developersancho.parkkit.ui.menu.navigation;

import java.util.ArrayList;
import java.util.List;

public class ExpandedMenuModel {
    String accessibilityId = "";
    int expandedIcon = 0;
    int iconImg = -1;
    String iconName = "";
    boolean isExpanded;
    List<ExtendedSubMenuModel> subMenu = new ArrayList();

    public String getAccessibilityId() {
        return this.accessibilityId;
    }

    public void setAccessibilityId(String str) {
        this.accessibilityId = str;
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    public void setExpanded(boolean z) {
        this.isExpanded = z;
    }

    public int getExpandedIcon() {
        return this.expandedIcon;
    }

    public void setExpandedIcon(int i) {
        this.expandedIcon = i;
    }

    public String getIconName() {
        return this.iconName;
    }

    public void setIconName(String str) {
        this.iconName = str;
    }

    public int getIconImg() {
        return this.iconImg;
    }

    public void setIconImg(int i) {
        this.iconImg = i;
    }

    public List<ExtendedSubMenuModel> getSubMenu() {
        return this.subMenu;
    }

    public void setSubMenu(List<ExtendedSubMenuModel> list) {
        this.subMenu = list;
    }
}
