package repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dtos.GroupDTO;
import exception.MyException;
import repositoriesInterface.IGroupRepository;

public class GroupRepository extends RepositoryBase<GroupDTO> implements IGroupRepository {

	public void add(GroupDTO group) {
		try {
			PreparedStatement pstm = connection.prepareStatement("insert into groups values(seq_groups.nextval,?,?)");
			pstm.setString(1, group.getName());
			pstm.setString(2, group.getDescription());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	public void update(GroupDTO group) {
		try {
			PreparedStatement pstm = connection
					.prepareStatement("update groups set name = ?, description =? where idGroup = ?");
			pstm.setString(1, group.getName());
			pstm.setString(2, group.getDescription());
			pstm.setInt(3, group.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	public void delete(GroupDTO group) {
		try {
			PreparedStatement pstm = connection.prepareStatement("delete groups where idGroup = ?");
			pstm.setInt(1, group.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	public GroupDTO findById(int id) {
		try {
			GroupDTO group = null;
			PreparedStatement pstm = connection
					.prepareStatement("select idGroup,name,description from groups where idGroup=?");
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next())
				group = new GroupDTO(rs.getInt(1), rs.getString(2), rs.getString(3));
			return group;
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	public List<GroupDTO> findByName(String name) {
		try {
			List<GroupDTO> groups = new LinkedList<GroupDTO>();
			PreparedStatement pstm = connection
					.prepareStatement("select idGroup,name,description from groups where name like ?");
			pstm.setString(1, name);
			ResultSet rs = pstm.executeQuery();
			while (rs.next())
				groups.add(new GroupDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			return groups;
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	@Override
	protected String getTableName() {
		return "groups";
	}

}
