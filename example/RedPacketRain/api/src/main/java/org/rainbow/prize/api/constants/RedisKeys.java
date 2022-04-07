package org.rainbow.prize.api.constants;

/**
 * Redis 缓存 Key
 *
 * @author: K
 * @date: 2022/04/06 20:05
 */
public class RedisKeys {

  /**
   * 活动信息
   */
  public static final String INFO = "game_info_";

  /**
   * 令牌前缀
   */
  public static final String TOKEN = "game_token_";

  /**
   * 令牌桶前缀
   */
  public static final String TOKENS = "game_tokens_";

  /**
   * 最大中奖数
   */
  public static final String MAXGOAL = "game_maxgoal_";

  /**
   * 最大抽奖数
   */
  public static final String MAXENTER = "game_maxenter_";

  /**
   * redis session
   */
  public static final String SESSIONID = "user_sessionid_";

  /**
   * 用户中奖数
   */
  public static final String USERHIT = "user_hit_";

  /**
   * 用户登录错误次数
   */
  public static final String USERLOGINTIMES = "user_login_times_";

  /**
   * 用户是否参与该活动
   */
  public static final String USERGAME = "user_game_";

}
