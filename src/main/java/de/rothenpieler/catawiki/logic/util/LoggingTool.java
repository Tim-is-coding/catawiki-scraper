package de.rothenpieler.catawiki.logic.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingTool {

    /**
     * Logs a fancy progress bar for the given input. Uses standard logger set to log level INFO
     *
     * @param remain
     * @param total
     */
    public static void logProgressInPercentage(int remain, int total) {
        if (remain > total) {
            throw new IllegalArgumentException();
        }
        int maxBareSize = 10; // 10unit for 100%
        int remainProcent = ((100 * remain) / total) / maxBareSize;
        char defaultChar = '-';
        String icon = "*";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append("[");
        for (int i = 0; i < remainProcent; i++) {
            bareDone.append(icon);
        }
        String bareRemain = bare.substring(remainProcent);
        log.info("" + bareDone + bareRemain + " " + remainProcent * 10 + "%");
        if (remain == total) {
            log.info("\n");
        }
    }
}
