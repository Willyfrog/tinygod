package org.ludumdare.tinygod

import org.newdawn.slick.{Animation, SpriteSheet}
import collection.immutable.HashMap
import org.ludumdare.tinygod.Animator._


/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 22/04/12
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 */

object Animator {
  //val manSheet: SpriteSheet = new SpriteSheet("man/man.png", 16,16)
  //val manSheet: SpriteSheet = new SpriteSheet("man/man.png", 16,16)

  //val child:Array[Animation] = new (new Animation(), new Animation(), new Animation(), new Animation())
  //val man:Array[Animation] = (new Animation(), new Animation(), new Animation(), new Animation())
  val LEFT = 0
  val DOWN = 3
  val UP = 6
  val boySheet = new SpriteSheet("man/boy.png",16,16)
  val manSheet = new SpriteSheet("man/man.png",16,16)
  val oldmanSheet = new SpriteSheet("man/oldman.png",16,16)

  //load animations
  val animaciones:Map[String, Map[String, Animation]] = Map("boy" -> Map("Left" -> cargarAnimacion(LEFT,boySheet), "Right" -> cargarAnimacion(LEFT,boySheet,true), "Up" -> cargarAnimacion(UP,boySheet), "Down" -> cargarAnimacion(DOWN,boySheet)),
  "man" -> Map("Left" -> cargarAnimacion(LEFT,manSheet), "Right" -> cargarAnimacion(LEFT,manSheet,true), "Up" -> cargarAnimacion(UP,manSheet), "Down" -> cargarAnimacion(DOWN,manSheet)),
  "oldman"-> Map("Left" -> cargarAnimacion(LEFT,oldmanSheet), "Right" -> cargarAnimacion(LEFT,oldmanSheet,true), "Up" -> cargarAnimacion(UP,oldmanSheet), "Down" -> cargarAnimacion(DOWN,oldmanSheet)))


  private def cargarAnimacion(pos:Int,spr:SpriteSheet,reverse:Boolean=false):Animation = {
    val a:Animation = new Animation()
    for (f <- (pos to (pos+3))){
      if (!reverse)
        a.addFrame(spr.getSprite(f,0),130)
      else
        a.addFrame(spr.getSprite(f,0).getFlippedCopy(true,false),130)
    }
    return  a
  }

}

class Animator {

}
