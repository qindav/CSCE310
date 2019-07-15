package readFootballData;
import java.io.*;

public class readData {
	public static void main(String[] args) {
		try {
		//open stream
		FileInputStream fstream = new FileInputStream("C:\\Users\\David\\eclipse-workspace\\readFootballData\\data.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		//Placeholder counts
		int gameCount = 0, overtimeCount = 0, tieCount = 0, homeVictoryCount = 0, awayVictoryCount = 0;
		int scorediff = 0, prescorediff = 0;
		
		//Placeholder Strings
		String strLine, gameId = null;
		String HomeTeam="", PostTeam="";
		String prevLine = null, prevGameID = null;
		String preHomeTeam = "", prePostTeam = "";
		
		strLine = br.readLine(); //ignore first line
		 while ((strLine = br.readLine()) != null){
			//16 pos team
		    //69 score diff
		    //71 home team
			strLine =initModify(strLine); //handle values with commas
			String[] arr = strLine.split(",");
			if(!(arr[69].equals("NA"))) {
				scorediff = Integer.parseInt(arr[69]); 
				HomeTeam = arr[71];
				PostTeam = arr[16];
			}
			
		    gameId=GetGameId(strLine);
		    if(!(gameId.equals(prevGameID)) && prevGameID != null) {
		    	//is a finished game
		    	gameCount++;
		    	
		    	prevLine = initModify(prevLine);
		    	String[] arr1 = prevLine.split(",");
		    	//is an overtime
				if(arr1[3].equals("5"))
					overtimeCount++;
				
				//determine victory
				if(prescorediff == 0)
				{
					tieCount++;
				}
				else if(prescorediff > 0)
				{
					if(prePostTeam.equals(preHomeTeam))
						homeVictoryCount++;
					else
						awayVictoryCount++;
				}
				else {
					if(prePostTeam.equals(preHomeTeam))
						awayVictoryCount++;
					else
						homeVictoryCount++;
				}
		    }
		    
		    prevGameID=gameId;
			prevLine=strLine;
		    prescorediff = scorediff;
		    preHomeTeam = HomeTeam;
		    prePostTeam = PostTeam;
		}
		 //wonky way of reading the last line, which is a finished game
		 gameCount++;
	    	
	    	prevLine = initModify(prevLine);
	    	String[] arr1 = prevLine.split(",");
	    	
			if(arr1[3].equals("5"))
				overtimeCount++;
			
			
		
			if(prescorediff == 0)
			{
				tieCount++;
			}
			else if(prescorediff > 0)
			{
				if(prePostTeam.equals(preHomeTeam))
					homeVictoryCount++;
				else
					awayVictoryCount++;
			}
			else {
				if(prePostTeam.equals(preHomeTeam))
					awayVictoryCount++;
				else
					homeVictoryCount++;
			}
		//print info
		System.out.println("Games Counted: " + gameCount);
		System.out.println("Overtimes Counted: " + overtimeCount);
		System.out.println("Home Victories Counted: " + homeVictoryCount);
		System.out.println("Away Victories Counted: " + awayVictoryCount);
		System.out.println("Ties Counted: " + tieCount);
	
		//Close the input stream
		fstream.close();
		}
		catch(IOException ioe){}
	}
	
	private static String GetGameId(String line) {
		String[] arr = line.split(",");
		return arr[1];
	}
	private static String initModify(String line) {
		int firstIndex;
		int secondIndex=0;
		if(line.contains("\"")) {
			StringBuilder sb = new StringBuilder(line);
			firstIndex = line.indexOf("\"", secondIndex+1);
			secondIndex = line.indexOf("\"", firstIndex+1);
			while(firstIndex>-1) {
				for (int i = firstIndex; i < secondIndex; i++){
				    char c = sb.charAt(i);        
				    if(c == ',')
				    	sb.setCharAt(i, ' ');
				}
			    firstIndex = line.indexOf("\"", secondIndex+1);
			    secondIndex = line.indexOf("\"", firstIndex+1);
			}
			return sb.toString();
		}
		else
			return line;
	}

}
