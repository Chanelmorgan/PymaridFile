import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Method that decodes the message from the pyramid structure
    public static String decode(String messageFile) {
        List<String> words = readWordsFromFile(messageFile);
        List<List<String>> pyramid = generatePyramid(words);

        StringBuilder decodedMessage = new StringBuilder();
        int lineNumber = 1;

        for (List<String> row : pyramid) {
            decodedMessage.append(row.get(lineNumber - 1)).append(" ");
            lineNumber++;
        }

        return decodedMessage.toString().trim();
    }

    private static class NumberedWord {
        int number;
        String word;

        public NumberedWord(int number, String word) {
            this.number = number;
            this.word = word;
        }
    }

    // Mehthos that reads the words from the file in order
    public static List<String> readWordsFromFile(String messageFile) {
        List<NumberedWord> numberedWords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(messageFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int number = Integer.parseInt(parts[0]);
                String word = parts[1];
                numberedWords.add(new NumberedWord(number, word));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the numbered words based on their numbers
        numberedWords.sort((nw1, nw2) -> Integer.compare(nw1.number, nw2.number));

        // Extract the words in the sorted order
        List<String> words = new ArrayList<>();
        for (NumberedWord numberedWord : numberedWords) {
            words.add(numberedWord.word);
        }

        return words;

    }

    // Method that generates the pyramid structures from the list of words
    private static List<List<String>> generatePyramid(List<String> words) {
        List<List<String>> pyramid = new ArrayList<>();
        int currentIndex = 0;

        for (int lineNumber = 1; currentIndex < words.size(); lineNumber++) {
            List<String> row = new ArrayList<>();
            for (int i = 0; i < lineNumber && currentIndex < words.size(); i++) {
                row.add(words.get(currentIndex));
                currentIndex++;
            }
            //System.out.println(row);-> used for debugging purposes
            pyramid.add(row);
        }
        return pyramid; // returns a list which is each row, is a list of strings
    }

        public static void main(String[] args) {
            String messageFile = "coding_qual_input.txt";
            String decodedMessage = decode(messageFile);
            System.out.println(decodedMessage);
        }

}
