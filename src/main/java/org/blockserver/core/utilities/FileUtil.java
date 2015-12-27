/*
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.core.utilities;

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
