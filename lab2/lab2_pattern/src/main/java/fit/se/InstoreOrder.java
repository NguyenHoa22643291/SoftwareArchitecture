package fit.se;

public class InstoreOrder implements Order{
    private int id;
    public InstoreOrder(int id){this.id=id;}
    @Override
    public void process() {
        System.out.println("Don hang TAI QUAY "+ id + "dang duoc xu ly");

    }
}
