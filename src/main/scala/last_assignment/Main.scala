package last_assignment
import last_assignment.CountWords
/**
  * Created by nickolay on 07.07.17.
  */
object Main {
  def main(args: Array[String]): Unit = {
    var a = "dsfdsfsdf fsfdsd df sd  sdf f   dfs f    sdfdfsdfsd"
    val counter = new CountWords
    print(counter.count(a))
  }
}
