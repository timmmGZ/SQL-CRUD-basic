package repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dtos.UserDTO;
import exception.MyException;
import repositoriesInterface.IUserRepository;

public class UserRepository extends RepositoryBase<UserDTO> implements IUserRepository {

	@Override
	public void add(UserDTO user) {
		try {
			PreparedStatement pstm = connection.prepareStatement("insert into users values(seq_users.nextval,?,?)");
			pstm.setString(1, user.getLogin());
			pstm.setString(2, user.getPassword());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	@Override
	public void update(UserDTO user) {
		try {
			PreparedStatement pstm = connection
					.prepareStatement("update users set login = ?, password =? where idUser = ?");
			pstm.setString(1, user.getLogin());
			pstm.setString(2, user.getPassword());
			pstm.setInt(3, user.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	@Override
	public void delete(UserDTO user) {
		try {
			PreparedStatement pstm = connection.prepareStatement("delete users where idUser = ?");
			pstm.setInt(1, user.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	@Override
	public UserDTO findById(int id) {
		try {
			UserDTO user = null;
			PreparedStatement pstm = connection
					.prepareStatement("select idUser,login,password from users where idUser=?");
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next())
				user = new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3));
			return user;
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	@Override
	public List<UserDTO> findByLogin(String login) {
		try {
			List<UserDTO> users = new LinkedList<UserDTO>();
			PreparedStatement pstm = connection
					.prepareStatement("select idUser,login,password from users where login like ?");
			pstm.setString(1, login);
			ResultSet rs = pstm.executeQuery();
			while (rs.next())
				users.add(new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			return users;
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	@Override
	protected String getTableName() {
		return "users";
	}

}
