package com.fastsale;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


public class DBController
{
    private static DBController DBinstance = null;
    private static boolean firstCreation = true;

    public static DBController CreateHibernateDAOManager()
    {
	if (firstCreation)
	{
	    firstCreation = false;
	    DBinstance = new DBController();
	}
	return DBinstance;
    }

    private DBController()
    {
    }
    public boolean Add(Business business) throws CouponException
    {
	SessionFactory factory = null;
	Session session = null;

	try
	{
	    factory = new AnnotationConfiguration().configure()
		    .buildSessionFactory();
	    session = factory.openSession();
	    session.beginTransaction();
	    session.save(business);
	    session.getTransaction().commit();
	}
	catch (Exception e)
	{
	    session.getTransaction().rollback();
	    throw new CouponException(e.getMessage(), e);
	}
	finally
	{
	    if (session != null)
		session.close();
	}

	return true;
    }

    public boolean Add(Coupon coupon) throws CouponException
    {
	SessionFactory factory = null;
	Session session = null;

	try
	{
	    factory = new AnnotationConfiguration().configure()
		    .buildSessionFactory();
	    session = factory.openSession();
	    session.beginTransaction();
	    session.save(coupon);
	    session.getTransaction().commit();
	}
	catch (Exception e)
	{
	    session.getTransaction().rollback();
	    throw new CouponException(e.getMessage(), e);
	}
	finally
	{
	    if (session != null)
		session.close();
	}

	return true;
    }

    public Coupon GetCoupon(int id) throws CouponException
    {
	Session session = null;
	SessionFactory factory = null;
	Coupon cp = null;
	try
	{
	    factory = new AnnotationConfiguration().configure()
		    .buildSessionFactory();
	    session = factory.openSession();
	    session.beginTransaction();
	    cp = (Coupon) session.get(Coupon.class, id);
	    session.getTransaction().commit();
	}
	catch (HibernateException e)
	{
	    session.getTransaction().rollback();
	    throw new CouponException(e.getMessage(), e);
	}
	finally
	{
	    if (session != null)
		session.close();
	}

	return cp;
    }

    public Business GetBusiness(int id) throws CouponException
    {
	Session session = null;
	SessionFactory factory = null;
	Business business = null;
	try
	{
	    factory = new AnnotationConfiguration().configure()
		    .buildSessionFactory();
	    session = factory.openSession();
	    session.beginTransaction();
	    business = (Business) session.get(Business.class, id);
	    session.getTransaction().commit();
	}
	catch (HibernateException e)
	{
	    session.getTransaction().rollback();
	    throw new CouponException(e.getMessage(), e);
	}
	finally
	{
/*	    if (session != null)
		session.close();*/
	}

	return business;
    }
    
    @SuppressWarnings("unchecked")
    public Iterator<Coupon> GetCoupons() throws CouponException
    {
	Session session = null;
	SessionFactory factory = null;
	List<Coupon> coupons = null;
	try
	{
	    factory = new AnnotationConfiguration().configure()
		    .buildSessionFactory();
	    session = factory.openSession();
	    session.beginTransaction();
	    coupons = session.createQuery("from Coupon").list();
	    session.getTransaction().commit();
	}
	catch (HibernateException e)
	{
	    session.getTransaction().rollback();
	    throw new CouponException(e.getMessage(), e);
	}
	finally
	{
	    if (session != null)
		session.close();
	}

	return coupons.iterator();

    }

    public boolean UpdateCoupon(Coupon ob) throws CouponException
    {
	Session session = null;
	SessionFactory factory = null;
	try
	{
	    factory = new AnnotationConfiguration().configure()
		    .buildSessionFactory();
	    session = factory.openSession();
	    session.beginTransaction();
	    session.update(ob);
	    session.getTransaction().commit();
	}
	catch (HibernateException e)
	{
	    session.getTransaction().rollback();
	    throw new CouponException(e.getMessage(), e);
	}
	finally
	{
	    if (session != null)
		session.close();
	}

	return true;
    }

    public boolean DeleteCoupon(int id) throws CouponException
    {
	Session session = null;
	SessionFactory factory = null;
	try
	{
	    factory = new AnnotationConfiguration().configure()
		    .buildSessionFactory();
	    session = factory.openSession();
	    session.beginTransaction();
	    session.delete(session.get(Coupon.class, id));
	    session.getTransaction().commit();
	}
	catch (Exception e)
	{
	    session.getTransaction().rollback();
	    throw new CouponException(e.getMessage(), e);
	}
	finally
	{
	    if (session != null)
		session.close();
	}

	return true;
    }
    
    public List<Business> validateLogin(String user, String password) throws CouponException{
    	
		Session session = null;
		SessionFactory factory = null;
		List<Business> business = null;
		
		try
		{
		    factory = new AnnotationConfiguration().configure()
			    .buildSessionFactory();
		    session = factory.openSession();
		    session.beginTransaction();
		    System.out.println(user + " " + password);
		    business = session.createQuery("from Business where businessName=" + "'" + user + "'" +" and password=" + "'" + password + "'" ).list();
		    session.getTransaction().commit();
		}
		catch (HibernateException e) 
		{
		    session.getTransaction().rollback();
		    throw new CouponException(e.getMessage(), e);
		}
		finally
		{
		    if (session != null)
			session.close();
		}

		return business;

    }

	public Iterator<Coupon> GetMyCoupons() throws CouponException {
		// TODO Auto-generated method stub
		
		Session session = null;
		SessionFactory factory = null;
		List<Coupon> coupons = null;
		
		try
		{
		    factory = new AnnotationConfiguration().configure()
			    .buildSessionFactory();
		    session = factory.openSession();
		    session.beginTransaction();
		    coupons = session.createQuery("from Coupon where businessId=2").list();
		    session.getTransaction().commit();
		}
		catch (HibernateException e)
		{
		    session.getTransaction().rollback();
		    throw new CouponException(e.getMessage(), e);
		}
		finally
		{
		    if (session != null)
			session.close();
		}

		return coupons.iterator();

	}
}
