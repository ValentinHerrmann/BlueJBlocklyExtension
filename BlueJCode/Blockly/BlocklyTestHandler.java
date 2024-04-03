package BlueJCode.Blockly;

import BlueJCode.Config;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;

public class BlocklyTestHandler extends BlocklyHandler
{
    private boolean tested = false;


    public void setTested(boolean tested)
    {
        this.tested = tested;
    }
    public boolean isTested()
    {
        return tested;
    }

    private Pair<String,String> testResultStrings = null;
    public void resetTestResultStrings()
    {
        testResultStrings = null;
    }
    public Pair<String,String> getTestResultStrings()
    {
        return testResultStrings;
    }



    private String classPrefix = "";

    public void setXmlPath(String xmlPath)
    {
        this.xmlPath = Paths.get(xmlPath).toAbsolutePath().toString();
    }

    public void setJavaPath(String javaPath)
    {
        this.javaPath = Paths.get(javaPath).toAbsolutePath().toString();
    }

    private String xmlPath = "";
    private String javaPath = "";

    private static BlocklyTestHandler instance;
    public static BlocklyTestHandler Instance()
    {
        if(instance == null)
        {
            Config.init(System.getProperty("user.dir"), System.getProperty("user.dir"));
            instance = new BlocklyTestHandler();
        }
        return instance;
    }

    private BlocklyTestHandler()
    {
        super();
    }

    public void setClassPrefix(String code)
    {
        classPrefix = code;
    }

    @Override
    public String getCurrentClassPrefix()
    {
        return classPrefix;
    }
    @Override
    public String getCurrentClassXml()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(xmlPath));
            String xml = br.lines().collect(Collectors.joining(System.lineSeparator()));
            br.close();
            return xml;
        }
        catch (Exception e)
        {
            fail("XML File not found: " + xmlPath);
            return null;
        }
    }
    private String readJavaFile()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(javaPath));
            String java = br.lines().collect(Collectors.joining(System.lineSeparator()));
            br.close();
            return java;
        }
        catch (Exception e)
        {
            fail("Java File not found: " + javaPath);
            return null;
        }
    }

    @Override
    public int incomingJavaCode(String code)
    {
        String expected = normalize(readJavaFile());
        String actual = normalize(code);

        testResultStrings = new Pair<>(expected, actual);
        tested = true;

        return 200;
    }
    @Override
    public void incomingXmlCode(String code)
    {
        // Hier irgendwie an den Test angepasst überschreiben oder einfach nichts tun.
    }


    private String normalize(String code)
    {
        code = code.replace("\r\n", "\n").replace("\r", "\n").replace("\n", " ").trim().replaceAll(" +", " ");

        return code;
    }
}
