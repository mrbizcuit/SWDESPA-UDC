/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.ClientController;
import CONTROLLER.DoctorController;
import CONTROLLER.ModuleController;
import MODEL.Appointment;
import MODEL.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class ScheduleItem extends JPanel {

    private ModuleController controller;

    //DOCTOR
    private JButton btnDelete, btnEdit;
    private String date, doctor;
    private Appointment app;

    private JLabel time;
    private JLabel name;

    public ScheduleItem() {
        time = new JLabel();
        name = new JLabel();

        btnDelete = new JButton();
        btnDelete.addActionListener(new btnDelete_Action());
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/btnDelete.png")));
            btnDelete.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }
        btnEdit = new JButton();
        btnEdit.addActionListener(new btnEdit_Action());
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/btnEdit.png")));
            btnEdit.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }

        this.add(time);
        this.add(name);

        setPreferredSize(new Dimension(260, 45));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
    }

    // CLIENT
    public ScheduleItem(ModuleController c, String time, Appointment app, User user) {
        this();
        this.controller = c;
        this.app = app;
        this.setBackground(Color.white);

        if (app.getTaken().equals(user.getLastname())) {
            this.setBackground(new Color(186, 255, 133));
            this.name.setText("Dr. " + app.getName());
            this.add(btnDelete);
            this.add(btnEdit);
        }

        this.time.setText(time);
    }
    // CLIENT ENDS HERE

    // DOCTOR
    public ScheduleItem(ModuleController c, String time, List<Appointment> apps, User user) {
        this();
        this.controller = c;
        this.setBackground(Color.white);

        if (apps.size() == 1 && apps.get(0).getName().equals(user.getLastname())) {
            this.app = apps.get(0);
            if (apps.get(0).getTaken().equals("NOT_TAKEN")) {
                this.setBackground(new Color(186, 255, 133));
                this.name.setText("FREE SLOT");
                this.add(btnDelete);
                this.add(btnEdit);

            } else {
                this.setBackground(new Color(186, 255, 133).darker());
                this.name.setText("APPOINTMENT W/ " + apps.get(0).getTaken());
            }
        } else if (apps.size() == 1 && !apps.get(0).getName().equals(user.getLastname())) {
            this.setBackground(Color.LIGHT_GRAY);
            this.name.setText("SLOT TAKEN BY Dr. " + apps.get(0).getName());
        } else if (apps.size() > 1) {
            this.setBackground(new Color(151, 207, 83));
            for (int i = 0; i < apps.size(); i++) {
                if (apps.get(i).getName().equals(user.getLastname())) {
                    this.app = apps.get(i);
                    if (apps.get(i).getTaken().equals("NOT_TAKEN")) {
                        this.name.setText("FREE SLOT");
                        this.add(btnDelete);
                        this.add(btnEdit);
                    } else {
                        this.setBackground(new Color(151, 207, 83).darker());
                        this.name.setText("APPOINTMENT W/ " + apps.get(i).getTaken());
                    }
                }
            }
        }
        this.time.setText(time);
    }
    // DOCTOR ENDS HERE

    // SECRETARY
    public ScheduleItem(ModuleController c, String time, boolean first, Appointment app, String type) {
        this();
        System.out.println(type);
        this.controller = c;
        if (first) {
            System.out.println("First");

            if (type.equals("doctor")) {
                System.out.println("ENTERED DOCTOR");
                this.name.setText("Dr. " + app.getName());
            } else if (type.equals("client")) {
                System.out.println("ENTERED TAKEN");
                this.name.setText("Taken");
            }
        } else {
            this.name.setText("");
        }

        if (type.equals("doctor")) {
            this.setBackground(new Color(186, 255, 133));
        } else if (type.equals("client")) {
            this.setBackground(Color.RED);
        } else {
            this.setBackground(Color.white);
        }

        this.time.setText(time);
    }
    // SECRETARY ENDS HERE

    // GENERIC SLOT
    public ScheduleItem(ModuleController c, String time) {
        this();
        this.controller = c;
        this.setBackground(Color.white);
        this.time.setText(time);
        this.name.setText("");
    }

    class btnEdit_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller instanceof DoctorController) {
                ((DoctorController) controller).edit(time.getText());
            } else if (controller instanceof ClientController) {
                ((ClientController) controller).edit(time.getText());
            }

        }
    }

    class btnDelete_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            int sTime = Integer.valueOf(time.getText().replace(":", ""));
//            int eTime = sTime + 30;
//            if (eTime % 100 >= 60) {
//                eTime = ((eTime / 100) + 1) * 100;
//            }
//            if (eTime / 100 >= 24) {
//                eTime = 0;
//            }
            if (controller instanceof DoctorController) {
                ((DoctorController) controller).deleteAppointment(app);
            } else if (controller instanceof ClientController) {
                ((ClientController) controller).deleteAppointment(app);
            }
        }
    }
}
