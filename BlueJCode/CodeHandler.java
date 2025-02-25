package BlueJCode;

import BlueJCode.Blockly.BlocklyHandler;
import bluej.extensions2.BClass;
import bluej.extensions2.CompilationNotStartedException;
import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import bluej.extensions2.editor.JavaEditor;
import org.apache.commons.logging.Log;
import org.apache.velocity.runtime.directive.Block;

import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

// Multiton-Class
public class CodeHandler
{
    private static CodeHandler activeInstance = null;
    public static CodeHandler getActiveInstance()
    {
        return activeInstance;
    }
    public static void setActiveInstance(BClass bClass)
    {
        activeInstance = getCodeHandler(bClass);
    }

    private static Map<BClass,CodeHandler> codeHandlers = new java.util.HashMap<>();
    public static CodeHandler getCodeHandler(BClass bClass)
    {
        if(!codeHandlers.containsKey(bClass))
        {
            codeHandlers.put(bClass,new CodeHandler(bClass));
        }
        return codeHandlers.get(bClass);
    }
    private JavaEditor editor;
    private String javaFilePath;
    private String xmlFilePath;
    private String className;

    public void open()
    {
        try
        {
            activeInstance = this;
            BlocklyHandler.Instance().openBlockly();
        }
        catch (Exception e)
        {
            Logging.log(e.toString());
        }
    }

    public String getClassName()
    {
        return className;
    }

    private CodeHandler(BClass clz)
    {
        try
        {
            javaFilePath = clz.getJavaFile().getAbsolutePath();
            xmlFilePath = javaFilePath.indexOf(".java") > 0 ? javaFilePath.replace(".java", ".xml") : javaFilePath + ".xml";
            editor = clz.getJavaEditor();
            editor.showMessage("Achtung: Aenderungen im Java-Code (hier) werden beim Starten von Blockly geloescht!");
            className = clz.getName();
        }
        catch (Exception e)
        {
            Logging.log(e.toString());
        }
    }

    public String getXml()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(xmlFilePath));
            String xml = br.lines().collect(Collectors.joining(System.lineSeparator()));
            br.close();
            return xml;
        }
        catch (Exception e)
        {
            Logging.log(e.toString());
            return "";
        }
    }

    public String getClassPrefix()
    {
        try
        {
            String p = "";
            try
            {
                    if (!editor.getBClass().getPackage().getName().equals("")) {
                    p = "package " + editor.getBClass().getPackage().getName() + ";" + System.lineSeparator() + System.lineSeparator();
                }
            }
            catch (Exception e)
            {
                throw e;
            }
            String c = editor.getBClass().getName().replaceAll(".+\\.", "");
            return p + "import java.util.*;" + System.lineSeparator() + System.lineSeparator() + "public class " + c + " {" + System.lineSeparator();
        }
        catch (Exception e)
        {
            Logging.log(e.toString());
            return "";
        }
    }

    public void writeJavaCodeToFile(String code)
    {
        try
        {
            Logging.log(">>> CodeHandler.writeJavaCodeToFile()");

            BufferedWriter br = new BufferedWriter(new FileWriter(javaFilePath));
            br.write(code);
            br.close();

            Logging.log("Wrote java code to file: " + javaFilePath);

            //editor.getBClass().getPackage().getWindow().requestFocus();
            javafx.application.Platform.runLater(() ->
            {
                try
                {
                    editor.loadFile();
                    Logging.log("File loaded");
                    editor.getBClass().compile(false);
                    Logging.log("Compiled");

                    //Logging.log("Window: "+editor.getBClass().getPackage().getWindow().getTitle());
                    //editor.getBClass().getPackage().getWindow().requestFocus();
                }
                catch (ProjectNotOpenException e)
                {
                    throw new RuntimeException(e);
                }
                catch (PackageNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
                catch (CompilationNotStartedException e)
                {
                    throw new RuntimeException(e);
                }
            });


            Logging.log("<<< CodeHandler.writeJavaCodeToFile()");
        }
        catch (Exception e)
        {
            Logging.log(e.toString());
            Logging.log(e.getStackTrace());
        }
    }

    public void writeXmlToFile(String code)
    {
        try
        {
            Logging.log(">>> CodeHandler.writeXmlToFile()");

            BufferedWriter br = new BufferedWriter(new FileWriter(xmlFilePath));
            br.write(code);
            br.close();

            Logging.log("Wrote xml to file: " + xmlFilePath);
            Logging.log("<<< CodeHandler.writeXmlToFile()");
        }
        catch (Exception e)
        {
            Logging.log(e.toString());
            Logging.log(e.getStackTrace());
            throw new RuntimeException("");
        }
    }
}
