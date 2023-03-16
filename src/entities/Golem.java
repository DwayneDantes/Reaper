package entities;
import static utils.Constants.EnemyConstants.*;
import main.Game;
import static utils.Constants.Directions.*;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Color;

public class Golem extends Enemy {
    //Attackbox
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    
    public Golem(float x, float y) {
        super(x, y, GOLEM_WIDTH, GOLEM_HEIGHT,GOLEM2);
        
        initHitbox(x,y,(int)(20*Game.SCALE),(int)(33*Game.SCALE));
        initAttackBox();
        

    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(52*Game.SCALE),(int)(33*Game.SCALE));
        attackBoxOffsetX =(int)(Game.SCALE*15);
    }

    public void update(int[][] lvlData,Player player){
        updateBehavior(lvlData,player);
        updateAnimationTick();
        updateAttackBox();
        
    }
    
    private void updateAttackBox() {
            attackBox.x=hitbox.x-attackBoxOffsetX;
            attackBox.y=hitbox.y;
    }

    private void updateBehavior(int[][] lvlData,Player player){
        if(firstUpdate)
            firstUpdateCheck(lvlData);
            if(inAir)
                updateInAir(lvlData);
            else{
                switch(enemyState){
                    case IDLE:
                        newState(RUNNING);
                        break;
                    case RUNNING:
                        if(canSeePlayer(lvlData,player)){
                            turnTowardsPlayer(player);
                        if(isPlayerCloseForAttack(player))
                            newState(ATTACK);
                            
                            
                            
                        }
                            
                    
                        move(lvlData);
                        break;
                    case ATTACK:
                        if(aniIndex==0)
                            attackChecked=false;
                        if(aniIndex==3 && !attackChecked)
                            checkEnemyHit(attackBox,player);
                        break;
                    case HIT:
                        break;
                }
            }
            

        
    }



public  void drawAttackBox(Graphics g,int xLvlOffset){
    g.setColor(Color.red);
    g.drawRect((int)attackBox.x-xLvlOffset,
                (int)attackBox.y,
                (int)attackBox.width,
                (int)attackBox.height);
}


public int flipX(){
    if(walkDir==LEFT)
        return width;
    else    
        return 0;
    
}
public int  flipW(){
    if(walkDir==LEFT)
        return -1;
    else    
        return 1;
}
}
