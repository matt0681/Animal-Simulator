package graphics;

import java.awt.Graphics2D;
import maps.MapManager;

/**
 * The UserEntity class represents the user cursor. 
 * @author Matthew
 */
public class UserEntity {
    
    // userX and userY: the x and y positions respectively. these are actual GUI coordinates.
    public static int userX;
    public static int userY;
    private static SpriteHandler sprites;
    private static int count;
    
    
    public UserEntity(){
        this.userX = MapManager.offset;
        this.userY = MapManager.offset;
        this.sprites = new SpriteHandler();
        count = 0;
    }
    
    // manageUserEntity method draws the user cursor sprite onto the GUI.
    public static Graphics2D drawUserEntity(Graphics2D g){
       
        
        
        g.drawImage(sprites.getSpriteImage(Sprites.USER), userX, userY, null);
        
        return g; 
    }
    
    // The moveEntity method updates the x and y position of the user
    // based on the direction parameter passed to it.
    public static void moveEntity(int direction){
        int tileSize = SpriteHandler.tileSize;
        switch(direction){
            case 0:
                if((userY-tileSize) >= MapManager.offset){
                    userY -= tileSize;
                }
                break;
            case 1:
                if((userX+tileSize) <= (MapManager.width_X * tileSize)){
                    userX += tileSize; 
                }
                break;
            case 2:
                if((userY+tileSize) <= (MapManager.height_Y * tileSize)){
                    userY += tileSize; 
                }
                break;
            case 3:
                if((userX-tileSize) >= MapManager.offset){
                    userX -= tileSize; 
                }
                break;
        }
    }
}
