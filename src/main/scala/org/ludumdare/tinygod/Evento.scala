package org.ludumdare.tinygod

import org.newdawn.slick.{Animation, Sound}


/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 22/04/12
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */

class Evento (val sfx:Sound, val ani:Animation, val x:Float, val y:Float, var hmod:Int){
  // hmod: modifier for seeing the effect
  var timeout:Int = 50
  var played = false

  def tick(delta:Int){
    hmod = 0 //only affects once
    if (timeout>delta)
      timeout-=delta
    else
      timeout = 0
  }

  def play(){
    if (!played){
      played = true
      sfx.play()
    }
  }

}
