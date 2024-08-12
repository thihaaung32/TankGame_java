package tankrotationexample;
import tankrotationexample.game.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;



public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap();
    private final static Map<String, Sound> sounds = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animations = new HashMap<>();
    private final static Map<String, Integer> animationInfo = new HashMap<>() {{
        put("bullethit", 24);
        put("bulletshoot", 24);
        put("explosionlg", 6);
        put("powerpick", 32);
        put("puffsmoke", 32);
        put("rocketflame", 16);
        put("rockethit", 32);



    }};

    private static BufferedImage loadSprites(String path) throws IOException {

        try (InputStream is = ResourceManager.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Resource not found: " + path);
            }
            return ImageIO.read(is);

        }
    }

    private static Sound loadSound(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(ResourceManager.class.getResource(path)));
        Clip c = AudioSystem.getClip();
        c.open(ais);
        Sound s = new Sound(c);
        s.setVolume(.1f);
        return s;
    }


    private static void initSprites() {
        try {
            ResourceManager.sprites.put("tank1", loadSprites("resources/tank/tank1.png"));
            ResourceManager.sprites.put("tank2", loadSprites("resources/tank/tank2.png"));
            ResourceManager.sprites.put("tank3", loadSprites("resources/tank/tank3.png"));
            ResourceManager.sprites.put("bullet", loadSprites("resources/bullets/Weapon.gif"));
            ResourceManager.sprites.put("bwall", loadSprites("resources/walls/breakableWall.png"));
            ResourceManager.sprites.put("ubwall", loadSprites("resources/walls/unbreakableWall.png"));
            ResourceManager.sprites.put("shield", loadSprites("resources/PowerUp/shield.png"));
            ResourceManager.sprites.put("health", loadSprites("resources/PowerUp/health.png"));
            ResourceManager.sprites.put("speed", loadSprites("resources/PowerUp/speed.png"));
            ResourceManager.sprites.put("bg", loadSprites("resources/floor/bg.bmp"));
            ResourceManager.sprites.put("menu", loadSprites("resources/menu/title.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }


    public static void loadResources() {
        ResourceManager.initSprites();
        ResourceManager.initAnimations();
        ResourceManager.initSounds();
    }


    public static BufferedImage getSprite(String type) {
        if (!ResourceManager.sprites.containsKey(type)) {
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.sprites.get(type);
    }


    public static List<BufferedImage> getAnimation(String type) {
        if(!ResourceManager.animations.containsKey(type)) {
            throw new RuntimeException("%s resource is missing.".formatted(type));
        }
        return ResourceManager.animations.get(type);
    }

    public static Sound getSound(String type) {
        if(!ResourceManager.sounds.containsKey(type)) {
            throw new RuntimeException("%s resource is missing.".formatted(type));
        }
        return ResourceManager.sounds.get(type);
    }

    private static void initAnimations() {
        String baseName = "resources/animations/%s/%s_%04d.png";
        ResourceManager.animationInfo.forEach((animationName, frameCount) -> {
            List<BufferedImage> frames = new ArrayList<>();
            try {
                for (int i = 0; i < frameCount; i++) {
                    String spritePath = baseName.formatted(animationName, animationName, i);
                    frames.add(loadSprites(spritePath));
                }
                ResourceManager.animations.put(animationName, frames);
            } catch (IOException e) {
                System.out.println(e);
                    throw new RuntimeException(e);
            }
            });

        }

    private static void initSounds() {
        try {
            ResourceManager.sounds.put("bullet_shoot", loadSound("resources/sounds/bullet_shoot.wav"));
            ResourceManager.sounds.put("explosion", loadSound("resources/sounds/explosion.wav"));
            ResourceManager.sounds.put("bgs", loadSound("resources/sounds/Music.mid"));
            ResourceManager.sounds.put("pickup", loadSound("resources/sounds/pickup.wav"));
            ResourceManager.sounds.put("shotfire", loadSound("resources/sounds/shotfiring.wav"));
            ResourceManager.sounds.put("Tank_Explore", loadSound("resources/sounds/Explosion_large.wav"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
