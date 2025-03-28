package BlueJCode.Blockly;

import BlueJCode.Logging;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

class RestAPI
{
    private BlocklyHandler blockly;
    private HttpServer server;

    RestAPI(BlocklyHandler bHandler)
    {
        try
        {
            blockly = bHandler;
            Logging.logRest(">>> RestAPI.RestAPI()");
            server = HttpServer.create(new InetSocketAddress(8081), 0);
        }
        catch(Exception e)
        {
            Logging.logRest(e.toString());
        }
        init();
        Logging.logRest("<<< RestAPI.RestAPI()");
    }

    private void init()
    {
        try
        {
            getAPIRights();

            server.createContext("/api", (exchange ->
            {
                if ("GET".equals(exchange.getRequestMethod()))
                {
                    handleGet(exchange);
                }
                else if("POST".equals(exchange.getRequestMethod()))
                {
                    handlePost(exchange);
                }
                else
                {
                    exchange = cors(exchange);
                    exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
                }
                exchange.close();
            }));


            server.setExecutor(null); // creates a default executor
            server.start();
        }
        catch(Exception e)
        {
            Logging.logRest(e.toString());
        }

    }

    private String bodyToString(InputStream rawBody)
    {
        String ret = "";
        try
        {
            InputStreamReader isr = new InputStreamReader(rawBody, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            // From now on, the right way of moving from bytes to utf-8 characters:

            int b;
            StringBuilder buf = new StringBuilder(512);
            while ((b = br.read()) != -1)
            {
                buf.append((char) b);
            }
            br.close();
            isr.close();

            ret = buf.toString();
        }
        catch(Exception e)
        {
            Logging.logRest(e.getMessage());
        }
        return ret;
    }

    private void getAPIRights()
    {
        try
        {
            String cmd = "netsh http add urlacl url=http://localhost:8081/api/ user=Jeder";
            Process process = Runtime.getRuntime().exec(String.format("cmd.exe /c " + cmd, "."));
            process.waitFor();
            Logging.logRest("Access granted");
        }
        catch(Exception e)
        {
            Logging.logRest("Access failed");
        }
    }

    private HttpExchange cors(HttpExchange exchange)
    {
        try {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
                exchange.sendResponseHeaders(204, -1);
                return null;
            }
            return exchange;
        }
        catch(Exception e)
        {
            Logging.logRest(e.toString());
            return null;
        }
    }

    private void handleGet(HttpExchange exchange)
    {
        try
        {
            Logging.logRest(">>> GET");


            String responseText = blockly.getCurrentClassPrefix()
                    +"|||||"
                    + blockly.getCurrentClassXml();

            exchange = cors(exchange);

            if(exchange==null)
            {
                Logging.logRest("Exchange is null. CORS failed.");
                return;
            }

            exchange.sendResponseHeaders(200, responseText.getBytes().length);
            exchange.setAttribute("Access-Control-Allow-Origin", "*");
            OutputStream output = exchange.getResponseBody();
            output.write(responseText.getBytes());
            output.flush();


            Logging.logRest(responseText);

            Logging.logRest("<<< GET Response: "+exchange.getResponseCode());
        }
        catch(Exception e)
        {
            Logging.logRest(e.toString());
        }
    }

    private void handlePost(HttpExchange exchange)
    {
        try
        {
            assert exchange != null;

            String contentType = exchange.getRequestHeaders().getFirst("Content-type");// exchange.get

            Logging.logRest(">>> POST: "+contentType);

            String data = bodyToString(exchange.getRequestBody());

            Logging.logRest(contentType);
            Logging.logRest("'"+data+"'");



            int code = switch (contentType)
            {
                case "text/java" ->  blockly.incomingJavaCode(data);
                case "text/xml" -> handlePost_Xml(data);
                default -> 406;
            };

            exchange = cors(exchange);

            String responseText = "";
            exchange.sendResponseHeaders(code, responseText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(responseText.getBytes());
            output.flush();

            Logging.logRest("<<< POST Response: " + code);

        }
        catch(Exception e)
        {
            Logging.logRest(e.toString());
        }
    }



    private int handlePost_Xml(String code)
    {
        try
        {
            Logging.logRest(">>> RestAPI.handlePost_Xml(...)");
            blockly.incomingXmlCode(code);
            Logging.logRest("<<< RestAPI.handlePost_Xml(...): 200");
            return 200;
        }
        catch(Exception e)
        {
            Logging.logRest(e.toString());
            Logging.logRest("<<< RestAPI.handlePost_Xml(...): 400");
            return 400;
        }
    }

    void terminate()
    {
        try
        {
            Logging.logRest(">>> RestAPI.terminate()");
            server.stop(0);
            Logging.logRest("<<< RestAPI.terminate()");
        }
        catch(Exception e)
        {
            Logging.logRest(e.toString());
        }
    }


}