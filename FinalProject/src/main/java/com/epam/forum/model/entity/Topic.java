package com.epam.forum.model.entity;

import java.time.LocalDateTime;

public class Topic extends Entity {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String header;
	private String content;
	private boolean pinned;
	private boolean closed;
	private LocalDateTime creationDate;
	private Section section;
	private User user;
	
	public Topic() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long topicId) {
		this.id = topicId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean isPinned) {
		this.pinned = isPinned;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean isClosed) {
		this.closed = isClosed;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + (closed ? 1231 : 1237);
		result = prime * result + (pinned ? 1231 : 1237);
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (closed != other.closed)
			return false;
		if (pinned != other.pinned)
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Topic [id=");
		builder.append(id);
		builder.append(", header=");
		builder.append(header);
		builder.append(", content=");
		builder.append(content);
		builder.append(", isPinned=");
		builder.append(pinned);
		builder.append(", isClosed=");
		builder.append(closed);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", section=");
		builder.append(section);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}
}
