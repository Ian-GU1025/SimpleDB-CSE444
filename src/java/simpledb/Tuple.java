package simpledb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Tuple maintains information about the contents of a tuple. Tuples have a
 * specified schema specified by a TupleDesc object and contain Field objects
 * with the data for each field.
 */
public class Tuple implements Serializable {

    private static final long serialVersionUID = 1L;
    private TupleDesc tupleDesc;
    private RecordId recordId;
    private Field[] fields;
    
    /**
     * Create a new tuple with the specified schema (type).
     * 
     * @param td
     *            the schema of this tuple. It must be a valid TupleDesc
     *            instance with at least one field.
     */
    /*  static��Ա�����౾������������κ��ض�ʵ����
		����ζ������ʵ������ͬһ��static�����򷽷������ǲ���ÿ��������е�*/
    public Tuple(TupleDesc td) {
        tupleDesc = td;
        recordId = null;
        fields=new Field[td.numFields()];
    }

    /**
     * @return The TupleDesc representing the schema of this tuple.
     */
    public TupleDesc getTupleDesc() {
        return tupleDesc;
    }

    /**
     * @return The RecordId representing the location of this tuple on disk. May
     *         be null.
     */
    public RecordId getRecordId() {
        return recordId;
    }

    /**
     * Set the RecordId information for this tuple.
     * 
     * @param rid
     *            the new RecordId for this tuple.
     */
    public void setRecordId(RecordId rid) {		// recordId��ʼ��
        recordId=rid;
    }

    /**
     * Change the value of the ith field of this tuple.
     * 
     * @param i
     *            index of the field to change. It must be a valid index.
     * @param f
     *            new value for the field.
     */
    public void setField(int i, Field f) {
        fields[i]=f;
    }

    /**
     * @return the value of the ith field, or null if it has not been set.
     * 
     * @param i
     *            field index to return. Must be a valid index.
     */
    public Field getField(int i) {
        return fields[i];
    }

    /**
     * Returns the contents of this Tuple as a string. Note that to pass the
     * system tests, the format needs to be as follows:
     * 
     * column1\tcolumn2\tcolumn3\t...\tcolumnN\n
     * 
     * where \t is any whitespace, except newline, and \n is a newline
     * String�����ǲ��ɱ�ġ�����ζ��һ��������һ��String���󣬾Ͳ��ܸ�������
     * ÿ��ʹ��+�����������ַ���ʱ��ʵ���϶��ᴴ��һ���µ�String����
     * ���֮�£�StringBuilder�ǿɱ�ġ�������StringBuilder����������ַ���ʱ�������ᴴ���µĶ���
     */
    public String toString() {
    	StringBuilder sb=new StringBuilder();
    	for(int i=0;i<tupleDesc.numFields()-1;i++)	// �������ڶ���
    	{
    		sb.append(fields[i].toString());
    		sb.append(" ");
    	}
    	sb.append(fields[tupleDesc.numFields()-1].toString());
    	sb.append('\n');
    	return sb.toString();
    }
    
    /**
     * @return
     *        An iterator which iterates over all the fields of this tuple
     * */
    public Iterator<Field> fields()
    {
    	return Arrays.asList(fields).iterator();
    }
}

