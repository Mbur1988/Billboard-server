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
}
