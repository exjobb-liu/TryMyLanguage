package com.liu.trymylanguage.shared;

import java.io.Serializable;


public class LangParamDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String keywords;
	private String operators;
	private String commentSingle;
	private String commentMStart;
	private String commentMEnd;
	private String escapeChar;
	private String stringChar;
	private String	compileCmd;
	private String feedbackRegex;
	private String runCmd; 
	private long timeout; 
	private String suffix;
	private String plot;
	private boolean isSandboxed;
	
	
	public boolean isSandboxed() {
		return isSandboxed;
	}
	public void setSandboxed(boolean isSandboxed) {
		this.isSandboxed = isSandboxed;
	}
	public String getSampleProgram() {
		return sampleProgram;
	}
	public void setSampleProgram(String sampleProgram) {
		this.sampleProgram = sampleProgram;
	}
	private String sampleProgram;

	
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
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
	/**
	 * Gets the keywords for this instance.
	 *
	 * @return The keywords.
	 */
	public String getKeywords()
	{
		return this.keywords;
	}
	/**
	 * Sets the keywords for this instance.
	 *
	 * @param keywords The keywords.
	 */
	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}
	/**
	 * Gets the operators for this instance.
	 *
	 * @return The operators.
	 */
	public String getOperators()
	{
		return this.operators;
	}
	/**
	 * Sets the operators for this instance.
	 *
	 * @param operators The operators.
	 */
	public void setOperators(String operators)
	{
		this.operators = operators;
	}
	/**
	 * Gets the commentSingle for this instance.
	 *
	 * @return The commentSingle.
	 */
	public String getCommentSingle()
	{
		return this.commentSingle;
	}
	/**
	 * Sets the commentSingle for this instance.
	 *
	 * @param commentSingle The commentSingle.
	 */
	public void setCommentSingle(String commentSingle)
	{
		this.commentSingle = commentSingle;
	}
	/**
	 * Gets the commentMStart for this instance.
	 *
	 * @return The commentMStart.
	 */
	public String getCommentMStart()
	{
		return this.commentMStart;
	}
	/**
	 * Sets the commentMStart for this instance.
	 *
	 * @param commentMStart The commentMStart.
	 */
	public void setCommentMStart(String commentMStart)
	{
		this.commentMStart = commentMStart;
	}
	/**
	 * Gets the commentMEnd for this instance.
	 *
	 * @return The commentMEnd.
	 */
	public String getCommentMEnd()
	{
		return this.commentMEnd;
	}
	/**
	 * Sets the commentMEnd for this instance.
	 *
	 * @param commentMEnd The commentMEnd.
	 */
	public void setCommentMEnd(String commentMEnd)
	{
		this.commentMEnd = commentMEnd;
	}
	/**
	 * Gets the escapeChar for this instance.
	 *
	 * @return The escapeChar.
	 */
	public String getEscapeChar()
	{
		return this.escapeChar;
	}
	/**
	 * Sets the escapeChar for this instance.
	 *
	 * @param escapeChar The escapeChar.
	 */
	public void setEscapeChar(String escapeChar)
	{
		this.escapeChar = escapeChar;
	}
	/**
	 * Gets the stringChar for this instance.
	 *
	 * @return The stringChar.
	 */
	public String getStringChar()
	{
		return this.stringChar;
	}
	/**
	 * Sets the stringChar for this instance.
	 *
	 * @param stringChar The stringChar.
	 */
	public void setStringChar(String stringChar)
	{
		this.stringChar = stringChar;
	}
	/**
	 * Gets the compileCmd for this instance.
	 *
	 * @return The compileCmd.
	 */
	public String getCompileCmd()
	{
		return this.compileCmd;
	}
	/**
	 * Sets the compileCmd for this instance.
	 *
	 * @param compileCmd The compileCmd.
	 */
	public void setCompileCmd(String compileCmd)
	{
		this.compileCmd = compileCmd;
	}
	/**
	 * Gets the feedbackRegex for this instance.
	 *
	 * @return The feedbackRegex.
	 */
	public String getFeedbackRegex()
	{
		return this.feedbackRegex;
	}
	/**
	 * Sets the feedbackRegex for this instance.
	 *
	 * @param feedbackRegex The feedbackRegex.
	 */
	public void setFeedbackRegex(String feedbackRegex)
	{
		this.feedbackRegex = feedbackRegex;
	}
	/**
	 * Gets the runCmd for this instance.
	 *
	 * @return The runCmd.
	 */
	public String getRunCmd()
	{
		return this.runCmd;
	}
	/**
	 * Sets the runCmd for this instance.
	 *
	 * @param runCmd The runCmd.
	 */
	public void setRunCmd(String runCmd)
	{
		this.runCmd = runCmd;
	}
	/**
	 * Gets the timeout for this instance.
	 *
	 * @return The timeout.
	 */
	public long getTimeout()
	{
		return this.timeout;
	}
	/**
	 * Sets the timeout for this instance.
	 *
	 * @param timeout The timeout.
	 */
	public void setTimeout(long timeout)
	{
		this.timeout = timeout;
	}

}
