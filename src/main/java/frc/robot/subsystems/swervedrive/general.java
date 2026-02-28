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
    public void general(boolean Left,boolean Right)
    {
        if(Left)
        {
            MotorA.set(0.1);
            
        }
        else if(Right){
            MotorA.set(-0.1);
            
        }

        else 
        {
            MotorA.set(0);
        }
    }
}
