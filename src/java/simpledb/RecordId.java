package simpledb;

import java.io.Serializable;

/**
 * A RecordId is a reference to a specific tuple on a specific page of a
 * specific table.	����Ψһtuple
 */
public class RecordId implements Serializable {

    private static final long serialVersionUID = 1L;
    private PageId pageId;
    private int tupleNo;
    /**
     * Creates a new RecordId referring to the specified PageId and tuple
     * number.
     * 
     * @param pid
     *            the pageid of the page on which the tuple resides
     * @param tupleno
     *            the tuple number within the page.
     */
   
    public RecordId(PageId pid, int tupleno) {
        this.pageId=pid;
        this.tupleNo=tupleno;
    }

    /**
     * @return the tuple number this RecordId references.
     */
    public int tupleno() {
       return this.tupleNo;
    }

    /**
     * @return the page id this RecordId references.
     */
    public PageId getPageId() {
       return this.pageId;
    }

    /**
     * Two RecordId objects are considered equal if they represent the same
     * tuple.
     * 
     * @return True if this and o represent the same tuple
     */
    @Override
    public boolean equals(Object o) {
        if(o==null||o.getClass()!=this.getClass()){
        	return false;
        }
        RecordId that=(RecordId) o;
        return ((this.pageId.equals(that.pageId)) && (this.tupleNo==that.tupleNo));
    }

    /**
     * You should implement the hashCode() so that two equal RecordId instances
     * (with respect to equals()) have the same hashCode().
     * 
     * @return An int that is the same for equal RecordId objects.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result =17;
        result = prime * result + this.tupleNo;
        result = prime * result + this.pageId.hashCode();
        return result;
    }
}
