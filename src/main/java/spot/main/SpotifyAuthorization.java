package spot.main;

import static spot.funciones.SpotifyUsername.getSpotifyUsername;
import static spot.funciones.TopTracks.getTopTracks;
import static spot.login.MainLogin.*;

public class SpotifyAuthorization {

    public static String authorizationCode;

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

}
