package favorite_dailies;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class H2DatabaseConnection {
	private static final String DB_FILE = "~/favorite_dailies.h2db";
	private static final String DATABASE_URL = "jdbc:h2:" + DB_FILE + ";AUTO_SERVER=TRUE"; // home directory
	private static final String CREDENTIAL = "fav";

	private H2DatabaseConnection() {
		// utility class
	}

	public static void initDb() {
		if (!new File(DB_FILE).exists()) {
			try (Connection c = DriverManager.getConnection(DATABASE_URL, CREDENTIAL, CREDENTIAL)) {
				String statement = "CREATE TABLE IF NOT EXISTS stickyhist(id bigint auto_increment primary key, stickytext text not null);";
				
				try (PreparedStatement ps = c.prepareStatement(statement)) {
					ps.execute();
				}

			} catch (Exception e) {
				throw new IllegalStateException("Could not initialize DB", e);
			}
		}
	}

	public static String getLatestStickText() {
		String statement = "SELECT stickytext FROM stickyhist WHERE id = (SELECT max(id) FROM stickyhist)";

		try (Connection c = DriverManager.getConnection(DATABASE_URL, CREDENTIAL, CREDENTIAL);
			PreparedStatement ps = c.prepareStatement(statement)) {
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getString(1);
				} else {
					return "";
				}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static long getEntries() {
		String statement = "SELECT count(*) FROM stickyhist";

		try (Connection c = DriverManager.getConnection(DATABASE_URL, CREDENTIAL, CREDENTIAL);
			PreparedStatement ps = c.prepareStatement(statement)) {
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getLong(1);
				} else {
					return 0L;
				}
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	public static void insertStickyData(String text) {
		String statement = "INSERT INTO stickyhist(stickytext) VALUES (?)";

		try (Connection c = DriverManager.getConnection(DATABASE_URL, CREDENTIAL, CREDENTIAL); 
			PreparedStatement ps = c.prepareStatement(statement)) {
				ps.setString(1, text);
				ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
