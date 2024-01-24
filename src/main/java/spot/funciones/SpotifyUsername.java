package spot.funciones;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyUsername {

    public static String getSpotifyUsername(String accessToken) {
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
