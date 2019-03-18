package chielong.pattern.adapter.restructure;

public class TypeAdapter implements ChargingType{

    private ChargingType oldTyoe;
    private ChargingType newType;

    public TypeAdapter(ChargingType oldTyoe, ChargingType newType) {
        this.oldTyoe = oldTyoe;
        this.newType = newType;
    }

    @Override
    public void doCharge() {
        oldTyoe.doCharge();
        System.out.println("转换!");
        newType.doCharge();
    }
}
