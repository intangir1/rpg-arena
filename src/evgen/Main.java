package evgen;

import java.util.*;

public class Main {
	
	
	public static Scanner scn = new Scanner(System.in);
	public static Random rnd = new Random ();
	
	public static Character squall;
	public static Character seifer;
	
	
	public static int Get_integer() {
		int number;
		while(true) {
			try {
				number = scn.nextInt();
				scn.nextLine();
				break;
			}
			catch(Exception e){
				System.out.println("Enter a proper number!");
				scn.next();
			}
		}
		
		return number;
	}
	
	
	private static boolean Is_between(int number, int min, int max) {
		if (number >= min && number <= max)
			return true;
		System.out.println("It must be between " + min + " and " + max + "!");
		return false;
	}
	
	
	private static boolean Is_one_or_two(int choice) {
		if (choice == 1 || choice == 2)
			return true;
		System.out.println("Wrong number input!\n");
		return false;
	}
	
	
	private static boolean Is_one_or_two_or_three(int choice) {
		if (choice == 1 || choice == 2 || choice == 3)
			return true;
		System.out.println("Wrong number input!\n");
		return false;
	}
	
	
	private static int Again_or_menu(){
		int choice;
		do{
			System.out.println("Do you wish to try again?");
			System.out.println("	1) Try again\n	2) Return to the main menu\n	3) Exit the game");
			choice = Get_integer();
		}while(!Is_one_or_two_or_three(choice));
		if (choice == 3){
			System.out.println("--------------------------");
			System.out.println("	GAME OVER");
			scn.nextLine();
			System.exit(0);
		}
		return choice;
	}
	
	
	public static boolean Is_sure() {
		int choice;
		do{
			System.out.println("Are you sure with your choice?\n");
			System.out.println("1)Yes\n2)No");
			choice = Get_integer();
		}while(!Is_one_or_two(choice));
		if(choice == 2)
			return false;
		return true;
	}
	
	
	public static int Percent_of(int part, int full) {
		int percent = (int)((part * 100.0f) / full);
		return percent;
	}
	
	
	private static void Final_message(){
		int place = 25;
		for (int i = 0; i < place; i++){
			System.out.print("-");
		}
		System.out.print("Thank you for playing!");
		for (int i = 0; i < place; i++){
			System.out.print("-");
		}
	}
	
	
	private static char Choose_job(String name){
		String choice;
		while(true){
			System.out.println("1) Warrior\n2) Thief\n3) Mage");
			do{
				choice = scn.nextLine().toLowerCase();
			}while(choice.equals(""));
			switch(choice){
				case "1":
					System.out.println("Unsheathe your sword, " + name + " the Warrior!");
					return 'w';
				case "2":
					System.out.println("Draw your knives, " + name + " the Thief!");
					return 't';
				case "3":
					System.out.println("Clutch your stuff tighter, " + name + " the Mage!");
					return 'm';
				case "evgeny":
					System.out.println("F1nish th3m, " + name + " the Creator!");
					return 'c';
				default:
					System.out.println("Wrong number input!\n");
			}
		}
	}
	

