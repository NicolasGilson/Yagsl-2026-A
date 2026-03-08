package frc.robot.subsystems.swervedrive;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class turetShoot extends SubsystemBase
{
    private final SparkMax MotorA;
    private double speed=0.5;
    private final SparkMax MotorB;
    private final SparkMax MotorC;
    private static final double k1 = 0.60;
    private static final double k2 = 1.0;

    public turetShoot(int motorAID,int motorBID,int motorCID)
    {
        MotorA = new SparkMax(motorAID,MotorType.kBrushless);
        MotorB = new SparkMax(motorBID,MotorType.kBrushless);
        MotorC = new SparkMax(motorCID,MotorType.kBrushless);
    }
    public void setSpeed(double speed) 
    {
        MotorA.set(speed);
        MotorB.set(-speed);
    }
    public void rpmSPEED(double Target, double dist)
    {
        //MotorA.set((projMotion(Target,dist))/(MAX_RPM));
    }
    public void turetShooty(boolean Left,boolean Right,boolean MAX_PWR)
    {
        if(Left)
        {
            setSpeed(speed);
            MotorC.set(1);
        }
        else if(Right)
        {
            setSpeed(-speed);
            MotorC.set(-1);
        }
        else if(MAX_PWR)
        {
            this.speed=0.57;
        }
        else 
        {
           setSpeed(0);
           MotorC.set(0);
        }
    }
    public double getSpeed()
    {
        return speed;
    }
//-5937.1416015625
//75
    public void projMotion(double RPM,double dist)
    {
        speed = 1.659308*((60/(2*3.14159*(2)*0.051*k1))*((Math.sqrt(((9.81*dist*0.0254*dist*0.0254)/0.060373792)*(1/((dist*0.0254*5.6712818)-5.08))))+k2*(RPM*(2*3.14159*(0.029)/60))))/5937.1416015625; //RPM ->rad/s* asc const
    }
}