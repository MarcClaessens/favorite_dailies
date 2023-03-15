package favorite_dailies.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import favorite_dailies.Main;

public class H2DatabaseConnection {
	private static final String DB_FILE = Main.context().getH2File();
	private static final String DATABASE_URL = "jdbc:h2:" + DB_FILE + ";AUTO_SERVER=TRUE"; // home directory
	private static final String CREDENTIAL = "fav";

	private H2DatabaseConnection() {
		// utility class
	}

	public static void initDb() {
		System.out.println("Loading database : " + DB_FILE);
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

	public static StickyHist getLatestStickText() {
		String statement = "SELECT id, stickytext FROM stickyhist WHERE id = (SELECT max(id) FROM stickyhist)";

		try (Connection c = DriverManager.getConnection(DATABASE_URL, CREDENTIAL, CREDENTIAL);
			PreparedStatement ps = c.prepareStatement(statement)) {
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return new StickyHist(rs.getLong(1), rs.getString(2));
				} else {
					return null;
				}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<StickyHist> getFullHistory() {
		String statement = "SELECT id, stickytext FROM stickyhist";

		try (Connection c = DriverManager.getConnection(DATABASE_URL, CREDENTIAL, CREDENTIAL);
			PreparedStatement ps = c.prepareStatement(statement)) {
				ResultSet rs = ps.executeQuery();
				List<StickyHist> hist= new ArrayList<>();
				while (rs.next()) {
					hist.add(new StickyHist(rs.getLong(1), rs.getString(2)));
				} 
				return hist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void deleteHistory(Long id) {
		String statement = "DELETE FROM stickyhist where id = ?";
		
		try (Connection c = DriverManager.getConnection(DATABASE_URL, CREDENTIAL, CREDENTIAL);
			PreparedStatement ps = c.prepareStatement(statement)) {
				ps.setLong(1,id);
				ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Deleted " + id);
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
