package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintButton extends JButton implements ActionListener {

    /**
     * Panel to print for
     */
    private Panel p;

    /**
     * Creates a button with text "Print" on it
     *
     * @param p Panel to work with
     */
    public PrintButton(Panel p){
        super("Print");
        this.p = p;
        this.addActionListener(this);
    }


    /**
     * On click shows dialog for printing
     *
     * @param e button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        if(pj.printDialog()){
            pj.setPrintable(p);
            try {
                pj.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }


}
