package simpledb;

import java.io.Serializable;
import java.util.*;


/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * A help class to facilitate organizing the information of each field
     * */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        Type fieldType;	//  The type of the field
        
        String fieldName;	// The name of the field

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
        
        public boolean equals(Object o) {
        	if(o==null||o.getClass()!=this.getClass()){
        		return false;
        	}
        	TDItem other=(TDItem) o;
        	return this.fieldName.equals(other.fieldName) && (this.fieldType.equals(other.fieldType));
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    private TDItem[] items;					// �洢���tuple�Ĳ�ͬfields����Ϣ
    
    private int numfields;						// �洢���tuple�ĳ���(�ж��ٸ��ֶ�)
    
    public Iterator<TDItem> iterator() {
        return Arrays.asList(items).iterator();
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        this.numfields=typeAr.length;						// �洢�ֶε�����
        this.items=new TDItem[typeAr.length];			// �洢��ͬ�ֶε�����
        for(int i=0;i<typeAr.length;i++){
        	items[i].fieldName=fieldAr[i];
        	items[i].fieldType=typeAr[i];
        }
    }

    /**
     * Constructor. Create a new tuple description with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
    	 this.numfields=typeAr.length;					
         this.items=new TDItem[typeAr.length];	
         for(int i=0;i<typeAr.length;i++){
        	items[i]=new TDItem(typeAr[i],"field"+i);	// ʵ����
         }
    }
    
    public TupleDesc(TDItem[] tdItems) {
    	this.numfields=tdItems.length;
    	items=Arrays.copyOf(tdItems, tdItems.length);
    	/*��Java�У�ʹ��array1 = array2�﷨�������鸴�Ʋ����ᴴ��array1�ĸ�����
    	 * ���ǽ�array2�����ø�ֵ��array1��
    	 * ����ζ��array1��array2ָ��ͬһ���������
    	 * ����ʹ��copyof���Ա�֤����һ���µ����鸱��
    	 * */
        }
   

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        return numfields;
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
    	if (i >= items.length || i < 0) {
        	throw new NoSuchElementException("This tuple does not contain a field at index " + i);
        } else {
        	return items[i].fieldName;
        }
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        if(i>=items.length||i<0){
        	throw new NoSuchElementException("This tuple does not contain a field at index "+i);
        }else{
        	return items[i].fieldType;
        }
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
    	   int cnt=0;
    	   Iterator<TDItem> iterator=this.iterator();
    	   while(iterator.hasNext())
           {
           	String tempName=iterator.next().fieldName;
           	if(tempName.equals(name)){
           		return cnt;
           	}
           	cnt+=1;
           }
        throw new NoSuchElementException("no field with a matching name is found");
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
    	int size=0;
    	for(int i=0;i<numFields();i++)
    	{
    		size+=getFieldType(i).getLen();	// Type��getLen���ص���һ���ֶε��ֽڳ�
    	}
 	   	return size;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
    	// TupleDesc��ʵ����һ��TDItem
    	TDItem[] tdItem=new TDItem[td1.numFields()+td2.numFields()] ;
    	System.arraycopy(td1, 0, tdItem, 0, td1.numFields());
    	System.arraycopy(td2, 0, tdItem, td1.numFields(), td2.numFields());
    	/*System.arraycopy()����ֻ�Ǽ򵥵ظ�����������ݣ��������漰������Ĺ���
    	 * ���Բ���Ҫ�������е�ÿһ��Ԫ��(TDItem)���й��죬����TDItemҲûд���췽��*/
        return new TupleDesc(tdItem);
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        if (o.getClass()!=this.getClass()||o==null)return false;
        TupleDesc that=(TupleDesc) o;
        if(that.numFields()!=this.numFields())return false;
        return Arrays.equals(this.items, that.items);
    }

    public int hashCode() {
       return Arrays.hashCode(items);
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
    	StringBuilder sb=new StringBuilder();
        for(int i=0;i<this.numfields;i++)
        {
        	sb.append(items[i].fieldName);
        	sb.append('('+items[i].fieldType.toString()+"),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
