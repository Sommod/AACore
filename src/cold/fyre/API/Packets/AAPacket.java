package cold.fyre.API.Packets;

/**
 * Class that is used to handle classes of NMS. This uses reflection
 * to access and use the packets. As such, the normal methods will not
 * appear. To use any of the methods found within the class, you need
 * to know what the method name is, including any capitalization, what
 * objects the method takes, and what type of objects the methods take.
 * Once all the information is given, you can grab the object from the
 * return method.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public interface AAPacket {
	
	/**
	 * Gets the name of the packet intended to use.
	 * @return Name of the Packet Class
	 */
	String getName();
	
	/**
	 * Gets the packet in the form of an object.
	 * @return Packet
	 */
	Object getPacket();
	
	/**
	 * Runs the method of the given method name. This will return the
	 * object of the method. If the method is a void method, then this
	 * a NULL object.
	 * @param methodName - name of method to run
	 * @param parameters - any parameters needed by the method
	 * @return Object of method, or null
	 */
	Object runMethod(String methodName, Object... parameters);
	
	/**
	 * Gets the value of the field.
	 * @param fieldName - name of field
	 * @return Object of value
	 */
	Object getFieldValue(String fieldName);
	
	/**
	 * Sets the field to the given value. If the
	 * field is not compatible, then this method
	 * will thrown an error.
	 * @param fieldName
	 * @param value
	 */
	void setFieldValue(String fieldName, Object value);
}
