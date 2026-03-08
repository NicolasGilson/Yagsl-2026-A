package frc.robot.subsystems.swervedrive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class cmdLifeweaverRotate extends Command 
{
    private double time;
    public cmdLifeweaverRotate()
    {

    }

    public void execute()
    {
        RobotContainer.lifeWeaverRotate.setSpeed(0.1);
        time = Timer.getFPGATimestamp()+0.5;
    }
    @Override
    public boolean isFinished()
    {
        return (time<=Timer.getFPGATimestamp());
    } 

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.lifeWeaverRotate.setSpeed(0);
    }
}
