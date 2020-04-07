package Tools;

public class ProjectPath {

    /**
     * Gets the absolute path of the project root directory
     * @return project root directory e.g C:/User/Billboard/
     */
    public static String AsString() {
        ClassLoader loader = ProjectPath.class.getClassLoader();
        String output = loader.getResource("Tools/ProjectPath.class").toString();
        output = output.replaceFirst("file:/", "");
        output = output.replaceFirst("out/production/Billboard/Tools/ProjectPath.class", "");
        return output;
    }
}
