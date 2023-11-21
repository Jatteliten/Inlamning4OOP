package Client;

import Server.Question;

import java.util.ArrayList;
import java.util.List;

public class Category {
    List<Question> questionList;
    String categoryText;

    public Category(String text) {
        this.categoryText = text;
        this.questionList = new ArrayList<>();
    }

    public List<Question> getQuestionsList() {
        return questionList;
    }

    public void setQuestionsList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public void addQuestionToList(Question q){
        questionList.add(q);
    }

    /*
    public static void main(String[] args) {
        new QuizApp();
    }

    public static class QuizApp extends JFrame {

        private List<Category> categoriesList;

        public QuizApp() {
            categoriesList = readCategoriesFromFile("src/Server/Categories and questions");

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Quizkampen");
            setLayout(new FlowLayout());

            // Skapa knappar för varje kategori
            for (Category category : categoriesList) {
                JButton categoryButton = new JButton(category.categoryText);
                categoryButton.addActionListener(new CategoryButtonListener(category));
                add(categoryButton);
            }

            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private List<Category> readCategoriesFromFile(String filePath) {
            List<Category> categoriesList = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;

                while ((line = br.readLine()) != null) {
                    // Splitta raden på parenteserna för att få kategorin
                    String[] parts = line.split("\\(");
                    if (parts.length >= 2) {
                        String categoryText = parts[1].substring(0, parts[1].indexOf(")"));
                        Category category = new Category(categoryText);
                        categoriesList.add(category);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return categoriesList;
        }

        private class CategoryButtonListener implements ActionListener {
            private Category category;

            public CategoryButtonListener(Category category) {
                this.category = category;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                // Visar en dialogruta med kategorins frågor och svarsalternativ
                StringBuilder message = new StringBuilder("Questions for " + category.categoryText + ":\n");
                for (String question : category.questionsList) {
                    message.append(question).append("\n");
                }
                JOptionPane.showMessageDialog(QuizApp.this, message.toString(), "Category Selected", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    */
}