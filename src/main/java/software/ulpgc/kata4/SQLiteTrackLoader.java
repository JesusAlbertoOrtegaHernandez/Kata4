package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SQLiteTrackLoader implements TrackLoader{

    private final Connection connection;
    private final static String SQL = "select tracks.name as track, composer, Milliseconds, title, artists.Name as artist from tracks, albums, artists, genres where tracks.AlbumId = albums.AlbumId and albums.ArtistId = artists.ArtistId and tracks.GenreId = genres.GenreId";

    public SQLiteTrackLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Track> loadAll() {
        try {
            return load(queryAll());
        }
        catch (SQLException e);
        return Collections.emptyList();
    }

    private List<Track> load(ResultSet resultSet)throws SQLException {
        List<Track> result = new ArrayList<>();
        while (resultSet.next())
            result.add(trackFrom(resultSet));
        return result;

    }

    private ResultSet queryAll() throws SQLException {
        return connection.createStatement().executeQuery(SQL);
    }

    private static Track trackFrom(ResultSet resultSet) throws SQLException{
        return new Track(
                resultSet.getString("Track"),
                resultSet.getString("composer"),
                resultSet.getString("Title"),
                resultSet.getString("artist"),
                resultSet.getInt("milliseconds") / 100
        );
    }


}
