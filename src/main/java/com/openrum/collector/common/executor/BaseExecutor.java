package com.openrum.collector.common.executor;

import java.util.concurrent.ExecutorService;

/**
*
*
* @description: BaseExecutor
*
* @author: lou renzheng
*
* @create: 2022/12/30
**/
public class BaseExecutor {

  /**
   * processor 线程池
   */
  public static ExecutorService PROCESSOR_EXECUTOR;

  /**
   * exporter线程池
   */
  public static ExecutorService EXPORTER_EXECUTOR;


}
