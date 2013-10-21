package com.gazman.city_map.out;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.gazman.city_map.city.City;

/**
 * @author Ilya Gazman
 *
 */
public class OutputView extends JPanel {

	private static final long serialVersionUID = 1400121054898423919L;
	private JTextField textField;
	private JTextArea textArea;
	public int numberOfCities = -1;
	public Runnable startCallback;
	public String cities;

	private ActionListener textChangeHandler = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent event){
			String text = textField.getText();
			try {
				numberOfCities = Integer.parseInt(text);
			}
			catch (Exception exception) {
				cities = text;
			}
			startCallback.run();
		}
	};

	public ArrayList<City> citiesList;

	public OutputView(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {
				0, 0
		};
		gridBagLayout.rowHeights = new int[] {
				0, 0, 0, 0, 0
		};
		gridBagLayout.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gridBagLayout.rowWeights = new double[] {
				0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE
		};
		setLayout(gridBagLayout);

		JButton btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 5, 0);
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 0;
		add(btnStart, gbc_btnStart);
		btnStart.addActionListener(textChangeHandler);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(10);
		textField.addActionListener(textChangeHandler);
		textField.setText("20");

		textArea = new JTextArea();
		textArea.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 3;
		add(textArea, gbc_textArea);

	}

	public void print(String message){
		textArea.append(message);
		textArea.append("\n");
	}

}
