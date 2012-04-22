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

class Person(var x:Double, var y:Double, val initialAge:Int = 0) {

 /* val spri: SpriteSheet = new SpriteSheet("man/man.png", 16,16)
  val aniLeft: Animation = new Animation()
  val aniRight: Animation = new Animation()
  val aniUp: Animation = new Animation()
  val aniDown: Animation = new Animation() */

  val SPEED = 0.1
  var walking: Boolean = false // true: walk, false: stop
  var destination: (Double, Double) = (0,0) //where is he/she headed? IMPORTANT is relative to his/her position
  var sleep: Int = 0
  var age:Int = initialAge
  var ageRange:String = "boy"
  var orientation:String = "Down"
  var alive:Boolean = true
  var happiness:Int = 50
  var pregnant = -1 // -1: not pregnant, any other: time to deliver child
  val male:Boolean = Random.nextBoolean()
  val terrorist:Boolean = (Random.nextInt(20)==1)

  def isHit(hx: Float, hy: Float): Boolean =
    ((x <= hx) && (hx <= x + 20) && (y <= hy) && (hy <= y+20)) //a little bigger than the sprite to make it easier

  def currAni:Animation={
    Animator.animaciones(ageRange)(orientation)
  }

  def newDestination(){
    destination = (Random.nextInt(Comun.MAXX)-Comun.MAXX/2,Random.nextInt(Comun.MAXY)-Comun.MAXY/2) //TODO: change to parameter
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
    if (age<3000)
      if (male)
        ageRange = Comun.BOY
      else
        ageRange = Comun.GIRL
    else if (age<10000)
      if (terrorist)
        ageRange = Comun.TERR
      else if (male)
        ageRange = Comun.MAN
      else
        ageRange = Comun.WMAN
    else
    {
      if (male)
        ageRange = Comun.OMAN
      else
        ageRange = Comun.OWMAN
      if (Random.nextInt(50)==1){ //1 entre 50 posibilidades a cada update
        alive = false
        happiness += 20
      }
    }
  }

  def impregnate(other:Boolean):Boolean={
    if ((ageRange == Comun.WMAN) && (pregnant < 0) && other){ //are they from different sex and this one is a fertile woman?
      pregnant = 90 + Random.nextInt(30) //variable time of pregnancy
      return true
    }
    return false
  }

  def update(delta:Int){
    edad()
    if (pregnant>0)
      if (pregnant>delta)
        pregnant -= delta
      else
        pregnant = 0 //newborn!
    if (alive) {
      age += delta
      if (walking) {
        //move!
        //get displacement
        var movx: Double = 0
        var movy: Double = 0
        var h = hypot(destination._1, destination._2)
        if (h <= SPEED) {
          movx = destination._1
          movy = destination._2
          walking = !walking
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
          walking = !walking
        }

      }
    }

  }

  def isCloseTo(x2:Double,y2:Double,distance:Double=30):Boolean={
    hypot(x-x2,y-y2)<=distance
  }

  def changeHappynessAround(x2:Double,y2:Double,hvar:Int,distance:Double=30){
    if (isCloseTo(x2,y2,distance)){
      happiness += hvar
    }
  }

  def kill(mod:Int = 0){
    alive = false
    happiness += mod
  }
}
