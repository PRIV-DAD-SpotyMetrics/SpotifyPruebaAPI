package spot.funciones;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TopTracks {

    public static String[] getTopTracks(String TOKEN) throws Exception {
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

}
