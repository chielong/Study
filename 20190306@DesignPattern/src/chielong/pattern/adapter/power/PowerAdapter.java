package chielong.pattern.adapter.power;

public class PowerAdapter implements DC5 {

    private AC220 ac220;

    public PowerAdapter(AC220 ac220) {
        this.ac220 = ac220;
    }

    @Override
    public int outputDC5V() {
        int adapterInput = ac220.outputAC220V();
        int adapterOutput = adapterInput / 44;
        System.out.println("输入AC:" + adapterInput + "V,->PowerAdapter->输出DC:" + adapterOutput + "V");
        return adapterOutput;
    }
}
