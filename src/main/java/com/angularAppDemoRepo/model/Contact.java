package com.angularAppDemoRepo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "CONTACT")
public class Contact implements Serializable {
    private static final long serialVersionUID = -8162130538909152998L;

    @Id
    @Column(name = "CONTACT_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_ID_SEQUENCE")
    @SequenceGenerator(name = "CONTACT_ID_SEQUENCE", sequenceName = "CONTACT_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    private Long contactId;

    @Column(name = "NAME", nullable = false)
    private String contactName;

    @Column(name = "COMPANY", nullable = false)
    private String company;
    
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "EMAIL", nullable = false)
    private String email;
    
	public Contact() {
		super();
	}

	public Contact(Long contactId, String contactName, String company, String phoneNumber, String email) {
		super();
		this.contactId = contactId;
		this.contactName = contactName;
		this.company = company;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	/**
	 * @return the contactId
	 */
	public Long getContactId() {
		return contactId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Contact: [contactId=" + contactId + ", contactName=" + contactName + ", company=" + company
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}
    
    

}
