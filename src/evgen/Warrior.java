package evgen;


public class Warrior extends Character{
	private boolean defended = false;
	private boolean berserked = false;
	private int berserked_turns = 0;
	
	
	public Warrior(String name, boolean is_player) {
		setName(name);
		setJob("warrior");
		setIs_player(is_player);
		setMax_hp(60);
		setHp(getMax_hp());
		setMax_mp(30);
		setMp(getMax_mp());
		setStr(10);
		setDef(7);
		setSpd(5);
		setSkl(5);
	}


	public boolean isDefended() {
		return defended;
	}


	public void setDefended(boolean defended) {
		this.defended = defended;
	}
	
	
	public boolean isBerserked() {
		return berserked;
	}


	public void setBerserked(boolean berserked) {
		this.berserked = berserked;
	}


	public int getBerserked_turns() {
		return berserked_turns;
	}


	public void setBerserked_turns(int change) {
		this.berserked_turns += change;
	}
	
	
	private void Show_commands(){
		System.out.println("\nCOMMANDS:");
		System.out.println("	1) Thrust");
		System.out.println("	2) Reckless attack");
		System.out.print("	3) Break strength (5 MP)");
		this.Show_sealed_word();
		System.out.print("	4) Break defence (5 MP)");
		this.Show_sealed_word();
		System.out.print("	5) Berserk (8 MP)");
		this.Show_sealed_word();
		System.out.println("	6) Pray (10 MP)");
		System.out.println("	7) Defend");
		System.out.println("	0) Analyze (turn-free)");
		System.out.println("\nChoose your command:");
	}
	
	
	public void Commands_ai(Character target) {
		int random = Main.rnd.nextInt(100) + 1;
		
		if (this.Is_debuffed(this) && Main.rnd.nextBoolean()){
			if(this.getMp() >= 10)
				this.Pray();
			else
				this.Defend();
		}
		
		else if(Main.Percent_of(this.getHp(), this.getMax_hp()) >= 60) {
			if (random <= 40)
				this.Thrust(target);
			else if (random <= 70)
				this.Reckless_attack(target);
			else if (random <= 85){
				if (this.getMp() >= 5 && !this.isSealed())
					this.Break_strength(target);
				else
					this.Defend();
			}
			else{
				if (this.getMp() >= 5 && !this.isSealed())
					this.Break_defence(target);
				else
					this.Defend();
			}
		}
		
		else if(Main.Percent_of(this.getHp(), this.getMax_hp()) >= 30) {
			if (random <= 40 && !this.berserked){
				if (this.getMp() >= 8 && !this.isSealed())
					this.Berserk();
				else
					this.Defend();
			}
			else if (random <= 40)
				this.Thrust(target);
			else if (random <= 60){
				if (this.getMp() >= 5 && !this.isSealed())
					this.Break_strength(target);
				else
					this.Defend();
			}
			else if (random <= 80){
				if (this.getMp() >= 5 && !this.isSealed())
					this.Break_defence(target);
				else
					this.Defend();
			}
			else
				this.Thrust(target);
		}
		
		else{
			if (random <= 30){
				if(this.getMp() >= 10)
					this.Pray();
				else
					this.Defend();
			}
			else if (random <= 75)
				this.Reckless_attack(target);
			else
				this.Thrust(target);
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
					command_chosen = this.Thrust(target);
					break;
				case 2:
					command_chosen = this.Reckless_attack(target);
					break;
				case 3:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(5))
						break;
					command_chosen = this.Break_strength(target);
					break;
				case 4:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(5))
						break;
					command_chosen = this.Break_defence(target);
					break;
				case 5:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(8))
						break;
					command_chosen = this.Berserk();
					break;
				case 6:
					if (!this.is_enough_MP(10))
						break;
					command_chosen = this.Pray();
					break;
				case 7:
					command_chosen = this.Defend();
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


	public boolean Thrust (Character target) {
		int num_attacks = 1;
		boolean is_double = Is_double(this.getSpd(), target.getSpd());
		if (is_double)
			num_attacks*=2;
		
		if(this.Is_player()){
			System.out.println("'Thrust': Thrust your sword into an enemy, with a chance of critical damage");
			if (is_double)
				System.out.println("You are fast enough to do it twice!");
			if (!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " thrusts his sword into " + target.getName());
			if (is_double)
				System.out.println("And he does this really quickly!");
			Main.scn.nextLine();
		}
		
		for(int i = 0; i < num_attacks; i++){
			if (Is_miss(target));
			else{
				Damage(1.75, target, true);
			}
		}
		return true;
	}
	
	
	public boolean Reckless_attack(Character target) {
		if (this.Is_player()){
			System.out.println("'Reckless-attack': A heavy almost-blind attack, trippling your damage - yet has 30% chance to connect");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " rushes ahead, swinging his sword recklessly!");
			Main.scn.nextLine();
		}
		
		boolean connect = false;
		int random = Main.rnd.nextInt(10) + 1;
		if (random <= 3)
			connect = true;
		if (connect){
			if (Is_miss(target));
			else{
				Damage(3.0, target, false);
			}
		}
		else {
			if(this.Is_player())
				System.out.println("Too bad! Your sword wasn't even close to the enemy.");
			else
				System.out.println("Hah! " + this.getName() + "s aim was nowhere near the target!");
		}
		return true;
	}


	public boolean Break_strength(Character target){
		if (this.Is_player()){
			System.out.println("'Break-strength': Attack and cut the strength of your enemy by half, reducing his damage for the next 3 turns (5 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " takes an aim for the hands");
			Main.scn.nextLine();
		}
		
		this.setMp(-5);
		if (Is_miss(target));
		else{
			Damage(1.0, target, false);
			target.setStr_broken(true);
			target.setStr_broken_turns(4);
			if (this.Is_player())
				System.out.println("Your enemy will now attack with reduced strength for the next 3 turns");
			else
				System.out.println(target.getName() + "s hands feel numb - the strength was cut by half");
		}
		return true;
	}


	public boolean Break_defence(Character target){
		if(this.Is_player()){
			System.out.println("'Break-defence': Attack and cut the defence of your enemy by half, reducing his resistance for the next 3 turns (5 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " takes an aim for the chest");
			Main.scn.nextLine();
		}
		
		this.setMp(-5);
		if (Is_miss(target));
		else{
			Damage(1.0, target, false);
			target.setDef_broken(true);
			target.setDef_broken_turns(4);
			if (this.Is_player())
				System.out.println("Your enemy will now receive increased damage for the next 3 turns");
			else
				System.out.println(target.getName() + "s chest feels weak - the defence was cut by half");
		}
		return true;
	}


	public boolean Berserk() {
		if (this.Is_player()){
			System.out.println("'Berserk': Give into the berserker within - raise your strength by 1.5 for the next 3 turns (8 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else {
			System.out.println(this.getName() + " gives a chilling roar");
			Main.scn.nextLine();
		}
		
		this.setMp(-8);
		if (this.Is_player())
			System.out.println("You let out a mighty roar - which increases your strength for the next 3 turns");
		else
			System.out.println("Suddenly he looks mightier than before!");
		this.berserked = true;
		this.berserked_turns = 4;
		return true;
	}


	public boolean Pray() {
		if (this.Is_player()){
			System.out.println("'Pray': Pray for a miracle - restores moderate HP and has 50% chance to cure from all the debuffs (10 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else {
			System.out.println(this.getName() + " bows his head and whispers something");
			Main.scn.nextLine();
		}
		
		this.setMp(-10);
		int random = Main.rnd.nextInt((15 - 10) + 1) + 10;
		if (this.Is_player())
			System.out.println("You clutch your arms and pray - restoring " + random + " HP");
		else
			System.out.println("He prayed - restoring " + random + " HP");
		this.setHp(random);
		boolean cure = Main.rnd.nextBoolean();
		if (cure && this.Clear_debuffs()){
				if (this.Is_player())
					System.out.println("And, miracously, you no longer feel any debuffs!");
				else
					System.out.println("And all of a sudden he looks healthier than a moment ago");
		}
		return true;
	}


	public boolean Defend() {
		if (this.Is_player()){
			System.out.println("'Defend': Defend with your shield, doubling your defence for a turn and restoring little MP");
			if(!Main.Is_sure())
				return false;
			}
		else {
			System.out.println(this.getName() + " hides behind his shield");
			Main.scn.nextLine();
		}
		
		this.defended = true;
		int random = Main.rnd.nextInt((8 - 3) + 1) + 3;
		if (this.Is_player())
			System.out.println("You raise your shield and take a deep breath - restoring " + random + " MP");
		else
			System.out.println("He restores " + random + " MP");
		this.setMp(random);
		return true;
	}
	
}
