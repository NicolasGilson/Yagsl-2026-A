package frc.robot.subsystems.swervedrive;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.RobotContainer;


public class cmdTurretTurn extends Command 
{
  //private final turetTurn turret;
  private final double kP = 0.0003; //this is changeable based on how fast turrent moves

  public cmdTurretTurn(/*turetTurn turret*/)
  {
    //this.turret = turret;
    //addRequirements(RobotContainer.turetTurner);
  }

  @Override
  public void execute()
  {
    if (!LimelightHelpers.getTV("limelight")) {
        RobotContainer.turetTurner.setSpeed(0);
        return;
    }

    double tx = LimelightHelpers.getTX("limelight");
    double output = tx * kP; //invert it if it goes the wrong direction

    RobotContainer.turetTurner.setSpeed(output);
  }

  @Override
  public boolean isFinished()
  {
    return false;
  } 

  @Override
  public void end(boolean interrupted)
  {
    RobotContainer.turetTurner.setSpeed(0);
  }
}