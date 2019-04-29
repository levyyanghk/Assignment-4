import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordRecommender recommender = new WordRecommender("engDictionary.txt");
	
		Scanner InputScanner = new Scanner(System.in);
		System.out.println("Enter the file's name for spell checking:");
		String inputFileName = InputScanner.nextLine();
		File myFile = new File(inputFileName);
		try {
			Scanner inputFileScanner = new Scanner(myFile);
			
			
			String wordNeedToCheck;
			String inputWord;
			String finalString;
			ArrayList<String> tmpStrArray;
			int i = 0;
			String outputFileName = inputFileName.replace(".", "_chk"+".");
			System.out.println("output file name: " + outputFileName);
			
			FileWriter outputFileWriter = new FileWriter(outputFileName, false);
					
			PrintWriter outputPrint = new PrintWriter(outputFileWriter);
			
			while (inputFileScanner.hasNext()) 
			{
				wordNeedToCheck = inputFileScanner.nextLine();
				if (recommender.isWordInFile(wordNeedToCheck))
				{
					finalString = wordNeedToCheck;
				}
				else
				{
					tmpStrArray = recommender.getWordSuggestions(wordNeedToCheck, 2, 0.5, 10);
					System.out.println(wordNeedToCheck + " spell is not correct");
					
					if (tmpStrArray.size() == 0)
					{
						System.out.println("Could not find any suggestion in dict for " + wordNeedToCheck);
						System.out.println("Please enter the optioin: a (accept) t (type) ");
					}
					else
					{
						for(String s: tmpStrArray)
						{
							System.out.println(i + ". " + s);
							i++;
						}
						System.out.println("Please enter the optioin: r (replacement) a (accept) t (type) ");
					}
					
					
					inputWord = InputScanner.nextLine();
					System.out.println(inputWord + " is entered");
					while (true) {
						if (inputWord.equals("r") && (tmpStrArray.size() != 0)) {
							int inputInt = 0;
							System.out.println("Please enter the number: ");
							
							while (true) {
								inputInt = Integer.parseInt(InputScanner.nextLine());
								if (inputInt > i) {
									System.out.println("The number is out of range, please try again");
									continue;
								}
								break;
							}
						
							finalString = tmpStrArray.get(inputInt);

							break;
						}
						else if (inputWord.equals("a")) {
							finalString = wordNeedToCheck;
							break;
						}
						else if (inputWord.contentEquals("t")) {
							System.out.println("Please enter the word: ");
							finalString = InputScanner.nextLine();
							break;
						}
						else {
							System.out.println("The option is wrong, please try again, option is  " + inputWord);
							inputWord = InputScanner.nextLine();
						}
								
					}
				}
				System.out.println(finalString + " will be used");
				i = 0;
				outputPrint.println(finalString);
	
			}
			System.out.println("Whole file is checked");
			inputFileScanner.close();
			InputScanner.close();
			outputPrint.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
