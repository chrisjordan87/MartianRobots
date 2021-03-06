package instruction;

import data.Position;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import robot.Robot;

public class TurnleftInstructionTest
{
    public static final int X = 2;
    public static final int Y = 2;

    @Test
    public void shouldUpdateRobotOrientationWhenTurningLeft() throws Exception
    {
        Position initialPosition = new Position(X, Y);
        Orientation initialOrientation = Orientation.N;
        Robot robot = new Robot(initialPosition, initialOrientation);
        Instruction.L.execute(robot);

        Assert.assertThat(robot.getCurrentOrientation(), Matchers.is(TurnUtils.calculateNextLeftOrientation(initialOrientation)));
    }
}