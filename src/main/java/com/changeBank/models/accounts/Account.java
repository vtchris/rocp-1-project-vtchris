package com.changeBank.models.accounts;

import com.changeBank.models.users.User;

public class Account {
	private int accountId; 
	private int userId;
	private User user;
	private int acctNbr;
	private float balance;  
	private AccountStatus status;
	private AccountType type;
		
	public Account() {
		super();
	}	

	public Account(int userId, AccountType type) {
		super();
		this.userId = userId;
		this.type = type;
	}
	
	public Account(int accountId, AccountStatus status) {
		super();
		this.accountId = accountId;
		this.status = status;
	}
		
	public Account(int accountId, int userId, User user, int acctNbr, float balance, AccountStatus status, AccountType type ) {
		super();
		this.accountId = accountId;
		this.userId = userId;
		this.user = user;
		this.acctNbr = acctNbr;
		this.balance = balance;
		this.status = status;
		this.type = type;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getAcctNbr() {
		return acctNbr;
	}

	public void setAcctNbr(int acctNbr) {
		this.acctNbr = acctNbr;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", userId=" + userId + ", user=" + user + ", acctNbr=" + acctNbr
				+ ", balance=" + balance + ", status=" + status + ", type=" + type + "]";
	}

}
