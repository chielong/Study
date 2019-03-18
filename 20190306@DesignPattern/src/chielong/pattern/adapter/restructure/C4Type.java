package chielong.pattern.adapter.restructure;

public class C4Type implements ChargingType{
    @Override
    public void doCharge() {
        System.out.println("C4 充电头");
    }
}
