package test;

import org.junit.Assert;
import org.junit.Test;

import dtos.DTOBase;
import dtos.GroupDTO;
import repositories.GroupRepository;
import repositoriesInterface.IGroupRepository;

public class GroupRepositoryTest extends RepositoryTestBase<GroupDTO, IGroupRepository> {

	@Test
	public void add() {
		IGroupRepository repository = getRepository();
		int count = repository.getCount();
		repository.add(new GroupDTO(0, "a", "normal group"));
		repository.add(new GroupDTO(0, "a", "normal group"));
		repository.add(new GroupDTO(0, "b", "simple group"));
		Assert.assertEquals(count + 3, repository.getCount());
	}

	@Test
	public void update() {
		IGroupRepository repository = getRepository();
		int count = repository.getCount();
		repository.update(new GroupDTO(21, "dsadsafa", "dsadasfwqfwq"));
		Assert.assertEquals(count, repository.getCount());
	}

	@Test
	public void addOrUpdate() {
		IGroupRepository repository = getRepository();
		repository.addOrUpdate(new GroupDTO(0, "haha", "jajaa"));
		int lastestId = repository.findByName("haha").stream().map(GroupDTO::getId).max(Integer::compare).get();
		repository.addOrUpdate(new GroupDTO(lastestId, "haha", "qqqqqqqqqqq"));
		Assert.assertEquals(repository.findById(lastestId).getDescription(), "qqqqqqqqqqq");
		Assert.assertNull(repository.findById(lastestId + 1));
	}

	@Test
	public void delete() {
		IGroupRepository repository = getRepository();
		repository.addOrUpdate(new GroupDTO(0, "haha", "jajaa"));
		int lastestId = repository.findByName("haha").stream().map(DTOBase::getId).max(Integer::compare).get();
		Assert.assertNotNull(repository.findById(lastestId));
		repository.delete(new GroupDTO(lastestId, "haha", "jajaa"));
		Assert.assertNull(repository.findById(lastestId));
	}

	@Test
	public void findById() {
		IGroupRepository repository = getRepository();
		repository.addOrUpdate(new GroupDTO(0, "haha", "jajaa"));
		int lastestId = repository.findByName("haha").stream().map(DTOBase::getId).max(Integer::compare).get();
		Assert.assertNotNull(repository.findById(lastestId));

	}

	@Test
	public void findByName() {
		IGroupRepository repository = getRepository();
		repository.addOrUpdate(new GroupDTO(4, "haha", "jajaa"));
		Assert.assertNotNull(repository.findByName("haha"));
	}

	@Override
	protected IGroupRepository Create() {
		return new GroupRepository();
	}
}