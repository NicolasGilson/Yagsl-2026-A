package frc.robot.subsystems.swervedrive;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class general 
{
    private final SparkMax MotorA;
    private double speed=0.5;
   

    public general(int motorAID)
    {
        MotorA = new SparkMax(motorAID,MotorType.kBrushless);
    }
    public void setSpeed(double speed) 
    {
        MotorA.set(speed);
    }
    public void generale(boolean Left,boolean Right,double speed)
    {
        this.speed=speed;
        if(Left)
        {
            MotorA.set(speed);
            
        }
        else if(Right)
        {
            MotorA.set(-1*speed);
        }

        else 
        {
            MotorA.set(0);
        }
    }

    public double getRPM()
    {
        return MotorA.getEncoder().getVelocity();
    }
}
