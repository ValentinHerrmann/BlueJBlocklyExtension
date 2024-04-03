package BlueJCode;
import bluej.extensions2.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

class MenuBuilder extends MenuGenerator
{
    private BPackage curPackage;
    private BClass curClass;
    private BObject curObject;
    private EventHandler menPopupAction = menuPopupAction();
    private BlueJ bj;

    public void setBJ(BlueJ b)
    {
        bj = b;
    }

    public MenuItem getClassMenuItem(BClass aClass)
    {
        MenuItem mi = new MenuItem();
        mi.setId("Class menu:");
        mi.setText("Blockly");
        mi.setOnAction(openBlocklyEvent());
        return mi;
    }

    // These methods will be called when
    // each of the different menus are about to be invoked.

    public EventHandler openBlocklyEvent()
    {
        return actionEvent ->
        {
            Logging.log(">>> MenuBuilder.openBlocklyEvent");
            CodeHandler.setActiveInstance(curClass);
            closeAllWindowsExceptMain();
            //Blocklyfenster
            try
            {
                CodeHandler.getCodeHandler(curClass).open();
            }
            catch(Exception ex)
            {
                Logging.log(ex.toString());
            }
            Logging.log("<<< MenuBuilder.openBlocklyEvent");
        };
    }

    public void notifyPostClassMenu(BClass bc, MenuItem mi)
    {
        System.out.println("Post on Class menu");
        curPackage = null;
        curClass = bc;
        curObject = null;
    }

    // A utility method which pops up a dialog detailing the objects
    public EventHandler menuPopupAction()
    {
        return actionEvent ->
        {
            try
            {
                if (curObject != null)
                    curClass = curObject.getBClass();
                if (curClass != null)
                    curPackage = curClass.getPackage();

                String msg = ((MenuItem) actionEvent.getSource()).getId();
                if (curPackage != null)
                    msg += "\nCurrent Package = " + curPackage;
                if (curClass != null)
                    msg += "\nCurrent Class = " + curClass;
                if (curObject != null)
                    msg += "\nCurrent Object = " + curObject;

                Alert dlg = new Alert(Alert.AlertType.NONE, msg, ButtonType.OK);
                dlg.initStyle(StageStyle.UTILITY);
                dlg.showAndWait();
            } catch (Exception exc)
            {
                exc.printStackTrace();
            }
        };
    }

    private void closeAllWindowsExceptMain()
    {
        try
        {
            //BlueJfenster
            for(Window bjwindow : Stage.getWindows())
            {
                if(bjwindow instanceof Stage)
                {
                    try
                    {
                        Stage s = (Stage)bjwindow;
                        Logging.log("BlueJ Window Title: '" + s.getTitle()+"'");
                        if(!s.getTitle().startsWith("BlueJ:  "))
                        {
                            Logging.log("Closed");
                            s.close();
                        }
                        else
                        {
                            Logging.log("Mainwindow remains open");
                        }
                    }
                    catch(Exception e)
                    {
                        Logging.log(e.toString());
                        Logging.log(e.getStackTrace());
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logging.log(e.toString());
            Logging.log(e.getStackTrace());
        }
    }
}