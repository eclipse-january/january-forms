/*******************************************************************************
 * Copyright (c) 2012, 2016 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Initial API and implementation and/or initial documentation - Jay Jay Billings,
 *   Jordan H. Deyton, Dasha Gorin, Alexander J. McCaskey, Taylor Patterson,
 *   Claire Saunders, Matthew Wang, Anna Wojtowicz
 *******************************************************************************/
package org.eclipse.january.form;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * <p>
 * The Resource class represents persistent data resources used by January and
 * the other software packages with which it interacts. This includes files
 * containing simulation input and output data, movies and plots, amongst
 * others.
 * </p>
 * 
 * @author Scott Forest Hull II
 */
@XmlRootElement(name = "JanuaryResource")
@XmlAccessorType(XmlAccessType.FIELD)
public class JanuaryResource extends JanuaryObject {

	/**
	 * <p>
	 * A File reference to the Resource.
	 * </p>
	 * 
	 */
	@XmlAttribute()
	private File file;
	/**
	 * <p>
	 * The path to the file as a URI.
	 * </p>
	 * 
	 */
	@XmlAttribute()
	private URI path;

	/**
	 * <p>
	 * The set of properties associated with this resource.
	 * </p>
	 * 
	 */
	@XmlElementWrapper
	@XmlAnyElement(lax = true)
	private ArrayList<IEntry> properties;

	/**
	 * <p>
	 * An attribute that determines if JanuaryResouce is a picture or not. Can
	 * be set multiple times.
	 * </p>
	 * 
	 */
	@XmlAttribute
	private boolean isPicture;

	/**
	 * <p>
	 * The default constructor. If this constructor is used, a second call to
	 * setContents() must be made.
	 * </p>
	 * 
	 */
	public JanuaryResource() {

		// Set the particulars
		setName(null);
		setDescription(null);
		file = null;
		path = null;

		// Setup properties list
		properties = new ArrayList<IEntry>();

		// Default is set to false for isPicture
		isPicture = false;

		return;

	}

	/**
	 * <p>
	 * An alternative constructor that takes the File as an argument. The
	 * default values of the name, id and description for this class are the
	 * filename, 1 and the absolute path, respectively. It is the same as
	 * calling the no-arg constructor followed by calling setContents().
	 * </p>
	 * 
	 * @param resourceFile
	 *            <p>
	 *            The file that the Resource should be created to represent.
	 *            </p>
	 * @throws IOException
	 */
	public JanuaryResource(File resourceFile) throws IOException {

		// Set the particulars
		setName(resourceFile.getName());
		setId(1);
		setDescription(resourceFile.getAbsolutePath());

		// Set the file properties
		setContents(resourceFile);

		// Setup properties list
		properties = new ArrayList<IEntry>();

		// Default is set to false for isPicture
		isPicture = false;

		return;

	}

	/**
	 * <p>
	 * This operation returns the last modification date of the file.
	 * </p>
	 * 
	 * @return
	 *         <p>
	 *         The date.
	 *         </p>
	 */
	public String getLastModificationDate() {

		// Local Declarations
		Date fileDate = null;
		String retVal = "0";

		// Get the modification date if the File is good
		if (file != null) {
			fileDate = new Date(file.lastModified());
			retVal = fileDate.toString();
		}

		return retVal;
	}

	/**
	 * <p>
	 * This operations returns the contents of the Resource as an instance of
	 * File.
	 * </p>
	 * 
	 * @return
	 *         <p>
	 *         The file.
	 *         </p>
	 */
	public File getContents() {

		return file;
	}

	/**
	 * <p>
	 * This operation returns the URI to the Resource.
	 * </p>
	 * 
	 * @return
	 *         <p>
	 *         The path as a URL.
	 *         </p>
	 */
	public URI getPath() {
		if (this.file != null) {
			path = file.toURI();
		}
		return path;
	}

	/**
	 * <p>
	 * This operation sets the path to the Resource and is an alternative to
	 * setContents(). It will reset the File handle if it is different.
	 * </p>
	 * 
	 * @param path
	 */
	public void setPath(URI path) {

		// If null, return
		if (path == null) {
			return;
		}

		// Set the path and create a new file
		this.path = path;
		// If the URI differs from the current file, change it
		if (!this.file.toURI().equals(path)) {
			this.file = new File(path);
		}

	}

	/**
	 * <p>
	 * This operation associates a set of Entries with the resource that
	 * describe specific properties. The list of Entries is returned by
	 * reference and is not a deep copy, i.e. - changing one will change it on
	 * the resource.
	 * </p>
	 * 
	 * @return
	 *         <p>
	 *         The properties or null if there are no properties.
	 *         </p>
	 */
	public ArrayList<IEntry> getProperties() {

		return this.properties;
	}

