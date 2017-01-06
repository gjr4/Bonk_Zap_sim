import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by glyn on 30/03/2016.
 */

// Main routine
public class GameEngine {
    private static int noZaps=0;
    private static int noBonks=0;
    private static int turns=0;
    private static int worldXvalue=0;
    private static int worldYvalue=0;
    // I created world as a class variable. there is only one world. easier to access
    static World world;
    public static void main(String[] args) throws InterruptedException {
        menuChoices();
        System.out.println("Grid World has begun");
        world = new World(worldXvalue,worldYvalue);

        for (int i = 0; i < noBonks; i++) {
            // random room for bonk
            int newX = (int) (Math.random() * world.X_SIZE);
            int newY = (int) (Math.random() * world.Y_SIZE);
            Position newpos = new Position(newX, newY);
            System.out.println("Adding bonk to room["+newX+"]["+newY+"]");
            world.addBonk(newpos);
        }

        for (int i = 0; i < noZaps; i++) {
            // random room for zap
            int newX = (int) (Math.random() * world.X_SIZE);
            int newY = (int) (Math.random() * world.Y_SIZE);
            Position newpos = new Position(newX, newY);
            System.out.println("Adding zap to room["+newX+"]["+newY+"]");
            world.addZap(newpos);
        }

        System.out.println(world.toString());
        // main cycle loop
        for (int turn = 1 ; turn <= turns; turn++) {
            System.out.println("Turn "+turn);
            // clear flags for turn
            world.resetAllFlags();
            for (int i = 0; i < world.X_SIZE; i++)
                for (int j = 0 ; j < world.Y_SIZE; j++) {
                    // make copy of monster list to avoid ConcurrentModification Exception
                    ArrayList<Monster> monsterCopy = new ArrayList<>();
                    monsterCopy.addAll(world.rooms[i][j].monsters);
                    for (Monster monster : monsterCopy)
                        // First act on Zaps
                        if (monster.getName().charAt(0)=='Z')
                            monster.act();
                    // Reset the monster copy
                    monsterCopy.clear();
                    monsterCopy.addAll(world.rooms[i][j].monsters);
                    // now act on Bonks
                    for (Monster monster : monsterCopy)
                        if (monster.getName().charAt(0)=='B')
                            monster.act();
                }
            System.out.println(world.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




    }

    public static void menuChoices() {
        Scanner sc = new Scanner(System.in); ;;
        System.out.println("Enter x value for world: ");
        worldXvalue = sc.nextInt();
        System.out.println("Enter y value for world: ");
        worldYvalue = sc.nextInt();
        System.out.println("Enter number of bonks: ");
        noBonks = sc.nextInt();
        System.out.println("Enter number of zaps: ");
        noZaps = sc.nextInt();
        System.out.println("Enter number of turns: ");
        turns = sc.nextInt();

    }


}

