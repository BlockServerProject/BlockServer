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

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ArchiveUtils {
    //TODO Make this whole thing not suck
    private ArchiveUtils() {
    }

    public static void downloadFileInto(CharSequence stringURL, File directory) {
        try {
            URL url = new URL(stringURL.toString());
            unzipIntoDirectory(url.openStream(), directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File downloadFile(CharSequence stringURL, File directory) {
        try {
            if (directory.isFile()) {
                return null;
            }
            directory.mkdirs();
            URL url = new URL(stringURL.toString());
            String urlPath = url.getPath();
            String fileName = urlPath.substring(urlPath.lastIndexOf('/') + 1);

            String[] fileComponents = fileName.split("\\.");

            String extension = "";
            if (fileComponents.length >= 2)
                extension = fileComponents[1];

            if (!extension.equals("zip"))
                Files.copy(url.openStream(), Paths.get(directory.getPath() + extension), StandardCopyOption.REPLACE_EXISTING);
            else
                unzipIntoDirectory(url.openStream(), directory);
            return directory;
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void copyFile(File from, File into) {
        if (!from.exists())
            return;
        if (from.getPath().equals(into.getPath()))
            return;
        from.setReadable(true);
        if (into.exists())
            into.delete();
        try {
            StreamUtil.writeBuffer(into, new FileInputStream(from), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unzipIntoDirectory(File file, File directory) {
        try {
            unzipIntoDirectory(new FileInputStream(file), directory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void unzipIntoDirectory(InputStream inputStream, File directory) {
        if (directory.isFile())
            return;
        directory.mkdirs();

        try {
            inputStream = new BufferedInputStream(inputStream);
            inputStream = new ZipInputStream(inputStream);

            for (ZipEntry entry = null; (entry = ((ZipInputStream) inputStream).getNextEntry()) != null; ) {
                StringBuilder pathBuilder = new StringBuilder(directory.getPath()).append('/').append(entry.getName());
                File file = new File(pathBuilder.toString());

                if (entry.isDirectory()) {
                    file.mkdirs();
                    continue;
                }

                StreamUtil.write(pathBuilder, inputStream, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeQuietly(inputStream);
        }
    }

}