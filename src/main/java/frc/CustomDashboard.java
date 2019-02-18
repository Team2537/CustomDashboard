/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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


/**
 * Add your docs here.
 */
public class CustomDashboard {

    private JFrame logFrame, driveFrame;
    private JMenuBar mb;
    private JTextArea ta;
    
    public CustomDashboard(){
        //Creating the Frame
        logFrame = new JFrame("Changelog Frame");
        driveFrame = new JFrame("Ultra Gooey");
        
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
        JPanel statusPanel = new JPanel();
        JPanel pathPanel = new JPanel();

        driveFrame.getContentPane().add(BorderLayout.CENTER, positionPanel);
        driveFrame.getContentPane().add(BorderLayout.EAST, armPanel);
        driveFrame.getContentPane().add(BorderLayout.SOUTH, statusPanel);
        positionPanel.add(pathPanel);
        positionPanel.add(cameraPanel);
        
        
        armPanel.add(new JLabel("4B"));
        armPanel.add(new JLabel("4H"));
        

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

    public static void main(String args[]) {
        CustomDashboard cd = new CustomDashboard();
        cd.logMessage("test1");
        cd.logMessage("test2");
    }

}