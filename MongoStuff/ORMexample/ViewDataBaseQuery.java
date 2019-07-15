package ORMexample;

import ORMexample.model.DBprofile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ORMexample.model.DBgame;
import ORMexample.model.DBplay;

public class ViewDataBaseQuery {

	public static void main(String[] args) throws Exception {
		//Connects to mongodb
		long ctm1, ctm2;
        ctm1 = java.lang.System.nanoTime();//get current timer value
		MongoDBmanager mDBmgr = new MongoDBmanager();
		
		//Take input from user
		//2009091000, 2017112000, 2016101601, 2018092303
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a gameID: ");
	    Long playID = keyboard.nextLong();
	    
	    //Variables that will be read in
	    int preqtr = 0;
	    String homeTeamName="", awayTeamName="";
		Integer homeScore=0, awayScore=0, quarter=0;
		String description;
		
		//Will be used for printing near the end
		ArrayList<String> descriptions = new ArrayList<String>();
		Integer[] homeTeam = {0,0,0,0,0,0};
		Integer[] awayTeam = {0,0,0,0,0,0};
		
		//Used for the cases where there are more than 10 descriptions in a game
		ArrayList<Integer> descriptionsQtr = new ArrayList<Integer>();
		int count = 0;
		
		for(DBplay pg: mDBmgr.findAllPlays(playID)) {
			//Read all the necessary info here
			homeTeamName = pg.getString("home_team");
			awayTeamName = pg.getString("away_team");
			homeScore = pg.getInteger("total_home_score");
			awayScore = pg.getInteger("total_away_score");
			quarter = pg.getInteger("qtr");
			description = pg.getString("desc");
			
			//Finds descriptions w/ touchdown and good
			//Filters out descriptions w/ penalty/overturned
			//Is NOT able to check for game-wnning good extra points
			if((description.indexOf("TOUCHDOWN") > -1 || description.indexOf("GOOD") > -1) 
					&& description.toLowerCase().indexOf("penalty") < 0 && description.toLowerCase().indexOf("overturn") < 0)
			{ 
				descriptions.add(GetDescription(description));
				descriptionsQtr.add(quarter);
				count++;
			}
			
			//Records score of the current quarter
			if(quarter!=preqtr && preqtr!=0) {
				homeTeam[quarter-2] = homeScore;
				awayTeam[quarter-2] = awayScore;
			}
			preqtr = quarter;
        }
		
		//Array is a bit messed up, this will adjust it to have a final score
		//System.out.println(quarter+"\t"+homeScore+"\t"+awayScore+"\t");
		homeTeam[quarter-1] = homeScore;
		awayTeam[quarter-1] = awayScore;
		homeTeam[5] = homeScore;
		awayTeam[5] = awayScore;
		int a = quarter-1;
		//Adjusted for scores earned in each quarter
		for (int i = a; i >= 1; i--) {
			homeTeam[i] -= homeTeam[i-1];
			awayTeam[i] -= awayTeam[i-1];
		}
		//Print out rows for scores, also accomidates for overtimes
		System.out.println("QTR\t1\t2\t3\t4\tOT\tTotal");
		System.out.print(homeTeamName+"\t"+homeTeam[0]+"\t"+homeTeam[1]+"\t"+homeTeam[2]+"\t"+homeTeam[3]+"\t");
		if(quarter==4)
			System.out.println(" "+"\t"+homeTeam[5]);
		else
			System.out.println(homeTeam[4]+"\t"+homeTeam[5]);
		
		System.out.print(awayTeamName+"\t"+awayTeam[0]+"\t"+awayTeam[1]+"\t"+awayTeam[2]+"\t"+awayTeam[3]+"\t");
		if(quarter==4)
			System.out.println(" "+"\t"+awayTeam[5]);
		else
			System.out.println(awayTeam[4]+"\t"+awayTeam[5]);
		
		//List of descriptions, the if only goes through if there are too many descriptions
		ArrayList<String> finalDescriptions = new ArrayList<String>();
		if(count>10) {
			System.out.println("Very busy game here!");
			for(int i =0; i<descriptions.size();i++)
			{
				if(descriptionsQtr.get(i) <= 2)
				{
					finalDescriptions.add(descriptions.get(i));
				}
			}
		}
		else { //Just enough 
			finalDescriptions = descriptions;
		}
		for(String str: finalDescriptions)
		{
			System.out.println(str); //print here
		}
		
        ctm2 = java.lang.System.nanoTime();//get current timer value
		System.out.println("Seconds to run View: "+ (double)(ctm2 - ctm1)/1000000000d);//+"\n"
	}

	private static String GetDescription(String description) {
		//Removes timestamps at the descriptions
		int start,end;
		start = description.lastIndexOf(")");
		if(start < 0)
			start = 0;
		else
			start+=2;
		end = description.length();
		String cut = description.substring(start, end);
		
		
		String[] arr = cut.split(" ");
		String firstWord = arr[0]; //gets first word, the name
		
		
		//Formats sentences w/ good points
		if(cut.indexOf("extra point is GOOD") > -1)
			cut = firstWord + " scores a nice extra point!";
		
		//Formats sentences w/ field goals
		if(cut.indexOf("field goal is GOOD") > -1) {
			String secondWord = arr[1]; //gets second word, used for field goals
			cut = firstWord + " lands a " + secondWord + " yard field goal!";
		}
		
		
		//Formats the touchdown at the last sentence
		if(cut.indexOf("TOUCHDOWN") > -1)
			cut = cut.replaceAll("TOUCHDOWN.","Lands an excellent touchdown!");
		
		return cut;
	}
	
	
}
