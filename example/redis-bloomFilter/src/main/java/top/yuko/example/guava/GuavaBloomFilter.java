package top.yuko.example.guava;

import cn.hutool.core.util.NumberUtil;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import java.util.ArrayList;
import java.util.List;

/**
 * Guava 内置的布隆过滤器
 *
 * @author: K
 * @date: 2022/02/16 11:48
 */
public class GuavaBloomFilter {

  public static final int _1W = 10000;

  // 布隆过滤器预计要插入的数据量
  public static int size = 100 * _1W;

  // 误判率。并不是越小越好，误判率意味着需要更多的哈希函数和更多的位空间。
  public static double fpp = 0.03;

  // 构建布隆过滤器
  private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

  public static void main(String[] args) {

    // 结论1：对于存在于布隆过滤器的元素不存在误判，误判只会发生于不在布隆过滤器中的元素。
    // 结论2：如果判断结果为存在时不一定存在，如果结果为不存在一定不存在

    // 往布隆过滤器中插入 100W 的样本数据
    for (int i = 0; i < size; i++) {
      bloomFilter.put(i);
    }

    // 测试存在的元素是否存在误判
    /*for (int i = 0; i < size; i++) {
      if (!bloomFilter.mightContain(i)) {
        System.out.println("误判了：" + i);
      }
    }*/

    // 测试不存在的元素误判率是多少
    List<Integer> list = new ArrayList<>();
    for (int i = size + 1; i < size + 100000; i++) {
      if (bloomFilter.mightContain(i)) {
        list.add(i);
        System.out.println("误判了：" + i);
      }
    }

    System.out.println("误判率为" + NumberUtil.div(list.size(), 100000));
  }

}
