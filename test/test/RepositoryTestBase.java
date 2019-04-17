package test;

import org.junit.After;
import org.junit.Before;

import dtos.DTOBase;
import repositoriesInterface.IRepository;

public abstract class RepositoryTestBase<TDTO extends DTOBase, TRepository extends IRepository<TDTO>> {

	private TRepository _repository;

	@Before
	public void before() {

		_repository = Create();
		if (_repository != null) {
			_repository.beginTransaction();
		}
	}

	@After
	public void after() {
		if (_repository != null) {
			_repository.rollbackTransaction();
			_repository.close();
			_repository = null;
		}
	}

	protected abstract TRepository Create();

	protected TRepository getRepository() {
		return _repository;

	}
}