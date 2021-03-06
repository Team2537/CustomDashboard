/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//NETWORK TABLE
//2 or 3 classes inherit from panel
//2 Monitors
//one panel to display GUI
//one to display
//changing speed to test changing through GUI
//append timestamp to robot?
//push smartdashboard errors 
//merge smartdashboard values with tiem and arm positions
package frc;
import java.lang.Thread;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.wpilibj.Relay.Value;


/**
 * Add your docs here.
 */
public class CustomDashboard {

    private JFrame logFrame, driveFrame;
    private JMenuBar mb;
    private JTextArea ta;
    private JLabel[] armPositions;
    private JLabel[] diagnostics;
    private JLabel[] sensorOutputs;
    private int wristamperage;
    private int intakeamperage;
   // private int armamperage;
    private double pdpCurrent;
    private int encoderValue;
    private double ultrasonicValue;

    

    public CustomDashboard(){
        //Creating the Frame
        logFrame = new JFrame("Changelog Frame");
        driveFrame = new JFrame("Ultra GUI");
        
        logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        driveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logFrame.setSize(400, 400);
        driveFrame.setSize(900,700);
        


        //Creating the MenuBar and adding components
        menuBarCreation();

        //Creating the logPanel at bottom and adding components
        JPanel logPanel = new JPanel(); // thelogPanel is not visible in output
        JTextField tf = new JTextField(30);
       logPanel.add(tf);
       

       // panels for Ultra Gooey mode
        JPanel armPanel = new JPanel(new GridLayout(9, 1));
        JPanel positionPanel = new JPanel();
        JPanel cameraPanel = new JPanel();
        JPanel statusPanel = new JPanel(new BorderLayout());
        JPanel pathPanel = new JPanel();
        JPanel sensorPanel = new JPanel(new GridLayout(4, 1));
        JPanel diagnosticPanel = new JPanel(new GridLayout(2, 3));
        
        driveFrame.getContentPane().add(BorderLayout.CENTER, positionPanel);
        driveFrame.getContentPane().add(BorderLayout.EAST, armPanel);
        driveFrame.getContentPane().add(BorderLayout.SOUTH, statusPanel);
        
        positionPanel.add(pathPanel);
        positionPanel.add(cameraPanel);
        statusPanel.add(BorderLayout.WEST, sensorPanel);
        statusPanel.add(BorderLayout.CENTER, diagnosticPanel);
        statusPanel.setBackground(Color.BLACK);
        positionPanel.setBackground(Color.BLACK);
        
        Border border = BorderFactory.createLineBorder(Color.GREEN, 5);
        Border miniborder = BorderFactory.createLineBorder(Color.GREEN, 1);
        Font font = new Font("Serif", Font.BOLD, 18);
        tf.setFont(font);
        tf.setForeground(Color.GREEN);
        // positions count down from top to bottom, like any standard rows

        //order is super important here
        
        
        
        sensorOutputs = new JLabel[4];
        sensorOutputs[0] = new JLabel("Encoder Value: " + encoderValue);
        sensorOutputs[1] = new JLabel("Ultrasonic Value: " + String.format("%5d", 0));
        sensorOutputs[2] = new JLabel("Left Speed: " + String.format("%5d", 0));
        sensorOutputs[3] = new JLabel("Right Speed: " + String.format("%5d", 0));

        armPositions = new JLabel[9];
        armPositions[0] = new JLabel("Frame Perimeter");
        armPositions[1] = new JLabel("Ship Cargo");
        armPositions[2] = new JLabel("High Rocket Cargo");
        armPositions[3] = new JLabel("High Rocket Hatch");
        armPositions[4] = new JLabel("Mid Rocket Cargo");
        armPositions[5] = new JLabel("Mid Rocket Hatch");
        armPositions[6] = new JLabel("Low Rocket Cargo");
        armPositions[7] = new JLabel("Ship Hatch");
        armPositions[8] = new JLabel("Vision Input");

        diagnostics = new JLabel[6];
        diagnostics[0] = new JLabel("Potentiometer");
        diagnostics[1] = new JLabel("PDP: " + String.format("%5d", 0));
        diagnostics[2] = new JLabel("Wrist");
        diagnostics[3] = new JLabel("Intake");
        diagnostics[4] = new JLabel("Arm");
        diagnostics[5] = new JLabel("Compressor");

        for(int i = 0; i < diagnostics.length; i++){
            diagnosticPanel.add(diagnostics[i]);
            diagnosticPanel.setBorder(border);
            diagnosticPanel.setOpaque(true);
            diagnosticPanel.setBackground(Color.BLACK);
            diagnostics[i].setOpaque(true);
            diagnostics[i].setBackground(Color.BLACK);
            diagnostics[i].setForeground(Color.GREEN);
            diagnostics[i].setBorder(miniborder);
            diagnostics[i].setFont(font);

        }

        for(int i = 0; i < armPositions.length; i++){
            armPanel.add(armPositions[i]);
            armPanel.setBorder(border);
            armPanel.setOpaque(true);
            armPanel.setBackground(Color.BLACK);
            armPositions[i].setOpaque(true);
            armPositions[i].setBackground(Color.BLACK);
            armPositions[i].setForeground(Color.GREEN);
            armPositions[i].setBorder(miniborder);
            armPositions[i].setFont(font);
        }
        
        for(int i = 0; i < sensorOutputs.length; i++){
            sensorPanel.add(sensorOutputs[i]);
            sensorPanel.setOpaque(true);
            sensorPanel.setBackground(Color.BLACK);
            sensorPanel.setBorder(border);
            sensorOutputs[i].setFont(font);
            sensorOutputs[i].setForeground(Color.GREEN);
        }


        tf.addKeyListener(new KeyListener(){
           
            @Override
            public void keyTyped(KeyEvent e) {
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                
                if(key == KeyEvent.VK_ENTER){
                    logMessage(tf.getText());
                    tf.setText("");
                }

        }
            public void keyReleased(KeyEvent e){


            }
        

        });



        // Text Area at the Center
        ta = new JTextArea();
        JScrollPane scroll = new JScrollPane(ta);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(375, 25, 10, 1000); 
       logPanel.add(scroll);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setEditable(false);
        
        

        // Adding Components to the Frame.
        logFrame.getContentPane().add(scroll);
        logFrame.getContentPane().add(BorderLayout.SOUTH, logPanel);
        logFrame.getContentPane().add(BorderLayout.NORTH, mb);
        logFrame.setVisible(true);
        driveFrame.setVisible(false);


        
    }

