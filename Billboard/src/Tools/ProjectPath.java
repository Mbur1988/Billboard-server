package Tools;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectPath {

    /**
     * Gets the absolute path of the project root directory as a string
     * @return project root directory String e.g C:\User\Billboard
     */
    public static String RootString() {
        return System.getProperty("user.dir");
    }

    /**
     * Gets the absolute path of the project root directory as a Path object
     * @return project root directory Path e.g C:\User\Billboard
     */
    public static Path RootPath() {
        Path path = Paths.get(".");
        path = path.normalize();
        path = path.toAbsolutePath();
        return path;
    }
<<<<<<< Updated upstream
=======

    /**
     * Gets the absolute path of the project resources directory as a string
     * @return project resources directory String e.g C:\Billboard\Resources
     */
    public static String ResourcesString() {
        File file = new File("Resources");
        return file.getAbsolutePath();
    }

    /**
     * Gets the absolute path of the project resources directory as a Path object
     * @return project resources directory Path e.g C:\Billboard\Resources
     */
    public static Path ResourcesPath() {
        Path path = Paths.get("Billboard\\Resources");
        path = path.normalize();
        path = path.toAbsolutePath();
        return path;
    }

     /**
     * Gets the absolute path of a specified project file as a string
     * @param filename filename including relative path
     * @return project resources file String
     */
    public static String FileString(String filename) {
        File file = new File(filename);
        return file.getAbsolutePath();
    }

    /**
     * Gets the absolute path of a specified project file as a Path object
     * @param filename filename including relative path
     * @return project resources directory Path e.g C:\Billboard\Resources
     */
    public static Path FilePath(String filename) {
        Path path = Paths.get(filename);
        path = path.normalize();
        path = path.toAbsolutePath();
        return path;
    }
>>>>>>> Stashed changes
}
