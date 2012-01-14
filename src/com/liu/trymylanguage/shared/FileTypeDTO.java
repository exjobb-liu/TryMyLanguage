package com.liu.trymylanguage.shared;

public class FileTypeDTO implements java.io.Serializable{
	private String id;
	private String name;
	/**
	 * Constructs a new instance.
	 */
	public FileTypeDTO()
	{
	}
	/**
	 * Constructs a new instance.
	 *
	 * @param id The id for this instance.
	 * @param name The name for this instance.
	 */
	public FileTypeDTO(String id, String name)
	{
		this.id = id;
		this.name = name;
	}
	/**
	 * Gets the id for this instance.
	 *
	 * @return The id.
	 */

	public String getId()
	{
		return this.id;
	}
	/**
	 * Sets the id for this instance.
	 *
	 * @param id The id.
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * Gets the name for this instance.
	 *
	 * @return The name.
	 */
	public String getName()
	{
		return this.name;
	}
	/**
	 * Sets the name for this instance.
	 *
	 * @param name The name.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}

