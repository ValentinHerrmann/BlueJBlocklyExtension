package BlueJCode;

import BlueJCode.Blockly.BlocklyHandler;
import bluej.extensions2.*;
import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.PackageListener;
import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;

import java.net.URL;

public class BlueJBlocklyExtension extends Extension implements PackageListener
{
    private BlueJ bj;

    public BlueJBlocklyExtension()
    {

    }

    public static void main(String[] args)
    {

    }


    public void startup(BlueJ bluej) {
        this.bj = bluej;
        bluej.addPackageListener(this);
        MenuBuilder myMenus = new MenuBuilder();
        myMenus.setBJ(bluej);
        bluej.setMenuGenerator(myMenus);

        Preferences myPrefs = new Preferences(bluej);
        bluej.setPreferenceGenerator(myPrefs);
    }

    public void packageOpened(PackageEvent ev) {
        try
        {
            Config.init(bj.getSystemLibDir().getAbsolutePath(), ev.getPackage().getProject().getDir().getAbsolutePath());

            Logging.log("ProjectDir: " + Config.ProjectPath());
            Logging.log("LibDir: " + Config.LibPath());
            Logging.log("BlocklyHtmlPath: " + Config.BlocklyHtmlPath());

            Logging.log("getUserConfigDir: "+bj.getUserConfigDir());
            Logging.log("Package " + ev.getPackage().getName() + " opened.");
            Logging.log("Project " + ev.getPackage().getProject().getName() + " opened.");
        }
        catch (ExtensionException var3)
        {
            Logging.log("Project closed by BlueJ");
        }

    }

    public void packageClosing(PackageEvent ev) {
    }

    public boolean isCompatible() {
        return getExtensionsAPIVersionMajor() >= 3;
    }

    public String getVersion(){
        return Config.Version();
    }

    public String getName() {
        return "BlueJ Blockly Extension";
    }

    public void terminate()
    {
        BlocklyHandler.Instance().closeBlockly();
        System.out.println("Simple extension terminates");
    }

    public String getDescription()
    {
        return "Eine blockbasiert BlueJ-Erweiterung, deren Bloecke automatisch in Java uebersetzt werden. ";
    }

    public URL getURL()
    {
        try
        {
            return new URL("");
        }
        catch (Exception var2)
        {
            System.out.println("Simple extension: getURL: Exception=" + var2.getMessage());
            return null;
        }
    }
}
