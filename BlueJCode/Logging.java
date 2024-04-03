package BlueJCode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Logging {
    private File file = null;
    private static Logging instance = null;
    private final static List<String> preInstantiationLogs = new ArrayList<String>();


    private static Logging Instance()
    {
        if (instance == null)
        {
            new Logging(Config.ProjectPath());
        }
        return instance;
    }



    private Logging(String path)
    {
        if(Config.LogActive())
        {
            String dir = path + "\\logs\\";

            System.out.println("Opening log-file at:\t" + dir + "\\BBE.log");
            (new File(dir)).mkdirs();
            this.file = new File(dir + "/BBE.log");

            try {
                if (this.file.createNewFile()) {
                    System.out.println("File created successfully");
                } else {
                    System.out.println("File opened successfully");
                }
                instance = this;
                log("---------- Logging instantiated for BBE Version " + Config.Version() + " ----------");

                writeToFile(String.join(System.lineSeparator(), preInstantiationLogs));

            } catch (IOException var3) {
                System.out.println("FAILED");
                throw new RuntimeException(var3);
            }
        }
    }

    public static void log(StackTraceElement[] e)
    {
        if(Config.LogActive())
        {
            List<String> l = new ArrayList<>();
            for (StackTraceElement s : e) {
                l.add(s.toString());
            }
            String log = String.join(System.lineSeparator(), l);
            log(log);
        }
    }

    public static void log(String text)
    {
        String dateTime = LocalDateTime.now().toString();
        String thread = Thread.currentThread().getName();
        String log = dateTime + "\t" + thread + "\t" + text + System.lineSeparator();
        System.out.println(log);

        if(Config.LogActive())
        {
            if (Instance() == null) {
                System.out.println("Logging not yet instantiated, logs are stored.");
                preInstantiationLogs.add(log);
            } else {
                Instance().writeToFile(log);
            }
        }
    }

    private void writeToFile(String log)
    {
        if(Config.LogActive())
        {
            try
            {
                BufferedWriter br = new BufferedWriter(new FileWriter(Instance().file, true));
                br.write(log);
                br.close();
            }
            catch (IOException var6)
            {
                throw new RuntimeException(var6);
            }
        }
    }
}
