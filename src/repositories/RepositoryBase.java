package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dtos.DTOBase;
import exception.MyException;
import repositoriesInterface.IRepository;

public abstract class RepositoryBase<TDTO extends DTOBase> implements IRepository<TDTO> {
	private static final String USERNAMR = "username";
	private static final String PASSWORD = "password";
	private static final String URL = "jdbc:oracle:thin:@host:port:sid";
	// "jdbc:oracle:thin:@( description=(address_list=( address=(protocol=tcp)
	// (host=     )(port=    )))(source_route=yes)
	// (connect_data=(sid=      )))"

	// private static final String DRVIER = "oracle.jdbc.driver.OracleDriver";
	Connection connection = null;

	protected RepositoryBase() {
		setConnection();
	}

	public void setConnection() {
		try {
			// Class.forName(DRVIER);
			connection = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException("get connection error!", e);
		}

	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void addOrUpdate(TDTO dto) {
		if (exists(dto)) {
			update(dto);
		} else
			add(dto);
	}

	@Override
	public boolean exists(TDTO dto) {
		return dto.hasExistingId();
	}

	@Override
	public void beginTransaction() {
	}

	@Override
	public void commitTransaction() {
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rollbackTransaction() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getCount() {
		try {
			Connection connection = getConnection();
			PreparedStatement pstm = connection.prepareStatement("select count(1) from " + getTableName());
			ResultSet rs = pstm.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	protected abstract String getTableName();
	
	public final void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}