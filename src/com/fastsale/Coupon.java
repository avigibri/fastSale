package com.fastsale;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Table(name = "Coupon")
@Entity
public class Coupon
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    //private int businessid;
    private double price;
    private String details; 
    private Blob avatar;
    private String path;
    private Business business;

    public Coupon()
    {
	super();
    }

    public Coupon(int id, double price, String details,Blob avatar, String path, Business business)
    {
	super();
	this.id = id;
	//this.businessid = businessid;
	this.price = price;
	this.details = details;
	this.avatar = avatar;
	this.path = path;
	this.business = business;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public Blob getAvatar() {
		return avatar;
	}
    
/*	public int getBusinessid()
    {
	return businessid;
    }

    public void setBusinessid(int businessid)
    {
	this.businessid = businessid;
    }*/

    public double getPrice()
    {
	return price;
    }

    public void setPrice(double price)
    {
	this.price = price;
    }

    public String getDetails()
    {
	return details;
    }

    public void setDetails(String details)
    {
	this.details = details;
    }

    @Override
    public String toString()
    {
	return ("[ " + id + ", " + " " + ", " + price + ", " + details + "]");
    }

	public void setAvatar(Blob blob) {
		// TODO Auto-generated method stub
		this.avatar = blob;
		
	}
	
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "businessid")
	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}
    
}