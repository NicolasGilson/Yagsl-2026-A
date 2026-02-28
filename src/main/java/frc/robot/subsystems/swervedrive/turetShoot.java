package frc.robot.subsystems.swervedrive;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class turetShoot
{
    private final SparkMax MotorA;
    private final SparkMax MotorB;

    public turetShoot(int motorAID,int  motorBID)
    {
        MotorA = new SparkMax(motorAID,MotorType.kBrushless);
        MotorB = new SparkMax(motorBID,MotorType.kBrushless);
    }
    public void turetShoot(boolean Left,boolean Right)
    {
        if(Left)
        {
            MotorA.set(0.1);
            MotorB.set(-0.1);
        }
        else if(Right){
            MotorA.set(-0.1);
            MotorB.set(0.1);
        }

        else 
        {
            MotorA.set(0);
            MotorB.set(0);
        }
    }
}
