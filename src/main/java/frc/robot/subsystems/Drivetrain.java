/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.lib.util.Debugger;
import frc.lib.util.SpectrumLogger;
import frc.robot.Robot;
import frc.robot.HW;
import frc.robot.commands.Drive;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public final WPI_TalonSRX leftRearMotor;
  public final WPI_TalonSRX rightRearMotor;
  public final WPI_VictorSPX leftFrontMotor;
  public final WPI_VictorSPX rightFrontMotor;

  public DifferentialDrive differentialDrive;

  public Drivetrain() {
    super("drivetrain");
    leftRearMotor = new WPI_TalonSRX(HW.LEFT_DRIVE_REAR);
    rightRearMotor = new WPI_TalonSRX(HW.RIGHT_DRIVE_REAR);
    leftFrontMotor = new WPI_VictorSPX(HW.LEFT_DRIVE_FRONT);
    rightFrontMotor = new WPI_VictorSPX(HW.RIGHT_DRIVE_FRONT);

    leftRearMotor.configFactoryDefault();
    rightRearMotor.configFactoryDefault();
    leftFrontMotor.configFactoryDefault();
    rightFrontMotor.configFactoryDefault();

    leftRearMotor.setInverted(true);
    rightRearMotor.setInverted(false);
    
    int currentLimit = 65;
    leftRearMotor.configPeakCurrentLimit(currentLimit);
    rightRearMotor.configPeakCurrentLimit(currentLimit);

    leftFrontMotor.follow(leftRearMotor);
    rightFrontMotor.follow(rightRearMotor); 

    differentialDrive = new DifferentialDrive(leftRearMotor, rightRearMotor);

    //differentialDrive.setRightSideInverted(false);

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new Drive());
  }

  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }

  public void printDebug(String msg){
    Debugger.println(msg, Robot._drive, Debugger.debug2);
  }
  
  public void printInfo(String msg){
    Debugger.println(msg, Robot._drive, Debugger.info3);
  }
  
  public void printWarning(String msg) {
    Debugger.println(msg, Robot._drive, Debugger.warning4);
  }

  public void print(String msg){
    System.out.println(msg);
  }

  public void logEvent(String event){
		SpectrumLogger.getInstance().addEvent(Robot._drive, event);
  }
}
