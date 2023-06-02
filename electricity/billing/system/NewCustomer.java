package electricity.billing.system;

import javax.swing.*;
import java.util.regex.*;  
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class NewCustomer extends JFrame implements ActionListener{

    JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
    int flag = 0;
    JButton next, cancel;
    JLabel lblmeter;
    NewCustomer() {
        setSize(700, 500);
        setLocation(400, 200);
        
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        add(p);
        
        JLabel heading = new JLabel("New Customer");
        heading.setBounds(180, 10, 200, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);
        
        JLabel lblname = new JLabel("Customer Name");
        lblname.setBounds(100, 80, 100, 20);
        p.add(lblname);
        
        tfname = new JTextField();
        tfname.setBounds(240, 80, 200, 20);
        p.add(tfname);
        
        JLabel lblmeterno = new JLabel("Meter Number");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);
        
        lblmeter = new JLabel("");
        lblmeter.setBounds(240, 120, 100, 20);
        p.add(lblmeter);
        
        Random ran = new Random();
        long number = ran.nextLong() % 1000000;
        lblmeter.setText("" + Math.abs(number));
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(100, 160, 100, 20);
        p.add(lbladdress);
        
        tfaddress = new JTextField();
        tfaddress.setBounds(240, 160, 200, 20);
        p.add(tfaddress);
        
        JLabel lblcity = new JLabel("City");
        lblcity.setBounds(100, 200, 100, 20);
        p.add(lblcity);
        
        tfcity = new JTextField();
        tfcity.setBounds(240, 200, 200, 20);
        p.add(tfcity);
        
        JLabel lblstate = new JLabel("State");
        lblstate.setBounds(100, 240, 100, 20);
        p.add(lblstate);
        
        tfstate = new JTextField();
        tfstate.setBounds(240, 240, 200, 20);
        p.add(tfstate);
        
        JLabel lblemail = new JLabel("Email");
        lblemail.setBounds(100, 280, 100, 20);
        p.add(lblemail);
        
        tfemail = new JTextField();
        tfemail.setBounds(240, 280, 200, 20);
        p.add(tfemail);
        
        JLabel lblphone = new JLabel("Phone Number");
        lblphone.setBounds(100, 320, 100, 20);
        p.add(lblphone);
        
        tfphone = new JTextField();
        tfphone.setBounds(240, 320, 200, 20);
        p.add(tfphone);
        
        next = new JButton("Add");
        next.setBounds(120, 390, 100,25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);
        
        cancel = new JButton("Cancel");
        cancel.setBounds(250, 390, 100,25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        p.add(cancel);
        
        setLayout(new BorderLayout());
        
        add(p, "Center");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");
        
        getContentPane().setBackground(Color.WHITE);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            String meter = lblmeter.getText();
            String email = tfemail.getText();
            String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";  
            Pattern pattern = Pattern.compile(regex);  
            Matcher matcher = pattern.matcher(email);
            String city = tfcity.getText();
            String state = tfstate.getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String regex2 = "^[0-9]{10}";
//            Pattern pattern2 = Pattern.compile(regex2);
//            Matcher matcher2 = pattern.matcher(phone);
            String name = tfname.getText();
            if(name.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Name");
                flag = 1;
            } else if(address.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Address");
                flag = 1;
            } else if(city.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter City");
                flag = 1;
            } else if(state.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter State");
                flag = 1;
            } else if(!matcher.matches()) {
                JOptionPane.showMessageDialog(null, "Please Enter Valid Email");
                flag = 1;
            } else if(phone.length()>10 || phone.length()<10) {
                JOptionPane.showMessageDialog(null, "Please Enter Valid Phone Number");
                flag = 1;
                
            } else {
                flag = 0;
            }
            
          
            String query1 = "insert into customer values('"+name+"', '"+meter+"', '"+address+"', '"+city+"', '"+state+"', '"+email+"', '"+phone+"')";
            //String query1 = "insert into customer values('"+name+"', '"+meter+"', '"+address+"', '"+city+"', '"+state+"', '"+email+"', '"+phone+"')";
            String query2 = "insert into login values('"+meter+"', '', '"+name+"', '', '')";
            if(flag == 0) {
            try {
                Conn c = new Conn();
                c.s.executeUpdate(query1);
                c.s.executeUpdate(query2);
                
                JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
                setVisible(false);
                
                // new frame
                new MeterInfo(meter);
            } catch (Exception e) {
                e.printStackTrace();
            }
          }
        } else {
            setVisible(false);
        }
    }
    
    public static void main(String[] args) {
        new NewCustomer();
    }
}
