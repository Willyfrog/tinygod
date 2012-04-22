package org.ludumdare.tinygod

import org.newdawn.slick._
import scala.util.Random
import collection.mutable.ArrayBuffer
import state.StateBasedGame


object Game {
  def main(args: Array[String]) = {
    val container = new AppGameContainer(new Game, 800, 600, false)

    container.setTargetFrameRate(60)
    container.start
  }

  var souls = 0 //ultima puntuacion
}

class Game extends StateBasedGame("Tinygod") {


  //var current = new java.util.Date()
  this.addState(new MenuState(Comun.MENUSTATE))
  this.addState(new GameState(Comun.GAMESTATE))
  this.addState(new EndState(Comun.ENDSTATE))

  override def initStatesList(gc:GameContainer){
    getState(Comun.MENUSTATE).init(gc,this)
    getState(Comun.GAMESTATE).init(gc,this)
    getState(Comun.ENDSTATE).init(gc,this)
  }

}
