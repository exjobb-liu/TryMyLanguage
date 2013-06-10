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
	public CodeDTO(){}
	
    public CodeDTO(String fileName,
    		String suffix,
    		String compileCmd,
    		String runCmd,
    		String regex, 
    		boolean plot,
    		long timeout) {
		
		this.fileName = fileName;
		this.suffix = suffix;
		this.runCmd = replaceFnSuf(runCmd);
		this.plot = plot;
		this.timeout = timeout;
		this.compileCmd = replaceFnSuf(compileCmd);
		this.regex = replaceFnSuf(regex);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    
    
    private String regex;
    

	private String suffix;
    private String compileCmd;
    private String runCmd;
    private long timeout;
    private boolean	plot;
    public boolean isPlot() {
		return plot;
	}

	/**
     * Get the <code>Code</code> value.
     *
     * @return a <code>String</code> value
     */
    public  String getCode() {
	return code;
    }

    /**
     * Set the <code>Code</code> value.
     *
     * @param newCode The new Code value.
     */
    public  void setCode(final String newCode) {
	this.code = newCode;
    }

      /**
     * Get the <code>FileName</code> value.
     *
     * @return a <code>String</code> value
     */
    public  String getFileName() {
	return fileName;
    }

    /**
     * Set the <code>FileName</code> value.
     *
     * @param newFileName The new FileName value.
     */
   
    public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = replaceFnSuf(regex);
	}

	public String getSuffix() {
		return suffix;
	}

	


	public String getCompileCmd() {
		return compileCmd;
	}

	public void setCompileCmd(String compileCmd) {
		this.compileCmd =replaceFnSuf(compileCmd);
	}

	public String getRunCmd() {
		return runCmd;
	}

	


	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	

	public String replaceFnSuf(String in){
		return in.replaceAll("<filename>", fileName).replaceAll("<suffix>", suffix);
	}
	
	
}
