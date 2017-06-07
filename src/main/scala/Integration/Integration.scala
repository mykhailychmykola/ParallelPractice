package Integration
import  ua.edu.ucu.cs.parallel._
import scala.util.Random

/**
  * Created by nickolay on 04.06.17.
  */
object Integration {
//  val threachold = 1000

  def sumSegment(f: (Double) => Double, totalNumberOfPoints: Int, a: Double, b: Double): Double = {
    val rndX = new Random
    def iter(sum: Double, n: Int, a: Double, b: Double): Double ={
      if (n >= totalNumberOfPoints)
        sum
      else {
        val x = Random.nextDouble * (b - a ) + a
        iter(sum + f(x), n + 1, a, b)
      }
    }
    iter(0, 0, a, b)
  }

  def takeIntegral(f: Double => Double,
                   lowerbound: Double,
                   higherbound: Double,
                   totalNumberOfPoints: Int): Double = {
    (higherbound - lowerbound)/totalNumberOfPoints*sumSegment(f, totalNumberOfPoints, lowerbound, higherbound)
  }

  def sumSegmentPar(f: (Double) => Double, totalNumberOfPoints: Int, a: Double, b: Double): Double = {

    val middle: Int = totalNumberOfPoints/2
    val (sum1, sum2) = parallel(sumSegment(f, middle, a, b), sumSegment(f, middle, a, b))
    sum1 + sum2
  }


  def takeIntegralPar(f: Double => Double,
                      lowerbound: Double,
                      higherbound: Double,
                      totalNumberOfPoints: Int): Double = {
    (higherbound - lowerbound)/totalNumberOfPoints*sumSegmentPar(f, totalNumberOfPoints, lowerbound, higherbound)
  }

  def sumSegmentParPar(f: (Double) => Double, totalNumberOfPoints: Int, a: Double, b: Double): Double = {

    val middle: Int = totalNumberOfPoints/4
    val ((sum1, sum2), (sum3, sum4)) = parallel(parallel(sumSegment(f, middle, a, b), sumSegment(f, middle, a, b)),
                                                parallel(sumSegment(f, middle, a, b), sumSegment(f, middle, a, b)))
    sum1 + sum2 + sum3 + sum4
  }


  def takeIntegralParPar(f: Double => Double,
                      lowerbound: Double,
                      higherbound: Double,
                      totalNumberOfPoints: Int): Double = {
    (higherbound - lowerbound)/totalNumberOfPoints*sumSegmentParPar(f, totalNumberOfPoints, lowerbound, higherbound)
  }

}
