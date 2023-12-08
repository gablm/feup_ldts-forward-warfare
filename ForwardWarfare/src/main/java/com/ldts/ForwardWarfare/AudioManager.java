package com.ldts.ForwardWarfare;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static AudioManager audioManager;
    private static Map<String, Clip> currentClip = new HashMap<>();

    public static AudioManager get() {
        if (audioManager == null)
            audioManager = new AudioManager();
        return audioManager;
    }

    public void play(String url) {
        play(url, -1);
    }
    public void play(String url, int loopCount) {
        try {
            if (currentClip.containsKey(url)) {
                currentClip.get(url).start();
            }
            currentClip.put(url, AudioSystem.getClip());
            Clip newClip = currentClip.get(url);
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    this.getClass().getResourceAsStream("/sounds/" + url));
            newClip.open(inputStream);
            newClip.loop(loopCount);
            newClip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void pause(String url) {
        currentClip.get(url).stop();
    }

    public static void unpause(String url) {
        currentClip.get(url).start();
    }

    public static void stop(String url) {
        currentClip.get(url).stop();
        currentClip.get(url).close();
    }
}
