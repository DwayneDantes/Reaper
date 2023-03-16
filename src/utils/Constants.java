package utils;
import  main.Game;
public class Constants {


    public static class EnemyConstants{
        public static final int GOLEM2 = 0;
        
        public static final int IDLE = 0;
        public static final int HIT = 4;
        public static final int ATTACK = 2;
        public static final int DEATH=3;
        public static final int RUNNING =1;

        public static final int GOLEM_WIDTH_DEFAULT = 64;
        public static final int GOLEM_HEIGHT_DEFAULT =64;

        public static final int GOLEM_WIDTH =(int)(GOLEM_WIDTH_DEFAULT*Game.SCALE);
        public static final int GOLEM_HEIGHT=(int)(GOLEM_HEIGHT_DEFAULT*Game.SCALE);

        public static final int GOLEM_DRAWOFFSET_X = (int)(21*Game.SCALE);
        public static final int GOLEM_DRAWOFFSET_Y = (int)(15*Game.SCALE);

        public static int GetSpriteAmount(int enemy_type,int enemy_state){
                switch(enemy_type){
                    case GOLEM2:
                        switch(enemy_state){
                            case IDLE:
                                return 4;
                            case RUNNING:
                                return 4;
                            case ATTACK:
                                return 8;
                            case DEATH:
                                return 8;
                            case HIT:
                                return 5;


                        }
                }
                return 0;
        }
        public static int GetMaxHealth(int enemy_type){
            switch(enemy_type){
                case GOLEM2:
                    return 10;
                default:
                    return 1;
            }

        }
        public static  int GetEnemyDmg(int enemy_type){
            switch(enemy_type){
                case GOLEM2:
                    return 40;
                default:
                    return 0;
            }
        }
    }

    public static class Environment{
        //PLX2
        public static final int PLX2_WIDTH_DEFAULT= 384;
        public static final int PLX2_HEIGHT_DEFAULT =216;
        //PLX3
        public static final int PLX3_WIDTH_DEFAULT= 384;
        public static final int PLX3_HEIGHT_DEFAULT =216;
        //PLX4
        public static final int PLX4_WIDTH_DEFAULT= 384;
        public static final int PLX4_HEIGHT_DEFAULT =216;
        //PLX5
        public static final int PLX5_WIDTH_DEFAULT= 384;
        public static final int PLX5_HEIGHT_DEFAULT =216;

        public static final int PLX2_WIDTH =((int)(PLX2_WIDTH_DEFAULT*Game.SCALE));
        public static final int PLX2_HEIGHT=((int)(PLX2_HEIGHT_DEFAULT*Game.SCALE));

        public static final int PLX3_WIDTH =(int)(PLX2_WIDTH_DEFAULT*Game.SCALE);
        public static final int PLX3_HEIGHT=(int)(PLX2_HEIGHT_DEFAULT*Game.SCALE);
        
        public static final int PLX4_WIDTH =(int)(PLX2_WIDTH_DEFAULT*Game.SCALE);
        public static final int PLX4_HEIGHT=(int)(PLX2_HEIGHT_DEFAULT*Game.SCALE);
        
        public static final int PLX5_WIDTH =(int)(PLX2_WIDTH_DEFAULT*Game.SCALE);
        public static final int PLX5_HEIGHT=(int)(PLX2_HEIGHT_DEFAULT*Game.SCALE);
    }

    public static class UI{
        public static class Buttons{
                public static final int B_WIDTH_DEFAULT =140;
                public static final int B_HEIGHT_DEFAULT =56;
                public static final int B_WIDTH =(int) (B_WIDTH_DEFAULT*Game.SCALE);
                public static final int B_HEIGHT=(int)(B_HEIGHT_DEFAULT*Game.SCALE);
        }

    public static class PauseButtons{
            public  static final int SOUND_SIZE_DEFAULT= 42;
            public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT*Game.SCALE);
    }

    public static class URMButtons{
        public static final int URM_DEFAULT_SIZE = 56;
        public static final int URM_SIZE=(int)(URM_DEFAULT_SIZE*Game.SCALE);
    }

    public static class VolumeButtons{
        public static final int VOLUME_DEFAULT_WIDTH = 28;
        public static final int VOLUME_DEFAULT_HEIGHT=44;
        public static final int SLIDER_DEFAULT_WIDTH= 215;
        public static final int SLIDER_WIDTH =(int)(SLIDER_DEFAULT_WIDTH*Game.SCALE);
        
        public static final int VOLUME_WIDTH =(int)(VOLUME_DEFAULT_WIDTH*Game.SCALE);
        public static final int VOLUME_HEIGHT=(int)(VOLUME_DEFAULT_HEIGHT*Game.SCALE);
        
    }
    }

    


    public static class Directions{
        public static final int LEFT =0;
        public static final int UP =1;
        public static final int RIGHT =2;
        public static final int DOWN =3;

    }
    public static class PlayerConstants{
        public static final int RUNNING = 13;
        public static final int IDLE = 10;
        public static final int JUMP = 11;
        public static final int FALLING=9;
        public static final int GROUND=3;
        public static final int HIT=12;
        public static final int ATTACK_1=0;
        public static final int ATTACK_JUMP_1=2;
        public static final int ATTACK2=1;
        public static final int DASH = 7;
        public static final int DEATH=8;

        public static int GetSpriteAmount(int player_action){

            switch(player_action) {
            
            case RUNNING:
                return 10;
            case IDLE:
                return 10;
            case JUMP:
                return 3;
            case FALLING:
                return 3;
            case GROUND:
                return 3;
            case HIT:
                return 1;
            case ATTACK_1:
                return 4;
            case ATTACK_JUMP_1:
                return 10;
            case ATTACK2:
                return 6;
            case DASH:
                return 2;
            case DEATH:
                return 11;
            default:
                return 1;
            }
        }
    }
}
