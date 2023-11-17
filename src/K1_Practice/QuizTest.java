package K1_Practice;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class QuizTest {
    public static void main(String[] args) throws InvalidOperationException {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            try {
                quiz.addQuestion(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}

class InvalidOperationException extends Exception {
    public InvalidOperationException() {
        super("Answers and questions must be of same length!");
    }

    public InvalidOperationException(String option) {
        super(String.format("%s is not allowed option for this question", option));
    }
}

abstract class Question {
    String text;
    int points;
    String answer;

    public Question(String text, int points, String answer) {
        this.text = text;
        this.points = points;
        this.answer = answer;
    }

    public int getPoints() {
        return points;
    }

    abstract public double answerQuestion(String answer);
}

class TFQuestion extends Question {
    public TFQuestion(String text, int points, String answer) {
        super(text, points, answer);
    }

    @Override
    public String toString() {
        return String.format("True/False Question: %s Points: %d Answer: %s", text, points, answer);
    }

    @Override
    public double answerQuestion(String answer) {
        if (this.answer.equals(answer)) return points;
        else return 0;
    }
}

class MCQuestion extends Question {
    public MCQuestion(String text, int points, String answer) {
        super(text, points, answer);
    }

    @Override
    public String toString() {
        return String.format("Multiple Choice Question: %s Points %d Answer: %s", text, points, answer);
    }

    @Override
    public double answerQuestion(String answer) {
        if (this.answer.equals(answer)) return points;
        else return (double) -points / 5;
    }
}

class Quiz {
    List<Question> questions;

    public Quiz() {
        questions = new ArrayList<>();
    }

    public void addQuestion(String questionData) throws InvalidOperationException {
        String[] sections = questionData.split(";");
        String acceptedAnswers = "A/B/C/D/E/true/false";

        if (!acceptedAnswers.contains(sections[3])) throw new InvalidOperationException(sections[3]);

        if (sections[0].equals("TF")) questions.add(new TFQuestion(sections[1], Integer.parseInt(sections[2]), sections[3]));
        else questions.add(new MCQuestion(sections[1], Integer.parseInt(sections[2]), sections[3]));
    }

    public void printQuiz(OutputStream os) {
        System.setOut(new PrintStream(os));
        questions.stream().sorted(Comparator.comparing(Question::getPoints).reversed()).forEach(System.out::println);
    }

    public void answerQuiz (List<String> answers, OutputStream os) throws InvalidOperationException {
        System.setOut(new PrintStream(os));

        double finalPoints = 0;

        if (answers.size() != questions.size()) throw new InvalidOperationException();

        for (int i = 0; i < questions.size(); i++) {
            double points = questions.get(i).answerQuestion(answers.get(i));
            finalPoints += points;

            System.out.printf("%d. %.2f%n", i + 1, points);
        }

        System.out.printf("Total points: %.2f%n", finalPoints);
    }
}