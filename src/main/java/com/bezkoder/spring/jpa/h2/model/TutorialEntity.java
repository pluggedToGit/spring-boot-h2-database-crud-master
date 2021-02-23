package com.bezkoder.spring.jpa.h2.model;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
@SqlResultSetMapping(
		name="pojomapping",
		classes={
				@ConstructorResult(
						targetClass=TutorialNonEntityPOJO.class,
						columns={
								@ColumnResult(name="title"),
								@ColumnResult(name="description"),
								@ColumnResult(name="published")
						}
				)
		}
)
@org.hibernate.annotations.NamedNativeQuery(name="TutorialEntity.getTutorialFromTitleForPOJO", query = "select title, description, published from tutorials where title = :title", resultSetMapping = "pojomapping")
public class TutorialEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "published")
	private boolean published;

	public TutorialEntity() {

	}

	public TutorialEntity(String title, String description, boolean published) {
		this.title = title;
		this.description = description;
		this.published = published;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	@Override
	public String toString() {
		return "TutorialEntity [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
	}

}
