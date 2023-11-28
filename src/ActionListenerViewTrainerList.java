package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ActionListenerViewTrainerList implements ActionListener {
    private JPanel mainPanel;
    private JPanel subPanel;
    private JPanel trainerPanel;
    private JPanel buttonPanel;
    private JButton nextButton;
    private JButton previousButton;
    private ArrayList<Trainer> trainerList;
    private ArrayList<Member> memberList;
    private int trainerIndex;
    private int ptIndex;


    public ActionListenerViewTrainerList(JPanel mainPanel, ArrayList<Member> memberList, ArrayList<Trainer> trainerList){
        this.mainPanel = mainPanel;
        this.memberList = memberList;
        this.trainerList = trainerList;

        this.trainerIndex = 0;
        this.ptIndex = 0;
        subPanel = new JPanel();
        trainerPanel = new JPanel();
        buttonPanel = new JPanel();

        subPanel.setLayout(new BorderLayout());
        subPanel.setBounds(0, 0, 500, 600);

        trainerPanel.setLayout(new GridLayout(4,1));
        buttonPanel.setLayout(new GridLayout(1,2));

        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");

        nextButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(trainerIndex + 4 >= trainerList.size()){
                    JOptionPane.showMessageDialog(null, "마지막 페이지입니다.");
                    return;
                }
                trainerIndex += 4;
                updateTrainerList();
            }
        });
        previousButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(trainerIndex - 4 < 0){
                    JOptionPane.showMessageDialog(null, "첫 페이지입니다.");
                    return;
                }
                trainerIndex -= 4;
                updateTrainerList();
            }
        });

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        updateTrainerList();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(trainerList.size() == 0){
            JOptionPane.showMessageDialog(null, "등록된 트레이너가 없습니다.");
            return;
        }
        mainPanel.removeAll();
        mainPanel.add(subPanel);
        subPanel.add(trainerPanel, BorderLayout.CENTER);
        subPanel.add(buttonPanel, BorderLayout.SOUTH);

        updateTrainerList();

        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void updateTrainerList(){
        trainerPanel.removeAll();
        for(int i = trainerIndex; i < trainerIndex + 4; i++){
            if(i >= trainerList.size()){
                break;
            }
            Trainer trainer = trainerList.get(i);
            JPanel trainerInfoPanel = new JPanel(new BorderLayout());
            JPanel trainerButtonPanel = new JPanel(new GridLayout(1,2));
            JLabel trainerInfoLabel= new JLabel(trainer.toString());

            JButton trainerMemberButton = new JButton("Member List");
            JButton deleteButton = new JButton("Delete");

            trainerButtonPanel.add(trainerMemberButton);
            trainerButtonPanel.add(deleteButton);

            trainerMemberButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if(trainer.getMemberList().size() == 0){
                        JOptionPane.showMessageDialog(null, "등록된 회원이 없습니다.");
                        return;
                    }

                    JDialog memberDialog = new JDialog();
                    memberDialog.setTitle("Member List");

                    JPanel dialogPanel = new JPanel();
                    memberDialog.add(dialogPanel);
                    memberDialog.setSize(800, 300);

                    ActionListenerViewMemberList memberList = new ActionListenerViewMemberList(dialogPanel, trainer.getMemberList(), trainerList);

                    // ActionListenerViewMemberList의 actionPerformed 메서드를 호출합니다.
                    memberList.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

                    memberDialog.setVisible(true);
                }
            });

            deleteButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까? 회원정보도 삭제됩니다.", "Delete", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        for(Member removeMember : trainer.getMemberList()){
                            memberList.remove(removeMember);
                        }
                        trainerList.remove(trainer);
                        JOptionPane.showMessageDialog(null, "삭제되었습니다.");
                        updateTrainerList();
                    }
                }
            });

            trainerInfoPanel.add(trainerInfoLabel, BorderLayout.CENTER);
            trainerInfoPanel.add(trainerButtonPanel, BorderLayout.SOUTH);

            trainerPanel.add(trainerInfoPanel);
        }

        mainPanel.add(subPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
