package org.ludumdare.tinygod

/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 22/04/12
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */

object Comun {
  val MENUSTATE = 0
  val GAMESTATE = 1
  val ENDSTATE = 2
  val RESX = 800
  val RESY = 600
  val MAXX = 1600 //Dependen del mapa
  val MAXY = 1600 // TODO: FIXME
  var souls:Int = 0

  val INIPPL = 40
  val MAXPPL = 250

  val GIRL = "girl"
  val BOY = "boy"
  val MAN = "man"
  val OMAN = "oldman"
  val WMAN = "woman"
  val OWMAN = "oldwoman"
  val TERR = "terrorist"

  var minilog:List[String] = List()

  def log(info:String)  {
    if (minilog.length > 4)
     minilog = minilog.tail ::: List(info)
    else
      minilog = minilog ::: List(info)
    print(minilog)
  }

}
