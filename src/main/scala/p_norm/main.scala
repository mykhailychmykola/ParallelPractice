package p_norm

import scala.util.Random
import org.scalameter._

/**
  * Created by nickolay on 03.06.17.
  */
object main {
  def main(args: Array[String]): Unit = {
    val c = new Random
    val length = 100000
    val b = (0 until length).map(_* c.nextInt()).toArray



    val standardConfig = config(
      Key.exec.minWarmupRuns -> 100,
      Key.exec.maxWarmupRuns -> 300,
      Key.exec.benchRuns -> 100,
      Key.verbose -> true
    ).withWarmer(new Warmer.Default)

    val seqTime = standardConfig.measure{
      p_norm.pNorm(b, 2)
    }
    val parTime = standardConfig.measure{
      p_norm.parpNorm(b, 2)
    }
    println(p_norm.parpNorm(b, 2))
    println(p_norm.pNorm(b, 2))
    println(p_norm.pNorm(b, 2) == p_norm.parpNorm(b, 2))
    println(s"Sequential time is $seqTime")
    println(s"Parallel time is $parTime")
    println(seqTime.asInstanceOf[Double] / parTime.asInstanceOf[Double])

  }
}
