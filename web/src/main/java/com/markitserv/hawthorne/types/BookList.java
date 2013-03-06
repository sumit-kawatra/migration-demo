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
public class BookList extends Type {

	private int id;
	private String name;
	private Set<Book> books;
	private int participantId;

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		this.books.add(book);
	}

	public BookList(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public int hashCode() {
		return Objects.hashCode(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
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

}
