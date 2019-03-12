package chielong.pattern.proxy.restructure;

import java.util.ArrayList;
import java.util.List;

public class MemberAfter {
    private String name;
    private int age;
    private List<Account> accounts;

    public MemberAfter clone() {
        MemberAfter newMember = new MemberAfter();

        newMember.setName(new String(name));
        newMember.setAge(age);

        List<Account> newList = new ArrayList<>();
        for (Account account: accounts) {
            newList.add(account.clone());
        }
        newMember.setAccounts(newList);
        return newMember;
    }

    //constructor & getter & setter


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
