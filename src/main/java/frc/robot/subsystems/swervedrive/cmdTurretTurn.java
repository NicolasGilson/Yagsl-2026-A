package frc.robot.subsystems.swervedrive;

import edu.wpi.first.wpilibj2.command.Command;


import frc.robot.LimelightHelpers;
import frc.robot.Robot;
import frc.robot.RobotContainer;


public class cmdTurretTurn extends Command 
{
  private double RPM;
  private final double kP = 0.005; //this is changeable based on how fast turrent moves
  private int[] tags;

  public cmdTurretTurn(int[] april)
  {
    this.RPM= 4.566552639007568;
    this.tags=april;
    
    LimelightHelpers.SetFiducialIDFiltersOverride("limelight",tags); //maybe this should go under initialize()

    addRequirements(RobotContainer.turetTurner, RobotContainer.turetShoot);
  }

  @Override
  public void initialize() {
    //LimelightHelpers.SetFiducialIDFiltersOverride("limelight",tags); //right side
    
    //LimelightHelpers.SetFiducialIDFiltersOverride("limelight", new int[]{10, 26}); //right side
  }


  @Override
  public void execute()
  {
    LimelightHelpers.LimelightResults results = LimelightHelpers.getLatestResults("limelight");
    // go back to original getTX and getTY if this doesn't work. 

    if (!results.valid) {
        RobotContainer.turetTurner.setSpeed(0);
        return;
    }
    // LimelightHelpers.SetFiducialIDFiltersOverride("limelight",tags);
    //9, 10: red alliance
    //25, 26: blue alliance

    double tx = results.tx;
    double ty = results.ty; //a2

    /* Auto-aiming */
    double output = tx * kP; //invert it if it goes the wrong direction
    RobotContainer.turetTurner.setSpeed(output);

    /* NEW - Distance Calculation */
    double limelightMountingAngleDegrees = 30.0; //a1
    
    double limelightLensHeight = 20.0; //h1 (in inches)
    double goalHeight = 44.25; //h2

    double angleToGoalDegrees = limelightMountingAngleDegrees + ty;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    double distance = (goalHeight - limelightLensHeight) / Math.tan(angleToGoalRadians);
    //SmartDashboard.putNumber("Limelight Distance", distance);
    RobotContainer.turetShoot.projMotion(this.RPM, (distance)+70);
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