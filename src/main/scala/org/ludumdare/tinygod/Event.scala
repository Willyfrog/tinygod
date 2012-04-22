package org.ludumdare.tinygod

import org.newdawn.slick.{Animation, Sound}


/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 22/04/12
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */

class Event (val sfx:Sound, val ani:Animation){
  var timeout:Int = 20

  def tick(delta:Int){
    if (timeout>delta)
      timeout-=delta
    else
      timeout = 0
  }

}
