package fit.se;

public class OnlineOrder implements Order {
    private int id;
    public OnlineOrder(int id) {this.id = id;}
    @Override
    public void process() {
        System.out.println("Don hang ONLINE "+ id+ "dang duoc xu ly");
    }
}
