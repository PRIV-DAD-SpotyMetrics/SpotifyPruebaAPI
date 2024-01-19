import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Artist;

public class SpotifyExample {
    public static void main(String[] args) {
        // Configura las credenciales
        String clientId = "TU_CLIENT_ID";
        String clientSecret = "TU_CLIENT_SECRET";

        // Configura la API de Spotify
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        // Realiza una solicitud para obtener información sobre un artista (ejemplo)
        try {
            String artistId = "ID_DEL_ARTISTA";
            Artist artist = spotifyApi.getArtist(artistId).build().execute();
            System.out.println("Nombre del artista: " + artist.getName());
        } catch (Exception e) {
            System.err.println("Error al realizar la solicitud: " + e.getMessage());
        }
    }
}
