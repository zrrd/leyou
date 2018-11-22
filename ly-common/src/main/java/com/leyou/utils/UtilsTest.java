package com.leyou.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * .
 *
 * @author 邵益炯
 * @date 2018/11/21
 */
public class UtilsTest {

  public static void main(String[] args) throws InterruptedException {

    LinkedBlockingDeque<Long> longs = new LinkedBlockingDeque<>();

    //机器中心 数据中心
    IdWorker idWorker = new IdWorker(10L, 10L);
    long l = idWorker.nextId();
    ExecutorService service = Executors.newFixedThreadPool(1000);
    for (int j = 0; j < 1000; j++) {
      service.submit(() -> {
        for (int i = 0; i < 1000; i++) {
          long a = idWorker.nextId();
          longs.add(a);
          System.out.println(idWorker.nextId());
        }
      });
    }
    TimeUnit.SECONDS.sleep(10);

    Set<Long> set = new HashSet<>(1_000_000);
    set.addAll(longs);
    System.out.println(set.size());
    service.shutdown();
  }

}
