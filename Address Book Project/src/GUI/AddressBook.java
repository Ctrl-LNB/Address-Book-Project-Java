package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

//Background Image
class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel() {
        try {
            backgroundImage = ImageIO.read(new File("src\\GUI\\Background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

class Person {
    String name;
    String phone;
    String address;

    Person(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String toString() {
        return "Name: " + name + "\nPhone: " + phone + "\nAddress: " + address;
    }
}

class Book {
    JFrame frame;
    JTextField nameField, phoneField, addressField;
    ArrayList<Person> people = new ArrayList<>();

    public Book() {
        frame = new JFrame("Address Book");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Background Panel
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        frame.setContentPane(backgroundPanel);

        // Title
        JLabel titleLabel = new JLabel("Address Book");
        titleLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backgroundPanel.add(titleLabel);

        backgroundPanel.add(Box.createVerticalStrut(20)); // Space after title
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setOpaque(false);
        formPanel.setMaximumSize(new Dimension(400, 120));

        // Name label
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setForeground(Color.BLACK);
        formPanel.add(nameLabel);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(300, 30));
        formPanel.add(nameField);

        //Phone Label
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 20));
        phoneLabel.setForeground(Color.BLACK);
        formPanel.add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(300, 30));
        formPanel.add(phoneField);

        //Address label
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addressLabel.setForeground(Color.BLACK);
        formPanel.add(addressLabel);
        addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(300, 30));
        formPanel.add(addressField);


        backgroundPanel.add(formPanel);

        // Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");
        JButton searchBtn = new JButton("Search");
        JButton listBtn = new JButton("List");
        JButton clearBtn = new JButton("Clear");

        backgroundPanel.add(Box.createVerticalStrut(30));

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(listBtn);
        buttonPanel.add(clearBtn);

        backgroundPanel.add(buttonPanel);

        // Button actions
        addBtn.addActionListener(e -> addPerson());
        deleteBtn.addActionListener(e -> deletePerson());
        searchBtn.addActionListener(e -> searchPerson());
        listBtn.addActionListener(e -> listPeople());
        clearBtn.addActionListener(e -> clearFields());

        frame.setVisible(true);
    }

    void addPerson() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields.");
            return;
        }
        people.add(new Person(name, phone, address));
        JOptionPane.showMessageDialog(frame, "Person added.");
        clearFields();
    }

    void deletePerson() {
        String name = nameField.getText();
        boolean found = false;
        for (Person p : people) {
            if (p.name.equalsIgnoreCase(name)) {
                people.remove(p);
                JOptionPane.showMessageDialog(frame, "Person deleted.");
                found = true;
                break;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(frame, "Person not found.");
        }
        clearFields();
    }

    void searchPerson() {
        String name = nameField.getText();
        for (Person p : people) {
            if (p.name.equalsIgnoreCase(name)) {
                String result = "Name: " + p.name + "\nPhone: " + p.phone + "\nAddress: " + p.address;
                JOptionPane.showMessageDialog(frame, result);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Person not found.");
    }

    void listPeople() {
        if (people.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No entries found.");
            return;
        }
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < people.size(); i++) {
            names.append((i + 1)).append(". ").append(people.get(i).name).append("\n");
        }
        JOptionPane.showMessageDialog(frame, names.toString(), "All Names", JOptionPane.INFORMATION_MESSAGE);
    }

    void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }
}

// Main Class
public class AddressBook {
    public static void main(String[] args) {
        new Book();
    }
}