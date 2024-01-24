package spot.login;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static spot.main.SpotifyAuthorization.authorizationCode;

public class MainLogin {

    public static void startLocalServer() {
        try {
            // Crea un servidor HTTP en el puerto 8888
            HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);

            // Configura el manejador para la ruta de callback
            server.createContext("/callback", new CallbackHandler());

            // Inicia el servidor en un hilo separado
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void redirectToSpotifyAuthorization() {
        String clientId = "33e34aef0ac34bb2be3ca751252b16ff";
        String redirectUri = "http://localhost:8888/callback";
        String responseType = "code";
        String scope = "user-top-read";
        String spotifyAuthorizationUrl = "https://accounts.spotify.com/authorize" +
                "?client_id=" + clientId +
                "&response_type=" + responseType +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scope;

        // Redirecciona al usuario a la URL de autorización de Spotify
        System.out.println("Por favor, visita la siguiente URL para autorizar la aplicación:");
        System.out.println(spotifyAuthorizationUrl);
    }

    static class CallbackHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Extrae el código de autorización de la URL de redirección
            URI requestURI = exchange.getRequestURI();
            String query = requestURI.getQuery();
            authorizationCode = query.substring(query.indexOf("code=") + 5);

            System.out.println(authorizationCode);

            // Envía una respuesta al navegador del usuario
            String response = "Autorización exitosa. Puedes cerrar esta ventana.";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

            // Detén el servidor web después de manejar la respuesta
            exchange.getHttpContext().getServer().stop(0);
        }
    }

    // Implementación del método para intercambiar el código de autorización por el token de acceso
    @SuppressWarnings("deprecation")
    public static String exchangeCodeForToken(String clientId, String clientSecret, String redirectUri, String authorizationCode) {
        try {
            // Construye la cadena "client_id:client_secret" y la codifica en Base64
            String credentials = clientId + ":" + clientSecret;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

            // Construye el cuerpo de la solicitud para intercambiar el código de autorización por el token de acceso
            String requestBody = "grant_type=authorization_code" +
                    "&code=" + authorizationCode +
                    "&redirect_uri=" + redirectUri;

            // Realiza la solicitud POST al punto de token de Spotify
            URL tokenUrl = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection connection = (HttpURLConnection) tokenUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            connection.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));

            // Lee la respuesta y extrae el token de acceso
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString().split("\"access_token\":\"")[1].split("\"")[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
