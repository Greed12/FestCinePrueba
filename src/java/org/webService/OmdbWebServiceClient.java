package org.webService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.primefaces.json.JSONArray;

/**
 *
 * @author jensy
 */
public class OmdbWebServiceClient {

    public static final String URL_SEARCH = "http://www.omdbapi.com/?s=TITLE&apikey=APIKEY";
    public static final String URL_TITLE = "http://www.omdbapi.com/?t=TITLE&apikey=APIKEY";

    public static String sendGetRequest(String requestUrl) throws MalformedURLException {
        StringBuffer response = new StringBuffer();
        JSONArray array = new JSONArray();
        try {
            //Configuraciones en caso de tener proxy
            /*System.setProperty("proxySet", "true");
            System.setProperty("socksProxyHost", "192.168.5.7");
            System.setProperty("socksProxyPort", "1080");
            System.setProperty("http.proxyHost", "192.168.5.7");
            System.setProperty("http.proxyPort", "3128");
            System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");*/
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String line;//lee linea por linea de los recibido byte porbyte
            while ((line = buffer.readLine()) != null) {
                response.append(line);
            }
            buffer.close();
            connection.disconnect();
        } catch (Exception e) {
            return new String();
        }
        return response.toString();
    }

    public static String searchAllMovieByTitle(String title, String key) throws MalformedURLException {
        String requestUrl = URL_SEARCH.replaceAll("TITLE", title).replaceAll("APIKEY", key);
        return sendGetRequest(requestUrl);
    }

    public static String searchMovieByTitle(String title, String key) throws MalformedURLException {
        String requestUrl = URL_TITLE.replaceAll("TITLE", title).replaceAll("APIKEY", key);
        return sendGetRequest(requestUrl);
    }

    /*
     String jsonResponse = PmdbWebServiceCliente.searchMovieByTitle("bats","de7565f1");
     */
}
