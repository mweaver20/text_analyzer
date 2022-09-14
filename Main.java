package textAnalyzer;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {

	public static void main(String[] args) {

		// parsing file using jsoup and saving it to a Document object
		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();

		} catch (IOException e) {

			e.printStackTrace();
		}

		// parsing document for title, author, and poem and formatting as Strings
		String title = doc.select("h1").text().toLowerCase();

		String author = doc.select("h2").text().toLowerCase();

		String poem = doc.select("p").text().replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();

		// Concating strings together
		String fullText = title.concat(" ").concat(author).concat(" ").concat(poem);
		// System.out.println

		// converting full text string to an array
		String[] words = fullText.split(" ");

		// creating a hashmap to store the words and their occurences as key-value pairs
		Map<String, Integer> wordMap = new HashMap<String, Integer>();

		// for loop to count word occurences and add to the hashmap
		for (String word : words) {
			if (!wordMap.containsKey(word))
				wordMap.put(word, 1);
			else
				wordMap.put(word, wordMap.get(word) + 1);
		}

		// Sorting and saving into new hashmap
		Map<String, Integer> sorted = wordMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		// printing out sorted hashmap
		int i = 1;
		for (String key : sorted.keySet()) {
			System.out.println(i + ". " + key + " - " + sorted.get(key));
			i++;
		}

	}
}
