package evgen;


public class Thief extends Character{
	private boolean reflexed = false;
	private boolean energized = false;
	private int energized_turns = 0;
	
	
	public Thief(String name, boolean is_player) {
		setName(name);
		setJob("thief");
		setIs_player(is_player);
		setMax_hp(50);
		setHp(getMax_hp());
		setMax_mp(40);
		setMp(getMax_mp());
		setStr(7);
		setDef(5);
		setSpd(8);
		setSkl(7);
	}


	public boolean isEnergized() {
		return energized;
	}


	public void setEnergized(boolean energized) {
		this.energized = energized;
	}


	public int getEnergized_turns() {
		return energized_turns;
	}


	public void setEnergized_turns(int change) {
		this.energized_turns += change;
	}


	public boolean isReflexed() {
		return reflexed;
	}


	public void setReflexed(boolean reflexed) {
		this.reflexed = reflexed;
	}
	
	
	private void Show_commands(){
		System.out.println("\nCOMMANDS:");
		System.out.println("	1) Slice");
		System.out.println("	2) Throw");
		System.out.print("	3) Break speed (5 MP)");
		this.Show_sealed_word();
		System.out.print("	4) Break skill (5 MP)");
		this.Show_sealed_word();
		System.out.print("	5) Steal heart (10 MP)");
		this.Show_sealed_word();
		System.out.println("	6) Drink");
		System.out.println("	7) Reflex");
		System.out.println("	0) Analyze (turn-free)");
		System.out.println("\nChoose your command:");
	}
	
	
	public void Commands_ai(Character target) {
		int random = Main.rnd.nextInt(100) + 1;
		if (this.Is_debuffed(this) && Main.rnd.nextBoolean())
				this.Drink();
		else if(Main.Percent_of(this.getHp(), this.getMax_hp()) >= 60) {
			if (random <= 40)
				this.Slice(target);
			else if (random <= 70)
				this.Throw(target);
			else if (random <= 85){
				if (this.getMp() >= 5 && !this.isSealed())
					this.Break_skill(target);
				else
					this.Drink();
			}
			else{
				if (this.getMp() >= 5 && !this.isSealed())
					this.Break_speed(target);
				else
					this.Drink();
			}
		}
		
		else if(Main.Percent_of(this.getHp(), this.getMax_hp()) >= 30) {
			if (random <= 30 && !target.isCharmed()){
				if (this.getMp() >= 10 && !this.isSealed())
					this.Steal_heart(target);
				else
					this.Drink();
			}
			else if (random <= 30)
				this.Slice(target);
			else if (random <= 60){
				this.Throw(target);
			}
			else if (random <= 80){
				this.Reflex();
			}
			else if (random <= 90){
				if (this.getMp() >= 5 && !this.isSealed())
					this.Break_skill(target);
				else
					this.Drink();
			}
			else {
				if (this.getMp() >= 5 && !this.isSealed())
					this.Break_speed(target);
				else
					this.Drink();
			}
		}
		
		else{
			if (random <= 30 && !target.isCharmed()){
				if (this.getMp() >= 10 && !this.isSealed())
					this.Steal_heart(target);
				else
					this.Drink();
			}
			else if (random <= 30)
				this.Throw(target);
			else if (random <= 50)
				this.Drink();
			else if (random <= 70) {
				if (this.getMp() >= 5 && !this.isSealed())
					this.Break_skill(target);
				else
					this.Drink();
			}
			else
				this.Reflex();
		}
	}
	
	
	public void Commands(Character target){
		boolean command_chosen = false;
		int choice;
		int analysis = 4;
		while(!command_chosen){
			Show_information(this);
			Show_commands();
			choice = Main.Get_integer();
			switch(choice){
				case 1:
					command_chosen = this.Slice(target);
					break;
				case 2:
					command_chosen = this.Throw(target);
					break;
				case 3:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(5))
						break;
					command_chosen = this.Break_speed(target);
					break;
				case 4:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(5))
						break;
					command_chosen = this.Break_skill(target);
					break;
				case 5:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(10))
						break;
					command_chosen = this.Steal_heart(target);
					break;
				case 6:
					command_chosen = this.Drink();
					break;
				case 7:
					command_chosen = this.Reflex();
					break;
				case 0:
					analysis--;
					if (analysis == 3)
						System.out.println("You analyzed the enemy...\n");
					else if (analysis == 2)
						System.out.println("You analyzed the enemy. Again!\n");
					else if (analysis == 1)
						System.out.println("You analyzed the enemy for the third time. He's clearly getting tired of waiting for you...\n");
					else{
						System.out.println("That's it! Your enemy had enough of this - he starts his turn!");
						command_chosen = true;
						break;
					}
					this.Show_information(target);
					Main.scn.nextLine();
					break;
				default:
					System.out.println("Wrong number input!\n");
			}
		}
	}

	
	
	public boolean Slice(Character target){
		int num_attacks = 2;
		boolean is_double = Is_double(this.getSpd(), target.getSpd());
		if (is_double)
			num_attacks*=2;
		
		if (this.Is_player()){
			System.out.println("'Slice': Slice your enemy twice with your daggers, with a chance of critical damage");
			if (is_double)
				System.out.println("You are fast enough to do it twice!");
			if (!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " jumps next to " + target.getName() + ", twirling his daggers");
			if (is_double)
				System.out.println("And he does this really quickly!");
			Main.scn.nextLine();
		}
		
		for(int i = 0; i < num_attacks; i++){
			if (Is_miss(target));
			else{
				Damage(1.25, target, true);
			}
		}
		return true;
	}
	
	
	public boolean Throw(Character target){
		if (this.Is_player()){
			System.out.println("'Throw': Throw a random item from your backpack - who knows, what can it be?");
			if (!Main.Is_sure())
				return false;
			System.out.println("You reach for your backpack and throw...");
		}
		else
			System.out.println(this.getName() + " reaches for his backpack with a smirk - he's about to throw something!");
		Main.scn.nextLine();
		
		int random;
		random = Main.rnd.nextInt(100) + 1;
		if (random <= 35){
			if (this.Is_player())
				System.out.println("...a poison bomb!\nYour enemy loses 5 HP and is now poisoned for the next 3 turns!");
			else
				System.out.println("It's a poison bomb!\n" + target.getName() + " loses 5 HP and is now poisoned for the next 3 turns!");
			target.setHp(-5);
			target.setPoisoned(true);
			target.setPoison_turns(4);
			return true;
		}
		else if (random <= 80){
			if (this.Is_player())
				System.out.println("...a dynamite.\nYour enemy loses 10 HP!");
			else
				System.out.println("It's a dynamite.\n" + target.getName() + " loses 10 HP!");
			target.setHp(-10);
			return true;
		}
		else{
			if (this.Is_player())
				System.out.println("...a sandwitch..?\nYour enemy shrugs and eats the food - restoring 3 HP");
			else
				System.out.println("It's...a sandwitch..?\nWell, guess it's edible - " + target.getName() + " eats it and restores 3 HP");
			target.setHp(3);
			return true;
		}
	}


	public boolean Break_speed(Character target){
		if(this.Is_player()){
			System.out.println("'Break-speed': Attack and cut the speed of your enemy by half, reducing his dodge chances for the next 3 turns (5 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " takes an aim for the legs");
			Main.scn.nextLine();
		}
		
		this.setMp(-5);
		if (Is_miss(target));
		else{
			Damage(1.0, target, false);
			target.setSpd_broken(true);
			target.setSpd_broken_turns(4);
			if (this.Is_player())
				System.out.println("Your enemy will now struggle to dodge your attacks for the next 3 turns");
			else
				System.out.println(target.getName() + "s legs feels sluggish - the speed was cut by half");
		}
		return true;
	}


	public boolean Break_skill(Character target){
		if(this.Is_player()){
			System.out.println("'Break-skill': Attack and cut the skill of your enemy by half, reducing his hit and critical chances for the next 3 turns (5 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " takes an aim for the head");
			Main.scn.nextLine();
		}
		
		this.setMp(-5);
		if (Is_miss(target));
		else{
			Damage(1.0, target, false);
			target.setSkl_broken(true);
			target.setSkl_broken_turns(4);
			if (this.Is_player())
				System.out.println("Your enemy will now hardly hit or deal criticals for the next 3 turns");
			else
				System.out.println(target.getName() + "s head feels heavy - the skill was cut by half");
		}
		return true;
	}


	public boolean Steal_heart(Character target){
		if (this.Is_player()){
			System.out.println("'Steal-heart': Make a 40% chance to charm your enemy and make him skip the next 3 turns (10 MP)");
			if (!Main.Is_sure())
				return false;
		}
		else{
			System.out.println("All of a sudden " + this.getName() + " gives a sly wink to " + target.getName() + ". It's pretty gross...yet kinda attractive...");
			Main.scn.nextLine();
		}
		
		this.setMp(-10);
		int random = Main.rnd.nextInt(10) + 1;
		if (random <= 4){
			if (this.Is_player())
				System.out.println("Yes! Your enemy watches you with awe - he won't move for 2 turns");
			else
				System.out.println("Ah! " + target.getName() + " looked directly into his deep eyes - and got charmed for the next 3 turns!");
			target.setCharmed(true);
			target.setCharmed_turns(4);
		}
		else{
			if (this.Is_player())
				System.out.println("Dammit! The enemy resisted your charms");
			else
				System.out.println("Nope. Definitely gross. No idea what " + this.getName() + " was thinking");
		}
		return true;
	}


	public boolean Drink(){
		if (this.Is_player()){
			System.out.println("'Drink': Drink a random beverage - the effects vary");
			if (!Main.Is_sure())
				return false;
			System.out.println("You pull from your backpack some flask and drink...");
		}
		else
			System.out.println(this.getName() + " sits and frantically looks through his backpacks belongings - he's about to drink something!");
		Main.scn.nextLine();
		
		int random;
		random = Main.rnd.nextInt(100) + 1;
		if (random <= 35){
			if (this.Is_player())
				System.out.println("...an energy drink! You immediately restore 10 HP and feel energized for the next 3 turns");
			else
				System.out.println("It's an energy drink! He restores 10 HP and gets all energized");
			this.setHp(10);
			this.energized = true;
			this.energized_turns = 4;
			return true;
		}
		else if (random <= 80){
			if (this.Is_player())
				System.out.println("...mineral water. You restore 10 MP.");
			else
				System.out.println("It's a mineral water. He restores 10 MP");
			this.setMp(10);
			if (this.Clear_debuffs()){
				if (this.Is_player())
					System.out.println("You also feel your debuff is gone away!");
				else
					System.out.println("And he even looks healthier");
			}
			return true;
		}
		else{
			if (this.Is_player())
				System.out.println("...a tomato juice..?\nYou quickly spit it out! But the damage was made - you lost 3 HP");
			else
				System.out.println("It's...a tomato juice..?\n" + this.getName() + " desperately spits it out, losing 3 HP in the process");
			this.setHp(-3);
			return true;
		}
	}


	public boolean Reflex(){
		if (this.Is_player()){
			System.out.println("'Reflexes': Get ready for the next move of your enemy - tripling your chance to dodge for the next turn");
			if (!Main.Is_sure())
				return false;
		}
		else {
			System.out.println(this.getName() + " is watching the enemy carefully");
			Main.scn.nextLine();
		}
		this.reflexed = true;
		if (this.Is_player())
			System.out.println("You stare closely at your enemy - awaiting the next attack");
		else
			System.out.println("He seems to be expecting the next attack");
		return true;
	}
	
	
}
