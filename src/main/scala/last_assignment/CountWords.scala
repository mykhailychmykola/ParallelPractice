package last_assignment
import last_assignment.{Part, Stub}
import ua.edu.ucu.cs.parallel._
/**
  * Created by nickolay on 10.07.17.
  */
class CountWords {
  def reduce(s: String): Int = {
    if (s.length == 0) 0 else 1
  }

  def count(string: String): Int = {
    def rull(letter: Char): Parts = {
      if (letter.isWhitespace)
        Part("", 0, "")
      else
        Stub(letter.toString)
    }
    def unstub(s: String) = s.length min 1
    foldMapModifPar(string.toIndexedSeq, wordCounterMonoid)(rull) match {
      case Stub(s) => unstub(s)
      case Part(l, w, r) => unstub(l) + w + unstub(r)
    }
  }
  val wordCounterMonoid: Monoid[Parts] = new Monoid[Parts] {
    def op(a: Parts, b: Parts) = (a, b) match {
      case (Stub(a1), Stub(a2)) => Stub(a1 + a2)
      case (Stub(a1), Part(l, counter, r)) => Part(a1 + l, counter, r)
      case (Part(l, counter, r), Stub(c)) => Part(l, counter, r + c)
      case (Part(l1, counter1, r1), Part(l2, counter2, r2)) =>
        Part(l1, counter1 + (if ((r1 + l2).isEmpty) 0 else 1) + counter2, r2)
    }
    def zero = Stub("")
  }
  def foldMapModifPar[A, B](sequence: IndexedSeq[A], monoid: Monoid[B])(f: A => B): B = {
    if (sequence.length == 0)
      monoid.zero
    else if (sequence.length == 1)
      f(sequence(0))
    else {
      val (left_part, right_part) = sequence.splitAt(sequence.length / 2)
      val (l, r) = parallel(foldMapModifPar(left_part, monoid)(f), foldMapModifPar(right_part, monoid)(f))
      monoid.op(l, r)
    }
  }
}
