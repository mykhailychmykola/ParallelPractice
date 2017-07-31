package Lecture_10

import scala.util.Random
import Lecture_10.Monoid
import ua.edu.ucu.cs.parallel._
/**
  * Created by nickolay on 30.06.17.
  */
object main {

  def foldSegment[A](xs: IndexedSeq[A], from: Int, to: Int, m: Monoid[A]) = {
    var res = xs(from)
    var index = from + 1
    while (index < to) {
      res = m.op(res, xs(index))
      index = index + 1
    }
    res
  }

  def foldMapSegment[A, B](xs: IndexedSeq[A], from: Int, to: Int, m: Monoid[B],
                           f: (A) => B) = {
    var res = f(xs(from))
    var index = from + 1
    while (index < to) {
      res = m.op(res, f(xs(index)))
//      println(res)
      index += 1
    }
    res
  }

  def power(i: Int, i1: Int): Int = {
    math.exp(i1*math.log(math.abs(i))).asInstanceOf[Int]
  }

  def main(args: Array[String]): Unit = {

    val rnd = Random
    val length = 100
    val source = (0 until length).map(n => {
      val x = rnd.nextInt(100)
      if (x%2 == 0) x else -x
    }).toVector
    implicit val threshold = 10
    val monoid = new Monoid[(Int, Int)] {
      override def op(x: (Int, Int), y: (Int, Int)): (Int, Int) = {
        val x_ = if (x._1 >= 0) x else zero
        val y_ = if (y._1 >= 0) y else zero
        (x_._1 + y_._1, x_._2 + y_._2)
      }

      override def zero: (Int, Int) = (0, 0)
    }
    implicit val thresholdSize = 10
    println(foldMapPar(source, 0, source.length, monoid)((_, 1))((thresholdSize)))

    def monoid2 = new Monoid[Int] {
      override def op(a1: Int, a2: Int): Int = a1 + a2

      override def zero: Int = 0
    }

    def foldPar[A](xs: IndexedSeq[A], from: Int, to: Int, m: Monoid[A])(implicit thresholdSize: Int): A = {
      if (to - from < thresholdSize) foldSegment(xs, from, to, m)
      else {
        val middle = from + (to - from) / 2
        val (l, r) = parallel(foldPar(xs, from, middle, m)(thresholdSize),
          foldPar(xs, middle, to, m)(thresholdSize))
        m.op(l, r)
      }
    }
    def foldMapPar[A, B](xs: IndexedSeq[A], from: Int, to:Int,
                         m: Monoid[B])(f: A => B)(implicit thresholdSize: Int): B = {
      if (to - from <= thresholdSize) {
        foldMapSegment(xs, from, to, m, f)
      }
      else {
        val middle = from + (to - from) / 2
        val (l, r) = parallel(foldMapPar(xs, from, middle, m)(f)(thresholdSize), foldMapPar(xs,
          middle, to, m)(f)(thresholdSize))
        m.op(l, r)
      }


    }
//    implicit val thresholdSize = 100
//    val rnd2 = new Random
//    val length2 = 100000
//    val source2 = (0 until length2).map(_ * rnd2.nextInt()).toVector
//    print(foldMapPar(source2, 0, source2.length, monoid2)(power(_, 2))(thresholdSize))
  }


}
