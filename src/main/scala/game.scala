package org.ludumdare

import org.newdawn.slick._
import org.ludumdare.tinygod.Person
import scala.util.Random
import collection.mutable.ArrayBuffer


object Game {
  def main(args: Array[String]) = {
    val container = new AppGameContainer(new Game, 800, 600, false)

    container.setTargetFrameRate(60)
    container.start
  }
}

class Game extends BasicGame("tinygod") {
  val RESX = 800
  val RESY = 600
  //var current = new java.util.Date()
  var land:Image = null
  var manAnim:Animation = null
  var ppl:ArrayBuffer[Person] = new ArrayBuffer[Person]()
  var souls:Int = 0 //Score
  val MAXPPL = 200
  val INIPPL = 10
  var rayo:Animation = null
  var nextDraw:ArrayBuffer[(Float,Float, Animation)] = new ArrayBuffer[(Float, Float, Animation)]()

  override def init(gc: GameContainer) {
    println("Initialized Game")
    land = new Image("world/land86.png")
    for (_ <- (1 to INIPPL)){
      ppl += new Person(Random.nextInt(800), Random.nextInt(600))
    }
    val raySpri:SpriteSheet = new SpriteSheet("power/ray.png", 32,32)
    rayo = new Animation()
    for (f <- (0 to 2)){
      rayo.addFrame(raySpri.getSprite(f,0),130)
    }
  }

  def tryHit(x:Float,y:Float,ani:Animation){
    /*
    ani is the animation to run in case it hits a person
     */
    var h:Boolean = false
    //ppl.foreach(p => h |= p.isHit(x,y))
    for (p<-ppl){
      if (p.isHit(x,y)){
        p.kill(-30)
        h=true
      }
    }
    if (h)
      nextDraw += ((x,y,ani))
  }

  def removeDead(){
    for (p<-ppl.clone())
      if (!p.alive)
      {
        ppl -= p
        if (p.happiness > 0)
          souls +=1
      }

    //TODO: replace with a rotting corpse
  }

  override def update(gc: GameContainer, delta: Int) {
    //current = new java.util.Date()
    if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
      tryHit(gc.getInput.getMouseX, gc.getInput.getMouseY, rayo)
    }
    //update people
    ppl.foreach(p=>p.update(delta))
    removeDead()
  }

  override def render(gc: GameContainer, g: Graphics) {
    //g.setColor(Color.white)
    //g.drawString("Hello tinygod, %s".format(current), 200, 10)
    land.draw(0,0)
    g.drawString("Tiniers: %s Souls Collected: %s".format(ppl.length,souls),20,580)

    for (p:Person <- ppl){
      g.drawAnimation(p.currAni,p.x.toFloat, p.y.toFloat)
    }
    for (p:(Float,Float,Animation) <- nextDraw)
      g.drawAnimation(p._3, p._1, p._2) //TODO: check
    nextDraw.clear()
  }
}
