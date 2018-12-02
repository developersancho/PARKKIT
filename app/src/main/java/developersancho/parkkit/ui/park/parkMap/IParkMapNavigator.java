package developersancho.parkkit.ui.park.parkMap;

public interface IParkMapNavigator {
    void checkPermission();

    void handleError(Throwable throwable);
}
