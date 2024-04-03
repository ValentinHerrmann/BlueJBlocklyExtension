package BlueJCode.Blockly;

import BlueJCode.CodeHandler;
import BlueJCode.Config;
import BlueJCode.Logging;

import java.awt.*;


public class BlocklyHandler
{
    private static BlocklyHandler instance;
    public static BlocklyHandler Instance()
    {
        return instance == null ? (instance = new BlocklyHandler()) : instance;
    }

    private RestAPI restAPI;
    private MainFrame frame;


    protected BlocklyHandler()
    {
        try
        {
            Logging.log(">>> Init BlocklyHandler");
            Logging.log("Opening Blockly from: "+ Config.BlocklyHtmlPath());

            restAPI = new RestAPI(this);
            frame = new MainFrame(Config.BlocklyHtmlPath(), false, false, new String[]{Config.BlocklyHtmlPath()});

            Logging.log("<<< Init BlocklyHandler successful");
        }
        catch (Exception e)
        {
            Logging.log(e.toString());
        }
    }

    public void openBlockly(double x, double y)
    {
        try
        {
            Runnable runnable =
                    () ->
                    {
                        Logging.log(">>> Open Blockly(x,y)");
                        frame.reload();
                        frame.setVisible(true);
                        if(x >= 0 || y >= 0)
                        {
                            frame.setBounds((int) (x / 2), 0, (int) (x / 2), (int) y);
                        }
                        else
                        {
                            frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
                        }
                        frame.setTitle("BlueJ-Blockly: " + CodeHandler.getActiveInstance().getClassName());
                        Logging.log("<<< Open Blockly(x,y) successful");
                    };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        catch(Exception ex)
        {
            Logging.log(ex.toString());
        }
    }

    public void openBlockly()
    {
        try
        {
            openBlockly(-1,-1);
        }
        catch(Exception ex)
        {
            Logging.log(ex.toString());
        }
    }

    public void hideBlockly()
    {
        try
        {
            Logging.log(">>> Hide Blockly");
            frame.dispose();
            Logging.log("<<< Hide Blockly successful");
        }
        catch(Exception ex)
        {
            Logging.log(ex.toString());
        }
    }

    public void closeBlockly()
    {
        try
        {
            Logging.log(">>> Close Blockly");
            if(frame != null)
            {
                frame.realDispose();
                frame = null;
            }
            else
            {
                Logging.log("frame is null");
            }
            if(restAPI != null)
            {
                restAPI.terminate();
                restAPI = null;
            }
            else
            {
                Logging.log("restAPI is null");
            }
            Logging.log("<<< Close Blockly successful");
        }
        catch(Exception ex)
        {
            Logging.log(ex.toString());
        }
    }

    int incomingJavaCode(String code)
    {
        try
        {
            CodeHandler.getActiveInstance().writeJavaCodeToFile(code);
            return 200;
        }
        catch(Exception e)
        {
            Logging.log(e.toString());
            return 400;
        }
    }

    String getCurrentClassXml()
    {
        return CodeHandler.getActiveInstance().getXml();
    }

    void incomingXmlCode(String code)
    {
        CodeHandler.getActiveInstance().writeXmlToFile(code);
    }

    String getCurrentClassPrefix()
    {
        return CodeHandler.getActiveInstance().getClassPrefix();
    }
}
