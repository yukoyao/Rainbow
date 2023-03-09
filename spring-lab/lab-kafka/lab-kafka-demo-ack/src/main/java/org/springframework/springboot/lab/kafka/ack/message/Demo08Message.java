package org.springframework.springboot.lab.kafka.ack.message;

/**
 * 示例 08 的 Message 消息
 *
 * @author K
 */
public class Demo08Message {

    public static final String TOPIC = "DEMO_08";

    /**
     * 编号
     */
    private Integer id;

    public Demo08Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo08Message{" +
                "id=" + id +
                '}';
    }
}
