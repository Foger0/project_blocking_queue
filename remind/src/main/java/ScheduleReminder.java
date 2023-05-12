import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleReminder {
    private static final String[] SCHEDULE = {
            "2023-04-15T14:30: 电子技术",
"2023-04-15T15:10: 休息10分钟",
"2023-04-15T15:20: 编程",
"2023-04-15T16:00: 休息10分钟",
"2023-04-15T16:10: 英语",
"2023-04-15T16:50: 休息10分钟",
"2023-04-15T17:00: 运筹学",
"2023-04-15T17:40: 休息10分钟",
"2023-04-15T17:50: 材料力学",
"2023-04-15T18:30: 晚饭时间",
"2023-04-15T19:00: 电子技术",
"2023-04-15T19:40: 休息10分钟",
"2023-04-15T19:50: 编程",
"2023-04-15T20:30: 结束学习"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
            startReminders();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("今天的日程");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String scheduleItem : SCHEDULE) {
            JLabel label = new JLabel(scheduleItem.substring(17));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void startReminders() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        ActionListener reminderListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();

                for (String scheduleItem : SCHEDULE) {
                    LocalDateTime scheduleTime = LocalDateTime.parse(scheduleItem.substring(0, 16), formatter);
                    String scheduleDescription = scheduleItem.substring(17);

                    if (now.isAfter(scheduleTime) && now.isBefore(scheduleTime.plusMinutes(1))) {
                        JOptionPane.showMessageDialog(null, "提醒: " + scheduleDescription, "日程提醒", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        };

        Timer reminderTimer = new Timer(60 * 1000, reminderListener); // 每分钟检查一次
        reminderTimer.start();
    }
}