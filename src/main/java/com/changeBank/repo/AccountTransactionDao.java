package com.changeBank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import com.changeBank.models.accounts.AccountTransaction;
import com.changeBank.utils.ConnectionUtil;

public class AccountTransactionDao implements IDao<AccountTransaction> {
		// This is a design pattern called a "singleton" where only one implementation 
		// of a class can exist at a time.
		// Not set up for multi-threads
		private AccountDao adao = AccountDao.getInstance();
		private static AccountTransactionDao repo = new AccountTransactionDao();
		private AccountTransactionDao() {}
		public static AccountTransactionDao getInstance() {
			return repo;
		}
		@Override
		public AccountTransaction insert(AccountTransaction t) {
			System.out.println("Inserting New Transaction");
					
			Connection conn = null;
			
			try{
				conn = ConnectionUtil.getConnection();
				conn.setAutoCommit(false);
				
				String sql = "INSERT INTO account_transactions (account_id_fk,trans_type,debit,credit,signed_amount,running_balance,status,memo,user_id_fk) "
						+ "VALUES (?,?,?,?,?,?,?,?,?);";
				PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, t.getAccountId());
				statement.setString(2, String.valueOf(t.getType()));
				statement.setFloat(3, t.getDebit());
				statement.setFloat(4, t.getCredit());
				statement.setFloat(5, t.getSignedAmount());
				statement.setFloat(6, t.getRunningBalance());
				statement.setString(7, String.valueOf(t.getStatus()));
				statement.setString(8, t.getMemo());
				statement.setInt(9, t.getUserId());
			
				int rows = statement.executeUpdate();
				
				
				
				if(rows > 0) {
					ResultSet rs = statement.getGeneratedKeys();
					while(rs.next()) {	
						t.setTransactionId(rs.getInt(1));
					}
					
					if(!adao.updateBalance(t.getAccountId(), t.getRunningBalance())) {
						throw new Exception("Balance was not updated.");
					}
					
					if(t.getTargetAccount() != null) {
						
						AccountTransaction t2 = new AccountTransaction();
						t2.setAccountId(t.getTargetAccount().getAccountId());
						t2.setCredit(t.getDebit());
						t2.setDebit(0);
						t2.setMemo("TRANSFER FROM #");
						t2.setRunningBalance(t.getTargetAccount().getBalance() + t.getCredit());
						t2.setSignedAmount(t.getCredit());
						t2.setStatus('A');
						t2.setType('T');
						t2.setUserId(t.getUserId());						
						//Set target to null to prevent infinite regression
						t2.setTargetAccount(null);
						
						if(this.insert(t2) == null) {
							throw new Exception("Transfer to transaction not inserted.");
						}
					}
					
					
					//Throw Error
					//int error = 10/0;
					//conn.commit();
					conn.commit();
					return t;
				}else {
					return null;
				}
			
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {				
					e1.printStackTrace();
				}
			}
			return null;
			
		}
		@Override
		public boolean update(AccountTransaction t) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean delete(AccountTransaction t) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public AccountTransaction findById(int id) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Set<AccountTransaction> selectAll() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public List<AccountTransaction> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
		public boolean createTransaction() {
			// TODO Auto-generated method stub
			return false;
		}
}
