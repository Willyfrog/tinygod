package org.ludumdare.tinygod

import org.newdawn.slick._
import org.ludumdare.tinygod.Person
import scala.util.Random
import collection.mutable.ArrayBuffer
import state.StateBasedGame


object Game {
  def main(args: Array[String]) = {
    val container = new AppGameContainer(new Game, 800, 600, false)

    container.setTargetFrameRate(60)
    container.start
  }
  val MENUSTATE = 0
  val GAMESTATE = 1
}

class Game extends StateBasedGame("Tinygod") {
  val RESX = 800
  val RESY = 600
  val MENUSTATE = 0
  val GAMESTATE = 1
  //var current = new java.util.Date()
  this.addState(new MenuState(MENUSTATE))
  this.addState(new GameState(GAMESTATE))

  override def initStatesList(gc:GameContainer){
    getState(MENUSTATE).init(gc,this)
    getState(GAMESTATE).init(gc,this)
  }

}
