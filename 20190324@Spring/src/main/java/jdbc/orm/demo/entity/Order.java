package jdbc.orm.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by chielong on 2019-04-26.
 */
@Table(name="t_order")
@Entity
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 8987464718329166955L;

    private Long id;

    @Column(name = "mid")
    private Long memberId;

    private String deatil;

    private Long createTime;

    private String createTimeFmt;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", deatil='" + deatil + '\'' +
                ", createTime=" + createTime +
                ", createTimeFmt='" + createTimeFmt + '\'' +
                '}';
    }
}
