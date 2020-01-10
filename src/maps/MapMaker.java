package maps;

import entities.Component;
import entities.Entity;
import states.BirdStates;
import states.GrassStates;
import graphics.Sprites;
import java.util.ArrayList;
import java.util.UUID;
import utilities.Names;
import utilities.RandomNumber;
import utilities.UniqueNames;

/**
 * MapMaker creates a Map Class. Mainly used for testing, to be decided what
 * will be made of it.
 *
 * @author Matthew
 */
public class MapMaker {

    private static final double WEIGHTED_MAX = 10.0;

    public static Map createTestMap(int size) {
        double[] weights = new double[size];
        Entity[] groundLayer = new Entity[size];
        ArrayList<ArrayList<Entity>> entityLayer = new ArrayList<ArrayList<Entity>>(size);

        ArrayList<Entity> subList;
        for (int i = 0; i < size; i++) {
            subList = new ArrayList<Entity>();
            entityLayer.add(subList);
        }
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 0;
        }

        for (int i = 0; i < groundLayer.length; i++) {

            int rand = RandomNumber.getRandomNumberInRange(1, 3);

            Entity ground = new Entity()
                .addComponent("Name", new Component().setvalue(UniqueNames.getGroundName(Names.GRASS)))
                .addComponent("Sprite Text", new Component().setvalue("."))
                .addComponent("Sprite", new Component().setvalue(RandomNumber.getRandomNumberInRange(1, 6)))
                .addComponent("Ground State", new Component().setvalue(true))
                .addComponent("Weight", new Component().setvalue(10.0))
                .addComponent("UUID", new Component().setvalue(UUID.randomUUID()));
            groundLayer[i] = ground;
        }

        int birdPos = 39;
        Entity bird = new Entity()
            .addComponent("Name", new Component().setvalue(UniqueNames.getGroundName(Names.BIRD)))
            .addComponent("Sprite Text", new Component().setvalue("b"))
            .addComponent("Sprite", new Component().setvalue(Sprites.BIRD))
            .addComponent("Ground State", new Component().setvalue(false))
            .addComponent("Weight", new Component().setvalue(6.0))
            .addComponent("UUID", new Component().setvalue(UUID.randomUUID()))
            .addComponent("Hunger", new Component().setvalue(55))
            .addComponent("Dead", new Component().setvalue(false));
        bird.addComponent("FSM", new Component().setvalue(new BirdStates(bird)));
        entityLayer.get(birdPos).add(bird);
        weights[birdPos] += (double) bird.getComponent("Weight").getValue();

        
        // This is how to define an entity
        int grassPos = 5;
        Entity grass = new Entity()
            .addComponent("Name", new Component().setvalue("TallGrass"))
            .addComponent("Sprite Text", new Component().setvalue(";"))
            .addComponent("Sprite", new Component().setvalue(Sprites.GRASS))
            .addComponent("Ground State", new Component().setvalue(false))
            .addComponent("Weight", new Component().setvalue(1.0))
            .addComponent("UUID", new Component().setvalue(UUID.randomUUID()))
            .addComponent("Seeds", new Component().setvalue(400))
            .addComponent("Dead", new Component().setvalue(false));
        grass.addComponent("FSM", new Component().setvalue(new GrassStates(grass)));
        entityLayer.get(grassPos).add(grass);
        weights[grassPos] += (double) grass.getComponent("Weight").getValue();
        

        Map map = new Map(weights, groundLayer, entityLayer);
        return map;
    }
}
