// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.cmdLifeweaverRotate;
import frc.robot.subsystems.swervedrive.cmdTurretTurn;
import frc.robot.subsystems.swervedrive.general;

import java.io.File;
import swervelib.SwerveInputStream;

import frc.robot.subsystems.swervedrive.turetShoot;
import frc.robot.subsystems.swervedrive.turetTurn;//14
//10 lifeWeaver rotate up/down
//9 lifeWeaver grab
//15 ascending
//11 modelT
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
  public static turetShoot turetShoot = new turetShoot(12,13,15);
  public static general lifeWeaverRotate = new general(10);
  private general lifeWeaver = new general(9);
  //private general ascending = new general(15);
  private general modelT = new general(11);
  public static turetTurn turetTurner = new turetTurn();
  // Replace with CommandPS4Controller or CommandJoystick if needed
  final         CommandPS5Controller winton = new CommandPS5Controller(0);
   final         CommandPS5Controller mag = new CommandPS5Controller(1);
  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                                "swerve/neo"));

  // Establish a Sendable Chooser that will be able to be sent to the SmartDashboard, allowing selection of desired auto
  private final SendableChooser<Command> autoChooser;

  /**
   * Converts driver input into a field-relative ChassisSpeeds that is controlled by angular velocity.
   */
  //red negative
  //blue postive

  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                () -> winton.getLeftY() * 1,
                                                                () -> winton.getLeftX() * 1)
                                                            .withControllerRotationAxis(() -> winton.getRightX())
                                                            .deadband(OperatorConstants.DEADBAND)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  /**
   * Clone's the angular velocity input stream and converts it to a fieldRelative input stream.
   */
  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(winton::getRightX,
                                                                                             winton::getRightY)
                                                           .headingWhile(true);

  /**
   * Clone's the angular velocity input stream and converts it to a robotRelative input stream.
   */

   //TODO Changed
  SwerveInputStream driveRobotOriented = driveAngularVelocity.copy().robotRelative(true)
                                                             .allianceRelativeControl(true);

  SwerveInputStream driveAngularVelocityKeyboard = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                        () -> -winton.getLeftY(),
                                                                        () -> -winton.getLeftX())
                                                                    .withControllerRotationAxis(() -> -winton.getRightX())
                                                                    .deadband(OperatorConstants.DEADBAND)
                                                                    .scaleTranslation(0.8)
                                                                    .allianceRelativeControl(true);
  // Derive the heading axis with math!
  SwerveInputStream driveDirectAngleKeyboard     = driveAngularVelocityKeyboard.copy()
                                                                               .withControllerHeadingAxis(() ->
                                                                                                              Math.sin(
                                                                                                                  winton.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2),
                                                                                                          () ->
                                                                                                              Math.cos(
                                                                                                                  winton.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2))
                                                                               .headingWhile(true)
                                                                               .translationHeadingOffset(true)
                                                                               .translationHeadingOffset(Rotation2d.fromDegrees(
                                                                                   0));

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Configure the trigger bindings
    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);
    
    //Create the NamedCommands that will be used in PathPlanner
    NamedCommands.registerCommand("test", Commands.print("I EXIST"));

    //Have the autoChooser pull in all PathPlanner autos as options
    autoChooser = AutoBuilder.buildAutoChooser();

    //Set the default auto (do nothing) 
    autoChooser.setDefaultOption("Do Nothing", Commands.none());

    autoChooser.addOption("TEST",AutoBuilder.buildAuto("baller"));

    //Add a simple auto option to have the robot drive forward for 1 second then stop
    autoChooser.addOption("Drive Forward", drivebase.driveForward().withTimeout(1));
    autoChooser.addOption("Lifewearver", new cmdLifeweaverRotate());

    //Put the autoChooser on the SmartDashboard
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    //Command driveFieldOrientedDirectAngle      = drivebase.driveFieldOriented(driveDirectAngle);
    Command driveFieldOrientedAnglularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);
    //Command driveRobotOrientedAngularVelocity  = drivebase.driveFieldOriented(driveRobotOriented);
   // Command driveSetpointGen = drivebase.driveWithSetpointGeneratorFieldRelative(driveDirectAngle);
    Command driveFieldOrientedDirectAngleKeyboard      = drivebase.driveFieldOriented(driveDirectAngleKeyboard);
    //Command driveFieldOrientedAnglularVelocityKeyboard = drivebase.driveFieldOriented(driveAngularVelocityKeyboard);
    //Command driveSetpointGenKeyboard = drivebase.driveWithSetpointGeneratorFieldRelative(driveDirectAngleKeyboard);

    if (RobotBase.isSimulation())
    {
      drivebase.setDefaultCommand(driveFieldOrientedDirectAngleKeyboard);
    } else
    {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);
    }

    if (Robot.isSimulation())
    {
      Pose2d target = new Pose2d(new Translation2d(1, 4),
                                 Rotation2d.fromDegrees(0));
      //drivebase.getSwerveDrive().field.getObject("targetPose").setPose(target);
      driveDirectAngleKeyboard.driveToPose(() -> target,
                                           new ProfiledPIDController(5,
                                                                     0,
                                                                     0,
                                                                     new Constraints(5, 2)),
                                           new ProfiledPIDController(5,
                                                                     0,
                                                                     0,
                                                                     new Constraints(Units.degreesToRadians(360),
                                                                                     Units.degreesToRadians(180))
                                           ));
      winton.options().onTrue(Commands.runOnce(() -> drivebase.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
      winton.button(1).whileTrue(drivebase.sysIdDriveMotorCommand());
      winton.button(2).whileTrue(Commands.runEnd(() -> driveDirectAngleKeyboard.driveToPoseEnabled(true),
                                                     () -> driveDirectAngleKeyboard.driveToPoseEnabled(false)));

//      winton.b().whileTrue(
//          drivebase.driveToPose(
//              new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
//                              );
/*
    }
    if (DriverStation.isTest())
    {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity); // Overrides drive command above!

      winton.square().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      winton.options().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      winton.share().whileTrue(drivebase.centerModulesCommand());
      winton.L1().onTrue(Commands.none());
      winton.R1().onTrue(Commands.none());
    } else
    {
      winton.cross().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      winton.options().whileTrue(Commands.none());
      winton.share().whileTrue(Commands.none());
      winton.L1().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      winton.R1().onTrue(Commands.none());
    */}
    mag.povRight().whileTrue(new cmdTurretTurn(new int[] {10,26})); //put it under keybindings. front-right side
    mag.povLeft().whileTrue(new cmdTurretTurn(new int[] {9,25})); //front-left side
    mag.povUp().whileTrue(new cmdTurretTurn(new int[] {11,27})); //sidekick from the right **added
    mag.povDown().whileTrue(new cmdTurretTurn(new int[] {8,24})); //sidekick from the left **added
  }

  public void PS4buttons()
  {
    turetShoot.turetShooty(mag.L2().getAsBoolean(), mag.R2().getAsBoolean(),mag.povUp().getAsBoolean());
    turetTurner.TuretTurner(mag.R1().getAsBoolean(), mag.L1().getAsBoolean(),mag.triangle().getAsBoolean());
    lifeWeaver.generale(mag.touchpad().getAsBoolean(), mag.square().getAsBoolean(),1);
    lifeWeaverRotate.generale(mag.options().getAsBoolean(), mag.create().getAsBoolean(),0.3);
    //TODO update speed
    modelT.generale(mag.PS().getAsBoolean(), mag.circle().getAsBoolean(),1);
    //ascending.generale(winton.povDown().getAsBoolean(), winton.povUp().getAsBoolean(),1);
    // mag.povRight().whileTrue(new cmdTurretTurn(new int[] {10,26}));
    // mag.povLeft().whileTrue(new cmdTurretTurn(new int[] {9,25}));
  }  
  public String angle()
  {
    return ""+turetShoot.getSpeed();
  }
   /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // Pass in the selected auto from the SmartDashboard as our desired autnomous commmand 
    return autoChooser.getSelected();
  }



  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
