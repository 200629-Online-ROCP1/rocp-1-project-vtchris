package com.changeBank.repo;

import java.util.Map;
import java.util.Set;

public interface Dao<T> {
	public T insert(T t);
	public boolean update(T t);
	public boolean delete(T t);
	
	public T findById(int id);	
	public Set<T> selectAll();
}
