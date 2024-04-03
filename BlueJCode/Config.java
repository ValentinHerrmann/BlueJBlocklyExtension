package BlueJCode;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config
{
    private static final String version = "1.5.0";
    private static final String blocklyHtmlPath = "/Blockly/custom-generator-codelab/dist/index.html";
    private static String libPath = null;
    private static String projectPath = null;
    private static String htmlPath = null;
    private static boolean logActive = true;


    public static void init(String _libPath, String _projectPath)
    {
        libPath = Paths.get(_libPath).toAbsolutePath().toString();
        projectPath = Paths.get(_projectPath).toAbsolutePath().toString();

        Logging.log("LibPath: " + libPath);
        Logging.log("ProjectPath: " + projectPath);

        htmlPath = projectPath + blocklyHtmlPath;

        logActive = Files.exists(Paths.get(projectPath + "/Blockly/logactive.txt"));

        if(Files.exists(Paths.get(htmlPath)))
        {
            Logging.log("Detected Blockly in Project Dir. For loading Blockly vom BlueJ's Lib Folder delete the Blockly folder in the Project Dir before startup.");

        }
        else
        {
            htmlPath = libPath + blocklyHtmlPath;
            Logging.log("Using Blockly from BlueJ's Lib Folder.");
        }

    }

    public static String LibPath()
    {
        return libPath;
    }
    public static String ProjectPath()
    {
        return projectPath;
    }
    public static String BlocklyHtmlPath()
    {
        return htmlPath;
    }
    public static String Version()
    {
        return version;
    }
    public static boolean LogActive()
    {
        return logActive;
    }
}
