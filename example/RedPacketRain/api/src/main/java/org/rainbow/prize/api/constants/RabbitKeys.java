package org.rainbow.prize.api.constants;

/**
 * RabbitMQ Key
 *
 * @author: K
 * @date: 2022/04/06 20:15
 */
public class RabbitKeys {

  /**
   * 中奖队列
   */
  public static final String QUEUE_HIT = "prize_queue_hit";

  /**
   * 参与活动队列
   */
  public static final String QUEUE_PLAY = "prize_queue_play";

  /**
   * 中奖路由
   */
  public static final String EXCHANGE_DIRECT = "prize_exchange_direct";

}
