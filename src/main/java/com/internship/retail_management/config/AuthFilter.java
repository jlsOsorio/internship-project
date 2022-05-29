package com.internship.retail_management.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.internship.retail_management.dto.UserDTO;
import com.internship.retail_management.entities.Route;
import com.internship.retail_management.services.UserService;
import com.internship.retail_management.services.exceptions.AuthException;

@Component
public class AuthFilter implements Filter {

    @Autowired
	UserService userService;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
      throws IOException, ServletException {
    	HttpServletResponse resp = (HttpServletResponse) response;
    	HttpServletRequest req = (HttpServletRequest) request;
    	
    	//NORMAL ROUTES
		//users
		Route NORMAL_USER_GET_ALL = new Route("/users", "GET");
		Route NORMAL_USER_GET = new Route("/users/me", "GET");
		Route NORMAL_USER_CHANGE_PASSWORD = new Route("/users/me/changepassword", "PATCH");
		
		//cash registers
		Route NORMAL_CR_GET = new Route("/cashregisters/*", "GET");
		
		//invoices
		Route NORMAL_INVOICES_GET = new Route("/invoices/*", "GET");
		Route NORMAL_INVOICES_POST = new Route("/invoices", "POST");
		
		//invoiced products
		Route NORMAL_INVOICED_PRODUCTS_GET = new Route("/invoicedproducts/*", "GET");
		
		//iva
		Route NORMAL_IVA_GET = new Route("/iva/*", "GET");
		
		//operating funds
		Route NORMAL_OPERATING_FUNDS_GET = new Route("/operatingfunds/me", "GET");
		Route NORMAL_OPERATING_FUNDS_POST = new Route("/operatingfunds/me", "POST");
		
		//products
		Route NORMAL_PRODUCTS_GET = new Route("/products/*", "GET");
		
		//stock movements
		Route NORMAL_STOCK_MOVEMENTS_GET = new Route("/stockmovements/*", "GET");
		
		//stores
		Route NORMAL_STORES_GET = new Route("/stores/*", "GET");
		
		
		
		
		//SUPERVISOR ROUTES
		//users
		Route SUPERVISOR_USERS_ALL = new Route("/users/*", "*");
		
		//cash registers
		Route SUPERVISOR_CR_ALL = new Route("/cashregisters/*", "*");
		
		//invoices
		Route SUPERVISOR_INVOICES_ALL = new Route("/invoices/*", "*");
		
		//invoiced products
		Route NORMAL_INVOICED_PRODUCTS_ALL = new Route("/invoicedproducts/*", "*");
		
		//iva
		Route NORMAL_IVA_ALL = new Route("/iva/*", "*");
		
		//operating funds
		Route SUPERVISOR_OPERATING_FUNDS_ALL = new Route("/operatingfunds/*", "*");
		
		//products
		Route SUPERVISOR_PRODUCTS_ALL = new Route("/products/*", "*");
		
		//stock movements
		Route SUPERVISOR_STOCK_MOVEMENTS_ALL = new Route("/stockmovements/*", "*");
		
		//stores
		Route SUPERVISOR_STORES_ALL = new Route("/stores/*", "*");
		
		
		//Routes normal and supervisor arrays
		
		Route[] normalRoutes = {NORMAL_USER_GET_ALL,
				NORMAL_USER_GET,
				NORMAL_USER_CHANGE_PASSWORD,
				NORMAL_CR_GET,
				NORMAL_INVOICES_GET,
				NORMAL_INVOICES_POST,
				NORMAL_INVOICED_PRODUCTS_GET,
				NORMAL_IVA_GET,
				NORMAL_OPERATING_FUNDS_GET,
				NORMAL_OPERATING_FUNDS_POST,
				NORMAL_PRODUCTS_GET,
				NORMAL_STOCK_MOVEMENTS_GET,
				NORMAL_STORES_GET};
		
		Route[] supervisorRoutes = {SUPERVISOR_USERS_ALL,
				SUPERVISOR_CR_ALL,
				SUPERVISOR_INVOICES_ALL,
				NORMAL_INVOICED_PRODUCTS_ALL,
				NORMAL_IVA_ALL,
				SUPERVISOR_OPERATING_FUNDS_ALL,
				SUPERVISOR_PRODUCTS_ALL,
				SUPERVISOR_STOCK_MOVEMENTS_ALL,
				SUPERVISOR_STORES_ALL};
	
    	try
    	{
    		String pathURI = req.getRequestURI();
    		String method = req.getMethod();
    		
    		Route requestedRoute = new Route(pathURI, method);
    		
    		
    		boolean isNormalRoute = Arrays.asList(normalRoutes).contains(requestedRoute);
    		boolean isSupervisorRoute = Arrays.asList(supervisorRoutes).contains(requestedRoute);
    		
            if (isNormalRoute || isSupervisorRoute)
            {
            	String token = req.getHeader("Authorization");

        		UserDTO user = userService.getUserByToken(token);
        		
        		req.setAttribute("userId", user.getId().toString());
        		
        		String userCategory = user.getCategory().toString();
        		        		
        		if (isNormalRoute)
        		{
        			chain.doFilter(request, response);
        		}
        		else
        		{
        			if (userCategory.equals("SUPERVISOR"))
        			{
            			chain.doFilter(request, response);
        			}
        			else
        			{
    					resp.reset();
        		        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        		        resp.getWriter().write("User without supervisor permissions.");
        		        return;
        			}
        			
        		}
        		
            }
            else
            {
            	chain.doFilter(request, response);
            }
    	}
    	catch (AuthException e)
    	{
			resp.reset();
	        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        resp.getWriter().write(e.getMessage());
		
    	}
       
    }

}