import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by glyn on 30/03/2016.
 */
public class World {
    Room[][] rooms;
    /**
     * The number of spaces in this 20 by 20 grid
     */
    public final int X_SIZE ;
    public final int Y_SIZE ;

    // constructor - pass through size of world
    public World(int x, int y) {
        X_SIZE = x ;
        Y_SIZE = y;
        rooms = new Room[X_SIZE][Y_SIZE];

        for (int i = 0; i < X_SIZE; i++)
            for (int j = 0; j < Y_SIZE; j++)
                rooms[i][j] = new Room();
    }

    // method to create new bonk and add it to world
    void addBonk(Position position) {
        Bonk newBonk = new Bonk();
        rooms[position.x][position.y].monsters.add(newBonk);
    }

    // method to create new zap and add it to world
    void addZap(Position position) {
        Zap newZap = new Zap();
        rooms[position.x][position.y].monsters.add(newZap);
    }

    // this method goes through every room to find a monster with a particular name if not found returns null
    Position getLocation(String identifier) {
        for (int i = 0; i < X_SIZE; i++)
            for (int j = 0; j < Y_SIZE; j++)
                for (Monster monster : rooms[i][j].monsters)
                    if (monster.getName().equals(identifier))
                        return new Position(i, j);
        return null;
    }

    // this method moves the monster
    void moveMonster(String identifier, Position destinaton) {
        Position source = getLocation(identifier);
        for (Monster monster : rooms[source.getX()][source.getY()].monsters)
            if (monster.getName().equals(identifier)) {
                rooms[source.getX()][source.getY()].monsters.remove(rooms[source.getX()][source.getY()].monsters.indexOf(monster));
                rooms[destinaton.getX()][destinaton.getY()].monsters.add(monster);
                break;
            }
    }

    // this method determines all possible rooms that can be moved to from the room at position. it returns the room
    // as an arraylist
    ArrayList<Position> possibleMoves(Position position) {
        ArrayList<Position> moves = new ArrayList<>();

  /*  initially i thought of doing this code based on which room edge, corner etc
  if (position.getX()==0&&position.getY()==0) {
            moves.add(new Position(0, 1));
            moves.add(new Position(1, 1));
            moves.add(new Position(1, 0));
        } else if (position.getX()==0&&position.getY()==Y_SIZE-1) {
            moves.add(new Position(0, Y_SIZE - 2));
            moves.add(new Position(1, Y_SIZE - 2));
            moves.add(new Position(1, Y_SIZE - 1));
        } else if (position.getX()==X_SIZE-1&&position.getY()==0) {
            moves.add(new Position(X_SIZE-2,0));
            moves.add(new Position(X_SIZE-2,1));
            moves.add(new Position(X_SIZE-1,1));
        } else if (position.getX()==X_SIZE-1&&position.getY()==Y_SIZE-1) {
            moves.add(new Position(X_SIZE-1,Y_SIZE-2));
            moves.add(new Position(X_SIZE-2,Y_SIZE-2));
            moves.add(new Position(X_SIZE-2,Y_SIZE-1));
        } else if (position.getY()==0) {
            moves.add(new Position(position.getX()-1,0));
            moves.add(new Position(position.getX()-1,1));
            moves.add(new Position(position.getX(),1));
            moves.add(new Position(position.getX()+1,1));
            moves.add(new Position(position.getX()+1,0));
            . . . . . . .

            but came up with an easier way to generate all rooms and then just check if the room is still in the world
 */
        for (int i=-1; i<2; i++)
            for (int j=-1; j<2; j++)
                if (!(i==0&&j==0)) {
                    int newX = position.getX() + i;
                    int newY = position.getY() + j;
                    // if the room is still in the world i.e. the coords are still valid
                    if (newX >= 0 && newX < X_SIZE && newY >= 0 && newY < Y_SIZE)
                        moves.add(new Position(newX, newY));
                }
        return moves;

    }

    void resetAllFlags() {
        // reset the flags used to ensure we only move one or act once per turn
        for (int i = 0; i < X_SIZE; i++)
            for (int j = 0; j < Y_SIZE; j++)
                for (Monster monster : rooms[i][j].monsters) {
                    monster.acted = false;
                    monster.moved = false;
                }

    }



    @Override
    public String toString() {
        String output = "";

        for (int i = 0; i < X_SIZE; i++) {
            for (int j = 0; j < Y_SIZE; j++) {
                output+="Room[" + i + "," + j + "]";
                for (Monster monster : rooms[i][j].monsters) {
                    output+=" "+monster.getName();
                    if (monster.getName().charAt(0)=='B') {
                        if (((Bonk) monster).gender == Gender.MALE)
                            output+="M";
                        else
                            output+="F";
                        if (((Bonk) monster).isAlive)
                            output+="A";
                        else
                            output+="D";
                    }
                }
                output+="\n";
            }
        }
        return output;
    }
}



