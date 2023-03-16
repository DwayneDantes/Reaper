package gamestates;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utils.LoadSave;
import static utils.Constants.Environment.*;


public class Playing extends State implements Statemethods {
    
    private BufferedImage backgroundImg,PLX2,PLX3,PLX4,PLX5;
    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg= LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        PLX2 =LoadSave.GetSpriteAtlas(LoadSave.PLX2);
        PLX3 =LoadSave.GetSpriteAtlas(LoadSave.PLX3);
        PLX4 =LoadSave.GetSpriteAtlas(LoadSave.PLX4);
        PLX5 =LoadSave.GetSpriteAtlas(LoadSave.PLX5);
    }




    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private boolean paused =false;


    private int xLvlOffset;
    private int leftBorder =(int)(0.2*Game.GAME_WIDTH);
    private int rightBorder =(int)(0.8*Game.GAME_WIDTH);
    private int lvlTilesWide =LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset =lvlTilesWide-Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset*Game.TILES_SIZE;

    private boolean gameOver;

    

private void initClasses() {
    levelManager=new LevelManager(game);
    enemyManager= new EnemyManager(this);
    player = new Player(200*Game.SCALE,200*Game.SCALE,(int)(120*Game.SCALE),(int)(80*Game.SCALE),this);
    player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
    pauseOverlay = new PauseOverlay(this);
    gameOverOverlay=new GameOverOverlay(this);
    }

public void windowFocusLost(){
        player.resetDirBooleans();
    }
    
    
    
    
public Player getPlayer(){
    
        return player;
    }

@Override
public void update() {
    if (!paused){
        levelManager.update();
    player.update();
    enemyManager.update(levelManager.getCurrentLevel().getLevelData(),player);
    checkCloseToBorder();
    }else{
        pauseOverlay.update();
    }
    
    
}

private void checkCloseToBorder() {
    int playerX = (int)player.getHitbox().x;
    int diff = playerX - xLvlOffset;

    if(diff>rightBorder)
     xLvlOffset += diff-rightBorder;
    else if(diff<leftBorder)
        xLvlOffset+=diff-leftBorder;
    if(xLvlOffset>maxLvlOffsetX)
        xLvlOffset=maxLvlOffsetX;
    else if(xLvlOffset<0)
        xLvlOffset=0;
}

@Override
public void draw(Graphics g) {
    g.drawImage(backgroundImg,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
    
    drawPLX2(g);
    levelManager.draw(g,xLvlOffset);
    player.render(g,xLvlOffset);
    enemyManager.draw(g,xLvlOffset);
    if(paused){
        g.setColor(new Color(0,0,0,150));
        g.fillRect(0,0,game.GAME_WIDTH,Game.GAME_HEIGHT);
        pauseOverlay.draw(g);
    }else if(gameOver)
        gameOverOverlay.draw(g);

    
}

private void drawPLX2(Graphics g) {
    for (int i=0;i<3;i++){
    g.drawImage(PLX2,0+i*PLX2_WIDTH-(int)(xLvlOffset*0.2),(int)(100*Game.SCALE),(int)(PLX2_WIDTH*1.8),(int)(PLX2_HEIGHT*1.8),null);
    g.drawImage(PLX3,0+i*PLX2_WIDTH-(int)(xLvlOffset*0.3),(int)(100*Game.SCALE),(int)(PLX2_WIDTH*1.8),(int)(PLX2_HEIGHT*1.8),null);
    g.drawImage(PLX4,0+i*PLX2_WIDTH-(int)(xLvlOffset*0.4),(int)(100*Game.SCALE),(int)(PLX2_WIDTH*1.8),(int)(PLX2_HEIGHT*1.8),null);
    g.drawImage(PLX5,0+i*PLX2_WIDTH-(int)(xLvlOffset*0.5),(int)(100*Game.SCALE),(int)(PLX2_WIDTH*1.8),(int)(PLX2_HEIGHT*1.8),null);
    }
}

public void resetAll(){
    
    gameOver = false;
    paused=false;
    player.resetAll();
    enemyManager.resetAllEnemies();

}

public void setGameOver(boolean gameOver){
    this.gameOver=gameOver;
}
public void checkEnemyHit(Rectangle2D.Float attackBox) {
    enemyManager.checkEnemyHit(attackBox);
}

@Override
public void mouseClicked(MouseEvent e) {
    if(!gameOver)
    if(e.getButton()== MouseEvent.BUTTON1)
            player.setAttack(true);
}

@Override
public void mousePressed(MouseEvent e) {
    if(!gameOver)
        if(paused)
        pauseOverlay.mousePressed(e);
}

@Override
public void mouseReleased(MouseEvent e) {
    if(!gameOver)
        if(paused)
            pauseOverlay.mouseReleased(e);
}

@Override
public void mouseMoved(MouseEvent e) {
    if(!gameOver) 
    if(paused)
        pauseOverlay.mouseMoved(e);
}

@Override
public void keyPressed(KeyEvent e) {
    if(gameOver)
        gameOverOverlay.keyPressed(e);
    else    
    switch(e.getKeyCode()) {
        //kEYBOARD WASD
       
            
        case KeyEvent.VK_A:
            player.setLeft(true);
            break;
        
            
        case KeyEvent.VK_D:
            player.setRight(true);
            break;
        case KeyEvent.VK_SPACE:
            player.setJump(true);
            break;
        case KeyEvent.VK_BACK_SPACE:
            Gamestate.state = Gamestate.MENU;
        case KeyEvent.VK_ESCAPE:
            paused = !paused;
            break;
            
    }
}

public void mouseDragged(MouseEvent e){
    if(!gameOver)
        if(paused)
        pauseOverlay.mouseDragged(e);
}
@Override
public void keyReleased(KeyEvent e) {
    if(!gameOver)
    
    switch(e.getKeyCode()) {
        //kEYBOARD WASD
        
    case KeyEvent.VK_A:
        player.setLeft(false);
        break;
    
    case KeyEvent.VK_D:
        player.setRight(false);
        break;
    
        case KeyEvent.VK_SPACE:
        player.setJump(false);
        break;
    }
}
public void unpauseGame(){
    paused=false;
}

}
