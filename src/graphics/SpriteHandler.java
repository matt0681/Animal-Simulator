package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The SpriteHandler gets a spritesheet png from the computer and turns it into
 * individual sprite images.
 * @author Matthew
 */
public class SpriteHandler {
    
    // variables. This class uses BufferedImages for the sprites.
    // tileSize: the pixel width of each sprite. They are squares.
    // numRows & numCols: the numbers of rows and columns respectively
    // of sprites that are on the spritesheet.
    private BufferedImage spriteSheet;
    public static int tileSize = 20;
    private int numRows = 5;
    private int numCols = 10;
    private int[][] sprites;

    // Constructor ensures spritesheet exists.
    // Creates the sprites[][] array. This array is used for finding sprites later on.
    public SpriteHandler() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("\\res\\sprites.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.spriteSheet = image;
        
        sprites = new int[numRows][numCols];
        int i = 0;
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                sprites[r][c] = i;
                i++;
            }
        }
    }
    
    // Returns the BufferedImage of a specific sprite.
    // The sprite is calculated based on spriteNumber, findX(), and findY().
    // The list of sprites and their numbers is in the Sprites Class.
    public BufferedImage getSpriteImage(int spriteNumber){
        BufferedImage image;
        int x = findX(spriteNumber) * tileSize;
        int y = findY(spriteNumber) * tileSize;
        
        image = spriteSheet.getSubimage(x, y, tileSize, tileSize);
        
        return image;
    }
    
    // Calculates the X value based on a singe index. Uses sprites[][].
    private int findX(int num){
        for (int i = 0; i < sprites.length; i++) {
            for (int j = 0; j < sprites[i].length; j++) {
                if(sprites[i][j] == num){
                    return j;
                }
            }
        }
        return -1;
    }
    
    // Calculates the Y value based on a singe index. Uses sprites[][].
    private int findY(int num){
        for (int i = 0; i < sprites.length; i++) {
            for (int j = 0; j < sprites[i].length; j++) {
                if(sprites[i][j] == num){
                    return i;
                }
            }
        }
        return -1;
    }
}
