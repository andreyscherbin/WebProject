import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.epam.forum.model.entity.Abonent;

public class Main {

	public static void main(String[] args) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			} catch (SQLException e) {
			e.printStackTrace();
			}
			String url = "jdbc:mysql://localhost:3306/forumdb";
			Properties prop = new Properties();
			prop.put("user", "root");
			prop.put("password", "4544974qwerty");
			prop.put("autoReconnect", "true");
			prop.put("characterEncoding", "UTF-8");
			prop.put("useUnicode", "true");
			prop.put("useSSL", "true");
			prop.put("useJDBCCompliantTimezoneShift", "true");
			prop.put("useLegacyDatetimeCode", "false");
			prop.put("serverTimezone", "UTC");
			prop.put("serverSslCert", "classpath:server.crt");
			try (Connection connection = DriverManager.getConnection(url, prop);
			Statement statement = connection.createStatement()) {
			String sql = "SELECT idphonebook, lastname, phone FROM phonebook";
			ResultSet resultSet = statement.executeQuery(sql);
			List<Abonent> abonents = new ArrayList<>();
			while (resultSet.next()) {
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			int number = resultSet.getInt("phone");
			abonents.add(new Abonent((long) id, name, number));
			}
			System.out.println(abonents);
			} catch (SQLException e) {
			e.printStackTrace();
			}
	}
}
