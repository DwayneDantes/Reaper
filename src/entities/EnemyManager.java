package entities;
import java.awt.Graphics;
import gamestates.Playing;
import utils.LoadSave;
import static utils.Constants.EnemyConstants.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] GolemArr;
    private ArrayList<Golem> Golem = new ArrayList<>();
    public EnemyManager(Playing playing){
        this.playing=playing;
        loadEnemyImgs();
        addEnemies();
    }
    
    private void addEnemies() {
        Golem = LoadSave.GetGolems();
        System.out.println("size of golems" + Golem.size());
    }

    public void update(int[][] lvlData,Player player){
        for(Golem c: Golem)
           if(c.isActive())
            c.update(lvlData,player);
    }
    
    public void draw(Graphics g,int xLvlOffset){
            drawGolems(g,xLvlOffset);
    }


    private void drawGolems(Graphics g,int xLvlOffset) {
        for(Golem c: Golem){
        if(c.isActive()){
            g.drawImage(GolemArr[c.getEnemyState()][c.getAniIndex()],
            (int)(c.getHitbox().x-xLvlOffset-GOLEM_DRAWOFFSET_X)+c.flipX(), 
            (int)(c.getHitbox().y-GOLEM_DRAWOFFSET_Y),
            GOLEM_WIDTH*c.flipW(),GOLEM_HEIGHT,null);
            //c.drawAttackBox(g,xLvlOffset);
    }
}
}

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(Golem c: Golem)
            if(c.isActive())
                if(attackBox.intersects(c.getHitbox())){
                    c.hurt(10);
                    return;
            }
        
    }
    private void loadEnemyImgs() {
        GolemArr = new BufferedImage[5][8];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.GOLEM);
        for(int j=0;j<GolemArr.length;j++)
            for(int i=0;i<GolemArr[j].length;i++)
                GolemArr[j][i] = temp.getSubimage(i*GOLEM_WIDTH_DEFAULT,j*GOLEM_HEIGHT_DEFAULT,GOLEM_WIDTH_DEFAULT,GOLEM_HEIGHT_DEFAULT);

    }

	public void resetAllEnemies() {
        for(Golem c: Golem)
            c.resetEnemy();
	}


}
