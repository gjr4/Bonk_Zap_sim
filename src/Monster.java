import java.util.ArrayList;

/**
 * Created by glyn on 30/03/2016.
 */
public class Monster implements Being {

    // Counter used to create new monster id's
    static int bonkId = 0;
    static int zapId = 0;
    // name of the bonk
    String identifier;
    // flags to not move a monster twice during a turn and to only act once on a turn
    boolean moved = false;
    boolean acted = false;

    public String getName() {
        return identifier;
    }
    // this method effectively moves a bonk but it uses a month in the world that has easier access to world
    public void setLocation(Position position) {
        GameEngine.world.moveMonster(getName(),position);

    }

    // get the location based on the monstore name
    public Position getLocation() {
        return GameEngine.world.getLocation(identifier);
    }

    // method that does the actual activity
    public void act() {
        // for a zap kill all bonks
        if (getName().charAt(0)=='Z'&&!acted) {
            for (Monster monster : GameEngine.world.rooms[getLocation().getX()][getLocation().getY()].monsters) {
                if (monster.getName().charAt(0)=='B')
                    // this is how we kill the bonk. set the flag
                    if (((Bonk)monster).isAlive) {
                        System.out.println("Monster "+monster.getName()+" has been killed");
                        ((Bonk)monster).isAlive = false;
                    }
            }
            acted = true;
        }
        // for a bonk it reproduces
        if (getName().charAt(0)=='B'&&((Bonk)this).isAlive&&!acted) {
            Gender gender = ((Bonk)this).gender;
            int offspring=0;
            // go through all the monsters
            for (Monster monster : GameEngine.world.rooms[getLocation().getX()][getLocation().getY()].monsters)
                // if a bonk and alive (dont reproduce with a dead bonk!)
                if ((monster.getName().charAt(0)=='B')&&((Bonk)monster).isAlive) {
                    Gender otherGender = ((Bonk)monster).gender;
                    // if a female we reproduce with all males. this ensure that there is the right number
                    if ((gender==Gender.FEMALE&&otherGender==Gender.MALE))
                        // we just keep a count of number of offspring
                        offspring++;
                }
            // in the end create a new bonk offspring times
            for (int i=1;i<=offspring;i++) {
                System.out.println("Baby bonk in room (" + getLocation().getX() + "," + getLocation().getY() + ")");
                GameEngine.world.addBonk(getLocation());
            }
            acted = true;
        }

        // Now we move the monster it most be a zap, or live bonk and must have moved already
        if ((getName().charAt(0)=='Z'||(getName().charAt(0)=='B'&&((Bonk)this).isAlive))&&!moved) {
            ArrayList<Position> moves = GameEngine.world.possibleMoves(getLocation());
            Position newPosition = moves.get((int) (moves.size() * Math.random()));
            System.out.println("Monster " + getName() + " moving from (" + getLocation().getX() +
                    "," + getLocation().getY() + ") to (" + newPosition.getX() + "," + newPosition.getY() + ")");
            setLocation(newPosition);
            moved = true;
        }
    }

}

// sub class for bonk from monster
class Bonk extends Monster {
    // bonk can be killed and has a gender
    boolean isAlive;
    Gender gender;
    public Bonk() {
        isAlive = true;
        identifier = "B";
        int numidentifier = bonkId++;
        identifier += Integer.toString(numidentifier);
        // we randomly make the bonk male or female 50/50 chance
        if (Math.random() > 0.5)
            gender = Gender.MALE;
        else
            gender = Gender.FEMALE;
    }
}

// subclass for xap from monster
class Zap extends Monster {
    public Zap() {
        identifier = "Z";
        int numidentifer = zapId++;
        identifier += Integer.toString(numidentifer);
    }
}

