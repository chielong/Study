package chielong.pattern.adapter.power;

public class AC220 {
    public int outputAC220V() {
        int out = 220;
        System.out.println("这是ac220-" + out + "V");
        return out;
    }
}
