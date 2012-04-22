package org.ludumdare.tinygod

import org.newdawn.slick.state.{StateBasedGame, BasicGameState}
import org.newdawn.slick._

/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 22/04/12
 * Time: 18:10
 * To change this template use File | Settings | File Templates.
 */

class EndState (var stateID:Int = -1) extends BasicGameState{

  def EndState (id:Int){ //herencia de java?
    stateID = id
  }
  def getID:Int={
    return stateID
  }

  var bg:Image = null
  var start:Image = null
  var select:Sound = null

  override def init(gc:GameContainer, sbg:StateBasedGame){

  }
  override def update(gc:GameContainer, sbg:StateBasedGame, delta:Int){
    if (gc.getInput.isKeyDown(Input.KEY_ENTER)) {
      sbg.enterState(Comun.MENUSTATE)
    }

  }
  override def render(gc:GameContainer, sbg:StateBasedGame, g:Graphics){
    g.setColor(Color.darkGray)
    g.drawString("Every person in this tiny world has passed away", 20, 300)
    g.drawString("Press ENTER to go back to menu", 25,310)
    g.drawString("Souls Collected: %s".format(Comun.souls), 20, 580)
  }

}