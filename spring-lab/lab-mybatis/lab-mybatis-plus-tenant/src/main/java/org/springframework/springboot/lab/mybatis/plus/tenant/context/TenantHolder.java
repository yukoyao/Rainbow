package org.springframework.springboot.lab.mybatis.plus.tenant.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author K
 */
public class TenantHolder {

    private static final ThreadLocal<Integer> TENANT_ID = new TransmittableThreadLocal<>();

    public static void setTenantId(Integer tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static Integer getTenantId() {
        return TENANT_ID.get();
    }
}
