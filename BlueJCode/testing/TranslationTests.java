package BlueJCode.testing;

import BlueJCode.Blockly.BlocklyTestHandler;
import BlueJCode.Logging;
import javafx.util.Pair;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslationTests
{
    @BeforeAll
    static void beforeAll()
    {
        BlocklyTestHandler.Instance();
    }
    @BeforeEach
    void beforeEach()
    {
        BlocklyTestHandler.Instance().setTested(false);
        BlocklyTestHandler.Instance().resetTestResultStrings();

    }

    @AfterAll
    public static void afterAll()
    {
        Logging.log("Cleaning up");
        BlocklyTestHandler.Instance().closeBlockly();
    }

    @Test
    @Timeout(5)
    void testHallo()
    {
        BlocklyTestHandler.Instance().setClassPrefix(
                "import java.util.*;" + System.lineSeparator() +
                System.lineSeparator() +
                "public class Hallo {" + System.lineSeparator()
        );

        BlocklyTestHandler.Instance().setJavaPath("./BlueJCode/testing/ressources/Hallo.ja");
        BlocklyTestHandler.Instance().setXmlPath("./BlueJCode/testing/ressources/Hallo.xml");

        runTest();
    }

    @Test
    @Timeout(5)
    void testAdd()
    {
        BlocklyTestHandler.Instance().setClassPrefix(
                "import java.util.*;" + System.lineSeparator() +
                        System.lineSeparator() +
                        "public class Test {" + System.lineSeparator()
        );

        BlocklyTestHandler.Instance().setJavaPath("./BlueJCode/testing/ressources/Add.ja");
        BlocklyTestHandler.Instance().setXmlPath("./BlueJCode/testing/ressources/Add.xml");

        runTest();
    }

    @Test
    @Timeout(5)
    void testBlockWithoutFunction()
    {
        BlocklyTestHandler.Instance().setClassPrefix(
                "import java.util.*;" + System.lineSeparator() +
                        System.lineSeparator() +
                        "public class Test {" + System.lineSeparator()
        );

        BlocklyTestHandler.Instance().setJavaPath("./BlueJCode/testing/ressources/BlockWithoutFunction.ja");
        BlocklyTestHandler.Instance().setXmlPath("./BlueJCode/testing/ressources/BlockWithoutFunction.xml");

        runTest();
    }

    @Test
    @Timeout(5)
    void testFloat()
    {
        BlocklyTestHandler.Instance().setClassPrefix(
                "import java.util.*;" + System.lineSeparator() +
                        System.lineSeparator() +
                        "public class Test {" + System.lineSeparator()
        );

        BlocklyTestHandler.Instance().setJavaPath("./BlueJCode/testing/ressources/AddFloat.ja");
        BlocklyTestHandler.Instance().setXmlPath("./BlueJCode/testing/ressources/AddFloat.xml");

        runTest();
    }

    @Test
    @Timeout(5)
    void testIfAndBool()
    {
        BlocklyTestHandler.Instance().setClassPrefix(
                "import java.util.*;" + System.lineSeparator() +
                        System.lineSeparator() +
                        "public class Test {" + System.lineSeparator()
        );

        BlocklyTestHandler.Instance().setJavaPath("./BlueJCode/testing/ressources/IfAndBool.ja");
        BlocklyTestHandler.Instance().setXmlPath("./BlueJCode/testing/ressources/IfAndBool.xml");

        runTest();
    }

    @Test
    @Timeout(5)
    void testWhile()
    {
        BlocklyTestHandler.Instance().setClassPrefix(
                "import java.util.*;" + System.lineSeparator() +
                        System.lineSeparator() +
                        "public class Test {" + System.lineSeparator()
        );

        BlocklyTestHandler.Instance().setJavaPath("./BlueJCode/testing/ressources/While.ja");
        BlocklyTestHandler.Instance().setXmlPath("./BlueJCode/testing/ressources/While.xml");

        runTest();
    }

    @Test
    @Timeout(5)
    void testComparison()
    {
        BlocklyTestHandler.Instance().setClassPrefix(
                "import java.util.*;" + System.lineSeparator() +
                        System.lineSeparator() +
                        "public class Test {" + System.lineSeparator()
        );

        BlocklyTestHandler.Instance().setJavaPath("./BlueJCode/testing/ressources/Compare.ja");
        BlocklyTestHandler.Instance().setXmlPath("./BlueJCode/testing/ressources/Compare.xml");

        runTest();
    }

    void runTest(){
        int timer = 0;
        int count = 0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        BlocklyTestHandler.Instance().openBlockly(); // neue Get-Anfrage triggern
        BlocklyTestHandler.Instance().hideBlockly();
        System.out.println("Waiting for test results...");
        while(!BlocklyTestHandler.Instance().isTested() && timer<5000)
        {
            try
            {
                Thread.sleep(100);
                timer += 100;
            }
            catch (InterruptedException e) {  }
        }
        Pair<String,String> result = BlocklyTestHandler.Instance().getTestResultStrings();
        if(result.getKey().equals("}") && count < 3){
            runTest();
            count++;
        }
        Logging.log("Number of \"}\": " + count);
        Logging.log("expected: " + result.getKey());
        Logging.log("result: " + result.getValue());
        assertEquals(result.getKey(), result.getValue());
    }
}
