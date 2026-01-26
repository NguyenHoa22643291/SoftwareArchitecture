package fit.se;

//tao cac don hang khac nhau ma khong dung new onlineOrder() hay instoreOrder()

public class OrderFactory {
    public static Order createOrder(String type) {
       // lay id duy nhat tu singleton
        int newID = AppConfig.getInstance().getNextID();
        if (type.equalsIgnoreCase("ONLINE")) {
            return new OnlineOrder(newID);
        }
        else if (type.equalsIgnoreCase("OFFLINE")) {
            return new InstoreOrder(newID);
        }
        else {
            throw new IllegalArgumentException("Loai don hang khong hop le: " + type);
        }
    }
}
