package org.blockserver.server.core.util;

/**
 * Misc utility class
 *
 * @author BlockServer Team
 */
public abstract class MiscUtils {
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes) {
            sb.append(String.format("%02X", b)).append(" ");
        }
        return sb.toString();
    }
}
