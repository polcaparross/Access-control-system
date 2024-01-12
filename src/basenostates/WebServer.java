package basenostates;

import basenostates.requests.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Based on
 *<a
 * href="https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java">
 * ...</a>
 *<a href="http://www.jcgonzalez.com/java-socket-mini-server-http-example">
 *     ...</a>
 */
public final class WebServer {
  private static final Logger LOGGER = LoggerFactory.getLogger("fita1");
  private static final int PORT = 8080; // port to listen connection
  private static final DateTimeFormatter FORMATER =
          DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

  private static WebServer instance = null;

  private WebServer() {
    try {
      ServerSocket serverConnect = new ServerSocket(PORT);
      LOGGER.info("Server started.\nListening for connections on port : "
              + PORT + " ...\n");
      // we listen until user halts server execution
      while (true) {
        // each client connection will be managed in a dedicated Thread
        new SocketThread(serverConnect.accept());
        // create dedicated thread to manage the client connection
      }
    } catch (IOException e) {
      LOGGER.warn("Server Connection error : " + e.getMessage());
    }
  }

  public static WebServer getInstance() {
    if (instance == null) {
      instance = new WebServer();
    }
    return instance;
  }

  private class SocketThread extends Thread {
    // as an inner class, SocketThread sees WebServer attributes
    private final Socket insocked; // client connection via Socket class

    SocketThread(final Socket insocket) {
      this.insocked = insocket;
      this.start();
    }

    @Override
    public void run() {
      // we manage our particular client connection
      BufferedReader in;
      PrintWriter out;
      String resource;

      try {
        // we read characters from the client via input stream on the socket
        in = new BufferedReader(
                new InputStreamReader(insocked.getInputStream()));
        // we get character output stream to client
        out = new PrintWriter(insocked.getOutputStream());
        // get first line of the request from the client
        String input = in.readLine();
        // we parse the request with a string tokenizer

        LOGGER.info("sockedthread : " + input);

        StringTokenizer parse = new StringTokenizer(input);
        // we get the HTTP method of the client
        String method = parse.nextToken().toUpperCase();
        if (!method.equals("GET")) {
          LOGGER.warn("501 Not Implemented : " + method + " method.");
        } else {
          // what comes after "localhost:8080"
          resource = parse.nextToken();
          LOGGER.info("input " + input);
          LOGGER.info("method " + method);
          LOGGER.info("resource " + resource);

          parse = new StringTokenizer(resource, "/[?]=&");
          int i = 0;
          // more than the actual number of parameters
          String[] tokens = new String[20];
          while (parse.hasMoreTokens()) {
            tokens[i] = parse.nextToken();
            System.out.println(i + " " + tokens[i]);
            i++;
          }

          // Here is where we send the request and get the answer inside it
          Request request = makeRequest(tokens);
          if (request != null) {
            String typeRequest = tokens[0];
            LOGGER.info("created request "
                    + typeRequest + " " + request);
            request.process();
            LOGGER.info("processed request "
                    + typeRequest + " " + request);
            // Make the answer as a JSON string,
            // to be sent to the Javascript client
            String answer = makeJsonAnswer(request);
            LOGGER.info("answer\n" + answer);
            // Here we send the response to the client
            out.println(answer);
            // flush character output stream buffer
            out.flush();
          }
        }

        in.close();
        out.close();
        insocked.close(); // we close socket connection
      } catch (Exception e) {
        LOGGER.warn("Exception : " + e);
      }
    }

    private Request makeRequest(final String[] tokens) {
      // always return request because it
      // contains the answer for the Javascript client
      LOGGER.info("tokens : ");
      for (String token : tokens) {
        LOGGER.info(token + ", ");
      }
      LOGGER.info("");

      Request request;
      // assertions below evaluated to false won't stop the webserver,
      // just print an assertion error, maybe because the webserver runs in a
      // socked thread
      switch (tokens[0]) {
        case "refresh":
          request = new RequestRefresh();
          break;
        case "reader":
          request = makeRequestReader(tokens);
          break;
        case "area":
          request = makeRequestArea(tokens);
          break;
        case "get_children":
          request = makeRequestChildren(tokens);
          break;
        default:
          // just in case we change the user interface or the simulator
          assert false : "unknown request " + tokens[0];
          request = null;
          System.exit(-1);
      }
      return request;
    }

    private RequestChildren makeRequestChildren(String[] tokens) {
      String areaId = tokens[1];
      return new RequestChildren(areaId);
    }

    private RequestReader makeRequestReader(final String[] tokens) {
      String credential = tokens[2];
      String action = tokens[4];
      LocalDateTime dateTime = LocalDateTime.parse(tokens[6], FORMATER);
      String doorId = tokens[8];
      return new RequestReader(credential, action, dateTime, doorId);
    }

    private RequestArea makeRequestArea(final String[] tokens) {
      String credential = tokens[2];
      String action = tokens[4];
      LocalDateTime dateTime = LocalDateTime.parse(tokens[6], FORMATER);
      String areaId = tokens[8];
      return new RequestArea(credential, action, dateTime, areaId);
    }

    private String makeHeaderAnswer() {
      String answer = "";
      answer += "HTTP/1.0 200 OK\r\n";
      answer += "Content-type: application/json\r\n";
      answer += "Access-Control-Allow-Origin: *\r\n";
      // SUPERIMPORTANT to avoid the CORS problem :
      // "Cross-Origin Request Blocked:
      // The Same Origin Policy disallows reading
      // the remote resource..."
      // blank line between headers and content,
      // very important!
      answer += "\r\n";
      return answer;
    }

    private String makeJsonAnswer(final Request request) {
      String answer = makeHeaderAnswer();
      answer += request.answerToJson().toString();
      return answer;
    }

  }

}
