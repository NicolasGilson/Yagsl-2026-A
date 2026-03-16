package frc.robot.subsystems.swervedrive.CMDs;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class cmdturretShot extends Command 
{
    private double time;
    public cmdturretShot()
    {

    }

    public void execute()
    {
        //TODO check times shoot
        RobotContainer.turetShoot.setSpeed(1);
        time = Timer.getFPGATimestamp()+10;
    }
    @Override
    public boolean isFinished()
    {
        return (time<=Timer.getFPGATimestamp());
    } 

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.turetShoot.setSpeed(0);
    }
}
