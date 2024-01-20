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
import java.util.Scanner;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
public class SpotifyAuthorization {

    private static String authorizationCode;

    public static void main(String[] args) {
        // Inicia el servidor web local
        startLocalServer();

        // Redirecciona al usuario a la URL de autorización de Spotify
        redirectToSpotifyAuthorization();
       
        
        Scanner	sc = new Scanner(System.in);
        System.out.println("ingresa tu codigo de acceso: ");
        authorizationCode = sc.next();
        
        sc.close();
        
        
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
        String clientId = "d57f0277f54d4060bcd7095e1d4a2dd7";
        String clientSecret = "1c7000c93fda498089131deb5c0147d3"; // Reemplaza con tu propio client secret
        String redirectUri = "http://localhost:8888/callback";

        // Intercambia el código de autorización por el token de acceso
        String token = exchangeCodeForToken(clientId, clientSecret, redirectUri, authorizationCode);

        // Muestra el token
        System.out.println("Token de Acceso: " + token);
        
        
        // Obtiene y muestra el nombre de usuario
        String username = getSpotifyUsername(token);
        System.out.println("Nombre de Usuario: " + username);
        
        
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
        String clientId = "d57f0277f54d4060bcd7095e1d4a2dd7";
        String redirectUri = "http://localhost:8888/callback";
        String responseType = "code";
        String spotifyAuthorizationUrl = "https://accounts.spotify.com/authorize" +
                "?client_id=" + clientId +
                "&response_type=" + responseType +
                "&redirect_uri=" + redirectUri;

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

            // Lee la respuesta y extrae el nombre de usuario
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Parsea la respuesta JSON utilizando Gson y la clase SpotifyUser
                Gson gson = new Gson();
                SpotifyUser spotifyUser = gson.fromJson(response.toString(), SpotifyUser.class);

                return spotifyUser.getUserId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}