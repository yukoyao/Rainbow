package org.rainbow.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.rainbow.mybatis.entity.dos.Requisition;
import org.rainbow.mybatis.mapper.RequisitionMapper;
import org.rainbow.mybatis.service.RequisitionService;
import org.springframework.stereotype.Service;

/**
 * @author K
 */
@Service
public class RequisitionServiceImpl extends ServiceImpl<RequisitionMapper, Requisition> implements RequisitionService {

}
