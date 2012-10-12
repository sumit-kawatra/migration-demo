package com.markitserv.ssa.res;

import com.markitserv.rest.RestReference;

public class UserBook {
	
	private long id;
	private User user;
	private Book book;
	private ApprovalState approvalState;
	
	public UserBook(long id, User user, Book book) {
		super();
		this.id = id;
		this.user = user;
		this.book = book;
		this.approvalState = ApprovalState.APPROVED;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@RestReference
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@RestReference
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public ApprovalState getApprovalState() {
		return approvalState;
	}
	public void setApprovalState(ApprovalState approvalState) {
		this.approvalState = approvalState;
	}
}
