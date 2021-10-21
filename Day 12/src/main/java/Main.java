import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
//        File inputFile = new File("input-demo.txt");
         File inputFile = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            List<String> instructions = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                count++;
//                System.out.println("" + count + " " + line);
                instructions.add(line);
            }

            Ship ship = new Ship();

            for (String instruction : instructions) {
                String dir = instruction.split("[\\d]")[0];
                int value = Integer.parseInt(instruction.split("[\\D]")[1]);
//                System.out.println("dir: " + dir + ", val: " + value);

                switch (dir) {
                    case "N":
                        ship.move(0, value);
                        break;
                    case "S":
                        ship.move(0, -1 * value);
                        break;
                    case "E":
                        ship.move(value, 0);
                        break;
                    case "W":
                        ship.move(-1 * value, 0);
                        break;
                    case "L":
                        ship.turnLeft(value);
                        break;
                    case "R":
                        ship.turnRight(value);
                        break;
                    case "F":
                        ship.move(ship.getDirection().scale(value));
                        break;
                }

//                System.out.println(instruction);
//                System.out.println(ship);
            }
//            System.out.println("ship: " + ship + " " + ship.getPosition().manhattanDistance());

            Ship newShip = new Ship();
            Vector waypoint = new Vector(10, 1);
            for (String blah : instructions) {
                System.out.println(blah);
                String dir = blah.split("[\\d]")[0];
                int value = Integer.parseInt(blah.split("[\\D]")[1]);
//                System.out.println("dir: " + dir + ", val: " + value);

                System.out.println("before newShip: " + newShip);
                System.out.println("before waypoint: " + waypoint);
                switch (dir) {
                    case "N":
                        waypoint.add(0, value);
                        break;
                    case "S":
                        waypoint.add(0, -1 * value);
                        break;
                    case "E":
                        waypoint.add(value, 0);
                        break;
                    case "W":
                        waypoint.add(-1 * value, 0);
                        break;
                    case "L":
                        for (int i = value; i > 0; i -= 90) {
                            waypoint = new Vector(-1 * waypoint.getY(), waypoint.getX());
                        }
                        break;
                    case "R":
                        for (int i = value; i > 0; i -= 90) {
                            waypoint = new Vector(waypoint.getY(), -1 * waypoint.getX());
                        }
                        break;
                    case "F":
                        newShip.move(waypoint.scale(value));
                        break;
                }
                System.out.println("after newShip: " + newShip);
                System.out.println("after waypoint: " + waypoint);
                System.out.println("---------------------------------------------------------------------------------");
            }
            System.out.println("newShip : " + newShip + ", waypoint: " + waypoint + ", man: " + newShip.getPosition().manhattanDistance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class Ship {
        private Vector direction;
        private Vector position;

        Ship() {
            this.direction = new Vector(1, 0);
            this.position = new Vector(0, 0);
        }

        public void move(int addX, int addY) {
            this.position.add(addX, addY);
        }

        public void move(Vector newMovement) {
            move(newMovement.getX(), newMovement.getY());
        }

        public void turnLeft(int degrees) {
            Vector newDirection = new Vector(direction.getX(), direction.getY());
            for (int i = degrees; i > 0; i -= 90) {
                newDirection = new Vector(-1 * newDirection.getY(), newDirection.getX());
            }
            setDirection(newDirection);
        }

        public void turnRight(int degrees) {
            Vector newDirection = new Vector(direction.getX(), direction.getY());
            for (int i = degrees; i > 0; i -= 90) {
                newDirection = new Vector(newDirection.getY(), -1 * newDirection.getX());
            }
            setDirection(newDirection);
        }

        public String toString() {
            return "{\n\tdirection: " + direction + ",\n\tposition: " + position + "\n}";
        }
    }

    @Data
    public static class Vector {
        private int x, y;

        Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void add(int addX, int addY) {
            this.x += addX;
            this.y += addY;
        }

        public void subtract(int subX, int subY) {
            this.x -= subX;
            this.y -= subY;
        }

        public Vector add(Vector v) {
            return new Vector(x + v.getX(), y + v.getY());
        }

        public Vector sub(Vector v) {
            return new Vector(x - v.getX(), y - v.getY());
        }

        public Vector multiply(Vector otherVector) {
            return new Vector(this.x * otherVector.getX(), this.y * otherVector.getY());
        }

        public Vector scale(int scalar) {
            return new Vector(this.x * scalar, this.y * scalar);
        }

        public void set(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int manhattanDistance() {
            return Math.abs(x) + Math.abs(y);
        }

        public String toString() {
            return "{ x: " + this.x + ", y: " + this.y + " }";
        }
    }
}
