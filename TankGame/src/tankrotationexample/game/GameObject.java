package tankrotationexample.game;

import tankrotationexample.ResourceManager;

import java.awt.*;


public abstract class GameObject {

    protected boolean hasCollided = false;

    public static GameObject newInstance(String type, float x, float y) {
        return switch (type) {
            case "2" -> new BreakableWall(x, y, ResourceManager.getSprite("bwall"));
            case "3" -> new Wall(x, y, ResourceManager.getSprite("ubwall"));
            case "4" -> new Health(x, y, ResourceManager.getSprite("health"));
            case "5" -> new Speed(x, y, ResourceManager.getSprite("speed"));
            case "6" -> new Shield(x, y, ResourceManager.getSprite("shield"));

            default -> throw new UnsupportedOperationException();
        };
    }

    public abstract void drawImage(Graphics g);
    public abstract Rectangle getHitbox();

    public boolean hasCollided () {
        return hasCollided;
    }



    public abstract void collides(GameObject obj2);
    }