    public void sendData() {

    }


    public void potentiometerValue(boolean on){
        if(on == true){
            diagnostics[0].setBackground(Color.GREEN);
            diagnostics[0].setForeground(Color.BLACK);
        }else{
            diagnostics[0].setBackground(Color.RED);
            diagnostics[0].setForeground(Color.WHITE);
        }
    }

    public void pdpValue(double pdpCurrent){
        if( 1 > pdpCurrent || pdpCurrent > 4){
            diagnostics[1].setBackground(Color.RED);
        }else{
            diagnostics[1].setBackground(Color.GREEN);
            diagnostics[1].setForeground(Color.BLACK);
        }

    }

    public void wristAmpValue(double wristamperage){

        if(wristamperage >= 2){
            diagnostics[2].setBackground(Color.RED);
        }else{
            diagnostics[2].setBackground(Color.GREEN);
            diagnostics[2].setForeground(Color.BLACK);
        }

    }

    public void intakeAmpValue(double intakeamperage){
        if(intakeamperage >= 2){
            diagnostics[3].setBackground(Color.RED);
        }else{
            diagnostics[3].setBackground(Color.GREEN);
            diagnostics[3].setForeground(Color.BLACK);
        }
    }

    public void positionDetector(String ArmSetpoint){
        switch(ArmSetpoint){
            case "FRAME PERIMETER":
                changePosition(0);
                break;
            case "SHIP CARGO":
                changePosition(1);
                
            case "HIGH ROCKET CARGO":
                changePosition(2);
                break;

            case "HIGH ROCKET HATCH":
                changePosition(3);
                break;
            case "MID ROCKET CARGO":
                changePosition(4);
                break;
            case "MID ROCKET HATCH":
                changePosition(5);
                break;

            case "LOW ROCKET CARGO":
                changePosition(6);
                break;

            case "SHIP HATCH":
                changePosition(7);
                break;
        
        
            }
    }
/*
    public void armValue(boolean toomuch){
        if(armamperage >= 2){
            diagnostics[4].setBackground(Color.RED);
        }else{
            if(motor.get() = 0 && (potentiometer.get() > 0 || encoder.get() > 0)){
                diagnostics[4].setBackground(Color.RED);
            } else {
                diagnostics[4].setBackground(Color.GREEN);
            }
        }
    }
*/
    // positions count down from top to bottom, like any standard rows
    public void changePosition(int pos){
        for(int i = 0; i < armPositions.length; i++){
            armPositions[i].setBackground(Color.BLACK);
            armPositions[i].setForeground(Color.GREEN);
            armPositions[pos].setBackground(Color.GREEN);
            armPositions[pos].setForeground(Color.BLACK);
        }
    }

