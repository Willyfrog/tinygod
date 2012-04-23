package org.ludumdare.tinygod

import org.newdawn.slick.tiled.TiledMap
import collection.mutable.ArrayBuffer


/**
 * Created with IntelliJ IDEA.
 * User: guillermo
 * Date: 23/04/12
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */

//class TiledMapLoop (val path:String, val framex:Int, val framey:Int){
class TiledMapLoop(val path:String, val frameX:Int, val frameY:Int) extends TiledMap(path){
  //def this(val path:String, val frameX:Int, val frameY:Int) = this(path, true, frameX, frameY)

  def drawLoop(posX:Int,posY:Int){
    // head - tail of the viewport
    var hX:Int = frameX
    var hY:Int = frameY
    var tX:Int = 0
    var tY:Int = 0
    if ((posX + hX) > super.getWidth){
      hX = super.getWidth- posX
      tX = frameX - hX
    }
    if ((posY + hY) > super.getHeight){
      hY = super.getHeight - posY
      tY = frameY - hY
    }
    // draw the upperleft corner of the viewport first
    super.render(0,0,posX,posY,hX,hY)
    // upperright
    if (tY>0)
      super.render(hX,0,0,posY,tX,hY)
    //lowerleft
    if (tX>0)
      super.render(0,hY,posX,0,hX,tY)
    //lowerright
    if (tX>0 && tY>0)
      super.render(hX,hY,0,0,tX,tY)

  }

}
