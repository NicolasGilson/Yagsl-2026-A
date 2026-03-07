package frc.robot.subsystems.swervedrive;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class general 
{
    private final SparkMax MotorA;
   

    public general(int motorAID)
    {
        MotorA = new SparkMax(motorAID,MotorType.kBrushless);
    }
    public void generale(boolean Left,boolean Right,double speed)
    {
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