    public void changePositionLow(int pos){
        for(int i = 0; i < armPositions.length; i++){
            armPositions[i].setBackground(Color.BLACK);
            armPositions[i].setForeground(Color.GREEN);
            armPositions[pos].setBackground(new Color(100, 0, 20));
            armPositions[pos].setForeground(Color.BLACK);

        }
    }

    public void changePositionHigh(int pos){
        for(int i = 0; i < armPositions.length; i++){
            armPositions[i].setBackground(Color.BLACK);
            armPositions[i].setForeground(Color.GREEN);
            armPositions[pos].setBackground(new Color(255, 0, 20));
            armPositions[pos].setForeground(Color.BLACK);
        }
    }



    public void logMessage(String msg){
        
        ta.append(msg + "\n");
    }
    
    public void driveMode(){
        driveFrame.setVisible(true);
        logFrame.setVisible(false);
        

        menuBarCreation();
    }

    public void logMode(){
        driveFrame.setVisible(false);
        logFrame.setVisible(true);

       menuBarCreation();
    }

    public void menuBarCreation(){
        mb = new JMenuBar();
        JMenu m1 = new JMenu("Mode");
        mb.add(m1);
        JMenuItem m11 = new JMenuItem("Drive Mode");
        JMenuItem m22 = new JMenuItem("Changelog Mode");
        
        
        m1.add(m11);
        m1.add(m22);
        
        driveFrame.getContentPane().add(BorderLayout.NORTH, mb);
     
        m11.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                driveMode();
            }
        });
        m22.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                logMode();   
                //call to the method
            }
        });


    }


    // positions count down from top to bottom, like any standard rows
