
import javax.swing.*;
import java.awt.*;

public class OnlineQuizApplication extends JFrame {

    static class Question {
        String question;
        String[] options;
        int correctAnswer;

        Question(String question, String[] options, int correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    Question[] questions = {
        new Question("1. What does JVM stand for?",
            new String[]{"Java Variable Method", "Java Virtual Machine",
                "Joint Virtual Machine", "Java Visual Model"}, 1),

        new Question("2. Which keyword creates a class in Java?",
            new String[]{"class", "create", "new", "object"}, 0),

        new Question("3. What is the starting point of a Java program?",
            new String[]{"start()", "run()", "main()", "execute()"}, 2),

        new Question("4. Which symbol ends a Java statement?",
            new String[]{":", ";", ".", ","}, 1),

        new Question("5. Which keyword is used for inheritance?",
            new String[]{"implements", "extends", "inherits", "super"}, 1)
    };

    int currentQuestion = 0;
    int score = 0;
    boolean finished = false;

    JLabel questionLabel;
    JLabel progressLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup group;
    JButton nextButton;

    OnlineQuizApplication() {
        setTitle("Online Quiz Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        JLabel heading = new JLabel(
            "ONLINE QUIZ APPLICATION", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(Color.BLUE);
        add(heading, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 25, 10, 25));

        progressLabel = new JLabel();
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        centerPanel.add(progressLabel);
        centerPanel.add(questionLabel);

        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(new Font("Arial", Font.PLAIN, 14));
            group.add(options[i]);
            centerPanel.add(options[i]);
        }

        add(centerPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> nextQuestion());

        showQuestion();
        setVisible(true);
    }

    void showQuestion() {
        Question q = questions[currentQuestion];

        progressLabel.setText("Question " +
            (currentQuestion + 1) + " of " + questions.length);

        questionLabel.setText(q.question);

        for (int i = 0; i < 4; i++) {
            options[i].setText(q.options[i]);
            options[i].setSelected(false);
        }

        group.clearSelection();

        if (currentQuestion == questions.length - 1) {
            nextButton.setText("Submit Quiz");
        } else {
            nextButton.setText("Next");
        }
    }

    void nextQuestion() {
        if (finished) {
            restartQuiz();
            return;
        }

        int selected = -1;

        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select an answer!");
            return;
        }

        if (selected == questions[currentQuestion].correctAnswer) {
            score++;
        }

        if (currentQuestion < questions.length - 1) {
            currentQuestion++;
            showQuestion();
        } else {
            showResult();
        }
    }

    void showResult() {
        finished = true;

        int percentage = (score * 100) / questions.length;

        JOptionPane.showMessageDialog(this,
            "Quiz Completed!\n\nYour Score: " +
            score + "/" + questions.length +
            "\nPercentage: " + percentage + "%");

        questionLabel.setText("Your quiz is completed!");
        progressLabel.setText("Final Result");

        for (int i = 0; i < 4; i++) {
            options[i].setVisible(false);
        }

        nextButton.setText("Restart Quiz");
    }

    void restartQuiz() {
        currentQuestion = 0;
        score = 0;
        finished = false;

        for (int i = 0; i < 4; i++) {
            options[i].setVisible(true);
        }

        showQuestion();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new OnlineQuizApplication());
    }
}
