package developersancho.parkkit.ui.menu.navigation;

public class ExtendedSubMenuModel {
    String accessibilityId = "";
    int clickId = -1;
    String iconName = "";

    public String getAccessibilityId() {
        return this.accessibilityId;
    }

    public void setAccessibilityId(String str) {
        this.accessibilityId = str;
    }

    public String getIconName() {
        return this.iconName;
    }

    public void setIconName(String str) {
        this.iconName = str;
    }

    public int getClickId() {
        return this.clickId;
    }

    public void setClickId(int i) {
        this.clickId = i;
    }
}
