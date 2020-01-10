package entities;


/**
 * The Component Class is a generic class that represents any data type.
 * It is used in the entity component system.
 * @author Matthew
 * @param <T> 
 */
public class Component<T> {
    
    private T data;

    /**
     * This method returns the data stored in this class.
     * @return data
     */
    public T getValue() {
        return data;
    }
    
    /**
     * This method sets the data stored in this class then returns this class.
     * @param v 
     * @return this (Component)
     */
    public Component<T> setvalue(T v){
        this.data = v;
        return this;
    }
}