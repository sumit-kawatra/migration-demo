/**
 * 
 */
package com.markitserv.hawthorne.types;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.msws.Type;

/**
 * @author kiran.gogula
 * 
 */
public class LegalEntityList extends Type {

	private int id;
	private String name;
	private Set<LegalEntity> legalEntities;
	private int participantId;

	public Set<LegalEntity> getLegalEntities() {
		return legalEntities;
	}

	public void setLegalEntities(Set<LegalEntity> legalEntities) {
		this.legalEntities = legalEntities;
	}

	public void addLegalEntity(LegalEntity legalEntity) {
		this.legalEntities.add(legalEntity);
	}

	public LegalEntityList(int id, String name) {
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
		return HashCodeBuilder.reflectionHashCode(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		LegalEntityList book = (LegalEntityList) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(id, book.id)
				.isEquals();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		participantId = participantId;
	}

}
