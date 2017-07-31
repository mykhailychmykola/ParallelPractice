package lecture_9

import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory
import java.util.concurrent.{ForkJoinPool, RecursiveAction, RecursiveTask}

import scala.concurrent.forkjoin.ForkJoinWorkerThread

/**
  * Created by nickolay on 15.06.17.
  */
object parallel {
  val forkJoinPool = new ForkJoinPool

  def task[T](e: => T): RecursiveTask[T] = {
    val t = new RecursiveTask[T] {
      val compute = e
    }
    Thread.currentThread match {
      case wt: ForkJoinWorkerThread =>
        t.fork
      case _ =>
        forkJoinPool.execute(t)
    }
    t
  }

  def par[A, B](a: => A, b: => B): (A, B) = {
    val t2 = task(b)
    val v1 = a
    (v1, t2.join)
  }

}