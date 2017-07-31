package last_assignment

/**
  * Created by nickolay on 07.07.17.
  */

trait Monoid[A] {
  def op(a1: A, a2: A): A

  def zero: A
}
