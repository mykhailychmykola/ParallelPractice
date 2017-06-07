package p_norm
import ua.edu.ucu.cs.parallel._

/**
  * Created by nickolay on 03.06.17.
  */
object p_norm {
  var threashold = 1000
  private def power(d: Double, p: Double) = {
    math.exp(p*math.log(math.abs(d)))
  }
  private def sumSegment(a: Array[Int], p: Double, from: Int, to: Int) : Double = {

    def iter(sum: Double, index: Int): Double ={
      if (index > to) {
        sum
      }
      else {
        iter(sum + power(a(index), p), index + 1)
      }
    }
    iter(0, from)
  }
  def pNorm(a: Array[Int], p: Double): Double = power(sumSegment(a, p, 0, a.length - 1), 1/p)

  private def sumSegmentPar(a: Array[Int], p: Double, from: Int, to: Int): Double = {
  if (to - from < threashold){
    sumSegment(a, p, from, to)
  }
  else {
    val middle = from + (to - from)/2
    val (sum1, sum2) = parallel(sumSegmentPar(a, p, from, middle), sumSegmentPar(a, p, middle, to))
    sum1 + sum2
  }
  }
  def parpNorm(a: Array[Int], p:Double) = {
    power(sumSegmentPar(a, p, 0, a.length-1), 1/p)
  }



}
