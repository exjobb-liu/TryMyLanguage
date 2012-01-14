package com.liu.trymylanguage.shared;

/**
 * Describe class CodeDTO here.
 *
 *
 * Created: Tue Nov 29 11:15:33 2011
 *
 * @author <a href="mailto:amir@amir-laptop">Amir Hossein Fouladi</a>
 * @version 1.0
 */
public class CodeDTO implements java.io.Serializable {
  
    /**
     * Describe fileName here.
     */
    private String fileName;

    /**
     * Describe code here.
     */
    private String code;

    /**
     * Describe type here.
     */
    private int typeId;

    /**
     * Get the <code>Type</code> value.
     *
     * @return a <code>String</code> value
     */
    public final int getTypeId() {
	return typeId;
    }

    /**
     * Set the <code>Type</code> value.
     *
     * @param newType The new Type value.
     */
    public final void setTypeId(final int newTypeId) {
	this.typeId = newTypeId;
    }
    /**
     * Get the <code>Code</code> value.
     *
     * @return a <code>String</code> value
     */
    public final String getCode() {
	return code;
    }

    /**
     * Set the <code>Code</code> value.
     *
     * @param newCode The new Code value.
     */
    public final void setCode(final String newCode) {
	this.code = newCode;
    }

      /**
     * Get the <code>FileName</code> value.
     *
     * @return a <code>String</code> value
     */
    public final String getFileName() {
	return fileName;
    }

    /**
     * Set the <code>FileName</code> value.
     *
     * @param newFileName The new FileName value.
     */
    public final void setFileName(final String newFileName) {
	this.fileName = newFileName;
    }
}
