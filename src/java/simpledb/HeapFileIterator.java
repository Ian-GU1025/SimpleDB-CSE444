package simpledb;

import java.io.Serializable;
import java.util.*;

public class HeapFileIterator implements DbFileIterator {
	private final HeapFile heapFile;
	
	private final TransactionId transactionId;
	
	private int currentPageNo;
	
	private Iterator<Tuple> pageIterator;
	
	public HeapFileIterator(HeapFile hf, TransactionId tid){
		heapFile =hf;
		transactionId =tid;
	}
		
	/**
     * Opens the iterator
	 * @throws TransactionAbortedException 
     * @throws DbException when there are problems opening/accessing the database.
     */
    public void open() throws TransactionAbortedException, DbException{
    	this.currentPageNo=0;
    	this.pageIterator=getIterator(currentPageNo);
    }
    
    /** @return true if there are more tuples available. */
    public boolean hasNext()
        throws DbException, TransactionAbortedException{
    	if(pageIterator==null) {
    		return false;
    	}
    	while(!pageIterator.hasNext()){
	    	++currentPageNo;
	    	if(currentPageNo>=heapFile.numPages()){
	    		return false;
	    	}
	    	pageIterator=getIterator(currentPageNo);
    	}
    	return true;
    }
    /**
     * Gets the next tuple from the operator (typically implementing by reading
     * from a child operator or an access method).
     *
     * @return The next tuple in the iterator.
     * @throws NoSuchElementException if there are no more tuples
     */
    public Tuple next()
            throws DbException, TransactionAbortedException, NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return pageIterator.next();
        // 已经在hasNext这一步里完成可能需要的换页操作
    }
    
    /**
     * Resets the iterator to the start.
     * @throws DbException When rewind is unsupported.
     */
    public void rewind() throws DbException, TransactionAbortedException {
        currentPageNo = 0;
        pageIterator = getIterator(currentPageNo);
    }
    
    public void close() {
        pageIterator = null;
    }
    
	private Iterator<Tuple> getIterator(int pageNo) throws TransactionAbortedException, DbException{
		HeapPage page;
		// 因为一个table对应一个heapfile所以直接用heapfile的ID去对应tableid
		page=(HeapPage)Database.getBufferPool().getPage(
				transactionId, new HeapPageId(heapFile.getId(),pageNo), Permissions.READ_ONLY);
		return page.iterator();
	}
}
