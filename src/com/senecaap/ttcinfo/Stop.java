package com.senecaap.ttcinfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Stop implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3808410050267153857L;
//	Incompatible class (SUID): business.Stop: static final long serialVersionUID =-7634130594271720384L; but expected business.Stop: static final long serialVersionUID =5423939759832413490L;
	private String name;
	private int code;
	private int id;
	private double lat;
	private double lon;
	transient private List<Direction> directions;
	
	private static final String NEXTBUS_URL = "http://webservices.nextbus.com/service/publicXMLFeed?command=predictions&a=ttc&stopId=";
	//private static final String NEXTBUS_URL = "http://www.google.ca";
	
	public Stop() {
	}

	public List<Direction> getDirections()
	{
		return directions;
	}
	
	public void setDirections(List<Direction> d)
	{
		directions = d;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	
	public void fetchBusInfo() throws ClientProtocolException, IOException{
		
		StringBuilder buffer = new StringBuilder();
		
		
		DefaultHttpClient c = new DefaultHttpClient();
		HttpGet hget = new HttpGet(NEXTBUS_URL + this.code);
//		HttpGet hget = new HttpGet(NEXTBUS_URL);
		HttpResponse res = c.execute(hget);
		
		List<Direction> dset = new ArrayList<Direction>();
		
		
		if (res.getStatusLine().getStatusCode() == 200)
		{
			HttpEntity entity;
			entity = res.getEntity();

			if (entity != null) {
				entity = new BufferedHttpEntity(entity);
			}

			InputStream is = entity.getContent();
			Reader reader = new InputStreamReader(is);

			try {
				char[] tmp = new char[1024];
				int l;
				while ((l = reader.read(tmp)) != -1) {
					buffer.append(tmp, 0, l);
				}
			} finally {
				reader.close();
			}	
			
			Document doc = Jsoup.parse(buffer.toString());
			
			Elements delem = doc.select("direction");
			
			for (Element e : delem)
			{
				
				Direction newd = new Direction();
				newd.setTitle(e.attr("title"));
		
				Elements pelem = e.select("prediction");
				List<Prediction> pset = new ArrayList<Prediction>();
				
				for (Element pe : pelem)
				{
					
					newd.setBranch(pe.attr("branch"));
					Prediction newp = new Prediction();
					
					newp.setMinutes(Integer.parseInt(pe.attr("minutes")));
					newp.setSeconds(Integer.parseInt(pe.attr("seconds")));
					
					pset.add(newp);
					
				}
				
				newd.setPredictions(pset);
				
				dset.add(newd);
				
			}
			
			setDirections(dset);
			
		} else {
			
			throw new IOException("NextBus server down");
		}
		
	}
	
	public void printBusInfo()
	{
		
		System.out.println("Bus stop = " + this.code + ":");
		
		Iterator<Direction> i = directions.iterator();
		
		while(i.hasNext())
		{
			
			Direction dtemp = i.next();
			System.out.print("  Route " + dtemp.getBranch() + ":");
			 Collections.sort(dtemp.getPredictions());
			Iterator<Prediction> ip = dtemp.getPredictions().iterator();
			
			while(ip.hasNext())
			{
				System.out.print(" " + ip.next().getMinutes());
			}
			
			System.out.print(" minutes\n");
			
		}
		
	}
}