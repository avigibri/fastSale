package com.fastsale;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "business")
public class Business  {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Integer businessId;
	private String businessCode;
	private String location;
	private String name;
	private String password;
	private String phone;
	private String email;
	private Set<Coupon> relatedCoupons = new HashSet<Coupon>(0);
	
	public Business(String businessCode, String businessName,
			Set<Coupon> relatedCoupons) {
		this.setBusinessCode(businessCode);
		this.name = businessName;
		this.relatedCoupons = relatedCoupons;
	}
	
	public Business() {
		// TODO Auto-generated constructor stub
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getBusinessId() {
		return this.businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public String getLocation() {
		return this.location;
	}
	public void setLocation(String businessLocation) {
		this.location = businessLocation;
	}
	public String getBusinessName() {
		return this.name;
	}
	public void setBusinessName(String Name) {
		this.name = Name;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "business")
	public Set<Coupon> getRelatedCoupons() {
		return this.relatedCoupons;
	}
	public void setRelatedCoupons(Set<Coupon> relatedCoupons) {
		this.relatedCoupons = relatedCoupons;
	}

	public String getBusinessCode() {
		return this.businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
}
