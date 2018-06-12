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
  val handler = scheduler.scheduleAtFixedRate(
    new CloudWatchReporter("test"),
    0,
    1,
    TimeUnit.MINUTES)

  (1 to 6).foreach { _ ⇒
    new Thread(() ⇒ Thread.sleep(60000)).start()
    Thread.sleep(30000)
  }

  handler.cancel(false)
  println("exit")
}
