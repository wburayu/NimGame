import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Welela Burayu
 * 04-29-2022
 * CS372 - A.I
 * Project 5:  Nim with Q-learning,design a program to play the game of Nim optimally.
 * Nim is a 2-player game that starts with three piles of objects.
 * Players alternate removing any number of objects from a single pile (a player may not remove objects from multiple piles on a single turn).
 * The player who is forced to take the last object loses
 * (in some variants, the player who takes the last object wins, but in our version, they lose).
 */
public class QLearn{
    //Below are class instance variables.
	private String board;
	private String startboard;
	private Long games;

	QLearn(Long games,String board)// The constructor
	{

		this.startboard=board;
		this.games=games;
		startLearning();//calls the running method
	}

	private void startLearning()// the learning method.
	{

		String nextstateboard;
		HashMap<String, Double> qvalues = new HashMap<>();//stores the knowledge and statistics.
	for(int counter=0;counter<games;counter++)//Loop for the number of simulations to train
	{
		//System.out.println(counter);
		
		board=startboard;
		//System.out.println(board);
		while (!board.equals("A000")&&!board.equals("B000"))//loops while each game is not over.
		{
			//System.out.println(board);
			int pilenumber=getPileNumber();//gets the pile number
			//System.out.println(" Pile "+pilenumber);
            //Below edits the character at position 4 with the value of pile number
			char[] all=board.toCharArray();
			all[4]=(pilenumber+" ").charAt(0);
			String list="";
			for(char i:all)
				list+=i+"";
			board=list;//stores the result to board

			int sizeofpile=0;//holds the sizes of pile

			if(pilenumber==0)
				sizeofpile=Integer.valueOf(all[1]+"");
			if(pilenumber==1)
				sizeofpile=Integer.valueOf(all[2]+"");
			if(pilenumber==2)
				sizeofpile=Integer.valueOf(all[3]+"");

			//System.out.println("Size"+sizeofpile);

			int objectstaken=ThreadLocalRandom.current().nextInt(1,sizeofpile+1);//get a random value of objects taken
			//System.out.println("Objects "+objectstaken);

            //Below edits character at position 5 in the string
			char[] all2=board.toCharArray();
			all2[5]=(objectstaken+" ").charAt(0);
			String list2="";
			for(char i:all2)
				list2+=i+"";

			board=list2;
			//System.out.println("semi"+board);

			nextstateboard=""+board+"";

            //Below gets the characters in the board and gets their respective integer values.

			int b=Integer.valueOf(all2[1]+"");
			int c=Integer.valueOf(all2[2]+"");
			int d=Integer.valueOf(all2[3]+"");
            //Below adjusts the values of b,c,d
			if(pilenumber==0)
				b=b-objectstaken;
			else if(pilenumber==1)
				c=c-objectstaken;
			else
				d=d-objectstaken;

            //Below helps alternate between A and B.
			char a;
			if(board.charAt(0)=='A')
				a='B';
			else
				a='A';

			nextstateboard=a +""+ b +""+ c +""+ d;//creates a board for the next state
			//System.out.println(" next state board "+nextstateboard);

            //Below gives a reward depending on if the game was a win or a loose
			int reward;
			if(nextstateboard.equals("A000"))
                reward = 1000;
            else if (nextstateboard.equals("B000"))
                reward = -1000;
            else
                reward = 0;

            if(board.charAt(0)=='A')//If the model played as A
            {
            	if(!qvalues.containsKey(board))
            		qvalues.put(board,0.0);//checks for values of the current board configuration

            	double minvalue=1000;//initializes min value


                //Below compares the min value and adjusts it checking on the knowledge data

            	if(b>0)
            	{
            		int i = 0 ;                  
                    while (i < b){
                        nextstateboard = a +""+ b +""+ c +""+ d + "0"+ (b-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (minvalue > qvalues.get(nextstateboard)){
                                minvalue = qvalues.get(nextstateboard);
                            }
                           }
                       }
            	}

            	if(c>0)
            	{
            		int i = 0 ;                  
                    while (i < c){
                        nextstateboard = a +""+ b +""+ c +""+ d + "1"+ (c-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (minvalue > qvalues.get(nextstateboard)){
                                minvalue = qvalues.get(nextstateboard);
                            }
                           }
                       }
            	}

            	if(d>0)
            	{
            		int i = 0 ;                  
                    while (i < d)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "2"+ (d-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (minvalue > qvalues.get(nextstateboard)){
                                minvalue = qvalues.get(nextstateboard);
                            }
                           }
                       }
            	}


            //Below performs the training By adjusting the weights for each board configuration.
            //The rate is 0.9 and 1.0 respectively
            	double tmp=qvalues.get(board)+(reward+(0.9*minvalue*1.0)-qvalues.get(board));
            	qvalues.put(board,tmp);
            	board=""+nextstateboard+"";










            }
            else//If model plays as player B
            {
            	if(!qvalues.containsKey(board))
            		qvalues.put(board,0.0);

            	double max=-1000;

            	if(b>0)
            	{
            		int i = 0 ;                  
                    while (i < b)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "0"+ (b-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (max < qvalues.get(nextstateboard)){
                                max = qvalues.get(nextstateboard);
                            }
                           }
                       }
            	}

            	if(c>0)
            	{
            		int i = 0 ;                  
                    while (i < c)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "1"+ (c-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (max < qvalues.get(nextstateboard)){
                                max = qvalues.get(nextstateboard);
                            }
                           }
                       }
            	}

            	if(d>0)
            	{
            		int i = 0 ;                  
                    while (i < d)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "2"+ (d-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (max < qvalues.get(nextstateboard)){
                                max = qvalues.get(nextstateboard);
                            }
                           }
                       }
            	}


