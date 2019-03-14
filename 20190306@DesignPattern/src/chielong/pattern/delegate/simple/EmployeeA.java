package chielong.pattern.delegate.simple;

public class EmployeeA implements IEmployee {
    @Override
    public void doWork() {
        System.out.println("i am a , i can do-cut");
    }
}
