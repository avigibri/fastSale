package com.fastsale;

import java.io.DataInputStream;
import com.dropbox.core.*;

import java.io.*;
import java.util.Locale;

import java.awt.Desktop;  
import java.io.ByteArrayInputStream;  
import java.io.File;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.net.URL;  
import javax.swing.JOptionPane;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.*;
import java.net.*;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import javax.sql.DataSource;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.MultipartConfig;  
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part; 

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.tomcat.dbcp.pool.impl.GenericKeyedObjectPool.Config;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.Hibernate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;

import sun.misc.IOUtils;

import java.util.Map;
/**
 * Servlet implementation class ServletDemo
 */
@WebServlet("/Controller/*")
@MultipartConfig(maxFileSize = 16177215) 
public class Controller extends HttpServlet
{
	
    private DBController controller = null;
    private static final org.apache.log4j.Logger LOGGER2 = org.apache.log4j.Logger.getLogger(Controller.class);
    private static final long serialVersionUID = 1L;
    private static final String APP_KEY = "p2s1h8218tgwpi7";  
    private static final String APP_SECRET = "iarjz08hj3xf6sw";
    private DbxWebAuthNoRedirect webAuth = null;
    private DbxAppInfo appInfo = null;
    private DbxRequestConfig config = null;
    private static final Cloudinary cloudinary = new Cloudinary(new CloudinaryRepository("fastsale", "xXvwbHn4WiJRTkQVOboCk9-U3BY", "584341516736217"));
	private String publicId;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller()
    {
		super();
		controller = DBController.CreateHibernateDAOManager();
	}
	
