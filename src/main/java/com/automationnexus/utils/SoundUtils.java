package com.automationnexus.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;

/**
 * SoundUtils — Plays WAV sound alerts in a background thread.
 *
 * Used mainly by MaintenanceModeHandler to audibly notify the tester
 * when an element is not found, so they don't have to watch the
 * console constantly.
 */
public class SoundUtils {

    private static final Logger log =
            LogManager.getLogger(SoundUtils.class);

    private static final String RESOURCES_FOLDER = "Resources/";

    /**
     * Plays a WAV file by name, non-blocking (runs on a background thread).
     * If the file is missing or playback fails, logs a warning and
     * continues — never throws, never blocks test execution.
     *
     * @param fileName e.g. "notification.wav"
     */
    public static void playWavFile(String fileName) {
        new Thread(() -> playInternal(fileName)).start();
    }

    private static void playInternal(String fileName) {
        File soundFile = new File(RESOURCES_FOLDER + fileName);

        if (!soundFile.exists()) {
            log.warn("Sound file not found: " + soundFile.getPath());
            return;
        }

        try {
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();

            clip.open(audioStream);

            // Auto-close the clip once playback finishes,
            // so we don't leak audio resources
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();

        } catch (Exception e) {
            log.warn("Could not play sound: " + fileName
                    + " — " + e.getMessage());
        }
    }

    private SoundUtils() {
    }
}