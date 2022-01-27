import akka.actor.ActorSystem

object Test {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("helloWorld")

    println(actorSystem.name)
  }

}