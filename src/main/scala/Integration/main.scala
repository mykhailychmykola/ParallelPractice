package Integration
import math.{cos, sin}
import org.scalameter._
/**
  * Created by nickolay on 04.06.17.
  */
object main {
  def main(args: Array[String]): Unit = {

    val number_of_asterics = 10

    val standardConfig = config(
      Key.exec.minWarmupRuns -> 100,
      Key.exec.maxWarmupRuns -> 300,
      Key.exec.benchRuns -> 100,
      Key.verbose -> false
    ).withWarmer(new Warmer.Default)

    println("*" * number_of_asterics  + " "  + "REPORT" + " " +  "*" * number_of_asterics + "\n")
    println("Done by Nickolay Mykhlych\n\n")
    println("*" * number_of_asterics  + " "  + "Sequential implementation benchmarking" + " " +  "*" * number_of_asterics + "\n")

    val seqTime = standardConfig.measure(
      Integration.takeIntegral(x => math.exp(x) * math.sin(x) / math.cos(x) * math.log(x) + 100, 0, 500, 1000000)
    )

    println("*" * number_of_asterics  + " "  + "Sequential implementation benchmarking ENDED" + " " +  "*" * number_of_asterics + "\n")
    println("*" * number_of_asterics  + " "  + "Double Parllel implementation benchmarking" + " " +  "*" * number_of_asterics + "\n")

    val parparTime = standardConfig.measure(
      Integration.takeIntegralParPar(x => math.exp(x) * math.sin(x) / math.cos(x) * math.log(x) + 100, 0, 500, 1000000)
    )

    println("*" * number_of_asterics  + " "  + "Double Parllel implementation benchmarking ENDED" + " " +  "*" * number_of_asterics + "\n")
    println("*" * number_of_asterics  + " "  + "Parallel implementation benchmarking" + " " +  "*" * number_of_asterics + "\n")

    val parTime = standardConfig.measure(
      Integration.takeIntegralPar(x => math.exp(x) * math.sin(x) / math.cos(x) * math.log(x) + 100, 0, 500, 1000000)
    )

    val ratio = seqTime.value / parTime.value

    println("*" * number_of_asterics  + " "  + "Parallel implementation benchmarking ENDED" + " " +  "*" * number_of_asterics + "\n\n\n")
    println("*" * number_of_asterics  + " "  + "CONCLUSIONS:" + " " +  "*" * number_of_asterics + "\n")
    println(s"Sequential time is $seqTime \n")
    println(s"Parallel time is $parTime \n")
    println(s"Double parallel time is $parparTime \n")
    println(s"The RATIO is $ratio \n")
    println("MY CONCLUSIONS AND COMMENTS:\n1. If we are usin a simple function such as sin(x) or exp(x), and small interval for integraton.\n  Then sequential algorithm shows better performance.\n2. For " +
      "complex function and big intervals we get better 1.5 - 3 times better time of parallel implementation.\n But it depends from params.\n3. Parallel versions give us some bnefit but not much." )

//    println(Integration.takeIntegral(x => cos(x), 0, 2, 10000000))
//    println(Integration.takeIntegralPar(x => cos(x), 0, 2, 10000000))
//    println(Integration.takeIntegralParPar(x => cos(x), 0, 2, 10000000))

  }
}
