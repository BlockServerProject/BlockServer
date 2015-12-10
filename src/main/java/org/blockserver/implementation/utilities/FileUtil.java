package org.blockserver.implementation.utilities;

import java.io.File;

public class FileUtil {
    public static File getDirectoryUp(File file, int amount) {
        while (amount != 0) {
            file = file.getParentFile();
            amount--;
        }
        return file;
    }

    public static File searchFolder(File folder, String uniqueContent) {
        return searchFolder(folder, uniqueContent, 0);
    }

    private static File searchFolder(File folder, String uniqueContent, int depth) {
        if (folder != null)
            for (File file : folder.listFiles()) {
                if (file.getName().contains(uniqueContent))
                    return file.getParentFile();
                if (file.isDirectory()) {
                    File result = searchFolder(file, uniqueContent, depth++);
                    if (result != null)
                        return getDirectoryUp(result, depth - 1);
                }
            }
        return null;
    }


}
