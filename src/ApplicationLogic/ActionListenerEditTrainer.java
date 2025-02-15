package app.ApplicationLogic;

import app.Entity.Trainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerEditTrainer implements ActionListener {
    private Trainer trainer;

    public ActionListenerEditTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Name: ");
        JLabel addressLabel = new JLabel("Address: ");
        JLabel emailLabel = new JLabel("Email: ");
        JLabel phoneLabel = new JLabel("Phone: ");
        JTextField nameField = new JTextField(trainer.getName());
        JTextField addressField = new JTextField(trainer.getAddress());
        JTextField emailField = new JTextField(trainer.getEmail());
        JTextField phoneField = new JTextField(trainer.getPhone());

        panel.add(nameLabel); panel.add(nameField);
        panel.add(addressLabel); panel.add(addressField);
        panel.add(emailLabel); panel.add(emailField);
        panel.add(phoneLabel); panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Trainer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            trainer.setName(nameField.getText());
            trainer.setAddress(addressField.getText());
            trainer.setEmail(emailField.getText());
            trainer.setPhone(phoneField.getText());
            JOptionPane.showMessageDialog(null, "수정되었습니다.");
        }
    }
}
