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

public class StreamUtil {

    private StreamUtil() {
    }

    public static void write(CharSequence path, InputStream stream, boolean close) {
        write(new File(path.toString()), stream, close);
    }

    public static void write(File file, InputStream stream, boolean close) {
        file.getParentFile().mkdirs();
        if (file.exists())
            file.delete();

        OutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream = new BufferedOutputStream(fileOutputStream);
          //  IOUtils.copy(stream, fileOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeQuietly(fileOutputStream);
            if (close)
                StreamUtil.closeQuietly(stream);
        }
    }

    public static void writeBuffer(File file, InputStream inputStream, boolean close) {
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(inputStream);

            outputStream = new FileOutputStream(file);
            outputStream = new BufferedOutputStream(outputStream);

            //IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeQuietly(outputStream);
            if (close)
                StreamUtil.closeQuietly(inputStream);
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null)
            try {
                if (closeable.getClass().isAssignableFrom(OutputStream.class))
                    ((OutputStream) closeable).flush();
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Gets bytes from InputStream
     *
     * @param stream
     * The InputStream
     * @return
     * Returns a byte[] representation of given stream
     */

    public static byte[] getBytesFromIS(InputStream stream) {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
        } catch (Exception e) {
            System.err.println("Failed to convert IS to byte[]!");
            e.printStackTrace();
        }

        return buffer.toByteArray();

    }

    /**
     * Gets bytes from class
     *
     * @param clazz
     * The class
     * @return
     * Returns a byte[] representation of given class
     */

    public static byte[] getBytesFromClass(Class<?> clazz) {
        return getBytesFromIS(clazz.getClassLoader().getResourceAsStream( clazz.getName().replace('.', '/') + ".class"));
    }
}