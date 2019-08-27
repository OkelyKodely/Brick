import java.awt.Image;
import java.awt.image.BufferedImage;

public class Cloud {

    public int x = 0;
    public int y = 0;
    private BufferedImage image;
    private boolean moveleft = false;
    private int mv = 0;
    
    public void update(int movelft) {
        if((movelft == 1 && moveleft == false && this.x < 0) || this.x < 0)
            moveleft = false;
        else if(movelft == 0 && mv == 0 && !moveleft)
            moveleft = true;
        if(moveleft)
            this.x -= 2;
        else
            this.x += 2;
        mv = movelft;
        
        System.out.println(mv + "," + x);


        
    }
    
    public Render getRender() {

        Render r = new Render();
        r.x = x;
        r.y = y;
        
        if (image == null) {
            image = Util.loadImage("lib/cloud.png");     
        }
        r.image = image;

        return r;
    }
}