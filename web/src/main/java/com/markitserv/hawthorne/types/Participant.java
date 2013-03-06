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
public class Participant extends Type {

	private int id;
	private String name;

	private Set<User> users;

	private Set<LegalEntity> legalEntities;
	private Set<LegalEntityList> legalEntityLists;
	private Set<Book> books;
	private Set<BookList> bookLists;
	private Set<Product> products;
	private Set<ProductList> productLists;
	private Set<SubGroup> subgroups;
	private Set<InterestGroup> interestGroups;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> product) {
		this.products = product;
	}

	public Set<ProductList> getProductLists() {
		return productLists;
	}

	public void setProductLists(Set<ProductList> productLists) {
		this.productLists = productLists;
	}

	public Set<SubGroup> getSubgroups() {
		return subgroups;
	}

	public void setSubgroups(Set<SubGroup> subgroups) {
		this.subgroups = subgroups;
	}

	public Set<InterestGroup> getInterestGroups() {
		return interestGroups;
	}

	public void setInterestGroups(Set<InterestGroup> interestGroups) {
		this.interestGroups = interestGroups;
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
