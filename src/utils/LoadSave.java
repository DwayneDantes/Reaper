package utils;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

import entities.Golem;

import static utils.Constants.EnemyConstants.*;


import main.Game;
public class LoadSave {
    public static final String PLAYER_ATLAS = "Sprite.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_0= "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "Menubackground.gif";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String PLAYING_BG_IMG = "plx-1.png";
    public static final String PLX2 = "plx-2.png";
    public static final String PLX3 = "plx-3.png";
    public static final String PLX4 = "plx-4.png";
    public static final String PLX5 = "plx-5.png";
    public static final String GOLEM = "GOLEM2.png";
    public static final String STATUS_BAR = "Healthbar.png";


    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img =null;
        InputStream is =LoadSave.class.getResourceAsStream("/res/"+fileName);

            try {
             img = ImageIO.read(is);
            
            
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
            return img;
    }
    public static ArrayList<Golem> GetGolems(){
        BufferedImage img = GetSpriteAtlas(LEVEL_0);
        ArrayList<Golem> list = new ArrayList<>();
        for(int j =0;j<img.getHeight();j++)
            for (int i=0;i<img.getWidth(); i++){
                Color color =new Color(img.getRGB(i,j));
                int value = color.getGreen();
                if(value == GOLEM2)
                    list.add(new Golem(i*Game.TILES_SIZE,j*Game.TILES_SIZE));

            }
            return list;
    }


    public static int[][] GetLevelData(){
       
        BufferedImage img = GetSpriteAtlas(LEVEL_0);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int j =0;j<img.getHeight();j++)
            for (int i=0;i<img.getWidth(); i++){
                Color color =new Color(img.getRGB(i,j));
                int value = color.getRed();
                if(value>=48)
                    value =0;
                lvlData[j][i]=value;
            }
            return lvlData;
    }
}
