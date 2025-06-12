/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.computerprogramming2.motorphpayrollsystem;

/**
 *
 * @author Mssy
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PayrollGUI extends JFrame {

    private JTextField employeeIdField;
    private JTextArea outputArea;
    private JButton searchButton;
    private JButton calculateButton;
    private Map<String, EmployeeDetails> employees;

    public PayrollGUI(Map<String, EmployeeDetails> employees) {
        this.employees = employees;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("MOTORPH Payroll System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter Employee ID:");
        employeeIdField = new JTextField(15);
        searchButton = new JButton("Search Employee");
        calculateButton = new JButton("Calculate Payroll");

        topPanel.add(label);
        topPanel.add(employeeIdField);
        topPanel.add(searchButton);
        topPanel.add(calculateButton);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add to Frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button Listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayEmployeeDetails();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPayroll();
            }
        });

        setVisible(true);
    }

    private void displayEmployeeDetails() {
        String empId = employeeIdField.getText().trim();
        EmployeeDetails emp = employees.get(empId);

        if (emp != null) {
            outputArea.setText("=== Employee Info ===\n");
            outputArea.append("Employee ID: " + emp.getEmployeeNumber() + "\n");
            outputArea.append("Name       : " + emp.getLastName() + ", " + emp.getFirstName() + "\n");
            outputArea.append("Birthday   : " + emp.getBirthday() + "\n");
            outputArea.append("Salary     : " + emp.getMonthlySalary() + "\n");
            outputArea.append("Hourly Rate: " + emp.getHourlyRate() + "\n");
        } else {
            outputArea.setText("Employee not found.");
        }
    }

    private void displayPayroll() {
        String empId = employeeIdField.getText().trim();
        EmployeeDetails emp = employees.get(empId);

        if (emp == null) {
            outputArea.setText("Employee not found.");
            return;
        }

        // Simplified calculation (as example)
        double normalHours = 160;  // Assuming full time 40hrs/week
        double overtimeHours = 10; // Example

        double grossPay = (normalHours * emp.getHourlyRate()) + (overtimeHours * emp.getHourlyRate() * 1.25);
        double benefits = emp.getRiceSubsidy() + emp.getPhoneAllowance() + emp.getClothingAllowance();
        double totalDeductions = grossPay * 0.15; // Assume 15% deductions for demo
        double netPay = grossPay + benefits - totalDeductions;

        outputArea.append("\n\n=== Payroll Calculation ===\n");
        outputArea.append(String.format("Gross Pay        : %.2f%n", grossPay));
        outputArea.append(String.format("Benefits         : %.2f%n", benefits));
        outputArea.append(String.format("Deductions       : %.2f%n", totalDeductions));
        outputArea.append(String.format("Net Pay          : %.2f%n", netPay));
    }
}
