package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {
    private float x,y;
    private List<BufferedImage> frames;
    private long timeSinceUpdate = 0;
    private long delay = 10;
    private int currentFrame = 0;
    private boolean isRunning = false;

    public Animation(float x, float y, List<BufferedImage> frames) {
        this.x=x;
        this.y=y;
        this.frames = frames;
        isRunning = true;
    }
    public void update() {
        if(this.timeSinceUpdate + delay < System.currentTimeMillis()) {
            this.timeSinceUpdate = System.currentTimeMillis();
            this.currentFrame++;

            if (this.currentFrame == this.frames.size()) {
                isRunning = false;
            }
        }
    }

    public void drawImage(Graphics2D g) {
        if (this.isRunning) {
            g.drawImage(this.frames.get(currentFrame),(int)x,(int)y, null);
        }
    }


}