	public static void main(String[] args) {
		System.out.println("Welcome to the EvGame!");
		scn.nextLine();
		System.out.println("...yes i was too lazy to come up with a normal name...");
		scn.nextLine();
		System.out.println("Anyway...");
		Game_modes();		
	}
	
	
	private static void Game_modes(){
		while(true){
			System.out.println("Choose the gaming mode:");
			System.out.println("	1) Arcade\n	2) Versus\n	3) Survival\n	4) Audience\n	5) Credits");
			int choice = Get_integer();
			switch(choice){
				case 1:
					System.out.println("Arcade: Prevail against 6 randomly chosen combatants and become the Conqueror of the Arena!");
					if (Is_sure())
						Arcade();
					break;
				case 2:
					System.out.println("Versus: Battle against your friend in this 2-players mode!");
					if (Is_sure())
						Versus();
					break;
				case 3:
					System.out.println("Survival: Endure as long as you can against the endless hordes of enemies!");
					if (Is_sure())
						Survival();
					break;
				case 4:
					System.out.println("Audience: Make bets on randomly generated fighters!");
					if (Is_sure())
						Audience();
					break;
				case 5:
					System.out.println("Credits: See the creators of this game!");
					if (Is_sure()){
						System.out.println("Evgeny Likhtman. Yo!");
						scn.nextLine();
					}
					break;
				default:
					System.out.println("Wrong number input!\n");
			}
		}
	}
	
	
	private static char Choose_difficulty(){
		while(true){
			System.out.println("Choose the difficulty:");
			System.out.println("	1) Easy\n	2) Medium\n	3) Hard");
			int choice = Get_integer();
			switch(choice){
				case 1:
					System.out.println("Easy: Restore all of your HP and MP after each battle. Debuffs disappear as well");
					if (Is_sure()){
						return 'e';}
					break;
				case 2:
					System.out.println("Medium: Restore half of your HP and MP after each battle. Debuffs disappear as well");
					if (Is_sure())
						return 'm';
					break;
				case 3:
					System.out.println("Hard: Only 25% of your HP and MP are restored after each battle. Debuffs carry over to the next battle");
					if (Is_sure())
						return 'h';
					break;
				default:
					System.out.println("Wrong number input!\n");
			}
		}
	}
	
	
	private static void Battle_message(int battles){
		if (battles == 1)
			System.out.println("Get ready for the following battles! Lives on the line, winner takes all - ready or not, let's begin!!!");
		else if (battles == 2)
			System.out.println("One down - five to go! You've still got the whole road ahead of you!!!");
		else if (battles == 3)
			System.out.println("Two opponents bite the dust! Don't celebrate just yet - you're not even halfway!!!");
		else if (battles == 4)
			System.out.println("Three behind and three in front! Just finish the second half - and you're the winner!!!");
		else if (battles == 5)
			System.out.println("Four opponents are now defeated! Only two to go - you can do it!!!");
		else if (battles == 6){
			System.out.println("Five combatants are now slayed by you. Only one enemy is standing between you and your goal");
			scn.nextLine();
			System.out.println("And he's not to be underestimated...");
		}
		scn.nextLine();
	}
	
	
	private static void Game_over_message(){
		System.out.println("Alas, you were defeated...");
		scn.nextLine();
		System.out.println("But there's always next time - so good luck in the future!");
		scn.nextLine();
	}
	
	
	private static void Win_message(char difficulty, String job){
		System.out.println("You finished your last opponent...");
		scn.nextLine();
		if (difficulty == 'e'){
			System.out.println("Yet the victory feels so empty\nAlmost like if you took an EASY road...");
			scn.nextLine();
			System.out.println("But still - a victory is a victory\nCongratulations!\n");
			scn.nextLine();
			System.out.println("...though i truly hope you consider coming back and trying just a little bit harder...");
		}
		else if (difficulty == 'm'){
			System.out.println("Yes, it was truly a nice show you put there!\nJust the perfect MEDIUM of one persons abilities");
			scn.nextLine();
			if (!job.equals("Creator")){
				System.out.println("Bet you wished to had the power of your last opponent, huh?");
				scn.nextLine();
				System.out.println("Well, there is a way...\nIf you only entered certain something when you choose your job...maybe something other than a number...");
				scn.nextLine();
				System.out.println("Still...");
			}
			System.out.println("You certainly deserve a praise, Conqueror of the Arena!");
			scn.nextLine();
			System.out.println("Though don't get too drunk on your glory - because there's always a room for improvement!");
			scn.nextLine();
			System.out.println("Thank you for playing! I'll be waiting for the next show you'll put on the Arena...");
		}
		else{
			System.out.println("Feeling tired, eh..?");
			scn.nextLine();
			if (job.equals("Creator")){
				System.out.println("Heh, of course you don't!");
				scn.nextLine();
				System.out.println("Did you enjoy it - holding the whole power of the Creator on your hands?\nBet you did - very much so...");
				scn.nextLine();
				System.out.println("Welp, serves me right - for hoping you can beat the HARD difficulty fair and square");
				scn.nextLine();
				System.out.println("Congratulations, 'Conqueror' of the Arena!");
			}
			else{
				System.out.println("Relax, Champion - you truly deserved to rest");
				scn.nextLine();
				System.out.println("I'd say you deserved the title of Conquerer of the Arena, but that would be wrong...");
				scn.nextLine();
				System.out.println("You are the Conqueror of the Game - a true display of wits and tactics!");
				scn.nextLine();
				System.out.println("There's not much to be said, except for...");
				scn.nextLine();
				Final_message();
			}
		}
	}
	
	
	private static String Random_name(int job){
		int random = rnd.nextInt(5) + 1;
		if (job == 1){
			if (random == 1)
				return "Argus";
			else if (random == 2)
				return "Banon";
			else if (random == 3)
				return "Desh";
			else if (random == 4)
				return "Doma";
			else
				return "Leo";
		}
		else if (job == 2){
			if (random == 1)
				return "Baku";
			else if (random == 2)
				return "Biggs";
			else if (random == 3)
				return "Blank";
			else if (random == 4)
				return "Gerad";
			else
				return "Wedge";
		}
		else{
			if (random == 1)
				return "Duane";
			else if (random == 2)
				return "Gill";
			else if (random == 3)
				return "Kluya";
			else if (random == 4)
				return "Mid";
			else
				return "Tot";
		}
	}
	
	
	private static void Arcade(){
		char difficulty = Choose_difficulty();
		System.out.println("Welcome to the Nix Arena!");
		scn.nextLine();
		System.out.println("Tell us your name:");
		String name;
		do{
			name = scn.nextLine();
		}while(name.equals(""));
		if (name.toLowerCase().equals("rava")){
			System.out.println("Rava!? Could you be the little annoying brother of Evgeny the Creator!??\nWe shall expect a lot from you...");
			name = "Rava";
			scn.nextLine();
		}
		
		System.out.println("And what is your job, " + name + "?");
		char job = Choose_job(name);
		if (job == 'w')
			squall = new Warrior(name, true);
		else if (job == 't')
			squall = new Thief(name, true);
		else if (job == 'm')
			squall = new Mage(name, true);
		else
			squall = new Creator(name, true);
		
		scn.nextLine();
		System.out.println("You have 5 bonus points to add before the battles start");
		scn.nextLine();
		squall.Add_points(5);
		squall.setHP_to_max();
		squall.setMP_to_max();
		
		int battles = 0;
		int enemy_points = 5;
		while(squall.getHp() > 0 && battles < 6){
			battles++;
			Battle_message(battles);
			
			if (battles == 6)
				seifer = new Creator("Evgeny", false);
			else{
				int random_job = rnd.nextInt(3) + 1;
				String name_enemy;
				if (random_job == 1){
					name_enemy = Random_name(random_job);
					seifer = new Warrior(name_enemy, false);
				}
				else if (random_job == 2){
					name_enemy = Random_name(random_job);
					seifer = new Thief(name_enemy, false);
				}
				else{
					name_enemy = Random_name(random_job);
					seifer = new Mage(name_enemy, false);
				}
			}
			
			if (battles > 1){
				System.out.println("LEVEL UP!\nAdd 3 bonus points");
				scn.nextLine();
				squall.Add_points(3);
				if (difficulty == 'e'){
					squall.setHP_to_max();
					squall.setMP_to_max();
					squall.Clear_debuffs();
				}
				else if (difficulty == 'm'){
					squall.setHp( (int) squall.getMax_hp() / 2 );
					squall.setMp( (int) squall.getMax_mp() / 2 );
					squall.Clear_debuffs();
				}
				else{
					squall.setHp( (int) squall.getMax_hp() / 4 );
					squall.setMp( (int) squall.getMax_mp() / 4 );
				}
				squall.Clear_buffs(squall);
			}
			
			seifer.Random_points(enemy_points);
			seifer.setHP_to_max();
			seifer.setMP_to_max();
			enemy_points += 3;
			
			squall.Battle(seifer);
			
		}
		
		if (squall.getHp() == 0){
			Game_over_message();
			int choice = Again_or_menu();
			if (choice == 1)
				Arcade();
			else
				Game_modes();
		}
		else{
			Win_message(difficulty, squall.getJob());
			scn.nextLine();
			System.exit(0);
		}
		
	}
	
	
	private static void Show_score(String name_1, int squall_points, String name_2, int seifer_points) {
		System.out.println("		SCORE		");
		System.out.println(name_1 + "				" + name_2);
		System.out.println(squall_points + "				" + seifer_points);
	}
	
	
	private static void Show_score_result(String name_1, int squall_points, String name_2, int seifer_points) {
		if (squall_points > seifer_points)
			System.out.println(name_1 + " IS THE WINNER!!!");
		else if (squall_points < seifer_points)
			System.out.println(name_2 + " IS THE WINNER!!!");
		else
			System.out.println("It's a tie... YOU BOTH LOST!!!");
	}
	
	
	private static void Versus(){
		System.out.println("Let the Versus Battle begin!");
		scn.nextLine();
		System.out.println("Ready Player One!");
		scn.nextLine();
		System.out.println("Enter your name:");
		String name_1;
		do{
			name_1 = scn.nextLine();
		}while(name_1.equals(""));
		System.out.println("Choose your job:");
		char job = Choose_job(name_1);
		if (job == 'w')
			squall = new Warrior(name_1, true);
		else if (job == 't')
			squall = new Thief(name_1, true);
		else if (job == 'm')
			squall = new Mage(name_1, true);
		else
			squall = new Creator(name_1, true);
		scn.nextLine();
		
		System.out.println("Ready Player Two!");
		scn.nextLine();
		System.out.println("Enter your name:");
		String name_2;
		do{
			name_2 = scn.nextLine();
		}while(name_2.equals(""));
		System.out.println("Choose your job:");
		job = Choose_job(name_2);
		if (job == 'w')
			seifer = new Warrior(name_2, true);
		else if (job == 't')
			seifer = new Thief(name_2, true);
		else if (job == 'm')
			seifer = new Mage(name_2, true);
		else
			seifer = new Creator(name_2, true);
		scn.nextLine();
		
		System.out.println("How many rounds do you plan to go (1-10)?");
		int rounds;
		do{
			rounds = Get_integer();
		} while(!Is_between(rounds, 1, 10));
		
		System.out.println("How many points do you wish to add each round (1-5)?");
		int points;
		do{
			points = Get_integer();
		} while(!Is_between(points, 1, 5));
		
		int squall_points = 0;
		int seifer_points = 0;
		for (int i = 0; i < rounds; i++){
			System.out.println(squall.getName() + " bonus points:");
			scn.nextLine();
			squall.Add_points(points);
			squall.setHP_to_max();
			squall.setMP_to_max();
			
			System.out.println(seifer.getName() + " bonus points:");
			scn.nextLine();
			seifer.Add_points(points);
			seifer.setHP_to_max();
			seifer.setMP_to_max();
			
			squall.Battle(seifer);
			
			if(squall.getHp() == 0)
				seifer_points++;
			else
				squall_points++;
			
			Show_score(squall.getName(), squall_points, seifer.getName(), seifer_points);
			scn.nextLine();
		}
		
		Show_score_result(squall.getName(), squall_points, seifer.getName(), seifer_points);
		scn.nextLine();
		int choice = Again_or_menu();
		if (choice == 1)
			Versus();
		else
			Game_modes();
	}
	
	
	private static void Survival_result(int wins){
		System.out.println("WINS: " + wins);
		scn.nextLine();
		if (wins == 0)
			System.out.println("...well this is just embarrassing...");
		else if (wins == 1)
			System.out.println("Only one win, huh? Well - better than nothing, right?");
		else if (wins < 5)
			System.out.println("A pretty solid result - though you've still got a lot to learn...");
		else if (wins < 10)
			System.out.println("Not bad at all! Not many make it this far - so you should be pretty proud!");
		else if (wins < 20){
			System.out.println("Niiice! Excellent result! Your endurance is unparalleled");
			if (rnd.nextBoolean()){
				scn.nextLine();
				System.out.println("(that's what she said)");
			}
		}
		else{
			System.out.println("...wow...");
			scn.nextLine();
			System.out.println("...dude...");
			scn.nextLine();
			System.out.println("...get a life!");
		}
		scn.nextLine();
	}
	
	
	private static void Survival(){
		System.out.println("Let's see how long you're going to last!");
		scn.nextLine();
		if (rnd.nextBoolean()){
			System.out.println("(that's what she said)");
			scn.nextLine();
		}
		System.out.println("Enter your name:");
		String name;
		do{
			name = scn.nextLine();
		}while(name.equals(""));
		System.out.println("Choose your job:");
		char job = Choose_job(name);
		if (job == 'w')
			squall = new Warrior(name, true);
		else if (job == 't')
			squall = new Thief(name, true);
		else if (job == 'm')
			squall = new Mage(name, true);
		else
			squall = new Creator(name, true);
		scn.nextLine();
		System.out.println("Add 5 extra points:");
		scn.nextLine();
		squall.Add_points(5);
		squall.setHP_to_max();
		squall.setMP_to_max();
		
		int wins = 0;
		int enemy_points = 5;
		while(squall.getHp() > 0){
			if (wins > 0 && (wins+1) % 10 == 0)
				seifer = new Creator("Evgeny", false);
			else{
				int random_job = rnd.nextInt(3) + 1;
				String name_enemy;
				if (random_job == 1){
					name_enemy = Random_name(random_job);
					seifer = new Warrior(name_enemy, false);
				}
				else if (random_job == 2){
					name_enemy = Random_name(random_job);
					seifer = new Thief(name_enemy, false);
				}
				else{
					name_enemy = Random_name(random_job);
					seifer = new Mage(name_enemy, false);
				}
			}
			if (wins > 0){
				System.out.println("LEVEL UP!\nAdd 3 bonus points");
				scn.nextLine();
				squall.Add_points(3);
				squall.setHp( (int) squall.getMax_hp() / 2 );
				squall.setMp( (int) squall.getMax_mp() / 2 );
				squall.Clear_debuffs();
			}
			seifer.Random_points(enemy_points);
			seifer.setHP_to_max();
			seifer.setMP_to_max();
			enemy_points += 3;
			
			squall.Battle(seifer);
			if (squall.getHp() > 0)
				wins++;
		}
		
		System.out.println("Game! Set! Match!");
		scn.nextLine();
		System.out.println("Let's take look at your result...");
		scn.nextLine();
		Survival_result(wins);
		int choice = Again_or_menu();
		if (choice == 1)
			Survival();
		else
			Game_modes();
	}
	
	
	private static int Place_bet(String name_1, String name_2) {
		int choice;
		System.out.println("Who do you bet on?");
		do{
			System.out.println("	1) " + name_1);
			System.out.println("	2) " + name_2);
			choice = Get_integer();
		}while(!Is_one_or_two(choice));
		return choice;
	}
	
	
	private static int Finish_betting() {
		int choice;
		System.out.println("Wanna continue betting?");
		do{
			System.out.println("	1) Continue");
			System.out.println("	2) Exit");
			choice = Get_integer();
		}while(!Is_one_or_two(choice));
		return choice;
	}
	
	
	private static void Show_bet_results(int wins, int matches) {
		System.out.println(wins + " wins out of " + matches + " matches");
		scn.nextLine();
		if (wins == matches)
			System.out.println("Heh! You definitely have the devils luck!");
		else if (wins == 0)
			System.out.println("Not a single win, huh? Better stay away from any casino, buddy!");
		else if (wins == (matches/2))
			System.out.println("Interesting - an equal results of wins and losses. Congratulations: you're as much as a winner, as you are loser!");
		else if (wins < (matches/2))
			System.out.println("Too bad - you mostly had losses. So no more gambling from now on, ok?");
		else
			System.out.println("A pretty solid result - you mostly had wins! Who said gambling is always a bad thing, right?");
	}
	
	
	private static void Audience(){
		System.out.println("Sit back, relax and watch the fighters battling to the death for entertainment!");
		scn.nextLine();
		int wins = 0;
		int matches = 0;
		int enemy_points = 5;
		while(true){
			int random_job = rnd.nextInt(3) + 1;
			String name;
			if (random_job == 1){
				name = Random_name(random_job);
				squall = new Warrior(name, false);
			}
			else if (random_job == 2){
				name = Random_name(random_job);
				squall = new Thief(name, false);
			}
			else{
				name = Random_name(random_job);
				squall = new Mage(name, false);
			}
			
			random_job = rnd.nextInt(3) + 1;
			if (random_job == 1){
				name = Random_name(random_job);
				seifer = new Warrior(name, false);
			}
			else if (random_job == 2){
				name = Random_name(random_job);
				seifer = new Thief(name, false);
			}
			else{
				name = Random_name(random_job);
				seifer = new Mage(name, false);
			}
			
			squall.Random_points(enemy_points);
			squall.setHP_to_max();
			squall.setMP_to_max();
			seifer.Random_points(enemy_points);
			seifer.setHP_to_max();
			seifer.setMP_to_max();
			enemy_points += 3;
			
			squall.Show_fighters(squall, seifer);
			int bet = Place_bet(squall.getName(), seifer.getName());
			System.out.println("Take a last look at the fighter and cross your fingers:");
			scn.nextLine();
			squall.Battle(seifer);
			matches++;
			if ( (squall.getHp() > 0 && bet == 1) || (seifer.getHp() > 0 && bet == 2) ){
				System.out.println("Congratulations! Your bet was right");
				wins++;
			}
			else
				System.out.println("Too bad! Gotta choose more carefully next time");
			int exit = Finish_betting();
			if (exit == 2)
				break;
		}
		System.out.println("As you wish!");
		scn.nextLine();
		System.out.println("Now let's see just how lucky you are...");
		scn.nextLine();
		Show_bet_results(wins, matches);
		scn.nextLine();
		int choice = Again_or_menu();
		if (choice == 1)
			Audience();
		else
			Game_modes();
	}
	

}
