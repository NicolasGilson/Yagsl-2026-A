package frc.robot.subsystems.swervedrive;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class turetTurn extends SubsystemBase
{
    public final double LEFT_LIMIT = -10;//-10
    public final double RIGHT_LIMIT = 7.0002;//7.0

    //-1.1666666
    private static SparkMax MotorA= new SparkMax(14,MotorType.kBrushless);;

    public turetTurn()
    {
        
    }
    public void TuretTurn(boolean Left,boolean Right,boolean t)
    {
        if(Left)
        {
            MotorA.set(0.1);
            
        }
        else if(Right){
            MotorA.set(-0.1);
            
        }
        else if(t)
        {
            setAngle(0.1,5,0.005);
        }

        else 
        {
            MotorA.set(0);
        }
    }

      public void setAngle(double speed, double theta, double errorbound)
    {
        if(theta>RIGHT_LIMIT)
        {
            theta=RIGHT_LIMIT;
            if (theta - errorbound > getAngle()) 
            {
            MotorA.set(speed);
            }  
            if (theta + errorbound < getAngle()) 
            {
                MotorA.set(speed* -1);
            } 
            if(!(theta - errorbound > getAngle() || theta + errorbound < getAngle()))
            {
                MotorA.set(0);
            }
        }
        else if(theta<LEFT_LIMIT)
        {
            theta=LEFT_LIMIT;
            if (theta - errorbound > getAngle()) 
            {
                MotorA.set(speed);
            } 
            
            if (theta + errorbound < getAngle()) 
            {
                MotorA.set(speed* -1);
            } 
            if(!(theta - errorbound > getAngle() || theta + errorbound < getAngle()))
            {
                MotorA.set(0);
            }
        }   
    }
    public static void zeroEncoder()
    {
        MotorA.getEncoder().setPosition(0);
    }
    public static double getAngle()
    {
      return MotorA.getEncoder().getPosition();
    }
    
   
}


/*
 *     public void angle(double speed, double theta, double errorbound)
    {
        if (theta - errorbound > armEncoder.getDistance()) {
            armMotorA.set(speed );
            armMotorB.set(speed* -1);
            System.out.println("up");
        } 
        
        if (theta + errorbound < armEncoder.getDistance()) {
            armMotorA.set(speed* -1);
            armMotorB.set(speed );
            System.out.println("down");
        } 
        if(!(theta - errorbound > armEncoder.getDistance() || theta + errorbound < armEncoder.getDistance()))
        {
            armMotorA.set(0);
            armMotorB.set(0);
        }
    }
 * 
 * 
 * 
 */