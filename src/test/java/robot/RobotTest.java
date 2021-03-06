package robot;

import data.Grid;
import data.Position;
import instruction.Instruction;
import instruction.Orientation;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RobotTest
{

    public static final int UPPER_X_COORDINATE = 3;
    public static final int UPPER_Y_COORDINATE = 4;
    public static final Instruction FORWARD_INSTRUCTION = Instruction.F;
    private Grid grid;
    private List<Instruction> instructions;

    @Before
    public void setUp() throws Exception
    {
        grid = new Grid(UPPER_X_COORDINATE, UPPER_Y_COORDINATE);
        instructions = new ArrayList<>();
    }

    @Test
    public void shouldExecuteInstructionWhenRobotNotCurrentlyLost() throws Exception
    {
        instructions.add(FORWARD_INSTRUCTION);
        int x = 1;
        int y = 2;
        Position initialPosition = new Position(x, y);
        Orientation initialOrientation = Orientation.N;
        Robot robot = new Robot(initialPosition, initialOrientation);
        robot.executeInstructions(grid, instructions);

        Assert.assertThat(robot.getCurrentPosition(), Matchers.is(initialOrientation.calculateNextForwardPosition(initialPosition)));
    }

    @Test
    public void robotShouldNotBeLostIfStillOnGrid() throws Exception
    {
        instructions.add(FORWARD_INSTRUCTION);
        Robot robot = new Robot(new Position(Grid.ZERO_COORDINATE, Grid.ZERO_COORDINATE), Orientation.N);
        robot.executeInstructions(grid, instructions);

        Assert.assertThat(robot.isLost(), Matchers.is(false));
    }

    @Test
    public void robotShouldBeLostIfItHasFallenOffTheGridAndRegisterLostMoveWithGrid() throws Exception
    {
        Position initialPosition = new Position(UPPER_X_COORDINATE, UPPER_Y_COORDINATE);
        Robot robot = new Robot(initialPosition, Orientation.N);
        instructions.add(FORWARD_INSTRUCTION);

        robot.executeInstructions(grid, instructions);
        Assert.assertThat(robot.isLost(), Matchers.is(true));

        Assert.assertThat(grid.getLostMoves(), Matchers.hasSize(1));
    }

    @Test
    public void robotShouldReturnLastKnownPositionPriorToBeingLost() throws Exception
    {
        Position initialPosition = new Position(UPPER_X_COORDINATE, UPPER_Y_COORDINATE);
        Robot robot = new Robot(initialPosition, Orientation.N);
        instructions.add(FORWARD_INSTRUCTION);

        robot.executeInstructions(grid, instructions);
        Assert.assertThat(robot.getPreviousPosition(), Matchers.is(initialPosition));
    }

    @Test
    public void robotShouldNotLoseItselfIfPreviousRobotHasLostItselfOnAParticularPosition() throws Exception
    {
        Position position = new Position(UPPER_X_COORDINATE, UPPER_Y_COORDINATE);
        Orientation orientation = Orientation.N;
        grid.registerLostMove(position, orientation, FORWARD_INSTRUCTION);
        Robot robot = new Robot(position, orientation);

        instructions.add(FORWARD_INSTRUCTION);

        robot.executeInstructions(grid, instructions);

        Assert.assertThat(robot.isLost(), Matchers.is(false));
    }
}

