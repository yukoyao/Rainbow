package org.springframework.springboot.lab.kafka.concurrency.message;

/**
 * 示例 06 的 Message 消息
 *
 * @author K
 */
public class Demo06Message {

    public static final String TOPIC = "DEMO_06";

    /**
     * 编号
     */
    private Integer id;

    public Demo06Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo06Message{" +
                "id=" + id +
                '}';
    }
}
