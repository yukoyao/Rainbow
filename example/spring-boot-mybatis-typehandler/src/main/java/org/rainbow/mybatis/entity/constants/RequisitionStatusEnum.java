package org.rainbow.mybatis.entity.constants;

/**
 * 请购单状态枚举
 *
 * @author K
 */
public enum RequisitionStatusEnum {

    PENDING_APPROVAL(1, "待审批"),

    PENDING_DELIVERY(2, "待配送"),

    PENDING_INSPECTION(3, "待验收"),

    FINISHED(4, "已完成");

    private final Integer code;

    private final String desc;

    RequisitionStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
