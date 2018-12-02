package developersancho.parkkit.ui.park.parkList;


public interface IParkListNavigator {
    void handleError(Throwable throwable);
    void showNotFoundMessage();
}
