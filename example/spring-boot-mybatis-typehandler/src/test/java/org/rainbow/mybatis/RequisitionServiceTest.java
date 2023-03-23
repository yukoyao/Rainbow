package org.rainbow.mybatis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.rainbow.mybatis.entity.bos.GoodsBo;
import org.rainbow.mybatis.entity.bos.ProgressBo;
import org.rainbow.mybatis.entity.dos.Requisition;
import org.rainbow.mybatis.service.RequisitionService;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

/**
 * @author K
 */
@SpringBootTest
public class RequisitionServiceTest {

  @Resource
  private RequisitionService requisitionService;

  @Test
  public void testAdd() {
    Requisition requisition = new Requisition();
    requisition.setId(String.valueOf(RandomUtil.randomLong()));
    requisition.setCreateBy("me");
    requisition.setCreateTime(new Date());
    requisition.setDeleteFlag(false);
    requisition.setWorkPlace("佛山市祖庙派出所");
    requisition.setWorkAddress("广东省佛山市禅城区朝安北路25号");
    requisition.setDept("信息科");
    requisition.setRemark("最紧要快");
    requisition.setPhotos(Arrays.asList("https://xxxx.jpg", "https://xxxx.jpg", "https://xxxx.jpg"));
    requisition.setContacts("陈千惠");
    requisition.setContactNum("13800000000");
    requisition.setReqTime(new Date());
    requisition.setEta(DateUtil.offsetDay(new Date(), 3));
    requisition.setStatus(1);
    requisition.setProgress(Arrays.asList(
        new ProgressBo().setApprover("").setTime(new Date()).setStatusText("陈千惠发起请购")));
    requisition.setGoods(Arrays.asList(
        new GoodsBo().setSeqNo(1).setGoodsId(1L).setGoodsName("扫把").setGoodsExplain("非常能扫")
            .setNum(10).setSkuId(1L).setSn("SN001"),
        new GoodsBo().setSeqNo(2).setGoodsId(2L).setGoodsName("抹布").setGoodsExplain("非常能抹")
            .setNum(20).setSkuId(2L).setSn("SN002")
    ));
    requisitionService.save(requisition);
  }

  @Test
  public void testGetById() {
    Requisition requisition = requisitionService.getById(9029961927413440559L);
    System.out.println(requisition);
  }
}
