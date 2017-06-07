package pie

import org.scalameter.{Key, Warmer, config}

import scala.util.Random

/**
  * Created by nickolay on 03.06.17.
  */
object main {
  def main(args: Array[String]): Unit = {

    val totalNumberOfPoints = 1000000

    val standardConfig = config(
      Key.exec.minWarmupRuns -> 100,
      Key.exec.maxWarmupRuns -> 300,
      Key.exec.benchRuns -> 100,
      Key.verbose -> true
    ).withWarmer(new Warmer.Default)

    val seqTime = standardConfig.measure{
      MonteCarloSimulation.pi(totalNumberOfPoints)
    }
    val parTime = standardConfig.measure{
      MonteCarloSimulation.piPar(totalNumberOfPoints)
    }

    println(s"Sequential time is $seqTime")
    println(s"Parallel time is $parTime")
    println(seqTime.asInstanceOf[Double] / parTime.asInstanceOf[Double])
    val a = MonteCarloSimulation.pi(totalNumberOfPoints)
    val k = MonteCarloSimulation.piPar(totalNumberOfPoints)

    println(a)
    println(k)
  }

}
