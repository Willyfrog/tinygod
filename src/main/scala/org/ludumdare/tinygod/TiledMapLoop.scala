package org.ludumdare.tinygod

import org.newdawn.slick.tiled.TiledMap


/**
 * Created with IntelliJ IDEA.
 * User: guillermo
 * Date: 23/04/12
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */

//class TiledMapLoop (val path:String, val framex:Int, val framey:Int){
class TiledMapLoop(val path: String, val frameX: Int, val frameY: Int) extends TiledMap(path){
  //def this(val path:String, val frameX:Int, val frameY:Int) = this(path, true, frameX, frameY)
  //val map = new TiledMap(path)
  val mapwidth = super.getWidth*super.getTileWidth
  val mapheight = super.getHeight*super.getTileHeight
  print("medidas %d, %d\r\n".format(mapwidth, mapheight))
  def drawLoop(posX: Int, posY: Int) {

    // head - tail of the viewport
    var hX: Int = frameX
    var hY: Int = frameY
    var tX: Int = 0
    var tY: Int = 0
    if ((posX + hX) > super.getWidth) {
      hX = mapwidth - posX
      tX = frameX - hX
    }
    if ((posY + hY) > super.getHeight) {
      hY = mapheight - posY
      tY = frameY - hY
    }
    val addX:Int = if (posX % super.getTileWidth==0) 0 else 1
    val addY:Int = if (posY % super.getTileHeight==0) 0 else 1
    // draw the upperleft corner of the viewport first

    super.render(0, 0, posX / super.getTileWidth, posY / super.getTileHeight, hX / super.getTileWidth + addX , hY / super.getTileHeight + addY)
    // upperright
    if (tX!=0)
      super.render(hX, 0, 0, posY / super.getTileHeight, tX / super.getTileWidth + addX, hY / super.getTileHeight + addY)
    //lowerleft
    if (tY!=0)
      super.render(0, hY, posX / super.getTileWidth, 0, hX / super.getTileWidth + addX, tY / super.getTileHeight + addY)
    //lowerright
    if (tX!=0 && tY != 0)
      super.render(hX, hY, 0, 0, tX / super.getTileWidth, tY / super.getTileHeight)
    //print("draw: position:(%d,%d) -> (%d,%d/%d,%d)\r\n".format(posX, posY, hX, hY, tX, tY))
  }

}
