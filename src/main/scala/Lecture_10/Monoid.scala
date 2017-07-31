package Lecture_10

/**
  * Created by nickolay on 30.06.17.
  */
trait Monoid[A] {
  def op(a1: A, a2: A): A

  def zero: A
}