package pl.radoszewski.slick.utils;

import java.io.*;
import java.lang.reflect.Field;

public class NativeLoader {

    private static String extractPath;

    static {
        extractPath = setupExtractPath();
        setupLibraryPath(extractPath);

        System.out.println("Will extract libraries to " + extractPath);
    }

    private static String setupExtractPath() {
        String path = System.getProperty("java.io.tmpdir") + "/slick-libraries/";
        File tmpDir = new File(path);
        tmpDir.mkdirs();
        return tmpDir.getAbsolutePath();
    }

    private static void setupLibraryPath(String path) {
        String libraryPath = System.getProperty("java.library.path") + File.pathSeparator + path;
        System.setProperty("java.library.path", libraryPath);

        final Field sysPathsField;
        try {
            sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void extractFromjar(String path) throws IOException {
        File file = new File(path);
        File temp = new File(extractPath, file.getName());

        byte[] buffer = new byte[1024];
        int readBytes = 0;

        try(
                InputStream is = NativeLoader.class.getResourceAsStream(path);
                OutputStream os = new FileOutputStream(temp)
        ) {
            if (is == null) {
                throw new FileNotFoundException("Couldn't find library to extract!");
            }
            while((readBytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }
            System.out.println("Extracted " + file.getName() + "...");
        }

    }

    public static void loadFromClassPath(String path) throws IOException {
        File file = new File(path);
        String[] name = file.getName().split("\\.", 2);
        String prefix = name[0];
        String suffix = "." + name[1];

        File temp = File.createTempFile(prefix, suffix);
        temp.deleteOnExit();

        byte[] buffer = new byte[1024];
        int readBytes = 0;

        try(
                InputStream is = NativeLoader.class.getResourceAsStream(path);
                OutputStream os = new FileOutputStream(temp)
        ) {
            if (is == null) {
                throw new FileNotFoundException("Couldn't find library to extract!");
            }
            while((readBytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }

            System.load(temp.getAbsolutePath());
            System.out.println("Loaded " + file.getName() + "...");
        }

    }

}
