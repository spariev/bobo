/**
 * Bobo Browse Engine - High performance faceted/parametric search implementation 
 * that handles various types of semi-structured data.  Written in Java.
 * 
 * Copyright (C) 2005-2006  John Wang
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * To contact the project administrators for the bobo-browse project, 
 * please go to https://sourceforge.net/projects/bobo-browse/, or 
 * send mail to owner@browseengine.com.
 */

package com.browseengine.bobo.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


/**
 * A Browse result
 */
public class BrowseResult implements Serializable{
	private static final long serialVersionUID = -8620935391852879446L;
	private int numHits;
	private int totalDocs;
	private Map<String,FacetAccessible> _facetMap;
	private BrowseHit[] hits;
	private long time;
	private static BrowseHit[] NO_HITS=new BrowseHit[0];
		
	/**
	 * Constructor
	 */
	public BrowseResult() {
		super();
		_facetMap=new HashMap<String,FacetAccessible>();
		numHits=0;
		totalDocs=0;
		hits=null;
		time=0L;
	}
	
	/**
	 * Get the facets by name
	 * @param name
	 * @return FacetAccessible instance corresponding to the name
	 */
	public FacetAccessible getFacetAccessor(String name) {
      return _facetMap.get(name);
	}
	
	/**
	 * Get the hit count
	 * @return hit count
	 * @see #setNumHits(int)
	 */
	public int getNumHits() {
		return numHits;
	}

	/**
	 * Sets the hit count
	 * @param hits hit count
	 * @see #getNumHits()
	 */
	public void setNumHits(int hits) {
		numHits = hits;
	}

	/**
	 * Gets the total number of docs in the index
	 * @return total number of docs in the index.
	 * @see #setTotalDocs(int)
	 */
	public int getTotalDocs() {
		return totalDocs;
	}

	/**
	 * Sets the total number of docs in the index
	 * @param docs total number of docs in the index
	 * @see #getTotalDocs()
	 */
	public void setTotalDocs(int docs) {
		totalDocs = docs;
	}
	
	/**
	 * Add a container full of choices
	 * @param facets container full of facets
	 */
	public void addFacets(String name,FacetAccessible facets){
		_facetMap.put(name,facets);
	}	
	
	/**
	 * Add all of the given FacetAccessible to this BrowseResult
	 * @param facets map of facets to add to the result set
	 */
	public void addAll(Map<String,FacetAccessible> facets){
		_facetMap.putAll(facets);
	}
	
	/**
	 * Sets the hits
	 * @param hits hits
	 * @see #getHits()
	 */
	public void setHits(BrowseHit[] hits){
		this.hits=hits;
	}
	
	/**
	 * Gets the hits
	 * @return hits
	 * @see #setHits(BrowseHit[])
	 */
	public BrowseHit[] getHits(){
		return hits==null ? NO_HITS : hits;
	}
	
	/**
	 * Sets the search time in milliseconds
	 * @param time search time
	 * @see #getTime()
	 */
	public void setTime(long time){
		this.time=time;
	}
	
	/**
	 * Gets the search time in milliseconds
	 * @return search time
	 * @see #setTime(long)
	 */
	public long getTime(){
		return time;
	}
	
	/**
	 * Gets all the facet collections
	 * @return list of facet collections
	 */
	public Map<String,FacetAccessible> getFacetMap(){
		return _facetMap;
	}
    
	public static String toString(Map<String,FacetAccessible> map) {
		StringBuilder buffer=new StringBuilder();
		Set<Entry<String,FacetAccessible>> entries = map.entrySet();
		
		buffer.append("{");
		for (Entry<String,FacetAccessible> entry : entries)
		{
			String name = entry.getKey();
			FacetAccessible facetAccessor = entry.getValue();
			buffer.append("name=").append(name).append(",");
			buffer.append("facets=").append(facetAccessor.getFacets()).append(";");
		}
		buffer.append("}").append('\n');
		return buffer.toString();
	}
	
	@Override
	public String toString(){
		StringBuilder buf=new StringBuilder();
		buf.append("hit count: ").append(numHits).append('\n');
		buf.append("total docs: ").append(totalDocs).append('\n');
		buf.append("facets: ").append(toString(_facetMap));
		buf.append("hits: ").append(Arrays.toString(hits));
		return buf.toString();
	}
}
