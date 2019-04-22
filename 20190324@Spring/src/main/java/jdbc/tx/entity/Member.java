package jdbc.tx.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by chielong on 2019-04-22.
 */
@Table(name="study_member")
@Entity
@Data
public class Member implements Serializable {
    @Id
    private Long id;

    private String name;
    private String addr;
    private Integer age;

    public Member(String name, String addr, Integer age) {
        this.name = name;
        this.addr = addr;
        this.age = age;
    }

    public Member() {
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", age=" + age +
                '}';
    }
}
