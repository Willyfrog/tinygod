package org.ludumdare.tinygod

import org.newdawn.slick.state.{StateBasedGame, BasicGameState}
import collection.mutable.ArrayBuffer
import scala.util.Random
import org.newdawn.slick.tiled._
import scala.Int
import org.newdawn.slick._

/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 22/04/12
 * Time: 1:37
 * To change this template use File | Settings | File Templates.
 */

class GameState(var stateID:Int = -1) extends BasicGameState{

  var land: Image = null
  var map: TiledMap = null
  var marco: Image = null
  var ppl: ArrayBuffer[Person] = new ArrayBuffer[Person]()
  var souls: Int = 0 //Score
  var rayo: Animation = null
  var love: Animation = null
  var nextDraw: ArrayBuffer[(Float, Float, Animation)] = new ArrayBuffer[(Float, Float, Animation)]()
  var end:Boolean = false
  var position:(Int,Int) = (0,0)
  val godSpeed:Int = 5
  var pause:Boolean = false

  def GameState (id:Int){ //herencia de java?
    stateID = id
  }
  def getID:Int={
    return stateID
  }

  override def init(gc:GameContainer, sbg:StateBasedGame){
    println("Initialized Game")
    land = new Image("world/land86.png")
    map = new TiledMap("world/firstmap.tmx")
    marco = new Image("world/marco.png")
    end = false
    souls = 0
    for (_ <- (1 to Comun.INIPPL)) {
      ppl += new Person(Random.nextInt(800), Random.nextInt(600), Random.nextInt(500)) //personitas colocadas aleatoriamente y con edad variable
    }
    val raySpri: SpriteSheet = new SpriteSheet("effects/ray.png", 32, 32)
    rayo = new Animation()
    for (f <- (0 to 2)) {
      rayo.addFrame(raySpri.getSprite(f, 0), 130)
    }
    love = new Animation()
    val lovSpri: SpriteSheet = new SpriteSheet("effects/love.png", 16, 16)
    for (f<- (0 to 4)) {
      love.addFrame(lovSpri.getSprite(f,0), 200)
    }

  }


  def tryHit(x: Float, y: Float, ani: Animation) {
    /*
    ani is the animation to run in case it hits a person
     */
    var h: Boolean = false
    //ppl.foreach(p => h |= p.isHit(x,y))
    for (p <- ppl) {
      if (p.isHit(x, y)) {
        p.kill(-30)
        h = true
      }
    }
    if (h)
      nextDraw += ((x, y, ani))
  }

  def tryImpregnate(p:Person,l:ArrayBuffer[Person]){
    if (l.length >0){
      if (p.male){ //only man try
        var p2 = l.head
        if ((!p.walking)&&(!p2.walking)){ //no fucking and walking at the same time!
        //if (!p.walking){ //no fucking and walking at the same time!
          if (p2.impregnate(p.male)){
            nextDraw += ((((p.x+p2.x)/2).toFloat, ((p.y+p2.y)/2).toFloat, love)) //Love!
          }
        }
        tryImpregnate(p, l.tail)
      }
    }
  }

//recursiva
  def populationControl(l:ArrayBuffer[Person],delta:Int) {
    if (l.length>0){
      var p:Person = l.head
      if (!p.alive) {
        ppl -= p //TODO: replace with a rotting corpse
        if (p.happiness > 0)
          souls += 1
      }
      else{
        p.update(delta)
        if (p.pregnant == 0){
          ppl += new Person(p.x+8, p.y+8) //newborn
          p.pregnant = -1
        }

        if (l.length>1)
          tryImpregnate(p,l.tail)
      }
      populationControl(l.tail, delta)
    }
  }

  override def update(gc:GameContainer, sbg:StateBasedGame, delta:Int){
    //current = new java.util.Date()
    if (!pause){
      if (gc.getInput.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
        tryHit(gc.getInput.getMouseX, gc.getInput.getMouseY, rayo)
      }
      if (gc.getInput.isKeyDown(Input.KEY_W) && position._2 >0) {
        position = (position._1, position._2 + godSpeed * delta)
      }
      if (gc.getInput.isKeyDown(Input.KEY_S) && position._2 < Comun.MAXY) {
        position = (position._1, position._2 - godSpeed * delta)
      }
      if (gc.getInput.isKeyDown(Input.KEY_A) && position._1 < Comun.MAXX) {
        position = (position._1 + godSpeed * delta, position._2)
      }
      if (gc.getInput.isKeyDown(Input.KEY_D) && position._1 > 0) {
        position = (position._1 - godSpeed * delta, position._2)
      }
      //update people
      //ppl.foreach(p => p.update(delta))
      if (!ppl.isEmpty){
        populationControl(ppl.clone(),delta)
        if (ppl.length == 0) {
          Comun.souls = souls
          init(gc,sbg)
          sbg.enterState(Comun.ENDSTATE)
        }
      }
    }
    if (gc.getInput.isKeyPressed(Input.KEY_ESCAPE)) {
      pause = !pause
      gc.setPaused(pause)
    }
    if (pause && gc.getInput.isKeyPressed(Input.KEY_Q)){
      gc.exit()
    }



  }
  override def render(gc:GameContainer, sbg:StateBasedGame, g:Graphics){
    //g.setColor(Color.white)
    //g.drawString("Hello tinygod, %s".format(current), 200, 10)
      //land.draw(0, 0)
    map.render(position._1,position._2)

    for (p: Person <- ppl) {
      //g.drawAnimation(p.currAni, p.x.toFloat, p.y.toFloat)
      p.currAni.draw(p.x.toFloat+position._1, p.y.toFloat+position._2)
    }
    for (na: (Float, Float, Animation) <- nextDraw)
      na._3.draw(na._1 - na._3.getWidth/2, na._2 - na._3.getHeight/2)
    nextDraw.clear()
    marco.draw(0,0)
    g.drawString("Tiniers: %s Souls Collected: %s".format(ppl.length, souls), 20, 580)
    if (pause){
      g.drawString("PAUSED",350,290)
      g.drawString("Press Q to exit", 315,310)
    }
  }

}
