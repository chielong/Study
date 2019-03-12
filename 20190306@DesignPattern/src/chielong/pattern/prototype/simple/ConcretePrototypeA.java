package chielong.pattern.prototype.simple;

public class ConcretePrototypeA implements Prototype {
    private int age;
    private String name;
    private String[] hobbies;

    public ConcretePrototypeA(int age, String name, String[] hobbies) {
        this.age = age;
        this.name = name;
        this.hobbies = hobbies;
    }

    @Override
    public Prototype clone() {
        //循环，处理hobbies。
        String[] hobbies = new String[this.hobbies.length];
        //for hobbies[i] = this.hobbies[i];
        return new ConcretePrototypeA(this.age,
                this.name,
                hobbies);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

}
