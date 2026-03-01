package frc.robot.subsystems.swervedrive;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class cmdTurretTurn extends Command 
{
    protected double theta;
    protected double speed;
    protected double errorbound;
    public cmdTurretTurn(double theta,double errorbound)
    {
        this.theta = theta;
        this.speed = speed;
        this.errorbound = errorbound;
        addRequirements(RobotContainer.turetTurner);
    }

    @Override
    public void execute() 
    {
        RobotContainer.turetTurner.setAngle(speed,theta,errorbound);
    }

    @Override
    public void initialize() 
    {
        RobotContainer.turetTurner.setAngle(speed,theta,errorbound);
    }

    @Override
    public boolean isFinished() 
    {
        return !(theta - 0.005 > turetTurn.getAngle()|| theta + 0.005 < turetTurn.getAngle());
    }
}