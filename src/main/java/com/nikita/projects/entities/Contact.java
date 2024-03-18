package com.nikita.projects.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTACTS")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "c_id")
	private int cId;
	
	@Column(name = "contact_name")
	private String name;
	
	private String nickname;
	
	private String work;
	
	private String email;
	
	private String contactNo;
	
	@Column (name = "c_dec", length = 500)
	private String description;
	
	private String imageUrl;
	
	@ManyToOne
	private User user;
	
	public Contact() {
		super();
	}

	public Contact(int cId, String name, String nickname, String work, String email, String contactNo,
			String description, String imageUrl, User user) {
		super();
		this.cId = cId;
		this.name = name;
		this.nickname = nickname;
		this.work = work;
		this.email = email;
		this.contactNo = contactNo;
		this.description = description;
		this.imageUrl = imageUrl;
		this.user = user;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [cId=" + cId + ", name=" + name + ", nickname=" + nickname + ", work=" + work + ", email="
				+ email + ", contactNo=" + contactNo + ", description=" + description + ", imageUrl=" + imageUrl
				+ ", user=" + user + "]";
	}
	
}
