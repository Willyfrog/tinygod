package org.ludumdare.tinygod

import org.newdawn.slick.{Animation, SpriteSheet}
import scala.util.Random
import math._

import org.ludumdare.tinygod.Animator


/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 21/04/12
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */

class Person(var x:Double, var y:Double) {

 /* val spri: SpriteSheet = new SpriteSheet("man/man.png", 16,16)
  val aniLeft: Animation = new Animation()
  val aniRight: Animation = new Animation()
  val aniUp: Animation = new Animation()
  val aniDown: Animation = new Animation() */

  val SPEED = 0.1
  var state: Boolean = false // true: walk, false: stop
  var destination: (Double, Double) = (0,0) //where is he/she headed? IMPORTANT is relative to his/her position
  var sleep: Int = 0
  var age:Int = 0
  var ageRange:String = "boy"
  var orientation:String = "Down"
  var alive:Boolean = true
  var happiness:Int = 50

  def isHit(hx: Float, hy: Float): Boolean =
    ((x <= hx) && (hx <= x + 16) && (y <= hy) && (hy <= y+16))

  def currAni:Animation={
    Animator.animaciones(ageRange)(orientation)
  }

  def newDestination{
    destination = (Random.nextInt(800)-400,Random.nextInt(600)-300) //TODO: change to parameter
    //print("mi destino es %f,%f", destination)
    if (destination._1.abs >= destination._2.abs){
      if (destination._1 <=0)
        orientation = "Left"
      else
        orientation = "Right"
    }
    else{
      if (destination._2 <=0)
        orientation = "Up"
      else
        orientation = "Down"
    }

  }
  def newSleep(){
    sleep = Random.nextInt(1000)
  }

  def edad(){
    if (age<100)
      ageRange = "boy"
    else if (age<10000)
      ageRange = "man"
    else
    {
      ageRange = "oldman"
      if (Random.nextInt(50)==1){ //1 entre 50 posibilidades a cada update
        alive = false
        happiness += 20
      }
    }
  }

  def update(delta:Int){
    edad()
    if (alive) {
      age += delta
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
