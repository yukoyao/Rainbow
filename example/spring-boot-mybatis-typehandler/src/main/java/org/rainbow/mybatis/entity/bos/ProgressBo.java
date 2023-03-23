package org.rainbow.mybatis.entity.bos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * @author K
 */
@Data
@Accessors(chain = true)
public class ProgressBo {


    /**
     * 审批人
     */
    private String approver;

    /**
     * 状态文本
     */
    private String statusText;

    /**
     * 执行时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
}
