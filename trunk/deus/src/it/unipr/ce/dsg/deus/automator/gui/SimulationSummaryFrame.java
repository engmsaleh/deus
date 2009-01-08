/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SimulationSummaryFrame.java
 *
 * Created on 8-gen-2009, 18.51.25
 */

package it.unipr.ce.dsg.deus.automator.gui;

import java.awt.event.ActionEvent;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author picone
 */
public class SimulationSummaryFrame extends javax.swing.JFrame {

	private boolean isStart = false;
	private boolean isClose = false;
	
    /** Creates new form SimulationSummaryFrame */
    public SimulationSummaryFrame() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

    	this.setTitle("Deus Automator - Simualtion Summary");
    	
    	try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
        dsgLogoLabel = new javax.swing.JLabel();
        simulationSummaryScrollPane = new javax.swing.JScrollPane();
        simulationSummaryTextArea = new javax.swing.JTextArea();
        startButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        dsgLogoLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        dsgLogoLabel.setForeground(new java.awt.Color(204, 0, 0));
        dsgLogoLabel.setIcon(new javax.swing.ImageIcon(("res/dsgLogo_noBack_small.png"))); // NOI18N
        dsgLogoLabel.setText(" - Deus Automator - Simulation Summary");

        simulationSummaryTextArea.setBackground(new java.awt.Color(0, 0, 0));
        simulationSummaryTextArea.setColumns(20);
        simulationSummaryTextArea.setEditable(false);
        simulationSummaryTextArea.setForeground(new java.awt.Color(255, 255, 255));
        simulationSummaryTextArea.setRows(5);
        simulationSummaryScrollPane.setViewportView(simulationSummaryTextArea);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(closeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(startButton))
                    .addComponent(simulationSummaryScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                    .addComponent(dsgLogoLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dsgLogoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(simulationSummaryScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    protected void closeButtonActionPerformed(ActionEvent evt) {
		this.isClose = true;
	}

	protected void startButtonActionPerformed(ActionEvent evt) {
		this.isStart = true;
	}

	/**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimulationSummaryFrame().setVisible(true);
            }
        });
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel dsgLogoLabel;
    private javax.swing.JScrollPane simulationSummaryScrollPane;
    private javax.swing.JTextArea simulationSummaryTextArea;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	public javax.swing.JTextArea getSimulationSummaryTextArea() {
		return simulationSummaryTextArea;
	}

	public void setSimulationSummaryTextArea(
			javax.swing.JTextArea simulationSummaryTextArea) {
		this.simulationSummaryTextArea = simulationSummaryTextArea;
	}

}
