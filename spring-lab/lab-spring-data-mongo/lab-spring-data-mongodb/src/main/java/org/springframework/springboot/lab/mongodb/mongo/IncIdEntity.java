package org.springframework.springboot.lab.mongodb.mongo;

import org.springframework.data.annotation.Id;

/**
 * 自增主键实体
 *
 * @author K
 */
public abstract class IncIdEntity<T extends Number> {

    @Id
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
