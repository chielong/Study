package chielong.pattern.proxy.staticproxy;

public class Father {
    private Son mySon;

    public Father(Son mySon) {
        this.mySon = mySon;
    }

    public void findLove() {
        System.out.println("dad's arrange");
        mySon.findLove();
        System.out.println("dad's cry");
    }
}
