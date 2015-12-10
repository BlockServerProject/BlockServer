package org.blockserver.implementation.utilities;

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