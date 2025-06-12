/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.computerprogramming2.motorphpayrollsystem;

/**
 *
 * @author Mssy
 * 
 **/

import com.computerprogramming2.motorph.Deductions.MonthlyDeductionCalculator;
import com.computerprogramming2.motorph.data.DataLoader;
import com.computerprogramming2.motorph.summary.MonthlyTotals;
import com.computerprogramming2.motorph.summary.WeeklyTotals;
import com.computerprogramming2.motorph.attendance.WeeklyRecordsProcessor;

import javax.swing.*;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PayrollSystem serves as the entry point for the payroll application.
 * It loads data from CSV files, processes attendance records, computes deductions,
 * and launches the payroll GUI.
 */
public class PayrollSystem {

    private static final Logger logger = Logger.getLogger(PayrollSystem.class.getName());

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.ALL);
        logger.info("Payroll System started");

        try {
            // Load data from CSV files
            DataLoader dataLoader = new DataLoader();
            dataLoader.loadAllData();
            Map<String, EmployeeDetails> employees = dataLoader.getEmployees();
            var attendanceRecords = dataLoader.getAttendanceRecords();
            logger.info("Data successfully loaded");

            // Set financial brackets in helper
            PayrollSystemHelper.setSssBrackets(dataLoader.getSssBrackets());
            PayrollSystemHelper.setPhilHealthBrackets(dataLoader.getPhilHealthBrackets());
            PayrollSystemHelper.setPagIbigBrackets(dataLoader.getPagIbigBrackets());
            PayrollSystemHelper.setTaxBrackets(dataLoader.getTaxBrackets());

            // Calculate deductions and weekly data (can be passed to GUI if needed later)
            Map<String, Map<String, MonthlyTotals>> monthlyDeductionMap =
                    MonthlyDeductionCalculator.calculateMonthlyDeductions(attendanceRecords);
            Map<String, Map<String, WeeklyTotals>> weeklyData =
                    WeeklyRecordsProcessor.processWeeklyRecords(attendanceRecords);

            logger.info("Deductions and weekly data processed");

            // Launch GUI
            SwingUtilities.invokeLater(() -> new PayrollGUI(employees));

        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred during data loading or processing", e);
            System.exit(1);
        }
    }
}