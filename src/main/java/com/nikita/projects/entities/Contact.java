package com.nikita.projects.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CONTACTS")
public class Contact {
	
	private static final String EMAIL_REGEXP = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "c_id")
	private int cId;
	
	@Column(name = "contact_name")
	@Size(min = 2, max = 20, message = "Name must have characters ranging from 2 to 20")
	private String name;
	
	@Size(min = 2, max = 20, message = "Name must have characters ranging from 2 to 20")
	private String nickname;
	
	private String work;
	
	@Pattern(regexp = EMAIL_REGEXP, message = "Invalid email")
	private String email;
	
	private String contactNo;
	
	@Column (name = "c_dec", length = 500)
	@NotEmpty(message = "Desciption must be provided")
	private String description;
	
	private String imgUrl;
	
	@ManyToOne
	private User user;
	
	public Contact() {
		super();
	}

	public Contact(int cId, String name, String nickname, String work, String email, String contactNo,
			String description, String imgUrl, User user) {
		super();
		this.cId = cId;
		this.name = name;
		this.nickname = nickname;
		this.work = work;
		this.email = email;
		this.contactNo = contactNo;
		this.description = description;
		this.imgUrl = imgUrl;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	@Override
//	public String toString() {
//		return "Contact [cId=" + cId + ", name=" + name + ", nickname=" + nickname + ", work=" + work + ", email="
//				+ email + ", contactNo=" + contactNo + ", description=" + description + ", imgUrl=" + imgUrl
//				+ ", user=" + user + "]";
//	}
	
}
