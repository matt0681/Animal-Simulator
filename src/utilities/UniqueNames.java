package utilities;

/**
 * UniqueNames provides a unique name for entities.
 * @author Matthew
 */
public class UniqueNames {

    private static final String[] grasses = {"Clay", "Silty Clay", "Sandy Clay", 
                            "Clay Loam", "Silty Clay Loam", "Sandy Clay Loam", 
                            "Loam", "Silt Loam", "Silt", "Sandy Loam", "Sand"};
    
    private static final String[] birds = {"Khea", "Turkey", "Chicken", "Quail", 
                            "Pigeon", "Lark", "Guineafowl", "Peafowl", "Pheasant"};
    
    
    // Based on the type of name, See Name class for different types, this
    // method generates a random name from the name lists above.
    public static String getGroundName(int nameType){
        int rand;
        String out = "";
        switch(nameType){
            case Names.GRASS:
                rand = RandomNumber.getRandomNumberInRange(0, grasses.length-1);
                out = grasses[rand];
                break;
            case Names.NULL:
                out = "NULL";
                break;
            case Names.BIRD:
                rand = RandomNumber.getRandomNumberInRange(0, birds.length-1);
                out = birds[rand];
                break;
        }
        return out;
    }
    
    
}
