package levels;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import utils.LoadSave;

public class LevelManager {
    
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelzero;

    public LevelManager(Game game){
        this.game=game;
        levelzero=new Level(LoadSave.GetLevelData());

       // levelSprite= LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        
    
    }

    private void importOutsideSprites(){
        BufferedImage img= LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for(int j=0;j<4;j++)
            for (int i=0;i<12;i++) {
                int index= j*12 + i;
                levelSprite[index] =img.getSubimage(i*32,j*32,32,32);
            }
    }
    public void draw(Graphics g,int lvlOffset){
        for(int j=0;j<Game.TILES_IN_HEIGHT;j++)
            for (int i=0;i<levelzero.getLevelData()[0].length;i++){
                int index =levelzero.getSpriteIndex(i,j);
                g.drawImage(levelSprite[index],i*game.TILES_SIZE-lvlOffset,game.TILES_SIZE*j,game.TILES_SIZE,game.TILES_SIZE,null);
            }

        g.drawImage(levelSprite[2],0,0,null);


    }
    public void update(){

    }
    public Level getCurrentLevel(){
        return levelzero;
    }
}