            //Below performs the training By adjusting the weights for each board configuration.
            //The rate is 0.9 and 1.0 respectively
            	double tmp=qvalues.get(board)+(reward+(0.9*max*1.0)-qvalues.get(board));
            	qvalues.put(board,tmp);
            	board=""+nextstateboard+"";











            }

}

}

//End of training
/*
            for(String k:qvalues.keySet())
            {
            	System.out.println("Q["+k.substring(0,4)+","+k.substring(4,6)+"]="+qvalues.get(k));
            }*/

            /*for (Map.Entry<String, ?> entry : qvalues.entrySet()) {

            	String k=entry.getKey();
            	System.out.println("Q["+k.substring(0,4)+","+k.substring(4,6)+"]="+entry.getValue());
            }
*/

    //Below is after the training ends.

    //Below goes through all the keys in the hashmap in order and prints their configurations and weights.
    //These are the results of training.

    SortedSet<String> keys = new TreeSet<>(qvalues.keySet());
    for(String k:keys)
    {
    	System.out.println("Q["+k.substring(0,4)+","+k.substring(4,6)+"]="+(qvalues.get(k)*10));
    }



    //The below is the Playing part.
    int firstplayer;//holds the first player.

    int playagain = 1;
    int gameplayed = 0;
    while (playagain == 1){
        if (gameplayed != 0){       
            playagain = getinput("\nPlay again? (1) Yes (2) No ");//prompts for user response
            if (playagain != 1)
                break;
         }
        gameplayed = 1;
        board = startboard.substring(0,4);
        firstplayer = getinput(("\nWho moves first User (1) or Computer (2)? "));
        int playerturn;
        if (firstplayer == 1)
            playerturn = 1;
        else
            playerturn = 2;


        while(!board.equals("A000")&&!board.equals("B000"))//Loops while game is not over
        {

            if (playerturn == 1 && firstplayer == 1 || playerturn == 1 && firstplayer == 2)//When its aplayers turn to play
            {
            	char playerAorB;
                if (playerturn == 1 && firstplayer == 1)
                    playerAorB = 'A';
                else
                    playerAorB = 'B';
                    
                System.out.println("\nPlayer " + playerAorB + " (user)'s turn; the board is " +board.substring(1,board.length()));
                int pilenumber = getinput(("What pile do you chose? 0, 1, 2. "));
                while(pilenumber > 2 || pilenumber < 0)
                {
                    System.out.println("Invalid. Pick 0, 1 or 2. ");
                    pilenumber = getinput(" : ");
                }
                int objectsremoved = getinput(("How many objects do you want to remove from that pile? "));
                char[] all=board.toCharArray();
                char a=all[0];
                int b=Integer.valueOf(all[1]+"");
				int c=Integer.valueOf(all[2]+"");
				int d=Integer.valueOf(all[3]+"");

            	
                if (pilenumber == 0)
                    b = (b) - objectsremoved;
                    
                if (pilenumber == 1)
                    c = (c - objectsremoved);

                if (pilenumber == 2)
                    d = d - objectsremoved;


                if (firstplayer == 1)
                    a = 'B';
                else
                    a = 'A';

                    
                board = a +""+ b +""+ c +""+ d;
                playerturn = 2;
            }
            else if (playerturn == 2 && firstplayer == 2)//When its computers turn to play and it plays as A
            {
                System.out.println("\nPlayer A (computer)'s turn; board is " + board.substring(1,board.length()));
                char[] all=board.toCharArray();
                char a=all[0];
                int b=Integer.valueOf(all[1]+"");
				int c=Integer.valueOf(all[2]+"");
				int d=Integer.valueOf(all[3]+"");
                double maxqvalue = -1001;
                String nextboard="";


                //Below checks for the best move from the knowledge data stored during training and finds the best action .

                if(b>0)
            	{
            		int i = 0 ;                  
                    while (i < b)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "0"+ (b-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (maxqvalue <= qvalues.get(nextstateboard)){
                                maxqvalue = qvalues.get(nextstateboard);
                                nextboard=nextstateboard+"";
                            }
                           }
                       }
            	}

            	if(c>0)
            	{
            		int i = 0 ;                  
                    while (i < c)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "1"+ (c-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (maxqvalue <= qvalues.get(nextstateboard)){
                                maxqvalue = qvalues.get(nextstateboard);
                                nextboard=nextstateboard+"";
                            }
                           }
                       }
            	}

            	if(d>0)
            	{
            		int i = 0 ;                  
                    while (i < d)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "2"+ (d-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (maxqvalue <= qvalues.get(nextstateboard)){
                                maxqvalue = qvalues.get(nextstateboard);
                                nextboard=nextstateboard+"";
                            }
                           }
                       }
            	}


            	char[] all2=nextboard.toCharArray();
                char a2=all2[0];
                int b2=Integer.valueOf(all2[1]+"");
				int c2=Integer.valueOf(all2[2]+"");
				int d2=Integer.valueOf(all2[3]+"");
				int e=Integer.valueOf(all2[4]+"");
				int f=Integer.valueOf(all2[5]+"");

				char x=all2[4];
				char y=all2[5];
				System.out.println("Computer chooses pile " + x + " and removes " + y + ".");

				if(all2[4]=='2')
					d2=d2-f;
				else if(all2[4]=='1')
					c2=c2-f;
				else if(all2[4]=='0')
					b2=b2-f;

				nextboard="B"+b2+""+c2+""+d2;

				board=nextboard+"";
				playerturn=1;






            }
            else if (playerturn == 2 && firstplayer == 1)//When its computers turn to play and it plays as B
            {
                System.out.println("\nPlayer B (computer)'s turn; board is " + board.substring(1,board.length()));
                char[] all=board.toCharArray();
                char a=all[0];
                int b=Integer.valueOf(all[1]+"");
				int c=Integer.valueOf(all[2]+"");
				int d=Integer.valueOf(all[3]+"");
                double minqvalue = 1001;
                String nextboard="";



                if(b>0)
            	{
            		int i = 0 ;                  
                    while (i < b)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "0"+ (b-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (minqvalue >= qvalues.get(nextstateboard)){
                                minqvalue = qvalues.get(nextstateboard);
                                nextboard=nextstateboard+"";
                            }
                           }
                       }
            	}

            	if(c>0)
            	{
            		int i = 0 ;                  
                    while (i < c)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "1"+ (c-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (minqvalue >= qvalues.get(nextstateboard)){
                                minqvalue = qvalues.get(nextstateboard);
                                nextboard=nextstateboard+"";
                            }
                           }
                       }
            	}

            	if(d>0)
            	{
            		int i = 0 ;                  
                    while (i < d)
                    {
                        nextstateboard = a +""+ b +""+ c +""+ d + "2"+ (d-i)+"";
                        i += 1;

                        if (qvalues.containsKey(nextstateboard)){

                            if (minqvalue >= qvalues.get(nextstateboard)){
                                minqvalue = qvalues.get(nextstateboard);
                                nextboard=""+nextstateboard+"";
                            }
                           }
                       }
            	}


            	char[] all2=nextboard.toCharArray();
                char a2=all2[0];
                int b2=Integer.valueOf(all2[1]+"");
				int c2=Integer.valueOf(all2[2]+"");
				int d2=Integer.valueOf(all2[3]+"");
				int e=Integer.valueOf(all2[4]+"");
				int f=Integer.valueOf(all2[5]+"");

				char x=all2[4];
				char y=all2[5];
				System.out.println("Computer chooses pile " + x + " and removes " + y + ".");

				if(all2[4]=='2')
					d2=d2-f;
				else if(all2[4]=='1')
					c2=c2-f;
				else if(all2[4]=='0')
					b2=b2-f;

				nextboard="B"+b2+""+c2+""+d2;

				board=""+nextboard+"";
				playerturn=1;






            }




            






	

}



        //The below runs when Game is Over and displays the Winner.

			if (board.equals("A000")||board.equals("B000"))
            	System.out.println("\nGame Over.");
            if (board.equals("A000"))
                System.out.println("Winner is player A");
            if (board.equals("B000"))
                System.out.println("Winner is player B");

}

		
	}