	    /**
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	     *      response)
	     */
	protected void doGet(HttpServletRequest request,
		    HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispacher = null;
		MethodTypes method = null;
		int id = -1;
		String[] splitSTR = request.getPathInfo().split("/");
	
		
		// split return n+1 parameters because the string start with '/' so we
		// jump to the second index
		// while path info has more then 2 parameters we assume that the second
		// is an ID
		if (splitSTR.length == 3)
		{
		    id = Integer.parseInt(splitSTR[2]);
		}
		method = MethodTypes.valueOf(splitSTR[1]);
	
		dispacher = request.getRequestDispatcher("/" + splitSTR[1] + ".jsp");
		
		System.out.print(method);
		try
		{
		    switch (method)
		    {
			case help:
			    break;
			case contact:
			    break;
			case add:
			    addCoupon(request, response);
			    break;
			case update:
			    updateCoupon(request, response);
			    break;
			case about:
				about(request, response);
				break;
			case delete:
			    deleteCoupon(request, response);
			    break;
			case coupons:
			    getCoupons(request, response);
			    break;
			case coupon:
			    getCoupon(request, response, id);
			    break;
			case myCoupons:
				getMyCoupons(request, response);
			    break;
			case signUp:
				signUp(request, response);
			    break;
			case login:
				login(request, response);
			    break;
			default:
			    log("<---> Not supported action <--->");
			    break;
	
		    }
		}
		catch (CouponException e)
		{
		    e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudinaryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//dispacher.forward(request, response);
	    }
	
	    private void about(HttpServletRequest request,
				HttpServletResponse response) {
			// TODO Auto-generated method stub
	    	
	    	System.out.print("here");
			
		}

		private void login(HttpServletRequest request,
				HttpServletResponse response) throws CouponException {
			// TODO Auto-generated method stub
	    	
	    	log("<---> login method has been started! <--->");
	    	
	        String businessName = request.getParameter("businessName");
	    	String password = request.getParameter("password");
	    	
	         appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
	         config = new DbxRequestConfig("JavaTutorial/1.0",
	         Locale.getDefault().toString());
	         webAuth = new DbxWebAuthNoRedirect(config, appInfo);
	        
	        // DbxWebAuth webAuth2 = new DbxWebAuth(config, appInfo, fileName, null);
	         // Have the user sign in and authorize your app.
	         String authorizeUrl = webAuth.start();
	         System.out.println("1. Go to: " + authorizeUrl);
	         System.out.println("2. Click \"Allow\" (you might have to log in first)");
	         System.out.println("3. Copy the authorization code.");
	    	String md5 = null;
	    	try {
	    	MessageDigest mdEnc = MessageDigest.getInstance("MD5"); // Encryption algorithm
	    	mdEnc.update(password.getBytes(), 0, password.length());
	    	md5 = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
	    	} catch (Exception ex) {
	    	return;
	    	}
	    	List<Business> loginlist = controller.validateLogin(businessName, md5);
	    	System.out.println(loginlist);
	    	if(loginlist.size()>0)
	    	{
	    	System.out.print("loginsuccess");
	    	
			//userSession.setAttribute("user",businessName);
	    	}else{
	    	System.out.print("loginfaild");
	    	}
	    	
	    	request.setAttribute("url", authorizeUrl);
	    	log("<---> login method has been ended! <--->");
		}

		private void signUp(HttpServletRequest request,
				HttpServletResponse response) throws CouponException {
	    	log("<---> signup method has been started! <--->");
	    	Business business = new Business();
			
			//cp.setBusinessid(Integer.parseInt(request.getParameter("businessid")));
	    	business.setBusinessCode("1ED");
	    	business.setBusinessId(1);
	    	business.setLocation(request.getParameter("location"));
	    	business.setBusinessName(request.getParameter("businessName"));
	    	business.setEmail(request.getParameter("mail"));
	    	business.setPhone(request.getParameter("phone"));
	    	String password = request.getParameter("password");
	    	
	    	String md5 = null;
	    	try {
	    	MessageDigest mdEnc = MessageDigest.getInstance("MD5"); // Encryption algorithm
	    	mdEnc.update(password.getBytes(), 0, password.length());
	    	md5 = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
	    	} catch (Exception ex) {
		    	return;
		    	}
	    	business.setPassword(md5);
	    	
	    	if (controller.Add(business))
			{
			    log("<---> Add user PASS! <--->");
			}
			else
			{
			    log("<---> Add user FAILED! <--->");
			}
		log("<---> signup method has been Ended! <--->");

			//cp.setAvatar(bFile);
	    	
			
		}


		/**
	     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	     *      response)
	     */
	    protected void doPost(HttpServletRequest request,
		    HttpServletResponse response) throws ServletException, IOException
	    {
		doGet(request, response);
	    }
	
	    private void addCoupon(HttpServletRequest request,
		    HttpServletResponse response) throws CouponException, IOException, IllegalStateException, ServletException, DbxException, CloudinaryException
	    {
		log("<---> Add method has been Invoke! <--->");
		
		 Part p1 = request.getPart("photo");
         InputStream photo = p1.getInputStream();
         
         
         String fileName = "/" + getFilename(p1);
         System.out.println("filename" + fileName);
         System.out.println("content type" + p1.getContentType());
         byte[] existingPhotoBytes = toByteArrayUsingJava(photo);
         
         LOGGER2.info("GET FILE NAME" + fileName);
         publicId = null;

         try {
             publicId = cloudinary.postPhotoToCloudinary(existingPhotoBytes, p1.getContentType());
         } catch (CloudinaryException e) {
             throw new CloudinaryException("Exception occured while attempting to Upload Existing Photo to Cloudinary.", e);
         }
         
         URI uri = cloudinary.buildCloudinaryPhotoURI(publicId, p1.getContentType(), 400, 400);

         LOGGER2.info("URL" + uri.toString());
		    Business business = controller.GetBusiness(11);
			System.out.print(business);
			if (business!= null)
			{
			    log("<---> get business PASS! <--->");
			}
			
			Coupon cp = new Coupon();
			//cp.setBusinessid(Integer.parseInt(request.getParameter("businessid")));
			cp.setPrice(Double.parseDouble(request.getParameter("price")));
			cp.setDetails(request.getParameter("details"));
			cp.setPath(uri.toString());
			cp.setId(1);
			cp.setBusiness(business);
			business.getRelatedCoupons().add(cp);
			//cp.setAvatar(bFile);
			
				if (controller.Add(cp))
				{
				    request.setAttribute("addedCoupon", cp);
				    log("<---> Add Coupon PASS! <--->");
				}
				else
				{
				    String str = "The system failed to add the coupon";
				    request.setAttribute("Failed", str);
				    log("<---> Add Coupon FAILED! <--->");
				}
			log("<---> Add method has been Ended! <--->");

		
	    }
	    private void deleteCoupon(HttpServletRequest request,
		    HttpServletResponse response) throws CouponException
	    {
		log("<---> Delete method has been Invoke! <--->");
		int id = Integer.parseInt(request.getParameter("id"));
		Coupon cp = controller.GetCoupon(id);
		if (controller.DeleteCoupon(id))
		{
		    request.setAttribute("deletedCoupon", cp);
		    log("<---> Delete Coupon PASS! <--->");
		} else
		{
		    String str = "The system faild to delete the coupon";
		    request.setAttribute("Failed", str);
		    log("<---> Delete Coupon FAILED! <--->");
		}
		log("<---> Delete method has been ended! <--->");
    }
	
	 
    private void updateCoupon(HttpServletRequest request,
	    HttpServletResponse response) throws CouponException, IllegalStateException, IOException, ServletException
    {
	log("<---> Update method has been Invoke! <--->");
	Coupon cp = null, beforeCp = null;
	int id = Integer.parseInt(request.getParameter("couponId"));
//	String businessid = request.getParameter("businessid");
	String price = request.getParameter("price");
	String details = request.getParameter("details");

	log("<---> Add method has been Invoke! <--->");
	
    Part p1 = request.getPart("photo");
    
    InputStream is = p1.getInputStream();

    String filename = getFilename(p1);
    
    log(filename);
    
	String absoluteFilePath = "c://uploads//" + filename ;

	 FileOutputStream os = new FileOutputStream (absoluteFilePath);
      
      
      // write bytes taken from uploaded file to target file
      int ch = is.read();
      while (ch != -1) {
           os.write(ch);
           ch = is.read();
      }
      
      log("finish to write");
      os.close();
      

      
	cp = controller.GetCoupon(id);
	beforeCp = new Coupon(cp.getId(), cp.getPrice(),
		cp.getDetails(),cp.getAvatar(),cp.getPath(),cp.getBusiness());
	
    String absoluteFilePath2 = "c://uploads//" + cp.getPath() ; 
    
    if (new File(absoluteFilePath2).delete())
    {
    	System.out.print(absoluteFilePath2);
    	System.out.print("success");
    }
    
    
	/*if (businessid != "")
	    cp.setBusinessid(Integer.parseInt(businessid));*/
	    cp.setPrice(Double.parseDouble(price));
	    cp.setDetails(details);
	    cp.setPath(absoluteFilePath);

	if (controller.UpdateCoupon(cp))
	{
	    request.setAttribute("beforeUpdate", beforeCp);
	    request.setAttribute("updateCoupon", cp);
	    log("<---> Update Coupon PASS! <--->");
	} else
	{
	    String str = "The system failed to update the coupon";
	    request.setAttribute("Failed", str);
	    log("<---> Update Coupon FAILED! <--->");
	}
	log("<---> Update method has been ended! <--->");
    }
    
    private void getCoupon(HttpServletRequest request,
	    HttpServletResponse response, int id) throws CouponException
    {
	log("<---> getCoupon method has been Invoke! <--->");
	Coupon cp = controller.GetCoupon(id);
	if(cp != null)
	{
	    request.setAttribute("currentCoupon", cp);
	    log("<---> getCoupon PASS! <--->");
	} else
	{
	    String str = "The system faild to get the coupon";
	    request.setAttribute("Failed", str);
	    log("<---> getCoupon FAILED! <--->");
	}
	log("<---> getCoupon method has been ended! <--->");
    }
    
    private void getCoupons(HttpServletRequest request,
	    HttpServletResponse response) throws CouponException, IOException
    {
	log("<---> getCoupons method has been Invoke! <--->");
	Iterator<Coupon> itr = controller.GetCoupons();
	JSONArray jArray = new JSONArray();
	Coupon cp = null;
	int i=0;
	while (itr.hasNext())
	{
	    cp = itr.next(); 
        JSONObject arrayObj = new JSONObject();

        arrayObj.put("id",cp.getId());
        arrayObj.put("path",cp.getPath());
        arrayObj.put("price",cp.getPrice());
        arrayObj.put("details",cp.getDetails());     
        jArray.add(i,arrayObj);
        i++;
	}
	
	if(itr != null)
	{
	    request.setAttribute("allCoupons", jArray);
	    log("<---> getCoupons PASS! <--->");
	} 
	else
	{
	    String str = "The system faild to get the coupons";
	    request.setAttribute("Failed", str);
	    log("<---> getCoupon FAILED! <--->");
	}
	log("<---> getCoupons method has been ended! <--->");
	response.setContentType("application/json");
	// Get the printwriter object from response to write the required json object to the output stream      
	PrintWriter out = response.getWriter();
	// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
	out.print(jArray);
	out.flush();
	
    }
    
    private void getMyCoupons(HttpServletRequest request,
    	    HttpServletResponse response) throws CouponException
        {
    	log("<---> GetMyCoupons method has been Invoke! <--->");
    	Iterator<Coupon> itr = controller.GetMyCoupons();
    	
    	if(itr != null)
    	{
    	    request.setAttribute("myCoupons", itr);
    	    log("<---> getMyCoupons PASS! <--->");
    	} else
    	{
    	    String str = "The system faild to get the coupons";
    	    request.setAttribute("Failed", str);
    	    log("<---> getMyCoupon FAILED! <--->");
    	}
    	log("<---> GetMyCoupons method has been ended! <--->");
        }
    
    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
    
    public static byte[] toByteArrayUsingJava(InputStream is) throws IOException{ ByteArrayOutputStream baos = new ByteArrayOutputStream(); int reads = is.read(); while(reads != -1){ baos.write(reads); reads = is.read(); } return baos.toByteArray(); }

    
}