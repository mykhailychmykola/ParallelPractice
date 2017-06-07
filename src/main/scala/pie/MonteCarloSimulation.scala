package pie
import ua.edu.ucu.cs.parallel._
import scala.util.Random

/**
  * Created by nickolay on 03.06.17.
  */
object MonteCarloSimulation {

  private def numberInside(totalNumberOfPoints: Int): Int = {

    val rndX = new Random
    val rndY = new Random

    def simulate(hits: Int, generateNumbers: Int): Int = {
      if (generateNumbers >= totalNumberOfPoints)
        hits
      else {
        val x = rndX.nextDouble
        val y = rndY.nextDouble
        simulate(hits + (if (x*x + y*y <= 1) 1 else 0), generateNumbers + 1)
      }
    }
    simulate(0, 0)
  }

  def pi(totalNumberOfPoints: Int): Double = 4.0 * numberInside(totalNumberOfPoints).asInstanceOf[Double] / totalNumberOfPoints

  def piPar(totalNumberOfPoints: Int): Double = {
    val ((pi1, pi2), (pi3, pi4)) = parallel(parallel(numberInside(totalNumberOfPoints / 4).asInstanceOf[Double], numberInside(totalNumberOfPoints / 4).asInstanceOf[Double]),
                      parallel(numberInside(totalNumberOfPoints / 4).asInstanceOf[Double], numberInside(totalNumberOfPoints / 4).asInstanceOf[Double]))
    4.0 * (pi1 + pi2 + pi3 + pi4) / totalNumberOfPoints
  }
}
