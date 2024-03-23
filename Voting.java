import java.util.*;
import java.io.*;
import java.nio.*;
// this class has every voter and candidates details and also it is parent class for candidate class 
class Voter
{
	// instance variable declaration
	Election election = new Election();
	String votedPerson1;
	String votedPerson2;
	String name;
	int age;
	String address;
	boolean isVoted;
	boolean isVoted2;
	// initialize the variable using constructor
	Voter(String name,int age,String address,boolean hasVoted1,boolean hasVoted2,String votedPerson1,String votedPerson2)
	{
		this.name = name;
		this.age = age;
		this.address = address;
		this.votedPerson1 = votedPerson1;
		this.votedPerson2 = votedPerson2; 
		isVoted = hasVoted1;
		isVoted2 = hasVoted2;		
		Election.voters.add(this);
	}
	// get methods for name, age, address
	String getName()
	{
		return name;
	}
	int getAge()
	{
		return age;
	}
	String getPassword()
	{
		return address;
	}
	boolean hasVoted(int type)
	{
		if (type == 1)
		{
			return isVoted;
		}
		return isVoted2;
	}
	String getVotedPerson()
	{
		return votedPerson1;
	}
	String getVotedPerson2()
	{
		return votedPerson2;
	}
	void setVotedPerson1(String name)
	{
		votedPerson1 = name;
	}
	void setVotedPerson2(String name)
	{
		votedPerson2 = name;
	}
	// view profile
	void profile()
	{
		System.out.println("\n\n Name                 : "+getName());
		System.out.println(" Age                  : "+getAge());
		if (hasVoted(1))
		{
			System.out.println(" Lok Sabha Election   : You have voted for "+getVotedPerson());
		}
		else
		{
			System.out.println(" Lok Sabha Election   : You have not voted");
		} 
		if (hasVoted(2))
		{
			System.out.println(" Rajya Sabha Election : You have voted for "+getVotedPerson2());
		}
		else
		{
			System.out.println(" Rajya Sabha Election : You have not voted");
		} 
	}
	// method for giving the vote
	void giveVote(int type)
	{	
			election.polling(this,Election.voters.indexOf(this),type);
			if (type == 1)
			{
				isVoted = true;
			}
			else
			{
				isVoted2 = true;
			}
	}
}

// this class has candidates details and it is child class for Voter class
class Candidate extends Voter
{
	int votes = 0;
	int type;
	Candidate(String name,int age,String address,boolean hasVoted1,boolean hasVoted2,int votes,int type,String votedPerson1,String votedPerson2)
	{
		super(name,age,address,hasVoted1,hasVoted2,votedPerson1,votedPerson2);
		this.votes = votes;
		Election.candidates.add(this);
		this.type = type;
		if (type == 1)
		{
			Election.lokCandidates.add(this);
		}
		else
		{
			Election.rajyaCandidates.add(this);
		}
	}
	int getType()
	{
		return type;
	}
	int getVotes()
	{
		return votes;
	}
	// method for increase the votes for candidates
	void setVotes(int type)
	{
		votes += 1;
	}
}
class Election
{
	static Scanner input = new Scanner(System.in);
	// logo each candidates
	static String[] logo = {"\uD83D\uDC36", "\uD83D\uDC35", "\uD83D\uDC25", "\uD83E\uDD84", "\u2603 ", "\uD83D\uDCA7", "\u2601\uFE0F ", "\uD83C\uDF08", "\uD83E\uDEB7","\uD83D\uDC31", "\uD83C\uDF34","\uD83D\uDE80","\uD83D\uDC4D"};
	// voters list includes candidates also
	static ArrayList<Voter> voters = new ArrayList<>();
	static ArrayList<Candidate> candidates = new ArrayList<>();
	static ArrayList<Candidate> lokCandidates = new ArrayList<>();
	static ArrayList<Candidate> rajyaCandidates = new ArrayList<>();
	
