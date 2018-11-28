package fr.ynov.dap.client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.ynov.dap.client.dto.in.ExceptionInDto;
import fr.ynov.dap.client.exception.ServerSideException;

/**
 * This class allow client to discuss with server side.
 * @author Robin DUDEK
 *
 */
public class HttpService {
    /**
    * The default server domain: in our case we launch the app on our PC so it's localhost.
    */
    private static final String DOMAIN = "localhost";

    /**
    * The default port: it's the port where the server is started, by default it's 8080.
    */
    private static final String PORT = "8080";

    /**
    * Default url to use to discuss with server.
    */
    private static final String BASE_URL = "http://" + DOMAIN + ":" + PORT;


    /**
     * Logger instance.
     */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * Return current instance of Logger.
     * @return Logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Wrapper of method 'sendRequest'. Get request for url.
     * @param url Url of the endpoint to send request
     * @return Data retrieved from server. JSON formatted.
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public String sendGetRequest(final String url) throws IOException, ServerSideException {

        HttpURLConnection connection = getHttpURLConnection(url, "GET");

        return sendRequest(connection);

    }

    /**
     * Wrapper of method 'sendRequest'. Post request for url.
     * @param url Url to server
     * @return Data retrieved from server. JSON formatted.
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public String sendPostRequest(final String url) throws IOException, ServerSideException {

        HttpURLConnection connection = getHttpURLConnection(url, "POST");

        return sendRequest(connection);

    }

    /**
     * Send request to server and retrieve data from it.
     * @param connection Connection to use
     * @return Data retrieved from server.  JSON formatted.
     * @throws IOException Exception
     * @throws ServerSideException Exception thrown when server send an error
     */
    public String sendRequest(final HttpURLConnection connection) throws IOException, ServerSideException {

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            return readBuffer(connection.getInputStream());

        } else {

            String errorInput = readBuffer(connection.getErrorStream());

            getLogger().error(errorInput);

            ExceptionInDto dto = ExceptionInDto.fromJSON(errorInput);

            throw new ServerSideException(dto);

        }

    }

    /**
     * Create new HttpURLConnection object to create a connection between client and server.
     * @param url Url to server
     * @param method Http Method to use (e.g. GET, POST, PUT, ...)
     * @return HttpURLConnection instance
     * @throws IOException Exception
     */
    private HttpURLConnection getHttpURLConnection(final String url, final String method) throws IOException {

        URL obj = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Accept", "application/json");

        return connection;

    }

    /**
     * Read buffer from http url connection.
     * @param inptStream Buffer to read
     * @return Content readed from buffer
     * @throws IOException Exception
     */
    private String readBuffer(final InputStream inptStream) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(inptStream));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();

    }

    /**
     * Default url to use.
     * @return Default url
     */
    protected String getUrl() {
        return BASE_URL;
    }

    /**
     * Return current class name.
     * @return Class name
     */
    protected String getClassName() {
        return HttpService.class.getName();
    }

}