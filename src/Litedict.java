package src;
/*
* File: Litedict.java
* Author: Sallai András
* Copyright: 2014, Nagy József 
* Date: 2014-12-04
* Web: https://szit.hu
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details:
* http://www.gnu.org/licenses/gpl.html
*
*/

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

class Litedict extends JFrame
{
	DefaultListModel<String> wordModel = new DefaultListModel<>();
	JList wordList = new JList(wordModel);
	JScrollPane wordScrollPane = new JScrollPane(wordList);
	JTextField wordTextField = new JTextField(20);
	JPanel mainPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JButton addButton = new JButton("Hozzáad");
	JButton toEditButton = new JButton("Szerkesztésre");
	JButton modifyButton = new JButton("Módosít");
	JButton delButton = new JButton("Töröl");
	boolean editing = false;
	
	Litedict()
	{		
		wordModel.addElement("körte");
		wordModel.addElement("galagonya");
		wordModel.addElement("alma");
		wordModel.addElement("málna");
		wordModel.addElement("barack");
		wordModel.addElement("áfonya");
		wordModel.addElement("szőlő");
		wordModel.addElement("som");
		wordModel.addElement("meggy");
		wordModel.addElement("szilva");
		
		mainPanel.setLayout(new BoxLayout(
			mainPanel, BoxLayout.Y_AXIS
		));
		mainPanel.add(wordScrollPane);
		mainPanel.add(wordTextField);
		mainPanel.add(buttonPanel);
		
		buttonPanel.setLayout(new BoxLayout(
			buttonPanel, BoxLayout.X_AXIS
		));
		buttonPanel.add(addButton);
		buttonPanel.add(toEditButton);
		buttonPanel.add(modifyButton);
		buttonPanel.add(delButton);	

		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String str = wordTextField.getText();
				if(str.isEmpty())
				{
					JOptionPane.showMessageDialog(getContentPane(),
						"Üres elemet próbáltál meg hozzáadni",
						"Üres elem", JOptionPane.CANCEL_OPTION);
					return;
				}
				wordModel.addElement(str);
				wordTextField.setText("");
				
				int index = wordModel.getSize()-1;
				wordList.ensureIndexIsVisible(index);
			}
		});
		toEditButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(editing)
				{
					JOptionPane.showMessageDialog(getContentPane(),
						"Egy szöveg szerkesztés alatt van!",
						"Szerkesztés", JOptionPane.CANCEL_OPTION);
					return;
					
				}
				if(wordList.isSelectionEmpty())
				{
					JOptionPane.showMessageDialog(getContentPane(),
						"Nincs kiválasztva elem",
						"Kiválasztás hiánya", JOptionPane.CANCEL_OPTION);
					return;					
				}
				wordList.setEnabled(false);
				addButton.setEnabled(false);
				delButton.setEnabled(false);
				editing = true;
				int index = wordList.getSelectedIndex();
				String str = (String) wordModel.getElementAt(index);
				wordTextField.setText(str);
			}
		});
		modifyButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!editing)
				{
					JOptionPane.showMessageDialog(getContentPane(),
						"Nincs szerkesztés!",
						"Modosítás", JOptionPane.CANCEL_OPTION);
					return;					
				}
				editing = false;
				int index = wordList.getSelectedIndex();
				String str = wordTextField.getText();
				wordModel.setElementAt(str, index);
				wordTextField.setText("");
				wordList.setEnabled(true);
				addButton.setEnabled(true);
				delButton.setEnabled(true);
			}
		});
		delButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int index = wordList.getSelectedIndex();
				wordModel.remove(index);
			}
		});
		
		setTitle("Szótár");
		wordScrollPane.setBorder(new TitledBorder("Szavak"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(mainPanel);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Litedict();
	}
}
