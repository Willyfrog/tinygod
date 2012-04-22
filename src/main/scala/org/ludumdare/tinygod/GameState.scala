package org.ludumdare.tinygod

import org.newdawn.slick.state.{StateBasedGame, BasicGameState}
import collection.mutable.ArrayBuffer
import scala.util.Random
import org.newdawn.slick._

/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 22/04/12
 * Time: 1:37
 * To change this template use File | Settings | File Templates.
 */

class GameState(var stateID:Int = -1) extends BasicGameState{

  var land: Image = null
  var manAnim: Animation = null
  var ppl: ArrayBuffer[Person] = new ArrayBuffer[Person]()
  var souls: Int = 0 //Score
  val MAXPPL = 200
  val INIPPL = 10
  var rayo: Animation = null
  var nextDraw: ArrayBuffer[(Float, Float, Animation)] = new ArrayBuffer[(Float, Float, Animation)]()
  var end:Boolean = false

  def GameState (id:Int){ //herencia de java?
    stateID = id
  }
  def getID:Int={
    return stateID
  }

  override def init(gc:GameContainer, sbg:StateBasedGame){
    println("Initialized Game")
    land = new Image("world/land86.png")
    end = false
    for (_ <- (1 to INIPPL)) {
      ppl += new Person(Random.nextInt(800), Random.nextInt(600))
    }
    val raySpri: SpriteSheet = new SpriteSheet("power/ray.png", 32, 32)
    rayo = new Animation()
    for (f <- (0 to 2)) {
      rayo.addFrame(raySpri.getSprite(f, 0), 130)
    }

  }


  def tryHit(x: Float, y: Float, ani: Animation) {
    /*
    ani is the animation to run in case it hits a person
     */
    var h: Boolean = false
    //ppl.foreach(p => h |= p.isHit(x,y))
    for (p <- ppl) {
      if (p.isHit(x, y)) {
        p.kill(-30)
        h = true
      }
    }
    if (h)
      nextDraw += ((x, y, ani))
  }

  def removeDead() {
    for (p <- ppl.clone())
      if (!p.alive) {
        ppl -= p
        if (p.happiness > 0)
          souls += 1
      }

    //TODO: replace with a rotting corpse
  }

  override def update(gc:GameContainer, sbg:StateBasedGame, delta:Int){
    //current = new java.util.Date()
    if (gc.getInput.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
      if (!end)
        tryHit(gc.getInput.getMouseX, gc.getInput.getMouseY, rayo)
      else
      {
        init(gc,sbg)
        sbg.enterState(Game.MENUSTATE)
      }

    }
    //update people
    ppl.foreach(p => p.update(delta))
    removeDead()
    if (ppl.length == 0) {
      end = true
    }


  }
  override def render(gc:GameContainer, sbg:StateBasedGame, g:Graphics){
    //g.setColor(Color.white)
    //g.drawString("Hello tinygod, %s".format(current), 200, 10)
    if (!end) {
      land.draw(0, 0)
      for (p: Person <- ppl) {
        g.drawAnimation(p.currAni, p.x.toFloat, p.y.toFloat)
      }
      for (p: (Float, Float, Animation) <- nextDraw)
        g.drawAnimation(p._3, p._1, p._2) //TODO: check
      nextDraw.clear()
    } else {
      g.setColor(Color.darkGray)
      g.drawString("Every person in this tiny world has passed away", 20, 300)
      g.drawString("Click anywhere to go back to menu", 25,310)
    }

    g.drawString("Tiniers: %s Souls Collected: %s".format(ppl.length, souls), 20, 580)
  }

}
