package com.liu.trymylanguage.shared;





public final class RegexPos {
	
	private int start;
	private int end;
	public RegexPos(int start,int end){
		this.start = start;
		this.end = end;
		
	}
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof RegexPos))
			return false;
		RegexPos regexPos = (RegexPos)obj;
		return start==regexPos.start && end==regexPos.end; 
	}	
	@Override
	public int hashCode(){
		return start+end;
	
	}
}

