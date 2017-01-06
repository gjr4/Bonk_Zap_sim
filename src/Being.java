/**
 * Created by glyn on 30/03/2016.
 */

/**
 * Represents an inhabitant of GridWorld
 * @author cwl
 * @version 1.0
 */
public interface Being {
    /**
     * Every inhabitant on GridWorld must have a name given to them
    * at birth or creation. It is fixed, but can be discovered via this method
    * @return The name
    */
    public String getName();
/**
 * When called the Being does its stuff, e.g. move. So this represents behaviour.
 * What this is will vary between different kinds of Being
 * @throws CannotActException Thrown if the state of the Being prevents it
 * from acting, e.g. it is dead
 */
    public void act() throws CannotActException;
    /**
     * Returns the current location of the Being (which Room it's in)
     * @return Returns a Position that encapsulates its coordinates in Grid World
     */
    public Position getLocation();
    /**
     * Allows the relocation of the Being to another Room in Grid World
     * @param location
     */
    public void setLocation( Position location);


}
