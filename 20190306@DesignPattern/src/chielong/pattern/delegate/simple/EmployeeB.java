package chielong.pattern.delegate.simple;

public class EmployeeB implements IEmployee {
    @Override
    public void doWork() {
        System.out.println("i am b , i can fire");
    }
}
