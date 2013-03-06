/**
 * 
 */
package com.markitserv.hawthorne.types;

import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;
import com.markitserv.msws.Type;

/**
 * @author kiran.gogula
 * 
 */
public class SubGroup extends Type {

	private int id;
	private String name;
	private String shortName;
	private boolean active;
	private int participantId;

	private Set<User> users;
	private Set<LegalEntity> legalEntities;
	private Set<LegalEntityList> legalEntityLists;
	private Set<Book> books;
	private Set<BookList> bookLists;
	private Set<Product> product;
	private Set<ProductList> productList;

	public SubGroup(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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

	public Set<Product> getProduct() {
		return product;
	}

	public void setProduct(Set<Product> product) {
		this.product = product;
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

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
	}
}