//ethernet issue, either plug in with ethernet or use without ethernet with easy to fall out power cable
        public void run() {
            
            NetworkTableInstance inst = NetworkTableInstance.getDefault();
            NetworkTable table = inst.getTable("database");
            NetworkTableEntry UltraDistance = table.getEntry("Ultra");
            NetworkTableEntry Current = table.getEntry("Current");
            NetworkTableEntry Encoder = table.getEntry("Encoder");
            NetworkTableEntry Temperature = table.getEntry("Temperature");
            NetworkTableEntry ClutchCompressor = table.getEntry("ClutchCompressor");
            NetworkTableEntry BoostCompressor = table.getEntry("BoostCompressor");
            NetworkTableEntry ArmPot = table.getEntry("ArmPot");
            NetworkTableEntry WristPot = table.getEntry("WristPot");
            NetworkTableEntry ArmAmp = table.getEntry("ArmAmp");
            NetworkTableEntry WristAmp = table.getEntry("WristAmp");
            NetworkTableEntry IntakeAmp = table.getEntry("IntakeAmp");
            NetworkTableEntry ArmSetpoint = table.getEntry("ArmSetpoint");
            NetworkTableEntry VisionInput = table.getEntry("VisionInput");
            NetworkTableEntry LeftSpeed = table.getEntry("LeftSpeed");
            NetworkTableEntry RightSpeed = table.getEntry("RightSpeed");

            inst.startClientTeam(2537);
            inst.startDSClient();
            while (true) {
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
                return;
            }
            
        //double rounding and alignment
        
        //SENSOR OUTPUTS
            sensorOutputs[0].setText("Encoder Value: " + String.format("%5d", (int)(Encoder.getDouble(0.0))) + "  ");
            System.out.println("Encoder Value: " + Encoder.getDouble(0.0));

            sensorOutputs[1].setText("Ultrasonic Value: "  + String.format("%5d", (int)(UltraDistance.getDouble(0.0))) + "   ");
            System.out.println("DISTANCE ULTRA: " + UltraDistance.getDouble(0.0));

            sensorOutputs[2].setText("Left Speed: " + String.format("%5d", (int)(LeftSpeed.getDouble(0.0))) + "  ");
            System.out.println("Left Speed: " + LeftSpeed.getDouble(0.0));
            sensorOutputs[3].setText("Right Speed: " + String.format("%5d", (int)(RightSpeed.getDouble(0.0))) + "  ");
            System.out.println("Right Speed: " + RightSpeed.getDouble(0.0));

        //ARMPOSITIONS
            armPositions[8].setText("Vision Input: " + String.format("%2d", (int)VisionInput.getDouble(0.0)));
            System.out.println("VISION INPUT: " + VisionInput.getDouble(0.0));

            System.out.println("ARMSETPOINT: " + ArmSetpoint.getString("NONE"));
        
        //DIAGNOSTICS
            diagnostics[0].setText("ArmPot: " + String.format("%2d", (int)ArmPot.getDouble(0.0)) + " WristPot: " + (int)(WristPot.getDouble(0.0)));
            System.out.println("ARMPOT: " + ArmPot.getDouble(0.0));
            System.out.println("WRISTPOT: " + WristPot.getDouble(0.0)); 

            diagnostics[1].setText("PDP: " + String.format("%2d", (int)(Current.getDouble(0.0))) + " amp(s) " + "Temp: " + (int)(Temperature.getDouble(0.0)) + " C ");
            System.out.println("CURRENT: " + Current.getDouble(0.0));
            System.out.println("TEMPERATURE: " + Temperature.getDouble(0.0));
            
            diagnostics[2].setText("Wrist Amperage: " + String.format("%2d", (int)WristAmp.getDouble(0.0)));
            System.out.println("WRIST AMP: " + WristAmp.getDouble(0.0));

            diagnostics[3].setText("Intake Amperage: " + String.format("%2d", (int)IntakeAmp.getDouble(0.0)));
            System.out.println("INTAKE AMP: " + IntakeAmp.getDouble(0.0));
            
            diagnostics[4].setText("Arm Amperage: " + String.format("%2d", (int)ArmAmp.getDouble(0.0)));
            System.out.println("ARM AMP: " + ArmAmp.getDouble(0.0));

            diagnostics[5].setText("Clutch: " + ClutchCompressor.getBoolean(false) + " Boost: " + BoostCompressor.getBoolean(false)); //false is off
            System.out.println("CLUTCH COMPRESSOR: " + ClutchCompressor.getBoolean(false));

            
            //potentiometerValue(ArmPot.getDouble(0.0), WristPot.getDouble(0.0)); WHY IS IT BOOLEAN????!
        
            positionDetector(ArmSetpoint.getString("NONE"));
            wristAmpValue(WristAmp.getDouble(0.0));
            pdpValue(Current.getDouble(0.0));
            intakeAmpValue(IntakeAmp.getDouble(0.0));

            //logMessage("ArmSetpoint: " + ArmSetpoint.getString("None"));
        }
        }


    public static void main(String args[]) {

        CustomDashboard cd = new CustomDashboard();
        //cd.logMessage("test1");
        //cd.logMessage("test2");
        //cd.changePosition(0);
        //cd.potentiometerValue(false);
        cd.run();
    }

}