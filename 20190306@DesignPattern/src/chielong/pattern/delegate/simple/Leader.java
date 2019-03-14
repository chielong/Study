package chielong.pattern.delegate.simple;

import java.util.HashMap;
import java.util.Map;

public class Leader {
    private static Map<String , IEmployee> register = new HashMap<String , IEmployee>();

    static {
        register.put("eat" , new EmployeeA());
        register.put("eat" , new EmployeeB());
    }

    public void work(String command) {

    }

}
