package test;

import org.junit.Assert;
import org.junit.Test;

import dtos.DTOBase;
import dtos.UserDTO;
import repositories.UserRepository;
import repositoriesInterface.IUserRepository;

public final class UserRepositoryTest extends RepositoryTestBase<UserDTO, IUserRepository> {

	@Test
	public void add() {
		IUserRepository repository = getRepository();
		int count = repository.getCount();
		repository.add(new UserDTO(0, "Jay", "123456"));
		repository.add(new UserDTO(0, "Tim", "654321"));
		Assert.assertEquals(count + 2, repository.getCount());
	}

	@Test
	public void update() {
		IUserRepository repository = getRepository();
		int count = repository.getCount();
		repository.update(new UserDTO(1, "Jay", "654321"));
		Assert.assertEquals(count, repository.getCount());

	}

	@Test
	public void addOrUpdate() {
		IUserRepository repository = getRepository();
		repository.addOrUpdate(new UserDTO(0, "Jay", "123456"));
		int lastestId = repository.findByLogin("Jay").stream().map(UserDTO::getId).max(Integer::compare).get();
		repository.addOrUpdate(new UserDTO(lastestId, "Jay", "654321"));
		Assert.assertEquals(repository.findById(lastestId).getPassword(), "654321");
		Assert.assertNull(repository.findById(lastestId + 1));
	}

	@Test
	public void delete() {
		IUserRepository repository = getRepository();
		repository.addOrUpdate(new UserDTO(0, "Jay", "123456"));
		int lastestId = repository.findByLogin("Jay").stream().map(UserDTO::getId).max(Integer::compare).get();
		repository.delete(new UserDTO(lastestId, "", ""));
		Assert.assertNull(repository.findById(53));
	}

	@Test
	public void findById() {
		IUserRepository repository = getRepository();
		repository.addOrUpdate(new UserDTO(4, "Jay", "123456"));
		int lastestId = repository.findByLogin("Jay").stream().map(DTOBase::getId).max(Integer::compare).get();
		Assert.assertNotNull(repository.findById(lastestId));
	}

	@Test
	public void findByName() {
		IUserRepository repository = getRepository();
		repository.addOrUpdate(new UserDTO(4, "Jay", "123456"));
		Assert.assertNotNull(repository.findByLogin("Jay"));
	}

	@Override
	protected IUserRepository Create() {
		return new UserRepository();
	}
}