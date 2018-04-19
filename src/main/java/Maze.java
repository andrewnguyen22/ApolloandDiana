import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private Square start;
    private Square end;
    private Square[][] maze;
    private int width;
    private int height;

    Maze(String filePath) throws IOException {
        String s;
        String split[];
        String direction;
        String color;
        //Read File
        File inputfile = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(inputfile));
        //Grab first line for width and height
        String firstLine = br.readLine();
        String res[] = firstLine.split("\\s");
        width = Integer.parseInt(res[1]);
        height = Integer.parseInt(res[0]);
        //Create new maze using width and height
        maze = new Square[width][height];
        //For every row of the input file...
        for (int i = 0; i < height; ++i) {
            s = br.readLine();
            split = s.split("\\s");
            //For every column of the input file...
            for (int j = 0; j < width; ++j) {
                //If it is at the exit of the maze
                maze[j][i] = new Square();
                if (j == width - 1 && i == height - 1) {
                    maze[j][i].setPosition(new Position(j, i));
                    maze[j][i].setDirection(Direction.S);
                    continue;
                }
                color = split[j].split("-")[0];
                direction = split[j].split("-")[1];
                //Set Color
                if (color.toLowerCase().equals("b"))
                    maze[j][i].setColor(Color.BLUE);
                else
                    maze[j][i].setColor(Color.RED);
                //Set Direction
                String s1 = direction.toLowerCase();
                if ("s".equals(s1)) {
                    maze[j][i].setDirection(Direction.S);

                } else if ("n".equals(s1)) {
                    maze[j][i].setDirection(Direction.N);

                } else if ("w".equals(s1)) {
                    maze[j][i].setDirection(Direction.W);

                } else if ("e".equals(s1)) {
                    maze[j][i].setDirection(Direction.E);

                } else if ("se".equals(s1)) {
                    maze[j][i].setDirection(Direction.SE);

                } else if ("sw".equals(s1)) {
                    maze[j][i].setDirection(Direction.SW);

                } else if ("nw".equals(s1)) {
                    maze[j][i].setDirection(Direction.NW);

                } else if ("ne".equals(s1)) {
                    maze[j][i].setDirection(Direction.NE);

                }
                //Set !visited
                maze[j][i].setVisited(false);
                //Set Position in maze
                maze[j][i].setPosition(new Position(j, i));
            }
        }
        this.start = maze[0][0];
        this.end = maze[width - 1][height - 1];
    }

    private Square getSquareByDirection(Direction direction, Square origin) {
        Position p = new Position(origin.getPosition().getX(), origin.getPosition().getY());
        switch (direction) {
            case N:
                p.setY(p.getY() - 1);
                break;
            case S:
                p.setY(p.getY() + 1);
                break;
            case E:
                p.setX(p.getX() + 1);
                break;
            case W:
                p.setX(p.getX() - 1);
                break;
            case NW:
                p.setY(p.getY() - 1);
                p.setX(p.getX() - 1);
                break;
            case NE:
                p.setY(p.getY() - 1);
                p.setX(p.getX() + 1);
                break;
            case SE:
                p.setY(p.getY() + 1);
                p.setX(p.getX() + 1);
                break;
            case SW:
                p.setY(p.getY() + 1);
                p.setX(p.getX() - 1);
                break;
        }
        return (getSquareByPosition(p));
    }

    private Square getSquareByPosition(Position p) {
        if (p.getX() < width && p.getY() < height
                && p.getX() >= 0 && p.getY() >= 0)
            return maze[p.getX()][p.getY()];
        return null;
    }

    public List<Square> getPossibleMoves(Square s) {
        //Create a list of possible moves from current square s
        List<Square> possibleMoves = new ArrayList<Square>();
        //Square next = the next move to be inspected
        Square next = getSquareByDirection(s.getDirection(), s);
        //Traverse every possible move...
        while (next != null) {
            //If origin square and possible move square do not have the same color...
            if (next.getColor() != s.getColor() && !next.isVisited()) {
                //Set parent as origin
                next.setParent(s);
                //add to possible moves
                possibleMoves.add(next);
            }
            //Move on to the next square
            next = getSquareByDirection(s.getDirection(), next);
            if (next == end) {
                next.setParent(s);
                possibleMoves.add(next);
                break;
            }
        }
        return possibleMoves;
    }

    public Square getStart() {
        return start;
    }


    public Square getEnd() {
        return end;
    }
}
