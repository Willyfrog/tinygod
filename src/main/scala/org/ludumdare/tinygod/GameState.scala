package org.ludumdare.tinygod

import org.newdawn.slick.state.{StateBasedGame, BasicGameState}
import collection.mutable.ArrayBuffer
import scala.util.Random
import org.newdawn.slick.tiled._
import scala.Int
import org.newdawn.slick._
import net.java.games.input.Event

/**
 * Created with IntelliJ IDEA.
 * User: gvaya
 * Date: 22/04/12
 * Time: 1:37
 * To change this template use File | Settings | File Templates.
 */

class GameState(var stateID:Int = -1) extends BasicGameState{

  var land: Image = null
  var map: TiledMapLoop = null
  var marco: Image = null
  var ppl: ArrayBuffer[Person] = new ArrayBuffer[Person]()
  var souls: Int = 0 //Score
  var rayo: Animation = null
  var raysfx: Sound = null
  var love: Animation = null
  var lovsfx: Sound = null
  var explosion: Animation = null
  var explosfx: Sound = null
  var nextDraw: ArrayBuffer[Evento] = new ArrayBuffer[Evento]()
  var end:Boolean = false
  var posX:Int = 0
  var posY:Int = 0
  val godSpeed:Int = 1
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
    //map = new TiledMap("world/firstmap.tmx")
    map = new TiledMapLoop("world/firstmap.tmx",Comun.RESX,Comun.RESY)
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
    val expSpri: SpriteSheet = new SpriteSheet("effects/explosion.png",64,64)
    explosion = new Animation()
    for (f<- (0 to 3)) {
      explosion.addFrame(expSpri.getSprite(f,0), 200)
    }
    raysfx = new Sound("sounds/ray.wav")
    lovsfx = new Sound("sounds/love.wav")
    explosfx = new Sound("sounds/explosion.wav")

  }


  def tryHit(x: Float, y: Float, ani:Animation,sfx:Sound, hmod:Int) {
    /*
    ani is the animation to run in case it hits a person
     */
    var h: Boolean = false
    //ppl.foreach(p => h |= p.isHit(x,y))
    for (p <- ppl) {
      if (p.isHit(x, y)) {
        p.kill(hmod)
        h = true
      }
    }
    if (h)
      nextDraw += (new Evento(sfx,ani,x, y, hmod))
  }

  def tryImpregnate(p:Person,l:ArrayBuffer[Person]){
    var done = false
    if (l.length >0){
      if (p.male){ //only man try
        var p2 = l.head
        if ((!p.walking)&&(!p2.walking)){ //no fucking and walking at the same time!
        //if (!p.walking){ //no fucking and walking at the same time!
          if (p2.impregnate(p.male)){
            nextDraw += new Evento(lovsfx,love,((p.x+p2.x)/2).toFloat,((p.y+p2.y)/2).toFloat,20)
            done = true //once per update, maybe we need another counter?

          }
        }
        if (!done)
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
          p.pregnant = -1
          ppl += new Person(p.x+8, p.y+8) //newborn
        }

        if (p.blowupTime == 0){
          //nextDraw += new Evento(explosfx,explosion,p.x.toFloat, p.y.toFloat,-30)
          tryHit(p.x.toFloat,p.y.toFloat,explosion,explosfx,-30)
        }
        //happiness is affected by events
        for (e<-nextDraw){
          if (p.isCloseTo(e.x,e.y,60))
          {
            p.happiness += e.hmod
          }
        }

        if ((l.length>1) && (l.length < Comun.MAXPPL))
          tryImpregnate(p,l.tail)
      }
      populationControl(l.tail, delta)
    }
  }

  override def update(gc:GameContainer, sbg:StateBasedGame, delta:Int){
    //current = new java.util.Date()
    if (!pause){
      if (gc.getInput.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
        tryHit(gc.getInput.getMouseX, gc.getInput.getMouseY,rayo,raysfx,-10)
      }
      if (gc.getInput.isKeyDown(Input.KEY_S)) {
        posY = posY + godSpeed * delta
        if (posY>Comun.MAXY) posY -= Comun.MAXY
      }
      if (gc.getInput.isKeyDown(Input.KEY_W)) {
        posY -= godSpeed * delta
        if (posY<0) posY+=Comun.MAXY
      }
      if (gc.getInput.isKeyDown(Input.KEY_D)) {
        posX = posX + godSpeed * delta
        if (posX>Comun.MAXX) posX-=Comun.MAXX
      }
      if (gc.getInput.isKeyDown(Input.KEY_A)) {
        posX -= godSpeed * delta
        if (posX<0) posX+=Comun.MAXX
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
      //update events
      for (e<- nextDraw.clone()){
        e.tick(delta)
        e.play()
        if (e.timeout<=0)
          nextDraw -= e
      }
    print("Position: %d, %d".format(posX,posY))
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
    map.drawLoop(posX,posY)

    for (p: Person <- ppl) {
      //g.drawAnimation(p.currAni, p.x.toFloat, p.y.toFloat)
      p.currAni.draw(p.x.toFloat-posX, p.y.toFloat - posY)
    }
    for (na: Evento <- nextDraw)
      na.ani.draw(na.x - na.ani.getWidth/2, na.y - na.ani.getHeight/2)
    nextDraw.clear()
    marco.draw(0,0)
    g.drawString("Tiniers: %s Souls Collected: %s".format(ppl.length, souls), 20, 580)
    if (pause){
      g.drawString("PAUSED",350,290)
      g.drawString("Press Q to exit", 315,310)
    }
  }

}
