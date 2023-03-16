package entities;
import static utils.Constants.PlayerConstants.*;
import java.awt.geom.Rectangle2D;
import java.awt.Color;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamestates.Playing;

import static utils.HelpMethods.*;
import main.Game;
import utils.LoadSave;


public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick,aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false,attacking=false;
    private boolean left,up,right,down,jump;
    private float playerSpeed = 1.0f*Game.SCALE;
    private int[][] lvlData;
    private float xDrawOffset =45*Game.SCALE;
    private float yDrawOffset =49*Game.SCALE;
   
   //Jumping
    private float airSpeed =0f;
    private float gravity = 0.02f*Game.SCALE;
    private float jumpSpeed = -1.75f*Game.SCALE;
    private float fallSpeedAfterCollission = 0.3f*Game.SCALE;
    private boolean inAir = false;
    
    //Status Bar
    private BufferedImage statusBarImg;

    private int statusBarWidth=(int)(145*Game.SCALE);
    private int statusBarHeight= (int)(45*Game.SCALE);
    private int statusBarX= 0;
    private int statusBarY=0;

    private  int healthBarWidth=(int)(117*Game.SCALE);
    private  int healthBarHeight=(int)(9*Game.SCALE);
    private  int healthBarXstart=(int)(14*Game.SCALE);
    private  int healthBarYstart=(int)(6*Game.SCALE);

    private  int maxHealth =100;
    private  int currentHealth = maxHealth;
    private  int healthWidth=healthBarWidth;

    //Attackbox
    private Rectangle2D.Float attackBox;
    private int flipX=0;
    private int flipW=1;

    private boolean attackChecked;
    private Playing playing;
    public Player(float x, float y,int width, int height, Playing playing) {
        super(x, y,width,height);
        this.playing=playing;
        loadAnimations();
        initHitbox(x,y,20*Game.SCALE,30*Game.SCALE);
        initAttackBox();
        
    }
    
    private void initAttackBox() {
        attackBox=new Rectangle2D.Float(x,y,(int)(30*Game.SCALE),(int)(40*Game.SCALE));
    }

    public void update(){
        updateHealthBar();
        if(currentHealth<=0){
            playing.setGameOver(true);
            return;
        }
        
        updateAttackBox();
        updatePos();
        if(attacking)
            checkAttack();
        updateAnimationTick();
        setAnimation();
        

    }

    
    private void checkAttack() {
        if(attackChecked||aniIndex !=2)
            return;
        attackChecked=true;
        playing.checkEnemyHit(attackBox);

    }

    private void updateAttackBox() {
        if(right){
            attackBox.x= hitbox.x+hitbox.width+(int)(Game.SCALE*10);
        }else if(left){
            attackBox.x= hitbox.x-hitbox.width-(int)(Game.SCALE*10);
        }
        attackBox.y=hitbox.y;
    }

    private void updateHealthBar() {
        healthWidth=(int)((currentHealth/(float)maxHealth)*healthBarWidth);
    }

    public void render(Graphics g,int lvlOffset){

        g.drawImage(animations[playerAction][aniIndex],
            (int)(hitbox.x-xDrawOffset)-lvlOffset+flipX,
            (int)(hitbox.y-yDrawOffset),
            width*flipW,height, null);
       // drawHitbox(g,lvlOffset);
        //drawAttackbox(g,lvlOffset);

        drawUI(g);
    }

    
    
    private void drawAttackbox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int)attackBox.x-lvlOffsetX,(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }

    private void drawUI(Graphics g) {
        
        g.setColor(Color.red);
       g.fillRect(healthBarXstart+statusBarX,healthBarYstart+statusBarY,healthWidth,healthBarHeight);
       g.drawImage(statusBarImg,statusBarX,statusBarY,(int)(statusBarWidth),(int)(statusBarHeight),null);
    }

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick>=aniSpeed){
           aniTick=0;
           aniIndex++;
            if(aniIndex>= GetSpriteAmount(playerAction)){
                aniIndex=0;
                attacking=false;
                attackChecked=false;
            }
           
        }
    }
    
    private void setAnimation(){
        int startAni = playerAction;
        
        if(moving)
            playerAction= RUNNING;
        else
            playerAction = IDLE;

        if(inAir){
            if(airSpeed>0)
             playerAction=FALLING;
            else
                playerAction =JUMP;
        }
        
        if(attacking){
            playerAction= ATTACK_1;
            if(startAni!= ATTACK_1){
                aniIndex=1;
                aniTick=0;
                return;
            }
        }
        if(startAni!=playerAction)
            resetAniTick();
    }

    private void resetAniTick(){
        aniTick=0;
        aniIndex=0;
    }
    private void updatePos(){
        moving=false;

        if(jump)
            jump();
       
        if(!inAir)
            if((!left&&!right)||(right&&left))
                return;

        float xSpeed =0;
        if(left){
            xSpeed-=playerSpeed;
            flipX=width;
            flipW =-1;
        }
        if (right){
            xSpeed+=playerSpeed;
            flipX=0;
            flipW=1;
        }
        if(!inAir){
            if(!isEntityOnFloor(hitbox, lvlData)){
                inAir= true;
            }
        }
       
        if(inAir){

            if(CanMoveHere(hitbox.x,hitbox.y+airSpeed, hitbox.width,hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXpos(xSpeed);
            }else{
                hitbox.y= GetEntityYPosUnderRoofOrAboveFloor(hitbox,airSpeed);
                if(airSpeed>0)
                    resetInAir();
                    else
                    airSpeed =fallSpeedAfterCollission;
                    updateXpos(xSpeed);
            }

        }else
            updateXpos(xSpeed);
        
        moving=true;

      

    }


    private void jump() {
        if(inAir)
        return;
        inAir= true;
        airSpeed= jumpSpeed;
    }

    private void resetInAir() {
        inAir=false;
        airSpeed=0;
    }

    private void updateXpos(float xSpeed){
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,lvlData)){
            hitbox.x +=xSpeed;
        }else {
            hitbox.x= GetEntityXPosNextToWall(hitbox,xSpeed);
        }
           
    }
    public void changeHealth(int value){
        currentHealth+= value;

        if(currentHealth<=0 ){
            currentHealth=0;
            //gameOver();

        }else if(currentHealth>=maxHealth)
            currentHealth = maxHealth;
    }



    private void loadAnimations(){
        
            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
            
            animations = new BufferedImage[15][10];

            for(int j=0;j<animations.length;j++)
            for(int i= 0;i<animations[j].length;i++)
               animations[j][i] = img.getSubimage(i*120,j*80,120,80);

            statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);

       
        
            
        }
    
    public void loadLvlData(int[][] lvlData){
        this.lvlData=lvlData;
        if(!isEntityOnFloor(hitbox, lvlData))
            inAir=true;
    }

    public void resetDirBooleans(){
        left=false;
        right= false;
        up=false;
        down=false;
    }


    public void setAttack(boolean attacking){
        this.attacking=attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
        
public void setJump(boolean jump){
    this.jump = jump;
}

public void resetAll() {
    resetDirBooleans();
    inAir=false;
    attacking=false;
    moving=false;
    playerAction= IDLE;
    currentHealth=maxHealth;

    hitbox.x=x;
    hitbox.y=y;

        if(isEntityOnFloor(hitbox, lvlData))
            inAir=true;
}
}
