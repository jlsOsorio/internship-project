package com.internship.retail_management.entities;

import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Route {

	private String path;
	private String method;
	
	public Route(String path, String method) {
		this.setPath(path);
		this.setMethod(method);
	}
	
	public void setPath(String path) {
		this.path = path.toLowerCase();
	}
	
	public void setMethod(String method) {
		this.method = method.toUpperCase();
	}

	@Override
	public int hashCode() {
		return Objects.hash(method, path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		return this.doesRouteMatch(other);
	}
	
	public Boolean doesRouteMatch(Route route) {
		String path = route.getPath();
		String method = route.getMethod();
		
		return this.doesPathMatch(path) && this.doesMethodMatch(method);
		
	}
	
	private Boolean doesPathMatch(String path) {
		
		if (path.endsWith("/*"))
		{
			String basePath = path.substring(0, path.length()-2);
			if (this.path.startsWith(basePath))
			{
				return true;
			}
		}
		
		boolean doesPathMatch = path.equals(this.path);
		
		return  doesPathMatch;
	}
	
	private Boolean doesMethodMatch(String method) {
		boolean doesMethodMatch = method.equals(this.method);
		 if (method == "*")
		 {
			 return true;
		 }
		 
		return  doesMethodMatch;
	}

	@Override
	public String toString() {
		return "Route [path=" + path + ", method=" + method + "]";
	}
	
}
