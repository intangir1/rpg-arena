package evgen;


public class Creator extends Character{
	private int gum = 1;
	private int scratch_count = 0;
	
	public Creator(String name, boolean is_player) {
		setName(name);
		setJob("Creator");
		setIs_player(is_player);
		setMax_hp(80);
		setHp(getMax_hp());
		setMax_mp(50);
		setMp(getMax_mp());
		setStr(10);
		setDef(7);
		setSpd(5);
		setSkl(7);
	}
	
	
	public int getGum() {
		return gum;
	}


	public void setGum(int change) {
		this.gum += change;
	}
	
	
	public boolean Enough_gum(){
		if (gum > 0)
			return true; 
		System.out.println("No! You ran out of bubble gums!!!\n");
		return false;
	}
	
	
	private void Show_commands(){
		System.out.println("COMMANDS:\n");
		System.out.println("	1) Punch");
		System.out.println("	2) Bang (20 MP)");
		System.out.println("	3) Gum");
		System.out.print("	4) Summon (10 MP)");
		this.Show_sealed_word();
		System.out.println("	5) Laugh");
		System.out.println("	6) Scratch");
		System.out.println("	0) Analyze (turn-free)");
		System.out.println("\nChoose your command:");
	}
	
	
	public void Commands_ai(Character target) {
		if (this.gum == 0){
			if (this.scratch_count < 3){
				this.Scratch();
				scratch_count++;
			}
			else {
				if (Main.rnd.nextBoolean())
					this.Snap(target);
				else
					this.Laugh(target);
			}
		}
		else if (Main.Percent_of(this.getHp(), this.getMax_hp()) <= 50)
			this.Gum();
		else{
			int random = Main.rnd.nextInt(100) + 1;
			if (random <= 1 && this.getMp() >= 20)
				this.Bang();
			else if (random <= 11){
				if (this.getMp() >= 10 && !this.isSealed())
					this.Summon();
				else
					this.Laugh(target);
			}
			else if (random <= 55)
				this.Laugh(target);
			else
				this.Punch(target);
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
					command_chosen = this.Punch(target);
					break;
				case 2:
					if (!this.is_enough_MP(20))
						break;
					command_chosen = this.Bang();
					break;
				case 3:
					if (!this.Enough_gum())
						break;
					command_chosen = this.Gum();
					break;
				case 4:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(10))
						break;
					command_chosen = this.Summon();
					break;
				case 5:
					if (this.isSealed_message())
						break;
					command_chosen = this.Laugh(target);
					break;
				case 6:
					command_chosen = this.Scratch();
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
				case 13:
					command_chosen = this.Snap(target);
					break;
				default:
					System.out.println("Wrong number input!\n");
			}
		}
	}


	public boolean Punch(Character target) {
		if (this.Is_player()){
			System.out.println("'Punch': A simple punch in the face");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println("The enemy runs to you, screaming 'ONE PUUUNCH' like a madman!");
			Main.scn.nextLine();
		}
		
		if (Is_miss(target));
		else{
			Damage(2.0, target, false);
		}
		return true;
	}
	
	
	public boolean Bang() {
		if (this.Is_player()){
			System.out.println("'Bang': Finish the battle with a bang! (20 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println("The enemy decided to finish the battle with a bang");
			Main.scn.nextLine();
		}
		this.setMp(-20);
		this.setHp(-this.getHp());
		System.out.println("BANG");
		Main.scn.nextLine();
		if (this.Is_player())
			System.out.println("A powerful bolt of lightning strikes your head, finishing you with a bang!");
		else
			System.out.println("A powerful bolt of lightning strikes his head, finishing him with a bang!");
		return true;
	}


	public boolean Gum() {
		if (this.Is_player()){
			System.out.println("'Gum': Chew bubble gum - kicking ass can wait");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println("Your enemy takes away a bubble gum and chews it - not even offering you some!");
			Main.scn.nextLine();
		}
		this.setHP_to_max();
		this.gum--;
		if (this.Is_player())
			System.out.println("Cool! You restored all of your HP!");
		else
			System.out.println("What the hell! Did he just restore all of his HP!?");
		if (this.Clear_debuffs()) {
			if (this.Is_player())
				System.out.println("And all of the debuffs are gone! What do they make this gum from..?");
			else
				System.out.println("And he even clears himself from all of the debuffs! Gotta buy some of those...");
		}
		return true;
	}


	public boolean Summon() {
		if (this.Is_player()){
			System.out.println("'Summon': Summon a bubble gum. Why not? (10 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println("Your enemy falls on his knees and reaches to the sky!");
			Main.scn.nextLine();
		}
		
		this.setMp(-10);
		this.gum++;
		if (this.Is_player())
			System.out.println("A gum appears in your hand and you put it inside your pocket\nHope it's clean...");
		else
			System.out.println("And...a chewing gum falls into his hands. Obviously");
		return true;
	}


	public boolean Laugh(Character target) {
		if (this.Is_player()){
			System.out.println("'Laugh': Simply laugh at your opponent");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println("All of a sudden your enemy starts laughing at you!");
			Main.scn.nextLine();
		}
		
		this.setMp(8);
		if (this.Is_player())
			System.out.println("That attitude gives you some confidence (not like you need any more) and restores 10 MP");
		else
			System.out.println("What an asshole! Not only that, he also restores 10 MP!");
		if (Is_buffed(target)){
			if (this.Is_player())
				System.out.println("The enemy is so hurt - he loses all of his buffs!");
			else
				System.out.println("This is so humiliating - you even lose all of your debuffs!");
			Clear_buffs(target);
		}
		return true;
	}
	
	
	public boolean Scratch() {
		if (this.Is_player()){
			System.out.println("'Scratch': Scratch your chin. Does nothing");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println("Your enemy scrathes his chin");
			Main.scn.nextLine();
		}
		if (this.Is_player())
			System.out.println("Yep. Absolutely nothing");
		else
			System.out.println("Good for him!");
		return true;
	}
	
	
	public boolean Snap(Character target) {
		if (this.Is_player()){
			System.out.println("You remembered of your godly powers in this world!");
			Main.scn.nextLine();
			System.out.println("You raise your hand...");
		}
		else
			System.out.println("Your enemy raises his hand...");
		Main.scn.nextLine();
		System.out.println("SNAP");
		Main.scn.nextLine();
		
		boolean reverse = false;
		if (target instanceof Creator) {
			reverse = true;
			this.setHp(-this.getHp());
			if (this.Is_player())
				System.out.println("But the enemy stands still... He smiles and counter-snaps his fingers");
			else
				System.out.println("Heh! He forgot, who his opponent is. You smile and counter-snap your fingers");
		}
		else
			target.setHp(-target.getHp());
		
		if ( (this.Is_player() && !reverse) || (!this.Is_player() && reverse) )
			System.out.println("The ashes of your enemy scatter in a moment by the gust of wind");
		else
			System.out.println("Suddenly you don't feel so good...");
		return true;
	}
}
