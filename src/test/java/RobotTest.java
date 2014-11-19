import data.Grid;
import data.Position;
import instruction.ForwardInstruction;
import instruction.Instruction;
import instruction.Orientation;
import instruction.Robot;
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
        instructions.add(new ForwardInstruction());
        int x = 1;
        int y = 2;
        Position initialPosition = new Position(x, y);
        Orientation initialOrientation = Orientation.NORTH;
        Robot robot = new Robot(initialPosition, initialOrientation);
        robot.executeInstructions(grid, instructions);

        Assert.assertThat(robot.getCurrentPosition(), Matchers.is(initialOrientation.calculateNextForwardPosition(initialPosition)));
    }

    @Test
    public void robotShouldNotBeLostIfStillOnGrid() throws Exception
    {
        instructions.add(new ForwardInstruction());
        Robot robot = new Robot(new Position(Grid.ZERO_COORDINATE, Grid.ZERO_COORDINATE), Orientation.NORTH);
        robot.executeInstructions(grid, instructions);

        Assert.assertThat(robot.isLost(), Matchers.is(false));
    }

    @Test
    public void robotShouldBeLostIfItHasFallenOffTheGrid() throws Exception
    {
        Position initialPosition = new Position(UPPER_X_COORDINATE, UPPER_Y_COORDINATE);
        Robot robot = new Robot(initialPosition, Orientation.NORTH);
        instructions.add(new ForwardInstruction());

        robot.executeInstructions(grid, instructions);
        Assert.assertThat(robot.isLost(), Matchers.is(true));
    }
}

