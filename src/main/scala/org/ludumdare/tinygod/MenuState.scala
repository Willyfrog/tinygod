package org.ludumdare.tinygod

import org.newdawn.slick.state.{StateBasedGame, BasicGameState}
import org.newdawn.slick._

/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 22/04/12
 * Time: 1:28
 * To change this template use File | Settings | File Templates.
 */

class MenuState(var stateID:Int = -1) extends BasicGameState{

  def MainMenuState (id:Int){ //herencia de java?
    stateID = id
  }
  def getID:Int={
    return stateID
  }

  var bg:Image = null
  var start:Image = null
  var select:Sound = null

  override def init(gc:GameContainer, sbg:StateBasedGame){
    bg = new Image("menu/menu.png")
    start = new Image("menu/start.png")
    select = new Sound("sounds/menu.wav")
  }
  override def update(gc:GameContainer, sbg:StateBasedGame, delta:Int){
    if (gc.getInput.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
      select.play()
      sbg.enterState(Game.GAMESTATE)
    }

  }
  override def render(gc:GameContainer, sbg:StateBasedGame, g:Graphics){
    bg.draw(0,0)
    start.draw(450,350)
  }

}