	// list of candidates and their logo
	static void listOutCandidates(int type)
	{
		System.out.println("\n\u001B[38;5;223m.~.~.~.~.~.  List of candidates: .~.~.~.~.~.\u001B[0m\n");
		System.out.println("\u001B[34m===============================================\u001B[0m");
		System.out.println("");
		int count = 0;
		// list of candidates for lok sabha election
		if (type == 1)
		{
			try
			{
				for (int i=0; i< lokCandidates.size(); i++)
				{	
					count = i;
					System.out.println("\u001B[34m||\u001B[0m    "+(i+1) +"    \u001B[34m||\u001B[0m    "+logo[i]+"    \u001B[34m||\u001B[0m    "+ lokCandidates.get(i).getName()); 
					System.out.println("");
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("Null value found");
			}
		}
		// list of candidates for rajya sabha election
		else
		{
			try
			{
				for (int i=0; i< rajyaCandidates.size(); i++)
				{	
					count = i;
					System.out.println("\u001B[34m||\u001B[0m    "+(i+1) +"    \u001B[34m||\u001B[0m    "+logo[i]+"    \u001B[34m||\u001B[0m    "+ rajyaCandidates.get(i).getName()); 
					System.out.println("");
				}			
			}
			catch(NullPointerException e)
			{
				System.out.println("Null value found");
			}
		}		
		System.out.println("\u001B[34m||\u001B[0m    "+(count+2) +"    \u001B[34m||\u001B[0m     Nota");
		System.out.println("");
		System.out.println("\u001B[34m===============================================\u001B[0m");
	}
	// polling session
	void polling(Voter voter,int index,int type)
	{
		int canNum;
		System.out.println("\nEnter the candidate number");
		// checking for the valid candidate number
		do
		{
			canNum = input.nextInt();
			if (canNum < 1 || canNum > candidates.size()+1)
			{
				System.out.println("Please enter a valid candidate number: ");
			}
		}
		while(canNum < 1 || canNum > candidates.size()+1);
		// increase the votes for lok sabha election candidate 
		if (type == 1)
		{
			if (canNum == lokCandidates.size()+1)
			{
				voter.setVotedPerson1("Nota");	
			}
			else
			{
				lokCandidates.get(canNum-1).setVotes(type);
				voter.setVotedPerson1(lokCandidates.get(canNum-1).getName());
			}
		}
		// increase the votes for rajya sabha election candidate
		else
		{
			if (canNum == rajyaCandidates.size()+1)
			{
				voter.setVotedPerson2("Nota");	
			}
			else
			{
				rajyaCandidates.get(canNum-1).setVotes(type);
				voter.setVotedPerson2(rajyaCandidates.get(canNum-1).getName());
			}
		}	
	}
	// final result
	static void result(int type)
	{
		String winner = lokCandidates.get(0).getName();
		int winningVotes = 0;
		System.out.println("\u001B[34m=============================================================\u001B[0m");
		// winner of lok sabha election
		if (type == 1)
		{
			try
			{
				for (int i=0; i< lokCandidates.size(); i++)
				{
					// print the each candidates votes 
					System.out.println("\n\u001B[34m||\u001B[0m    "+(i+1)+"    \u001B[34m||\u001B[0m    "+"    \u001B[34m||\u001B[0m    "+logo[i]+"    \u001B[34m||\u001B[0m    "+lokCandidates.get(i).getName()+"    "+lokCandidates.get(i).getVotes()+" Votes\n");
					// checking for the winner
					if(winningVotes < lokCandidates.get(i).getVotes())
					{
						winningVotes = lokCandidates.get(i).getVotes();
						winner = lokCandidates.get(i).getName();	
					}
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("Null value found");
			}
		}
		// winner of rajya sabha election
		else
		{
			winner = rajyaCandidates.get(0).getName();;
			winningVotes = 0;
			try
			{
				for (int i=0; i< rajyaCandidates.size(); i++)
				{
					// print the each candidates votes 
					System.out.println("\n\u001B[34m||\u001B[0m    "+(i+1)+"    \u001B[34m||\u001B[0m    "+"    \u001B[34m||\u001B[0m    "+logo[i]+"    \u001B[34m||\u001B[0m    "+rajyaCandidates.get(i).getName()+"    "+rajyaCandidates.get(i).getVotes()+" Votes");
					// checking for the winner
					if(winningVotes < rajyaCandidates.get(i).getVotes())
					{
						winningVotes = rajyaCandidates.get(i).getVotes();
						winner = rajyaCandidates.get(i).getName();	
					}
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("Null value found");
			}
		}
		// image of small boy
		System.out.println("");
		System.out.println("\u001B[34m=============================================================\u001B[0m\n\n");
		System.out.println("             \u001B[38;5;94m////^\\\\\\\\");
        	System.out.println("             \u001B[38;2;194;178;128m|\u001B[0m \u001B[38;5;94m^   ^ \u001B[38;2;194;178;128m|\u001B[0m");
        	System.out.println("            \u001B[38;5;94m@ (o) (o) @");
        	System.out.println("             \u001B[38;2;194;178;128m|\u001B[0m   \u001B[38;5;94m<   \u001B[38;2;194;178;128m|\u001B[0m");
        	System.out.println("             \u001B[38;2;194;178;128m|\u001B[0m  \u001B[38;5;94m___  \u001B[38;2;194;178;128m|\u001B[0m");
        	System.out.println("              \u001B[38;2;194;178;128m\\_____/\u001B[0m");
        	System.out.println("           \u001B[38;5;129m ____\u001B[38;2;194;178;128m| |\u001B[0m\u001B[38;5;129m____");
        	System.out.println("           \u001B[38;5;129m/----\\_/----\\");
        	System.out.println("          \u001B[38;5;129m/-------------\\");
        	System.out.println("         \u001B[38;2;194;178;128m/\u001B[0m\u001B[38;5;129m\\_/|-------|\\_/\u001B[38;2;194;178;128m\\\u001B[0m");
        	System.out.println("        \u001B[38;2;194;178;128m/ /\u001B[0m  \u001B[38;5;129m|-------|\u001B[0m  \u001B[38;2;194;178;128m\\ \\\u001B[0m");
        	System.out.println("       \u001B[38;2;194;178;128m( <\u001B[0m   \u001B[38;5;129m|-------|\u001B[0m   \u001B[38;2;194;178;128m> )\u001B[0m");
        	System.out.println("        \u001B[38;2;194;178;128m\\ \\\u001B[0m  \u001B[38;5;129m|-------|\u001B[0m  \u001B[38;2;194;178;128m/ /\u001B[0m");
        	System.out.println("         \u001B[38;2;194;178;128m\\ \\\u001B[0m \u001B[38;5;129m|-------|\u001B[0m \u001B[38;2;194;178;128m/ /\u001B[0m");
        	// announce the winner 
        	if (winningVotes != 0)
        	{
			System.out.println("\n\n✨✨✨ Winner: "+winner+" "+winningVotes+" Votes ✨✨✨");
		}
		else
		{
			System.out.println("\n\n Nobody Won\n\n");
		}
	}
	// method for remove the candidate
	void removeCandidate()
	{
		System.out.println("\nEnter the candidate name: ");
		String name = input.next();
		try
		{
			for (int i=0; i<candidates.size(); i++)
			{
				if (candidates.get(i).getName().equals(name))
				{
					candidates.remove(i);
					System.out.println(name+" was removed from the candidates");
					break;
				}
			}
		}
		catch(NullPointerException e)
			{
				System.out.println("Null value found");
			}
	}
	// method for add the candidate
	void addCandidate()
	{
		System.out.println("\nEnter the candidate name: ");
		String name = input.next();
		System.out.println("Enter the candidate age: ");
		int age = input.nextInt();
		System.out.println("Enter the password: ");
		String password = input.next();
		System.out.println("\n1. Lok Sabha Election\n2. Rajya Sabha Election\n(Enter the number)");
		int type = input.nextInt();
		// checking whether the candidate is eligible
		if (age >= 25 && type > 0 && type < 3)
		{
        		Candidate newCandidate = new Candidate(name,age,password,false,false,0,type,"nobody","nobody");
		}
		else{
			System.out.println("\nThe candidate is not eligible to apply for candidate\n");
		}	
	}
	// front page of online voting system
	static void frontPage()
	{
		System.out.println("\u001B[38;5;208m############################################\u001B[0m");
		System.out.println("\u001B[38;5;208m##\u001B[0m                                        \u001B[38;5;208m##\u001B[0m");
		System.out.println("\u001B[38;5;208m##\u001B[0m   Your  Vote  decides  your  Future    \u001B[38;5;208m##\u001B[0m");
		System.out.println("\u001B[38;5;208m##\u001B[0m                                        \u001B[38;5;208m##\u001B[0m");
		System.out.println("\u001B[38;5;208m##\u001B[0m        \u001B[38;5;226mONLINE   VOTING   SYSTEM\u001B[0m        \u001B[38;5;208m##\u001B[0m");
		System.out.println("\u001B[38;5;208m##\u001B[0m     _                                  \u001B[38;5;208m##\u001B[0m");
		System.out.println("\u001B[38;5;208m##\u001B[0m    /_\\                                 \u001B[38;5;208m##\u001B[0m");
		System.out.println("##  \\(*_*)     Vote is not just your      ##");
		System.out.println("##    ( )Z       right, but it is your    ##");
		System.out.println("##    / \\           Duty also !           ##");
		System.out.println("##                               .-.      ##");
		System.out.println("##                               |||      ##");
		System.out.println("##                           .-.-| |      ##");
		System.out.println("##                         .-| | | |      ##");
		System.out.println("\u001B[32m##\u001B[0m                         | | | | |  .-. \u001B[32m##\u001B[0m");
		System.out.println("\u001B[32m##\u001B[0m                         |       | / /  \u001B[32m##\u001B[0m");
		System.out.println("\u001B[32m##\u001B[0m                         \\       |/ /   \u001B[32m##\u001B[0m");
		System.out.println("\u001B[32m##\u001B[0m                          \\        /    \u001B[32m##\u001B[0m");
		System.out.println("\u001B[32m##\u001B[0m                                        \u001B[32m##\u001B[0m");
		System.out.println("\u001B[32m##\u001B[0m                                        \u001B[32m##\u001B[0m");
		System.out.println("\u001B[32m############################################\u001B[0m");
	}
	// method for close the scanner
	void closeScanner()
	{
		input.close();
	}
}
class Voting
{
	static Scanner input = new Scanner(System.in);
	static Voter user;
	public static void main(String[] args)
	{
		Voting voting = new Voting();
       		// front page of website
       		Election.frontPage();
       		System.out.print("\n1.Login \n2.signup \n\u001B[33m(Enter the number):\u001B[0m ");
       		int authentication;
       		do
       		{
       			authentication = input.nextInt();
       			if (authentication < 1 || authentication > 2)
       			{
       				System.out.println("Please the enter the valid number");
       			}
       		}
       		while (authentication < 1 || authentication > 2);
       		//create the voter by reading the file 
       		try {
                 	Scanner reader2 = new Scanner(new File("VotingData.txt"));
 		        while (reader2.hasNextLine()) 
 		        {
            			String dataVoter = reader2.nextLine();
            			String[] voterArray = dataVoter.split(",");
            			ArrayList<String> voterArrayList = new ArrayList<>(Arrays.asList(voterArray));
            			String voterName = voterArray[0];
            			int voterAge = Integer.parseInt(voterArray[1]);
            			String voterAddress = voterArray[2];
            			boolean voterHasVoted1 = Boolean.parseBoolean(voterArray[3]);
            			boolean voterHasVoted2 = Boolean.parseBoolean(voterArray[4]);
            			String votedPerson1ForVoter = voterArray[5];
            			String votedPerson2ForVoter = voterArray[6];
            			Voter voter = new Voter(voterName, voterAge, voterAddress,voterHasVoted1,voterHasVoted2,votedPerson1ForVoter,votedPerson2ForVoter);
        		}
     
       		} 
       		catch (FileNotFoundException e)  
       		{
        		System.out.println("No such file or directory");
       		}
       		// create the candidate by reading the file
       		try {
                 	Scanner reader = new Scanner(new File("CandidateData.txt"));
 		        while (reader.hasNextLine()) 
 		        {
            			String dataCandidate = reader.nextLine();
            			String[] canArray = dataCandidate.split(",");
            			ArrayList<String> canArrayList = new ArrayList<>(Arrays.asList(canArray));
            			String name = canArray[0];
            			int age = Integer.parseInt(canArray[1]);
            			String address = canArray[2];
            			boolean hasVoted1 = Boolean.parseBoolean(canArray[3]);
            			boolean hasVoted2 = Boolean.parseBoolean(canArray[4]);
            			int votes = Integer.parseInt(canArray[5]);
            			int whichElection = Integer.parseInt(canArray[6]);
            			String votedPerson1 = canArray[7];
            			String votedPerson2 = canArray[8];
            			Candidate candidate = new Candidate(name, age, address,hasVoted1,hasVoted2,votes,whichElection,votedPerson1,votedPerson2);
        		}
       		} 
       		catch (FileNotFoundException e)  
       		{
        		System.out.println("No such file or directory");
       		}
                // login or signup
       		if (authentication == 1)
       		{
       			voting.login();
       		}
       		else
       		{
       			voting.signup();
       		}
       		// checking for is the user is a admin
       		if ("ragavi".equals(user.getPassword())) 
        	{
    			voting.menu2(user);
		}
		else 
		{
			voting.menu(user);
		}	
    	}
    	// method for update the file
    	void updateFile()
    	{
    	     	
    	     	// update file for voters 
    		try (BufferedWriter writer = new BufferedWriter(new FileWriter("VotingData.txt"))) 
    		 {
    		 	for (int i=0;i<Election.voters.size();i++)
    		 	{ 
            		writer.write(Election.voters.get(i).getName()+","+Election.voters.get(i).getAge()+","+Election.voters.get(i).getPassword()+","+Election.voters.get(i).hasVoted(1)+","+Election.voters.get(i).hasVoted(2)+","+Election.voters.get(i).getVotedPerson()+","+Election.voters.get(i).getVotedPerson2()+"\n");
            		}
        	} 
        	catch (IOException e) 
        	{
            		System.out.println("No such file or directory");
        	}
        	
        	// update file for candidates
        	try (BufferedWriter writer = new BufferedWriter(new FileWriter("CandidateData.txt"))) 
    		 {
    		 	for (int i=0;i<Election.candidates.size();i++)
    		 	{ 
    	    		writer.write(Election.candidates.get(i).getName()+","+Election.candidates.get(i).getAge()+","+Election.candidates.get(i).getPassword()+","+Election.candidates.get(i).hasVoted(1)+","+Election.candidates.get(i).hasVoted(2)+","+Election.candidates.get(i).getVotes()+","+Election.candidates.get(i).getType()+","+Election.candidates.get(i).getVotedPerson()+","+Election.candidates.get(i).getVotedPerson2()+"\n");
            		}
        	} 
        	catch (IOException e) 
        	{
            		System.out.println("No such file or directory");
        	}
    	}
    	// menu for user
    	void menu(Voter user)
    	{
    		Election election = new Election();
    		System.out.println("\n\n\u001B[33m 1. Give vote");
       		System.out.println(" 2. See the results");
       		System.out.println(" 3. View profile");
       		System.out.println(" 4. Exit\u001B[0m\n\n");
       		int menu = input.nextInt();
       		if (menu == 1 || menu ==2)
       		{
       			electionType(menu,election,user);
       		}
       		else if (menu == 3)
       		{
       			viewProfile(user);
       		}
       		else if(menu == 4)
       		{
       			updateFile();
       			election.closeScanner();
       			closeScanner();
       			System.exit(0);
       		}
       		else
       		{
       			System.out.println("Invalid number");
       			menu(user); 
       		}
    	}
    	// menu for admin
    	void menu2(Voter user)
    	{
    		Election election = new Election();
    		System.out.println("\n\n\u001B[33m 1. Give vote");
       		System.out.println(" 2. See the results");
       		System.out.println(" 3. View profile");
       		System.out.println(" 4. Add candidate");
       		System.out.println(" 5. Remove candidate");
       		System.out.println(" 6. Exit\u001B[0m\n\n");
       		int menu = input.nextInt();
       		if (menu == 1 || menu ==2)
       		{
       			electionType(menu,election,user);
       		}
       		else if (menu == 3)
       		{
       			viewProfile(user);
       		}
       		else if (menu == 4)
       		{
       			election.addCandidate();
       			menu2(user); 
       		}
       		else if(menu == 5)
       		{
       			election.removeCandidate();
       			menu2(user); 
       		}
       		else if(menu == 6)
       		{
       			updateFile();
       			election.closeScanner();
       			closeScanner();
       			System.exit(0);
       		}
       		else
       		{
       			System.out.println("Invalid number");
       			menu2(user); 
       		}
    	}
    	// lok sabha or rajya sabha election
    	void electionType(int menu,Election election,Voter user)
    	{
    		System.out.print("\n  \u001B[33mElections\u001B[0m: \n1. Lok Sabha Election \n2. Rajya Sabha Election\n(Enter the number): ");
       		int electionType = input.nextInt();
       		if (menu == 1)
       		{
       			polling(election,user,electionType);
       		}
       		else if (menu == 2)
       		{
       			Election.result(electionType);
       			if (user.getPassword().equals("ragavi"))
        		{
        			menu2(user);
        		}
        		else
        		{
        			menu(user);
        		}
       		}
    	}
    	// method for start the polling
    	void polling(Election election,Voter user,int electionType)
    	{
        	System.out.println("\n\u001B[38;5;223m.~.~.~.~.  The Polling has started  .~.~.~.~.\u001B[0m\n");
       		Election.listOutCandidates(electionType);
       		// checking whether the user is already voted
       		if (user.hasVoted(electionType) == false)
       		{
       			user.giveVote(electionType);
        	}
        	else
		{			        
			System.out.println("\nYou have already voted");
    		}	
    		
    		if (user.getPassword().equals("ragavi"))
        	{
        		menu2(user);
        	}
        	else
        	{
        		menu(user);
        	}
    	}
    	// user profile
    	void viewProfile(Voter user)
    	{
    		
        	user.profile();
       		if (user instanceof Candidate) 
       		{
       			// checking for is the user is candidate
       			if (((Candidate) user).getType() == 1)
       			{
       				if (((Candidate) user).getVotes() > 1)
       				{
  					System.out.println(" You got " + ((Candidate) user).getVotes() + " votes in Lok Sabha election");
    				}
    				else
       				{
   					System.out.println("\n\n You got " + ((Candidate) user).getVotes() + " vote in Lok Sabha election");
    				}
    			}
    				
        		else
        		{
        			if (((Candidate) user).getVotes() > 1)
       				{
    					System.out.println(" You got " + ((Candidate) user).getVotes() + " votes in Rajya Sabha election");
    				}
    				else
       				{
  					System.out.println("\n\n You got " + ((Candidate) user).getVotes() + " vote in Rajya Sabha election");
    				}
   			}
		}
        	if (user.getPassword().equals("ragavi"))
        	{
        		menu2(user);
        	}
        	else
        	{
        		menu(user);
        	}
    	}
    	// login method
    	void login() {
    		String name_login = null;
    		String password_login = null;
    		try 
    		{
	        	System.out.println("\nEnter the name: ");
	        	name_login = input.next();
	        	System.out.println("Enter the password");
	        	password_login = input.next();
	        }
	        catch(Exception e)
	        {
	        	System.out.println("Wrong input format");
	        	login();
	        }
	        // Checking whether the user has already signed up.
        	boolean validCredentials = false;
        	for (Voter voter : Election.voters) 
        	{
    			if (voter.getName().equals(name_login) && voter.getPassword().equals(password_login)) 
    			{
        			validCredentials = true;
        			user = voter;
        			break;
    			}
		}
	        if (!validCredentials) 
	        {
	            System.out.println("\nInvalid name or password\n");
	            login();
	        }
    	}
    	// signup method
    	void signup() {
    		String name_signup = null;
    		int age_signup = 0;
    		String password_signup = null;
    		try
    		{
        		System.out.println("\nEnter the name");
        		name_signup = input.next();
        		System.out.println("Enter the age");
        		age_signup = input.nextInt();
        		System.out.println("Enter the password");
        		password_signup = input.next();
		}
		catch(Exception e)
		{
			System.out.println("Wrong input format");
			signup();
		}
		// checking whether the user is eligible for voting
        	if (age_signup >= 18) 
        	{
           		ArrayList<String> voterArrayList = new ArrayList<>();
        		voterArrayList.add(name_signup);
        		voterArrayList.add(String.valueOf(age_signup));
        		voterArrayList.add(password_signup);
        		voterArrayList.add("false");
        		voterArrayList.add("false");
        		voterArrayList.add("nobody");
        		voterArrayList.add("nobody");
        		Voter voter = new Voter(name_signup, age_signup, password_signup, false, false,"nobody","nobody");
      		  	user = voter;
        	} 
        	else 
        	{
            		System.out.println("\nYou are not eligible for voting\n");
            		signup();
        	}
    	}
    	// to close the scanner
    	void closeScanner()
    	{
    		input.close();
    	}
}
