package com.liu.trymylanguage.shared;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;

public class LangParamDTO {
	private JSONString name;
	private JSONArray keywords;
	private JSONString operators;
	private JSONString commentSingle;
	private JSONString commentMStart;
	private JSONString commentMEnd;
	private JSONString escapeChar;
	private JSONArray stringChar;
	/**
	 * Gets the name for this instance.
	 *
	 * @return The name.
	 */
	public JSONString getName()
	{
		return this.name;
	}
	/**
	 * Sets the name for this instance.
	 *
	 * @param name The name.
	 */
	public void setName(JSONString name)
	{
		this.name = name;
	}
	/**
	 * Gets the keywords for this instance.
	 *
	 * @return The keywords.
	 */
	public JSONArray getKeywords()
	{
		return this.keywords;
	}
	/**
	 * Sets the keywords for this instance.
	 *
	 * @param keywords The keywords.
	 */
	public void setKeywords(JSONArray keywords)
	{
		this.keywords = keywords;
	}
	/**
	 * Gets the operators for this instance.
	 *
	 * @return The operators.
	 */
	public JSONString getOperators()
	{
		return this.operators;
	}
	/**
	 * Sets the operators for this instance.
	 *
	 * @param operators The operators.
	 */
	public void setOperators(JSONString operators)
	{
		this.operators = operators;
	}
	/**
	 * Gets the commentSingle for this instance.
	 *
	 * @return The commentSingle.
	 */
	public JSONString getCommentSingle()
	{
		return this.commentSingle;
	}
	/**
	 * Sets the commentSingle for this instance.
	 *
	 * @param commentSingle The commentSingle.
	 */
	public void setCommentSingle(JSONString commentSingle)
	{
		this.commentSingle = commentSingle;
	}
	/**
	 * Gets the commentMStart for this instance.
	 *
	 * @return The commentMStart.
	 */
	public JSONString getCommentMStart()
	{
		return this.commentMStart;
	}
	/**
	 * Sets the commentMStart for this instance.
	 *
	 * @param commentMStart The commentMStart.
	 */
	public void setCommentMStart(JSONString commentMStart)
	{
		this.commentMStart = commentMStart;
	}
	/**
	 * Gets the commentMEnd for this instance.
	 *
	 * @return The commentMEnd.
	 */
	public JSONString getCommentMEnd()
	{
		return this.commentMEnd;
	}
	/**
	 * Sets the commentMEnd for this instance.
	 *
	 * @param commentMEnd The commentMEnd.
	 */
	public void setCommentMEnd(JSONString commentMEnd)
	{
		this.commentMEnd = commentMEnd;
	}
	/**
	 * Gets the escapeChar for this instance.
	 *
	 * @return The escapeChar.
	 */
	public JSONString getEscapeChar()
	{
		return this.escapeChar;
	}
	/**
	 * Sets the escapeChar for this instance.
	 *
	 * @param escapeChar The escapeChar.
	 */
	public void setEscapeChar(JSONString escapeChar)
	{
		this.escapeChar = escapeChar;
	}
	/**
	 * Gets the stringChar for this instance.
	 *
	 * @return The stringChar.
	 */
	public JSONArray getStringChar()
	{
		return this.stringChar;
	}
	/**
	 * Sets the stringChar for this instance.
	 *
	 * @param stringChar The stringChar.
	 */
	public void setStringChar(JSONArray stringChar)
	{
		this.stringChar = stringChar;
	}

}
