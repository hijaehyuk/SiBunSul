package app.ApplicationLogic;

import app.Entity.Member;
import app.Entity.PTrecord;
import app.Entity.Trainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ActionListenerViewMemberList implements ActionListener {
	private JPanel mainPanel;
	private JPanel subPanel;
	private JPanel memberPanel;
	private JPanel buttonPanel;
	private JButton nextButton;
	private JButton previousButton;
	private ArrayList<Member> memberList;
	private ArrayList<Trainer> trainerList;
	private int memberIndex;
	private int ptIndex;

	public ActionListenerViewMemberList(JPanel panel, ArrayList<Member> memberList, ArrayList<Trainer> trainerList){
		this.mainPanel = panel;
		this.memberList = memberList;
		this.trainerList = trainerList;
		this.memberIndex = 0;
		this.ptIndex = 0;

		subPanel = new JPanel();
		memberPanel = new JPanel();
		buttonPanel = new JPanel();

		subPanel.setLayout(new BorderLayout());
		subPanel.setBounds(0, 0, 500, 600);
		memberPanel.setLayout(new GridLayout(4,1));
		buttonPanel.setLayout(new GridLayout(1,2));

		nextButton = new JButton("Next");
		previousButton = new JButton("Previous");
		buttonPanel.add(previousButton);
		buttonPanel.add(nextButton);

		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(memberIndex + 4 >= memberList.size()){
					JOptionPane.showMessageDialog(null, "마지막 페이지입니다.");
					return;
				}
				memberIndex += 4;
				updateMemberList();
			}
		});

		previousButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(memberIndex - 4 < 0){
					JOptionPane.showMessageDialog(null, "첫 페이지입니다.");
					return;
				}
				memberIndex -= 4;
				updateMemberList();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if(memberList.size() == 0){
			JOptionPane.showMessageDialog(null, "등록된 회원이 없습니다.");
			return;
		}
		mainPanel.removeAll();
		mainPanel.add(subPanel);
		subPanel.add(memberPanel, BorderLayout.CENTER);

		updateMemberList();

		subPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private void updateMemberList() {
		memberPanel.removeAll();
		for(int i = memberIndex; i< memberIndex +4 && i<memberList.size(); i++){
			JPanel memberInfoLabel = new JPanel(new GridLayout(2, 1));
			Member member = memberList.get(i);
			JLabel memberLabel = new JLabel(member.toString());
			JPanel memberButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JButton ptRecordButton = new JButton("PT Record");
			JButton healthRecordButton = new JButton("Health Record");
			JButton deleteButton = new JButton("Delete");
			JButton editButton = new JButton("Edit");
			JButton setTrainerButton = new JButton("Set Trainer");

			memberButtonPanel.add(ptRecordButton);
			memberButtonPanel.add(healthRecordButton);
			memberButtonPanel.add(editButton);
			memberButtonPanel.add(deleteButton);
			healthRecordButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Health Record: \n" + member.getHealthRecord().toString());
				}
			});
			ptRecordButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					ArrayList<PTrecord> ptRecordList = member.getPtRecord();
					ptIndex = 0;
					if (ptRecordList.size() == 0) {
						JDialog emptyListDialog = new JDialog();
						JPanel emptyListPanel = new JPanel();
						JButton addButton = new JButton("Add PTrecord");

						addButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								JDialog addRecordDialog = new JDialog();
								JPanel addRecordPanel = new JPanel();
								JTextField dateTextField = new JTextField(10);
								JTextField memoTextField = new JTextField(20);
								JButton saveButton = new JButton("Save");

								saveButton.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										// Get the input values
										String date = dateTextField.getText();
										String memo = memoTextField.getText();

										String[] dateParts = date.split("/");
										if (dateParts.length != 3) {
											JOptionPane.showMessageDialog(null, "날짜는 반드시 년/월/일 형식을 따라야 합니다.");
											return;
										}
										String year = dateParts[0];
										String month = dateParts[1];
										String day = dateParts[2];

										PTrecord newRecord = new PTrecord(year, month, day, memo);
										ptRecordList.add(newRecord);
										addRecordDialog.dispose();
									}
								});
								addRecordPanel.add(new JLabel("Date:"));
								addRecordPanel.add(dateTextField);
								addRecordPanel.add(new JLabel("Memo:"));
								addRecordPanel.add(memoTextField);
								addRecordPanel.add(saveButton);

								addRecordDialog.add(addRecordPanel);
								addRecordDialog.setSize(300, 150);
								addRecordDialog.setVisible(true);
							}
						});
						emptyListPanel.add(new JLabel("No PTrecords available. What do you want to do?"));
						emptyListPanel.add(addButton);
						emptyListDialog.add(emptyListPanel);
						emptyListDialog.setSize(300, 100);
						emptyListDialog.setVisible(true);
						return;
					}
					JDialog dialog = new JDialog();
					JPanel ptRecordPanel = new JPanel();
					JPanel ptRecordButtonPanel = new JPanel();
					JLabel ptDateLabel = new JLabel(ptRecordList.get(ptIndex).getDate());
					JLabel ptMemoLabel = new JLabel(ptRecordList.get(ptIndex).getMemo());
					JButton nextButton = new JButton("Next");
					JButton previousButton = new JButton("Previous");
					JButton addButton = new JButton("Add");
					JButton editButton = new JButton("Edit");

					ptRecordPanel.setLayout(new BorderLayout());
					ptRecordButtonPanel.setLayout(new GridLayout(1, 2));
					nextButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							if(ptIndex + 1 >= ptRecordList.size()){
								JOptionPane.showMessageDialog(null, "마지막 페이지입니다.");
								return;
							}
							ptIndex += 1;
							ptDateLabel.setText(ptRecordList.get(ptIndex).getDate());
							ptMemoLabel.setText(ptRecordList.get(ptIndex).getMemo());
						}
					});
					previousButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							if(ptIndex - 1 < 0){
								JOptionPane.showMessageDialog(null, "첫 페이지입니다.");
								return;
							}
							ptIndex -= 1;
							ptDateLabel.setText(ptRecordList.get(ptIndex).toString());
							ptMemoLabel.setText(ptRecordList.get(ptIndex).getMemo());
						}
					});
					addButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JPanel inputPanel = new JPanel(new GridLayout(4, 2)); // 4행 2열의 그리드 레이아웃으로 패널 생성

							inputPanel.add(new JLabel("년도:"));
							JTextField yearField = new JTextField();
							inputPanel.add(yearField);

							inputPanel.add(new JLabel("월:"));
							JTextField monthField = new JTextField();
							inputPanel.add(monthField);

							inputPanel.add(new JLabel("일:"));
							JTextField dayField = new JTextField();
							inputPanel.add(dayField);

							inputPanel.add(new JLabel("메모:"));
							JTextField memoField = new JTextField();
							inputPanel.add(memoField);

							int result = JOptionPane.showConfirmDialog(null, inputPanel, "데이터 입력", JOptionPane.OK_CANCEL_OPTION);
							if (result == JOptionPane.OK_OPTION) {
								String newYear = yearField.getText();
								String newMonth = monthField.getText();
								String newDay = dayField.getText();
								String newMemo = memoField.getText();

								PTrecord newRecord = new PTrecord(newYear, newMonth, newDay, newMemo);
								ptRecordList.add(newRecord);

								ptIndex = ptRecordList.size() - 1;

								ptDateLabel.setText(ptRecordList.get(ptIndex).getDate());
								ptMemoLabel.setText(ptRecordList.get(ptIndex).getMemo());
							}
						}
					});
					editButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (ptIndex < 0 || ptIndex >= ptRecordList.size()) {
								JOptionPane.showMessageDialog(null, "편집할 데이터가 없습니다.");
								return;
							}

							JPanel inputPanel = new JPanel(new GridLayout(4, 2)); // 4행 2열의 그리드 레이아웃으로 패널 생성

							PTrecord selectedRecord = ptRecordList.get(ptIndex);
							JTextField yearField = new JTextField(selectedRecord.getYear());
							JTextField monthField = new JTextField(selectedRecord.getMonth());
							JTextField dayField = new JTextField(selectedRecord.getDay());
							String memo = selectedRecord.getMemo().replace("Memo: ", "");
							JTextField memoField = new JTextField(memo);

							inputPanel.add(new JLabel("년도:"));
							inputPanel.add(yearField);

							inputPanel.add(new JLabel("월:"));
							inputPanel.add(monthField);

							inputPanel.add(new JLabel("일:"));
							inputPanel.add(dayField);

							inputPanel.add(new JLabel("메모:"));
							inputPanel.add(memoField);

							int result = JOptionPane.showConfirmDialog(null, inputPanel, "데이터 편집", JOptionPane.OK_CANCEL_OPTION);
							if (result == JOptionPane.OK_OPTION) {
								String updatedYear = yearField.getText();
								String updatedMonth = monthField.getText();
								String updatedDay = dayField.getText();
								String updatedMemo = memoField.getText();

								PTrecord updatedRecord = new PTrecord(updatedYear, updatedMonth, updatedDay, updatedMemo);
								ptRecordList.set(ptIndex, updatedRecord);

								ptDateLabel.setText(ptRecordList.get(ptIndex).getDate());
								ptMemoLabel.setText(ptRecordList.get(ptIndex).getMemo());
							}
						}
					});
					ptRecordButtonPanel.add(previousButton);
					ptRecordButtonPanel.add(nextButton);
					ptRecordButtonPanel.add(editButton);
					ptRecordButtonPanel.add(addButton);
					ptRecordPanel.add(ptDateLabel, BorderLayout.NORTH);
					ptRecordPanel.add(ptMemoLabel, BorderLayout.CENTER);
					ptRecordPanel.add(ptRecordButtonPanel, BorderLayout.SOUTH);
					dialog.add(ptRecordPanel);
					dialog.setSize(500, 200);
					dialog.setVisible(true);
				}
			});
			deleteButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "Delete", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION){
						for(Trainer trainer: trainerList){
							if(trainer.getName().equals(member.getTrainerName())){
								trainer.getMemberList().remove(member);
								memberList.remove(member);
								JOptionPane.showMessageDialog(null, "삭제되었습니다.");
								updateMemberList();
								return;
							}
						}
					}
				}
			});
			editButton.addActionListener(new ActionListenerEditMember(member));
			if(member.getTrainerName().equals("")){
				memberButtonPanel.add(setTrainerButton);
				setTrainerButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(trainerList.size() == 0){
							JOptionPane.showMessageDialog(null, "등록된 트레이너가 없습니다.");
							return;
						}
						String[] trainerNameList = new String[trainerList.size()];
						for(int i = 0; i<trainerList.size(); i++){
							trainerNameList[i] = trainerList.get(i).getName();
						}
						String trainerName = (String) JOptionPane.showInputDialog(null, "트레이너를 선택하세요.", "Set Trainer", JOptionPane.QUESTION_MESSAGE, null, trainerNameList, trainerNameList[0]);
						if(trainerName == null){
							return;
						}
						for(Trainer trainer: trainerList){
							if(trainer.getName().equals(trainerName)){
								trainer.getMemberList().add(member);
								member.setTrainerName(trainerName);
								JOptionPane.showMessageDialog(null, "트레이너가 설정되었습니다.");
								updateMemberList();
								return;
							}
						}
					}
				});
			}

			memberInfoLabel.add(memberLabel);
			memberInfoLabel.add(memberButtonPanel);
			memberPanel.add(memberInfoLabel);
		}
		memberPanel.revalidate();
		memberPanel.repaint();
	}
}
