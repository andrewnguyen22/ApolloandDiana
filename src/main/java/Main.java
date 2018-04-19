import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("USAGE ./program <path to file>");
            System.exit(1);
        }
        try {
            //Construct a new maze from the file
            Maze maze = new Maze(args[0]);
            //Create a new queue of squares for BFS
            Queue<Square> q = new LinkedList<Square>();
            //Square s is current square
            Square s;
            //Add start to queue
            q.add(maze.getStart());
            //Begin BFS
            while (!q.isEmpty()) {
                //Dequeue q to s
                s = q.poll();
                //If s is visited already, skip
                if (!s.isVisited()) {
                    //Visit s
                    s.setVisited(true);
                    //Add all posible moves to the queue
                    q.addAll(maze.getPossibleMoves(s));
                }
            }
            //Once finished with BFS print the path
            printPath(maze);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printPath(Maze m) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("/Users/andrew_nguyen/files/cs/java/algorithms_project3/project_files/output.txt",
                "UTF-8");
        Stack<String> results = new Stack<String>();
        if (m.getEnd().getParent() == null) {
            writer.print("NO FOUND SOLUTION TO THE MAZE");
            writer.close();
            System.exit(0);
        }
        Square temp = m.getEnd();
        do {
            int hops;
            if ("s".equals(temp.getParent().getDirection().name().toLowerCase()) || "n".equals(temp.getParent().getDirection().name().toLowerCase())) {
                hops = Math.abs(temp.getPosition().getY() - temp.getParent().getPosition().getY());
            } else {
                hops = Math.abs(temp.getPosition().getX() - temp.getParent().getPosition().getX());
            }
            temp = temp.getParent();
            results.push("" + hops + temp.getDirection().name());
        } while (temp.getParent() != null);
        String res = "";
        while (!results.isEmpty()) {
            res = res.concat(results.pop() + " ");
        }
        System.out.println(res);
        writer.println(res);
        writer.close();
    }
}
