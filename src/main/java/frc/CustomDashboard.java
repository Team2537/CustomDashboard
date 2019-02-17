/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.net.URL;


import javax.swing.ImageIcon;
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

    private JFrame frame, frame1, frame2;
    private JMenuBar mb;
    private JTextArea ta;
    private JTextArea ta1;

    public CustomDashboard(){
        //Creating the Frame
        frame = new JFrame("Changelog Frame");
        frame1 = new JFrame("Drive Frame");
        frame2 = new JFrame("Cat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame1.setSize(400,400);
        frame2.setSize(400,400);


        //Creating the MenuBar and adding components
        menuBarCreation();

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JTextField tf = new JTextField(300);
        panel.add(tf);

        ta1 = new JTextArea();
        ta1.setLineWrap(true);
        ta1.setWrapStyleWord(true);
        ta1.setEditable(true);
        panel.add(ta1);
        ta1.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent event){

            }

            @Override
            public void keyReleased(KeyEvent arg0) {

            }

            @Override
            public void keyTyped(KeyEvent arg0) {

            }


        });



        // Text Area at the Center
        ta = new JTextArea();
        JScrollPane scroll = new JScrollPane(ta);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(375, 25, 10, 1000); 
        panel.add(scroll);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setEditable(false);
        
        
        // adding an image for fun
        URL img = null;
        try {
            img = new URL("https://pbs.twimg.com/profile_images/981578296071720961/yLO6qr2Z_400x400.jpg");
            
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        finally{
            ImageIcon image = new ImageIcon(img);
            JLabel label = new JLabel("", image, JLabel.CENTER);
            JPanel panel2 = new JPanel(new BorderLayout());
            panel2.add( label, BorderLayout.CENTER );
            frame2.getContentPane().add(BorderLayout.CENTER, panel2);
        }

        // Adding Components to the frame.
        frame.getContentPane().add(scroll);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, ta1);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.setVisible(true);
        frame1.setVisible(false);
        frame2.setVisible(false);
    }

    public void logMessage(String msg){
        ta.append(msg + "\n");
    }
    
    public void driveMode(){
        frame1.setVisible(true);
        frame.setVisible(false);
        frame2.setVisible(false);

        menuBarCreation();
    }

    public void logMode(){
        frame1.setVisible(false);
        frame.setVisible(true);
        frame2.setVisible(false);

       menuBarCreation();
    }

    public void sickoMode(){
        frame2.setVisible(true);
        frame1.setVisible(false);
        frame.setVisible(false);

        menuBarCreation();
    }

    public void menuBarCreation(){
        mb = new JMenuBar();
        JMenu m1 = new JMenu("Mode");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Drive Mode");
        JMenuItem m22 = new JMenuItem("Changelog Mode");
        JMenuItem m33 = new JMenuItem("Emotional Support Cat");
        m1.add(m11);
        m1.add(m22);
        m2.add(m33);
        frame1.getContentPane().add(BorderLayout.NORTH, mb);
     
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
        m33.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                sickoMode();   
            }
        });

    }

    public static void main(String args[]) {
        CustomDashboard cd = new CustomDashboard();
        cd.logMessage("test1");
        cd.logMessage("test2");
    }

}