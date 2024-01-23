package spot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

public class SpotifyAuthorization {

    private static String authorizationCode;

    public static void main(String[] args) throws Exception {
        // Inicia el servidor web local
        startLocalServer();

        // Redirecciona al usuario a la URL de autorización de Spotify
        redirectToSpotifyAuthorization();

        // Espera hasta que se obtenga el código de autorización
        while (authorizationCode == null) {
            try {
                Thread.sleep(1000); // Espera 1 segundo antes de verificar de nuevo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Muestra el código de autorización
        System.out.println("Código de Autorización: " + authorizationCode);

        // Intercambia el código de autorización por el token
        String clientId = "33e34aef0ac34bb2be3ca751252b16ff";
        String clientSecret = "46e68b7d5ec14b1bb02243d131bc0f4b"; // Reemplaza con tu propio client secret
        String redirectUri = "http://localhost:8888/callback";

        // Intercambia el código de autorización por el token de acceso
        String token = exchangeCodeForToken(clientId, clientSecret, redirectUri, authorizationCode);

        // Muestra el token
        System.out.println("Token de Acceso: " + token);

        // Obtiene y muestra el nombre de usuario
        String username = getSpotifyUsername(token);
        System.out.println("Nombre de Usuario: " + username);

        String[] topTracks = getTopTracks(token);
        for (String track : topTracks) {
            System.out.println(track);
        }

    }

    private static String[] getTopTracks(String TOKEN) throws Exception {
        // Endpoint reference: https://developer.spotify.com/documentation/web-api/reference/get-users-top-artists-and-tracks
        String endpoint = "v1/me/top/tracks?time_range=long_term&limit=1";
        String apiUrl = "https://api.spotify.com/" + endpoint;

        // Create the URL object and open the connection
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set up the request method, headers, and authorization
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        // Get the response code
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response and convert it to a string
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                return response.toString().split(",");
            }
        } else {
            // Print details of the error if the request was not successful
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                System.out.println("Error response: " + errorResponse.toString());
            }
            return null;
        }
    }

    private static void startLocalServer() {
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

    private static void redirectToSpotifyAuthorization() {
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
    private static String exchangeCodeForToken(String clientId, String clientSecret, String redirectUri, String authorizationCode) {
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

    private static String getSpotifyUsername(String accessToken) {
        try {
            // Realiza una solicitud GET a la API de Spotify para obtener detalles del usuario
            URL userUrl = new URL("https://api.spotify.com/v1/me");
            HttpURLConnection connection = (HttpURLConnection) userUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lee la respuesta y extrae el nombre de usuario
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parsea la respuesta JSON utilizando Gson
                    JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                    String username = jsonObject.get("display_name").getAsString();

                    return username;
                }
            } else {
                // Imprime detalles del error si la solicitud no fue exitosa
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    System.out.println("Error response: " + errorResponse);
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