	/**
	 * <p>
	 * This operation returns the set of Entries that describe specific
	 * properties of the resource. The properties can be set multiple times.
	 * </p>
	 * 
	 * @param props
	 *            <p>
	 *            The properties.
	 *            </p>
	 */
	public void setProperties(ArrayList<IEntry> props) {
		// If null return
		if (props == null) {
			return;
		}

		this.properties = props;

	}

	/**
	 * <p>
	 * This operation returns true if the ICEResource is an image and false if
	 * not based upon the isPicture attribute.
	 * </p>
	 * 
	 * @return
	 *         <p>
	 *         True if this is a picture, false otherwise.
	 *         </p>
	 */
	public boolean isPictureType() {
		return this.isPicture;
	}

	/**
	 * <p>
	 * An operation that sets the isPicture attribute on ICEResource.
	 * </p>
	 * 
	 * @param isPicture
	 *            <p>
	 *            Determines if ICEResource is a picture.
	 *            </p>
	 */
	@XmlTransient
	public void setPictureType(boolean isPicture) {
		this.isPicture = isPicture;

	}

	/**
	 * This operation performs a deep copy of the attributes of another
	 * ICEResource into the current ICEResource.
	 * 
	 * @param otherResource
	 *            The other ICEResource from which information should be copied.
	 */
	public void copy(JanuaryResource otherResource) {

		// if resource is null, return
		if (otherResource == null) {
			return;
		}

		// copy from super class
		super.copy(otherResource);

		// Copy current values
		// These files are not cloned
		this.file = otherResource.file;
		this.path = otherResource.path;

		// Iteratively clone the entries in properties
		// These items are cloned
		this.properties.clear();
		for (int i = 0; i < otherResource.properties.size(); i++) {
			this.properties
					.add((IEntry) otherResource.getProperties().get(i).clone());
		}

		// Copy picture
		this.isPicture = otherResource.isPicture;
	}

	/**
	 * This operation provides a deep copy of the JanuaryResource.
	 * 
	 * @return A clone of the JanuaryResource.
	 */
	@Override
	public Object clone() {
		// Create a new instance, copy contents, and return it
		JanuaryResource resource = null;
		resource = new JanuaryResource();
		resource.copy(this);

		return resource;
	}

	/**
	 * This operation is used to check equality between the JanuaryResource and
	 * another JanuaryResource. It returns true if the JanuaryResources are
	 * equal and false if they are not.
	 * 
	 * @param otherJanuaryResource
	 *            The other JanuaryResource to which this JanuaryResource should
	 *            be compared.
	 * @return True if the JanuaryResources are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object otherJanuaryResource) {

		// Check if they are same reference in memory
		if (this == otherJanuaryResource) {
			// If so, return true, saves time
			return true;
		}

		// Check that the object is not null, and that it is an instance of
		// JanuaryResource
		if (otherJanuaryResource == null
				|| !(otherJanuaryResource instanceof JanuaryResource)) {
			// If not, return false
			return false;
		}

		// Check that these objects have the same JanuaryObject data
		if (!super.equals(otherJanuaryResource)) {
			// If not return false
			return false;
		}

		// Object must be an JanuaryResource at this point
		JanuaryResource castedResource = (JanuaryResource) otherJanuaryResource;

		// Check that their attributes are the same
		return (file.equals(castedResource.file))
				&& (path.equals(castedResource.path)
						&& properties.equals(castedResource.properties)
						&& (isPicture == castedResource.isPicture));

	}

	/**
	 * This operation returns the hashcode value of the JanuaryObject.
	 * 
	 * @return The hashcode for the JanuaryResource.
	 */
	@Override
	public int hashCode() {

		// Local Declaration
		int hash = 11;

		// Compute hashcode from JanuaryResource data
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + (null == this.file ? 0 : this.file.hashCode());
		hash = 31 * hash + (null == this.path ? 0 : this.path.hashCode());
		// hash = 31 * hash
		// + (null == this.file.toString() ? 0 : this.file.hashCode());
		// hash = 31 * hash
		// + (null == this.path.toString() ? 0 : this.path.hashCode());
		hash = 31 * hash + this.properties.hashCode();
		hash = 31 * hash + (!this.isPicture ? 0 : 1);

		return hash;

	}

	/**
	 * <p>
	 * This operation sets the File which the Resource represents. The default
	 * values of the name, id and description for this class are the filename, 1
	 * and the absolute path, respectively.
	 * </p>
	 * 
	 * @param resourceFile
	 *            <p>
	 *            The file that the Resource should be created to represent.
	 *            </p>
	 * @throws IOException
	 * @throws NullPointerException
	 */
	@XmlTransient
	public void setContents(File resourceFile)
			throws IOException, NullPointerException {

		// Set the file reference and path
		try {
			file = resourceFile;
			path = resourceFile.toURI();
		} catch (NullPointerException e) {
			throw e;
		}

		return;

	}

}