private static Random random=new Random();
	private int getPileNumber()//Method helps generate a pile number from the board.
	{

            //Bellow converts the string to an array of characters and converts the characters to digits.
		//System.out.println("getting "+board);
			char[] all=board.toCharArray();
			//int a=Integer.valueOf(all[0]+"");
			int b=Integer.valueOf(all[1]+"");
			int c=Integer.valueOf(all[2]+"");
			int d=Integer.valueOf(all[3]+"");
			int e=Integer.valueOf(all[4]+"");
			int f=Integer.valueOf(all[5]+"");

			int pilenumber;
			
            //Below calculates the pile number.

			if ((b) == 0&&(c) != 0&&(d) != 0)
                pilenumber = randomchoice(new int[]{1,2});
            else if ((b) != 0&&(c) == 0&&(d) != 0)
                pilenumber = randomchoice(new int[]{0,2});
            else if ((b) != 0&&(c) !=0&&(d) == 0)
                pilenumber = randomchoice(new int[]{0,1});
            else if ((b) != 0&&(c) == 0&&(d) == 0)
                pilenumber = 0;
            else if ((b) == 0&&(c) != 0&&(d) == 0)
                pilenumber = 1;
            else if ((b) == 0&&(c) == 0&&(d) != 0)
                pilenumber = 2;
            else
                pilenumber =randomchoice(new int[]{0,1,2});


            return pilenumber;//returns it

	}

	public static int randomchoice(int[] arr)//method helps choose a random value from an array.
	{
		int index=random.nextInt(arr.length);
		return arr[index];
	}


	public static int getinput(String str){// The method helps get input from the use.

		Scanner s=new Scanner(System.in);
		System.out.println(str);
		return s.nextInt();

	}











}