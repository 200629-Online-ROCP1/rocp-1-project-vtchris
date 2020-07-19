package com.changeBank.repo;

import java.util.List;
import java.util.Set;

public interface IDao<T> {
	public T insert(T t);
	public boolean update(T t);
	public boolean delete(T t);
	
	public T findById(int id);	
	public Set<T> selectAll();
	public List<T> findAll();
}
