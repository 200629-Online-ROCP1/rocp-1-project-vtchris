package com.changeBank.repo;

import java.util.Set;

public class AccountTransactionDao implements Dao<AccountTransactionDao> {
		// This is a design pattern called a "singleton" where only one implementation 
		// of a class can exist at a time.
		// Not set up for multi-threads
		private static AccountTransactionDao repo = new AccountTransactionDao();
		private AccountTransactionDao() {}
		public static AccountTransactionDao getInstance() {
			return repo;
		}
		@Override
		public AccountTransactionDao insert(AccountTransactionDao accountTransaction) {
			return null;
		}
		@Override
		public boolean update(AccountTransactionDao t) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean delete(AccountTransactionDao t) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public AccountTransactionDao getById(int id) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Set<AccountTransactionDao> selectAll() {
			// TODO Auto-generated method stub
			return null;
		}
}
