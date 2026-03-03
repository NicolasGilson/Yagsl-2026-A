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
    public void turetShooty(boolean Left,boolean Right,double speed)
    {
        if(Left)
        {
            MotorA.set(speed);
            MotorB.set(-1*speed);
        }
        else if(Right){
            MotorA.set(-1*speed);
            MotorB.set(speed);
        }

        else 
        {
            MotorA.set(0);
            MotorB.set(0);
        }
    }
}
