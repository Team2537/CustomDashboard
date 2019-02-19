/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc;

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
        
        
        
        sensorOutputs = new JLabel[2];
        sensorOutputs[0] = new JLabel("Encoder Value: " + encoderValue);
        sensorOutputs[1] = new JLabel("Ultrasonic Value: " + ultrasonicValue);
        


        armPositions = new JLabel[9];
        armPositions[0] = new JLabel("3 Ball Reversed");
        armPositions[1] = new JLabel("3 Hatch Reversed");
        armPositions[2] = new JLabel("3 Ball Standard");
        armPositions[3] = new JLabel("3 Hatch Standard");
        armPositions[4] = new JLabel("2 Ball Standard");
        armPositions[5] = new JLabel("2 Hatch Standard");
        armPositions[6] = new JLabel("1 Ball Standard");
        armPositions[7] = new JLabel("1 Hatch Standard");
        armPositions[8] = new JLabel("0 (Intake Mode)");

        diagnostics = new JLabel[6];
        diagnostics[0] = new JLabel("Potentiometer");
        diagnostics[1] = new JLabel("PDP");
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


    public void potentiometerValue(boolean on){
        if(on == true){
            diagnostics[0].setBackground(Color.GREEN);
            diagnostics[0].setForeground(Color.BLACK);
        }else{
            diagnostics[0].setBackground(Color.RED);
            diagnostics[0].setForeground(Color.WHITE);
        }
    }

    public void pdpValue(boolean toomuch){
        if( 1 > pdpCurrent || pdpCurrent > 4){
            diagnostics[1].setBackground(Color.RED);
        }else{
            diagnostics[1].setBackground(Color.GREEN);
        }

    }

    public void wristValue(boolean toomuch){

        if(wristamperage >= 2){
            diagnostics[2].setBackground(Color.RED);
        }else{
            diagnostics[2].setBackground(Color.GREEN);
        }

    }

    public void intakeValue(boolean toomuch){
        if(intakeamperage >= 2){
            diagnostics[3].setBackground(Color.RED);
        }else{
            diagnostics[3].setBackground(Color.GREEN);
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
            }
        });


    }


    // positions count down from top to bottom, like any standard rows

    public static void main(String args[]) {
        CustomDashboard cd = new CustomDashboard();
        cd.logMessage("test1");
        cd.logMessage("test2");
        cd.changePosition(4);
        cd.potentiometerValue(false);
    }

}