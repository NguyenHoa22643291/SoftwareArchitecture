package fit.se;

//singleton class chua 1 instance duy nhat
public class AppConfig {
    //tao bien privte static
    private static AppConfig instance;
    private int currentID  =1;

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();

        }
        return instance;
    }
    public int getNextID() {
        return currentID++; // tra ve id hien tai va tang len 1
    }
}
