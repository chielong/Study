package chielong.pattern.delegate.simple;

public class Boss {
    public void command(String command , Leader leader) {
        leader.work(command);
    }
}
