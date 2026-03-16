package frc.robot.subsystems.swervedrive.CMDs;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class cmdModelT extends Command 
{
    private double time;
    public cmdModelT()
    {

    }

    public void execute()
    {
        //TODO CHECK ALL TIMES
        RobotContainer.modelT.setSpeed(1);
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
        RobotContainer.modelT.setSpeed(0);
    }
}
