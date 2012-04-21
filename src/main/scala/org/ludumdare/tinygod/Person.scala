package org.ludumdare.tinygod

import org.newdawn.slick.{Animation, SpriteSheet}
import scala.util.Random
import math._


/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 21/04/12
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */

class Person(var x:Double, var y:Double) {

  val spri: SpriteSheet = new SpriteSheet("man/man.png", 16,16)
  val aniLeft: Animation = new Animation()
  val aniRight: Animation = new Animation()
  val aniUp: Animation = new Animation()
  val aniDown: Animation = new Animation()
  var currAni:Animation = aniDown
  val SPEED = 0.1
  var state: Boolean = false // true: walk, false: stop
  var destination: (Double, Double) = (0,0) //where is he/she headed? IMPORTANT is relative to his/her position
  var sleep: Int = 0
  var age:Int = 0
  var alive:Boolean = true
  var happiness:Int = 50

  //LoadAnimations
  for (frame:Int <- (0 to 2)) {
    aniLeft.addFrame(spri.getSprite(frame,0), 150)
    aniRight.addFrame(spri.getSprite(frame,0).getFlippedCopy(true,false), 150)
  }
  for (frame:Int <- (3 to 5)) {
    aniUp.addFrame(spri.getSprite(frame,0), 150)
  }
  for (frame:Int <- (6 to 8)) {
    aniDown.addFrame(spri.getSprite(frame,0), 150)
  }

  def isHit(hx: Float, hy: Float): Boolean =
    return ((x <= hx) && (hx <= x + 16) && (y <= hy) && (hy <= y+16))

  def newDestination{
    destination = (Random.nextInt(800)-400,Random.nextInt(600)-300) //TODO: change to parameter
    //print("mi destino es %f,%f", destination)
    if (destination._1.abs >= destination._2.abs){
      if (destination._1 <=0)
        currAni = aniLeft
      else
        currAni = aniRight
    }
    else{
      if (destination._2 <=0)
        currAni = aniUp
      else
        currAni = aniDown
    }

  }
  def newSleep{
    sleep = Random.nextInt(1000)
  }

  def update(delta:Int){
    if (alive) {
      age += delta
      if (age>=100000 && Random.nextInt(7)==1)
        alive = false
        happiness += 20 //dying of old is usually a good thing
      if (state) {
        //move!
        //get displacement
        var movx: Double = 0
        var movy: Double = 0
        var h = hypot(destination._1, destination._2)
        if (h <= SPEED) {
          movx = destination._1
          movy = destination._2
          state = !state
          newSleep
        }
        else {
          var ang = atan2(destination._2, destination._1)
          movx = cos(ang) * SPEED * delta
          movy = sin(ang) * SPEED * delta
        }
        //update values
        destination = (destination._1 - movx, destination._2 - movy)
        x += movx
        y += movy
        if (x > 800)
          x -= 800
        else if (x < 0)
          x += 800
        if (y > 600)
          y -= 600
        else if (y < 0)
          y += 600
      }
      else {
        //sleep
        sleep -= delta
        if (sleep <= 0) {
          newDestination
          state = !state
        }

      }
    }

  }



  def kill(mod:Int = 0){
    alive = false
    happiness += mod
  }
}
