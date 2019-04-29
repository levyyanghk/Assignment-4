import java.io.*;
import java.util.*;
import java.lang.Math;
import java.util.Collections;



public class WordRecommender {
	public ArrayList<String> dictOfWords;
	public WordRecommender(String fileName)
	{
		dictOfWords = new ArrayList<String>();
		File myFile = new File(fileName);
		try {
			Scanner S = new Scanner(myFile);
			while (S.hasNext()) {
				dictOfWords.add(S.nextLine());
			}
			
			for (String s: dictOfWords)
			{
			//	System.out.println(s);
			}
			
			S.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class WordWithCommonPercent {
		String word;
		double percent;
		public WordWithCommonPercent(String varWord, double varPercent)
		{
			word = varWord;
			percent = varPercent;
			
		}
	};
	
	
	public static Comparator<WordWithCommonPercent> percentComparator = new Comparator<WordWithCommonPercent>()
	{
		public int compare(WordWithCommonPercent var1, WordWithCommonPercent var2)
		{
			return (var1.percent < var2.percent ? -1 : (var1.percent == var2.percent ? 0:1));
		}
	};
	
	public int getCommonLettersNumInTwoWords( String word1, String word2)
	{
		Set<Character> char1 = new TreeSet<Character>();
		Set<Character> char2 = new TreeSet<Character>();
		
		for (int i=0; i<word1.length();i++)
		{
			char1.add(word1.charAt(i));
		}
		
		for (int i=0; i<word2.length();i++)
		{
			char1.add(word2.charAt(i));
		}
		
		char1.retainAll(char2);
		
		return char1.size();

	}
	
	public ArrayList<String> getWordsWithCommonLetters(String word,
			ArrayList<String> listOfWords, int n)
	{
			ArrayList<String> result_words = new ArrayList<String>();
			
			for(String s: listOfWords)
			{
				if (getCommonLettersNumInTwoWords(s,word) >= n)
				{
					result_words.add(s);
				}
			}
			
			return result_words;
					
	}
	
	public double getWordsCommonPercent(String word1, String word2)
	{
		Set<Character> char1 = new TreeSet<Character>();
		Set<Character> char2 = new TreeSet<Character>();
		int totalDistinctNum = 0;
	
		//get distinct characters of word1
		for (int i=0; i<word1.length();i++)
		{
			char1.add(word1.charAt(i));
		}
		
		//get distinct characters of word2
		for (int i=0; i<word2.length();i++)
		{
			char2.add(word2.charAt(i));
		}
		
		//Get total distinct numbers
		totalDistinctNum = char1.size() + char2.size();
		
		//Get common characters of word1 and word2
		char1.retainAll(char2);
		
		return ((double)char1.size()/(double)totalDistinctNum);
		
	}

	public double getSimilarityMetric(String word1, String word2)
	{
		int left = 0;
		int right = 0;
		int len = 0;
		
		//Use the minimum length to loop
		len = word1.length() > word2.length() ? word2.length() : word1.length();
			
//		System.out.println("len is " + len);	
		
		for (int i=0; i<len; i++)
		{
			if (word1.charAt(i) == word2.charAt(i))
				left++;
			if (word1.charAt(word1.length()-i-1) == word2.charAt(word2.length()-i-1))
				right++;
		}
		
		System.out.println("left similarity is " + left + " right similarity is " + right);
		
		return (double)(left+right)/2.0;
		
	}
	
	public boolean isWordInFile(String word)
	{
		for (String s:dictOfWords )
		{
			if (word.equals(s))
				return true;
		}
		
		return false;
	}

	public ArrayList<String> getWordSuggestions(String word, int n, double commonPercent, int topN)
	{
		ArrayList<String> resultString = new ArrayList<String>();
		ArrayList<WordWithCommonPercent> resultWords= new ArrayList<WordWithCommonPercent>();
		
		double percent = 0;
		int count = 0;
	
		//Compare the word with all words in dictionary one by one
		for (String s : dictOfWords)
		{
			//Make sure candidate word length is word length +/- n characters
			if (word.length() > s.length()) {
				if ((word.length() - s.length()) > n)
					continue;
			}
			else {
				if ((s.length() - word.length() > n))
					continue;
			}
			
			
			percent = getWordsCommonPercent(word, s);
			
			if (percent < commonPercent )
				continue;
			
			WordWithCommonPercent temp = new WordWithCommonPercent(s, percent);
			//Add the new item to resultWords with word value and percent value
			resultWords.add(temp);
			
		}
		
		if (resultWords.size() > topN) {
			//Sort resultWords based on the value of percent
			Collections.sort(resultWords, WordRecommender.percentComparator);
			
		}
		
		//Fill the resultString with topN values or real number in resultWords
		count = resultWords.size() < topN ? resultWords.size():topN;
		
		for (int i=0; i<count; i++)
		{
			resultString.add(resultWords.get(i).word);
		}
				
		return resultString;
	}
	


}
