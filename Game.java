import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    
    public int continits = 0;

    public int point;
    
    public boolean startAgain;
    
    Random random = new Random();
    
    public Keyboard keyboard;

    Background background;
        
    Bird bird;
    
    ArrayList<Cloud> clouds;

    ArrayList<Brick> bricks;
    
    ArrayList<Minion> minions;

    boolean started = true;

    public void setStartOver(boolean flag) {
        bird.startOver = flag;
    }
    
    public Game() {
        bird = new Bird();
        background = new Background();
        bricks = new ArrayList<Brick>();
        clouds = new ArrayList<Cloud>();
        minions = new ArrayList<Minion>();
        keyboard = Keyboard.getInstance();
        
        initClouds();
    }
    
    public void initClouds() {
        int f = 5;
        for(int x=0; x<f; x++) {
            Cloud cloud = new Cloud();
            Random ra = new Random();
            int v = ra.nextInt(2);
            if(v == 0)
                cloud.x = 510 + random.nextInt(500);
            else if(v == 1)
                cloud.x = -1010 + random.nextInt(500);
            cloud.y = -random.nextInt(100) + random.nextInt(580);
            clouds.add(cloud);
        }
    }

    public void initBricks() {
        int f = 5;
        if(continits < 20)
        for(int x=0; x<f; x++) {
            Brick brick = new Brick();
            brick.x = random.nextInt(440);
            brick.y = -random.nextInt(100) + random.nextInt(600);
            bricks.add(brick);
        }
        if(continits >= 20)
        for(int x=0; x<15; x++) {
            Brick brick = new Brick();
            brick.x = random.nextInt(440);
            brick.y = -random.nextInt(100) + random.nextInt(600);
            bricks.add(brick);
            Brick brick2 = new Brick();
            brick2.x = brick.x + 30;
            bricks.add(brick2);
            Random rd = new Random();
            int v = rd.nextInt(3);
            if(v == 0)
            {
                Minion minion = new Minion();
                minion.x = brick.x;
                minion.y = brick.y;
                minions.add(minion);
            }
        }
        this.continits++;
    }

    public void update() {
        Random random = new Random();
        int randomVl = random.nextInt(2);
        boolean g = false;
        int count = 0;
        for(Cloud cloud: clouds) {
            g = false;
            if(cloud.x < -50)
            {
                g = true;
            }
            cloud.update(randomVl);
        }
        if(g) {
            initClouds();
        }
        g = false;
        count = 0;
        randomVl = random.nextInt(bricks.size());
        for(Brick brick: bricks) {
            if(count == randomVl)
            {
                if(started) {
                    bird.x = brick.x;
                    bird.y = brick.y - bird.height;
                    started = false;
                }
            }
            g = false;
            if(brick.y > 300)
                g = true;
            count++;
        }
        if(g) {
            initBricks();
        }
        this.point = bird.update(bricks, continits);
        this.startAgain = bird.startOver;
        for(int i=0; i<minions.size(); i++) {
            minions.get(i).update(bricks);
            if(bird.yvel > 0 && !keyboard.isDown(KeyEvent.VK_SPACE) && minions.get(i).y >= bird.y+25 && minions.get(i).y <= bird.y+35 && minions.get(i).x >= bird.x-30 && minions.get(i).x <= bird.x+36)
            {
                this.point += 10;
                bird.point = this.point;
                minions.remove(minions.get(i));
            }
        }
    }
    
    public ArrayList<Render> getRenders() {
        try {
            ArrayList<Render> renders = new ArrayList<Render>();
            renders.add(background.getRender());
            for(Cloud cloud : clouds)
                renders.add(cloud.getRender());
            for(Brick brick : bricks)
                renders.add(brick.getRender());
            for(Minion minion : minions)
                renders.add(minion.getRender());
            renders.add(bird.getRender());
            return renders;
        } catch(Exception e) {
            return null;
        }
    }
}