package fit.se;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("--- HỆ THỐNG QUẢN LÝ ĐƠN HÀNG ---");

        // Tạo đơn hàng mà không cần quan tâm bên trong làm gì
        Order order1 = OrderFactory.createOrder("ONLINE");
        Order order2 = OrderFactory.createOrder("OFFLINE");
        Order order3 = OrderFactory.createOrder("ONLINE");

        // Chạy xử lý
        order1.process();
        order2.process();
        order3.process();

        // Kiểm tra xem Singleton có hoạt động không (ID tiếp theo sẽ là bao nhiêu?)
        System.out.println("ID tiếp theo sẽ là: " + AppConfig.getInstance().getNextID());
    }
}