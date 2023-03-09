package org.springframework.springboot.lab.redisson.util;

/**
 * @author K
 */
@FunctionalInterface
public interface LockCallBack<T> {

    Object execute(T t) throws InterruptedException;
}
