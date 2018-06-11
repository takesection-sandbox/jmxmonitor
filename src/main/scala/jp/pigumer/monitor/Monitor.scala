package jp.pigumer.monitor

import java.lang.management.ManagementFactory
import java.util.concurrent.{Executors, TimeUnit}

import javax.management.MBeanServer

import scala.collection.JavaConverters._

object Monitor extends App {

  val server: MBeanServer = ManagementFactory.getPlatformMBeanServer
  val mbeans = server.queryMBeans(null, null)
  mbeans.asScala.foreach(mbeans ⇒ println(s"$mbeans"))

  val scheduler = Executors.newScheduledThreadPool(1)
  val command: Runnable = () ⇒ {
    val count = ManagementFactory.getThreadMXBean.getThreadCount
    println(s"$count")
  }
  val handler = scheduler.scheduleAtFixedRate(command, 0, 1, TimeUnit.SECONDS)

  (1 to 60).foreach { _ ⇒
    new Thread(() ⇒ Thread.sleep(60000)).start()
    Thread.sleep(1000)
  }
}
