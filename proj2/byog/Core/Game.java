package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Game {
    private static final int MAXCOUNT = 1000;
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static long seed;
    private static Random random;
    private static final int MAXNUMOFROOM = 20;
    private List<Room> rooms = new ArrayList<>(MAXNUMOFROOM);
    private static final int UPPERHEIGHTOFROOM = 10;
    private static final int UPPERWIDTHOFROOM = 10;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */

    public void playWithKeyboard() {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        //TERenderer ter = new TERenderer();



        // draws the world to the screen

    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        //  Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        seed = inputToSeed(input);
        random = new Random(seed);
        //TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];

        //TERenderer ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall
        generateWord(world);
        Collections.sort(rooms);

        generateHallway(world);
        // draws the world to the screen
        //ter.renderFrame(world);

        return world;
    }

    private class Point {
        int xPoint;
        int yPoint;

        public Point(int xPoint, int yPoint) {
            this.xPoint = xPoint;
            this.yPoint = yPoint;
        }
    }

    private void generateHallway(TETile[][] world) {
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room leftRoom = rooms.get(i);
            Room rightRoom = rooms.get(i + 1);
            List<Point> points = new ArrayList<>();
            //房间，扩容一圈
            if (leftRoom.getxLeftBottomPoint()
                    + leftRoom.getWidth() < rightRoom.getxLeftBottomPoint() - 1) {
                int x1 = leftRoom.getxLeftBottomPoint() + leftRoom.getWidth() - 1;
                int y1 = random.nextInt(leftRoom.getHeight() - 2)
                        + leftRoom.getyLeftBottomPoint() + 1;
                int x2 = rightRoom.getxLeftBottomPoint();
                int y2 = random.nextInt(rightRoom.getHeight() - 2)
                        + rightRoom.getyLeftBottomPoint() + 1;

                int xSlide = x1 + 1 + random.nextInt(x2 - x1 - 1);
                for (int j = x1; j <= xSlide; j++) {
                    points.add(new Point(j, y1));
                    world[j][y1] = Tileset.FLOOR;
                }
                for (int j = xSlide; j <= x2; j++) {
                    points.add(new Point(j, y2));
                    world[j][y2] = Tileset.FLOOR;
                }
                for (int j = y1 + 1; j <= y2 - 1; j++) {
                    points.add(new Point(xSlide, j));
                    world[xSlide][j] = Tileset.FLOOR;
                }
                for (int j = y2 + 1; j <= y1 - 1; j++) {
                    points.add(new Point(xSlide, j));
                    world[xSlide][j] = Tileset.FLOOR;
                }
            } else {
                if (leftRoom.getyLeftBottomPoint() + leftRoom.getHeight()
                        >= rightRoom.getyLeftBottomPoint() - 1) {
                    Room temp = rightRoom;
                    rightRoom = leftRoom;
                    leftRoom = temp;
                }
                int y1 = leftRoom.getyLeftBottomPoint() + leftRoom.getHeight() - 1;
                int x1 = random.nextInt(leftRoom.getWidth() - 2)
                        + leftRoom.getxLeftBottomPoint() + 1;
                int y2 = rightRoom.getyLeftBottomPoint();
                int x2 = random.nextInt(rightRoom.getWidth() - 2)
                        + rightRoom.getxLeftBottomPoint() + 1;

                int ySlide = y1 + 1 + random.nextInt(y2 - y1 - 1);
                for (int j = y1; j <= ySlide; j++) {
                    points.add(new Point(x1, j));
                    world[x1][j] = Tileset.FLOOR;
                }
                for (int j = ySlide; j <= y2; j++) {
                    points.add(new Point(x2, j));
                    world[x2][j] = Tileset.FLOOR;
                }
                for (int j = x1 + 1; j <= x2 - 1; j++) {
                    points.add(new Point(j, ySlide));
                    world[j][ySlide] = Tileset.FLOOR;
                }
                for (int j = x2 + 1; j <= x1 - 1; j++) {
                    points.add(new Point(j, ySlide));
                    world[j][ySlide] = Tileset.FLOOR;
                }
            }
            int[] difx = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dify = {-1, 0, 1, 1, -1, -1, 0, 1};
            for (Point p : points) {
                for (int k = 0; k < 8; k++) {
                    int newX = p.xPoint + difx[k];
                    int newY = p.yPoint + dify[k];
                    if (newX >= 0 && newX < WIDTH && newY >= 0
                            && newY < HEIGHT && world[newX][newY] == Tileset.NOTHING) {
                        world[newX][newY] = Tileset.WALL;
                    }
                }
            }
        }
    }

    private void generateWord(TETile[][] world) {
        for (int i = 0; i < MAXNUMOFROOM; i++) {
            Room newRoom = generateRoom();
            if (newRoom != null) {
                rooms.add(newRoom);
                int uMin = newRoom.getyLeftBottomPoint();
                int uMax = newRoom.getyLeftBottomPoint() + newRoom.getHeight() - 1;
                int vMin = newRoom.getxLeftBottomPoint();
                int vMax = newRoom.getxLeftBottomPoint() + newRoom.getWidth() - 1;
                for (int u = uMin; u <= uMax; u++) {
                    for (int v = vMin; v <= vMax; v++) {
                        if (u != uMin && v != vMin && u != uMax && v != vMax) {
                            world[v][u] = Tileset.FLOOR;
                        } else {
                            world[v][u] = Tileset.WALL;
                        }
                    }
                }
            } else {
                return;
            }
        }
    }

    private class Room implements Comparable<Room> {

        private int height;
        private int width;
        private int xLeftBottomPoint;
        private int yLeftBottomPoint;

        public Room(int height, int width, int xLeftBottomPoint, int yLeftBottomPoint) {
            this.height = height;
            this.width = width;
            this.xLeftBottomPoint = xLeftBottomPoint;
            this.yLeftBottomPoint = yLeftBottomPoint;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getxLeftBottomPoint() {
            return xLeftBottomPoint;
        }

        public void setxLeftBottomPoint(int xLeftBottomPoint) {
            this.xLeftBottomPoint = xLeftBottomPoint;
        }

        public int getyLeftBottomPoint() {
            return yLeftBottomPoint;
        }

        public void setyLeftBottomPoint(int yLeftBottomPoint) {
            this.yLeftBottomPoint = yLeftBottomPoint;
        }


        @Override
        public int compareTo(Room o) {
            if (this.getxLeftBottomPoint() - o.getxLeftBottomPoint() != 0) {
                return this.getxLeftBottomPoint() - o.getxLeftBottomPoint();
            } else {
                return this.getyLeftBottomPoint() - o.getyLeftBottomPoint();
            }
        }
    }

    private Room generateRoom() {
        Room room = null;
        int count = 0;
        do {
            room = new Room(random.nextInt(UPPERHEIGHTOFROOM - 4) + 4,
                    random.nextInt(UPPERWIDTHOFROOM - 4) + 4,
                    random.nextInt(WIDTH), random.nextInt(HEIGHT));
            count++;
        } while (checkCover(room) && count < MAXCOUNT);
        return count != MAXCOUNT ? room : null;
    }

    private boolean checkCover(Room room) {

        if (room.getWidth() + room.getxLeftBottomPoint() >= WIDTH
                || room.getHeight() + room.getyLeftBottomPoint() >= HEIGHT) {
            return true;
        }
        for (Room item : rooms) {
            int[] xArr = new int[4];
            int[] yArr = new int[4];
            //为了好画通道将房间大小扩容一圈
            xArr[0] = room.getxLeftBottomPoint() - 1;
            xArr[1] = room.getxLeftBottomPoint() + room.getWidth() - 1 + 1;
            xArr[2] = item.getxLeftBottomPoint() - 1;
            xArr[3] = item.getxLeftBottomPoint() + item.getWidth() - 1 + 1;
            yArr[0] = room.getyLeftBottomPoint() - 1;
            yArr[1] = room.getyLeftBottomPoint() + room.getHeight() - 1 + 1;
            yArr[2] = item.getyLeftBottomPoint() - 1;
            yArr[3] = item.getyLeftBottomPoint() + item.getHeight() - 1 + 1;
            if (!(xArr[0] > xArr[3] || xArr[2] > xArr[1] || yArr[0] > yArr[3]
                    || yArr[2] > yArr[1])) {
                return true;
            }
        }
        return false;
    }

    public long inputToSeed(String input) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                str.append(input.charAt(i));
            }
        }
        return Long.parseLong(str.toString());
    }
}
