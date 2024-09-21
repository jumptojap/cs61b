package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;


import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * ClassName: Clorus
 * Package: creatures
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/21 - 13:21
 * Version: v1.0
 */
public class Clorus extends Creature {
    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("clorus");
        energy = e;
        r = 34;
        g = 0;
        b = 231;
    }

    @Override
    public void move() {
        energy = energy - 0.03;
    }

    @Override
    public void attack(Creature c) {
        energy = energy + c.energy();
    }

    @Override
    public Creature replicate() {
        energy = energy / 2;

        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy = energy + 0.01;
    }

    private boolean checkEmpty(Collection<Occupant> values) {
        for (Occupant o : values) {
            if ("empty".equals(o.name())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSeenPlip(Collection<Occupant> values) {
        for (Occupant o : values) {
            if ("plip".equals(o.name())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Collection<Occupant> values = neighbors.values();
        if (!checkEmpty(values)) {
            stay();
            return new Action(Action.ActionType.STAY);
        } else if (hasSeenPlip(values)) {
            List<Direction> directionList = new ArrayList<>();
            for (Direction d : Direction.values()) {
                if ("plip".equals(neighbors.get(d).name())) {
                    directionList.add(d);
                }
            }
            Random rand = new Random();
            int index = rand.nextInt(directionList.size());
            attack((Creature) neighbors.get(directionList.get(index)));
            return new Action(Action.ActionType.ATTACK, directionList.get(index));
        } else if (energy >= 1) {
            List<Direction> directionList = new ArrayList<>();
            for (Direction d : Direction.values()) {
                if ("empty".equals(neighbors.get(d).name())) {
                    directionList.add(d);
                }
            }
            Random rand = new Random();
            int index = rand.nextInt(directionList.size());
            neighbors.put(directionList.get(index), replicate());
            return new Action(Action.ActionType.REPLICATE, directionList.get(index));
        } else {
            List<Direction> directionList = new ArrayList<>();
            for (Direction d : Direction.values()) {
                if ("empty".equals(neighbors.get(d).name())) {
                    directionList.add(d);
                }
            }
            Random rand = new Random();
            int index = rand.nextInt(directionList.size());
            move();
            return new Action(Action.ActionType.MOVE, directionList.get(index));
        }
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }
}
