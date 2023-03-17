package org.rainbow.kill.redis.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CountDownLatch;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author K
 */
@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTest {

  @Autowired
  private MockMvc mockMvc;


  /**
   * 清空数据库和 Redis 数据
   */
  @Test
  public void refreshDBAndRedis() throws Exception {
    this.mockMvc.perform(get("/initDBAndRedisBefore")
        ).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testCreateWrongOrder() throws Exception {
    CountDownLatch countDownLatch = new CountDownLatch(1000);
    for (int i = 0; i < 1000; i++) {
      new Thread(() -> {
        try {
          this.mockMvc.perform(get("/createWrongOrder")
                  .param("sid", "1")
              ).andDo(result -> System.out.println(result.getResponse().getContentAsString()))
              .andExpect(status().isOk());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        countDownLatch.countDown();
      }).start();
    }
    countDownLatch.await();
  }

  @Test
  public void testCreateOptimisticOrder() throws Exception {
    CountDownLatch countDownLatch = new CountDownLatch(1000);
    for (int i = 0; i < 1000; i++) {
      new Thread(() -> {
        try {
          this.mockMvc.perform(get("/createOptimisticOrder")
                  .param("sid", "1")
              ).andDo(result -> System.out.println(result.getResponse().getContentAsString()))
              .andExpect(status().isOk());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        countDownLatch.countDown();
      }).start();
    }
    countDownLatch.await();
  }

  @Test
  public void createOptimisticLimitOrder() throws Exception {
    CountDownLatch countDownLatch = new CountDownLatch(1000);
    for (int i = 0; i < 1000; i++) {
      new Thread(() -> {
        try {
          this.mockMvc.perform(get("/createOptimisticLimitOrder")
                  .param("sid", "1")
              ).andDo(result -> System.out.println(result.getResponse().getContentAsString()))
              .andExpect(status().isOk());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        countDownLatch.countDown();
      }).start();
    }
    countDownLatch.await();
  }

  @Test
  public void createOrderWithLimitAndRedis() throws Exception {
    this.mockMvc.perform(get("/createOrderWithLimitAndRedis")
            .param("sid", "1")
        ).andDo(print())
        .andExpect(status().isOk());
  }
}
