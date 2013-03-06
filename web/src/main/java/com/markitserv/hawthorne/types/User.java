/**
 * 
 */
package com.markitserv.hawthorne.types;

import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.markitserv.msws.Type;
import com.markitserv.msws.util.JsonTimeStampSerializer;

/**
 * @author kiran.gogula
 * 
 */
public class User extends Type {

	private int id;

	private String userName;
	private String firstName;
	private String lastName;

	private int legalEntityId;
	private int participantId;

	private String emailAddress;
	private String phoneNumber;

	@JsonSerialize(using = JsonTimeStampSerializer.class)
	private DateTime lastLogin;

	private Set<LegalEntity> legalEntities;
	private Set<LegalEntityList> legalEntityLists;
	private Set<Book> books;
	private Set<BookList> bookLists;
	private Set<Product> products;
	private Set<ProductList> productList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getLegalEntityId() {
		return legalEntityId;
	}

	public void setLegalEntityId(int legalEntityId) {
		this.legalEntityId = legalEntityId;
	}

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public DateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(DateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Set<LegalEntity> getLegalEntities() {
		return legalEntities;
	}

	public void setLegalEntities(Set<LegalEntity> legalEntities) {
		this.legalEntities = legalEntities;
	}

	public Set<LegalEntityList> getLegalEntityLists() {
		return legalEntityLists;
	}

	public void setLegalEntityLists(Set<LegalEntityList> legalEntityLists) {
		this.legalEntityLists = legalEntityLists;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Set<BookList> getBookLists() {
		return bookLists;
	}

	public void setBookLists(Set<BookList> bookLists) {
		this.bookLists = bookLists;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Set<ProductList> getProductList() {
		return productList;
	}

	public void setProductList(Set<ProductList> productList) {
		this.productList = productList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
