package chielong.pattern.adapter.restructure;

public class AndroidType implements ChargingType{
    @Override
    public void doCharge() {
        System.out.println("安卓充电头");
    }
}
