package net.anotheria.anodoc.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.anotheria.util.xml.XMLNode;

/**
 * This property hold the list of Properties.
 *
 * @since 1.0
 * @author another
 * @version $Id: $Id
 */
public class ListProperty extends Property{
	/**
	 * svid.
	 */
	private static final long serialVersionUID = -8714197156967135129L;
	
	/**
	 * Creates a new ListProperty with the given name
	 * and an empty list as data.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public ListProperty(String name){
		super(name, new ArrayList<Property>());
	}
	
	/**
	 * Creates a new ListProperty with the given name
	 * and the given list. The list should only contain Property objects.<br>
	 * <b>Warning: </b> the content of the list parameter will be not explicitely check
	 * to contain only Property object, but will cause runtime exceptions later in case it contained something else.
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param aList a {@link java.util.List} object.
	 */
	public ListProperty(String name, List<Property> aList){
		super(name, aList);
	}
	
	
	/**
	 * Returns the underlying list as java.util.List
	 *
	 * @return a {@link java.util.List} object.
	 */
	@SuppressWarnings("unchecked")
	public List<Property> getList(){
		return (List<Property>) getValue();
	}
	
	/**
	 * Returns the Property at position specified by index.
	 *
	 * @param index a int.
	 * @return a {@link net.anotheria.anodoc.data.Property} object.
	 */
	public Property get(int index){
		return getList().get(index);
	}
	
	/**
	 * Adds the property p to the current list at position index (equal to java.util.List.set(index, object)
	 *
	 * @param index a int.
	 * @param p a {@link net.anotheria.anodoc.data.Property} object.
	 */
	public void add(int index, Property p){
		getList().set(index, p);
	}
	
	/**
	 * Adds the property p to the current list.
	 *
	 * @param p a {@link net.anotheria.anodoc.data.Property} object.
	 */
	public void add(Property p){
		getList().add(p);
	}

	/**
	 * Removes the given property from this list. If the same property is
	 * contained in the list more then one time, only the first occurence of the property will be removed.
	 *
	 * @param p a {@link net.anotheria.anodoc.data.Property} object.
	 */
	public void remove(Property p){
		getList().remove(p);
	}
	
	/**
	 * Remove the property at position index from this list.
	 *
	 * @param index a int.
	 */
	public void remove(int index){
		getList().remove(index);
	}
	

	/**
	 * {@inheritDoc}
	 *
	 * Returns the cumulative size of contained properties. The ListProperty itself is not counted.
	 */
	@Override public long getSizeInBytes() {
		long sum = 0;
		int mySize = getList().size();
		for (int i=0; i<mySize; i++)
			sum += get(i).getSizeInBytes();
		return sum;
	}
	
	/**
	 * <p>getListData.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Object> getListData(){
		List<Property> propertyList = getList();
		List<Object> ret = new ArrayList<Object>(propertyList.size());
		for (int i=0; i<propertyList.size(); i++){
			Property p = propertyList.get(i);
			ret.add(p.getValue());
		}
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see net.anotheria.anodoc.data.Property#cloneValue()
	 */
	/** {@inheritDoc} */
	@Override protected Object cloneValue() throws CloneNotSupportedException{
		List<Property> src = getList();
		List<Property> ret = new ArrayList<Property>(src.size());
		
		for (Iterator<Property> it = src.iterator(); it.hasNext();)
			ret.add((Property)(it.next()).clone());		
		
		return ret;
	}

	/** {@inheritDoc} */
	@Override public XMLNode toXMLNode(){
		XMLNode elem = super.toXMLNode();
		
		elem.setContent("");
		
		for (Property p : getList()){
			elem.addChildNode(p.toXMLNode());
		}
		
		return elem;
	}
	
	/** {@inheritDoc} */
	@Override public PropertyType getPropertyType(){
		return PropertyType.LIST;
	}


}
