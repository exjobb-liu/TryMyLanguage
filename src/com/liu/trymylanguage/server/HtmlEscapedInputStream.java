package com.liu.trymylanguage.server;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


public class HtmlEscapedInputStream extends FilterInputStream {
	final private int capacity = 50;
	private boolean previousWasASpace = false;
	private IntegerBlockingQueue queue;
	protected HtmlEscapedInputStream(InputStream in) {
		super(in);
		queue = new IntegerBlockingQueue(capacity);
	
	}
	
	
	
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		 if(off < 0 || len <= 0 || len > b.length - off) throw new IndexOutOfBoundsException();         
		    int i = 0;
		    while (i < len) {
		        int j = read();
		        if (j < 0) break; // Stop reading at EOF
		        b[off + i] = (byte) j;
		        i++;
		    }
		    if (i == 0) return -1; // If we get EOF with no data, return it to the caller
		    else return i;
		
	}

	@Override
	public int read() throws IOException{
		// TODO Auto-generated method stub
		Integer elem = queue.poll();
		if (elem != null) {
			return elem.intValue();
		} else {


			int c = super.read();
			try {
				switch(c) {
				case '<': 
					queue.put("&lt;");
					break;
				case '>': queue.put("&gt;"); break;
				case '&': queue.put("&amp;"); break;
				case '"': queue.put("&quot;"); break;
				case '\n': queue.put("<br />"); break;
				// We need Tab support here, because we print StackTraces as HTML
				case '\t': queue.put("&nbsp; &nbsp; &nbsp;"); break;
				case ' ' : 
					if (previousWasASpace) {
						queue.put("&nbsp;");
						previousWasASpace = false;
					} else {
						queue.put((int)' ');
						previousWasASpace = true;
					}
					break;
				default:
					previousWasASpace = false;
					if( c < 128 ) {
						queue.put(c);
					} else {
						queue.put("&#" + c + ";");
					}    
				
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				throw new IOException(ex);
			}
			return queue.poll().intValue();
		}
	}

}
