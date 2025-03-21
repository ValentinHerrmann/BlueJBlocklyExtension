package BlueJCode;


import org.apache.commons.logging.Log;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class Config
{
    private static final String version = "1.6.1";
    private static String blocklyHtmlPath = "/Blockly/index.html";
    private static String jcefBundlePath = "C:\\Users\\Public\\jcef-bundle\\";
    private static String libPath = null;
    private static String projectPath = null;
    private static String htmlPath = null;
    private static boolean logActive = true;
    private static List<String> noBlocklyClasses;
    private static Map<String, Map<String, String>> iniMap;


    public static void init(String _libPath, String _projectPath)
    {
        libPath = Paths.get(_libPath).toAbsolutePath().toString();
        projectPath = Paths.get(_projectPath).toAbsolutePath().toString();

        Logging.log("LibPath: " + libPath);
        Logging.log("ProjectPath: " + projectPath);

        if(readIni(projectPath + "/Blockly/BBE.ini") || readIni(libPath + "/Blockly/BBE.ini"))
        {
            try { logActive = iniMap.get("Logging").get("active").equalsIgnoreCase("true"); }
            catch (Exception e) { Logging.log(e.toString()); }

            try { blocklyHtmlPath = iniMap.get("Paths").get("blocklyHtmlPath"); }
            catch (Exception e) { Logging.log(e.toString()); }

            try { jcefBundlePath = iniMap.get("Paths").get("jcefBundlePath"); }
            catch (Exception e) { Logging.log(e.toString()); }
        }
        try { noBlocklyClasses = readNoBlocklyClasses(); }
        catch (Exception e) { Logging.log(e.toString()); }

        htmlPath = projectPath + blocklyHtmlPath;
        if(Files.exists(Paths.get(htmlPath)))
        {
            Logging.log("Detected Blockly in Project Dire. For loading Blockly vom BluJ's Lib Folder delete the Blockly folder in the Project Dir before startup.");

        }
        else
        {
            htmlPath = libPath + blocklyHtmlPath;
            Logging.log("Using Blockly from BlueJ's Lib Folder.");
        }

    }

    private static List<String> readNoBlocklyClasses()
    {
        Path configPath = Paths.get(projectPath, "Blockly", "noBlocklyClasses.config");
        try
        {
            if (!Files.exists(configPath.getParent())) {
                Files.createDirectories(configPath.getParent());
            }
            if (!Files.exists(configPath)) {
                Files.createFile(configPath);
            }
            return Files.readAllLines(configPath, StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            Logging.log(e.toString());
            return Collections.emptyList();
        }
    }

    private static boolean readIni(String iniPath)
    {
        try
        {
            File fileToParse = new File(Paths.get(iniPath).toAbsolutePath().toString());
            if(!fileToParse.exists())
            {
                Logging.log(fileToParse.getAbsolutePath() + " is no valid ini file.");
                return false;
            }
            Ini ini = new Ini(fileToParse);
            iniMap = ini.entrySet().stream().collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
            iniMap.forEach((key, value) -> {
                Logging.log("Key: " + key);
                value.forEach((k, v) -> {
                    Logging.log("Key: " + k + " Value: " + v);
                });
            });
            return true;
        }
        catch(Exception e)
        {
            Logging.log(e.toString());
            return false;
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
    public static String JcefBundlePath()
    {
        return jcefBundlePath;
    }
    public static List<String> NoBlocklyClasses()
    {
        return noBlocklyClasses;
    }
}
