package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;
import huglife.HugLifeUtils;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {
    double enegry;
    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("clorus");
        enegry = e;
        r = 34;
        g = 0;
        b = 231;
    }

    public Clorus() {
        super("clorus");
        enegry = 1.0;
        r = 34;
        g = 0;
        b = 231;
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }

    @Override
    public String name() {
        return super.name();
    }

    @Override
    public void move() {
        enegry -= 0.03;
    }

    @Override
    public void stay() {
        enegry -= 0.01;
    }

    @Override
    public void attack(Creature c) {
        enegry += c.energy();
    }

    @Override
    public Creature replicate() {
        enegry *= 0.5;
        return new Clorus(enegry);
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");

        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plips.size() != 0) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plips));
        } else if (enegry >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(empties));
        }
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(empties));
    }
}
