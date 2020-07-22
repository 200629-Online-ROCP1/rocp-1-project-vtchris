package com.changeBank.repo;

import java.util.List;

public interface IDao<T> {
	public boolean delete(T t, int id);
	public T insert(T t);
	public List<T> findAll();
	public T findById(int id);
	public boolean update(T t);
	
}