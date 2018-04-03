/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import MODEL.User;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author ianona
 */
public class ClientBuilder extends Builder {

    @Override
    public void buildIcon() {
        try {
            ImageIcon imgicon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/client.png")));
            icon = new JLabel (new ImageIcon(imgicon.getImage().getScaledInstance(120,120, Image.SCALE_DEFAULT)));
        } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
        }
        mainPane.add(icon);
        icon.setBounds(10,10,100,100);
    }

    @Override
    public void buildCustom(User user) {
        System.out.println("Building custom CLIENT components...");
        module.setTitle("Client Module - Mr./Ms. " + user.getFirstname());
        
    }
    
}
