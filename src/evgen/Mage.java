package evgen;


public class Mage extends Character{
	private boolean concentrated = false;
	private int concentrated_turns = 0;
	private boolean meditated = false;
	private int meditated_turns = 0;
	
	
	public Mage(String name, boolean is_player) {
		setName(name);
		setJob("mage");
		setIs_player(is_player);
		setMax_hp(40);
		setHp(getMax_hp());
		setMax_mp(60);
		setMp(getMax_mp());
		setStr(9);
		setDef(4);
		setSpd(7);
		setSkl(8);
	}


	public boolean isMeditated() {
		return meditated;
	}


	public void setMeditated(boolean meditated) {
		this.meditated = meditated;
	}


	public int getMeditated_turns() {
		return meditated_turns;
	}


	public void setMeditated_turns(int change) {
		this.meditated_turns += change;
	}


	public boolean isConcentrated() {
		return concentrated;
	}


	public void setConcentrated(boolean concentrated) {
		this.concentrated = concentrated;
	}
	
	
	public int getConcentrated_turns() {
		return concentrated_turns;
	}


	public void setConcentrated_turns(int change) {
		this.concentrated_turns += change;
	}
	
	
	private void Show_commands(){
		System.out.println("\nCOMMANDS:");
		System.out.println("	1) Fire (5 MP)");
		System.out.println("	2) Drain (8 MP)");
		System.out.print("	3) Concentrate (3 MP)");
		this.Show_sealed_word();
		System.out.print("	4) Seal (20 MP)");
		this.Show_sealed_word();
		System.out.print("	5) Dispel (10 MP)");
		this.Show_sealed_word();
		System.out.println("	6) Cleanse (15 MP)");
		System.out.println("	7) Meditate");
		System.out.println("	0) Analyze (turn-free)");
		System.out.println("\nChoose your command:");
	}
	
	
	public void Commands_ai(Character target) {
		int random = Main.rnd.nextInt(100) + 1;
		
		if (this.getMp() < 8){
			if (this.meditated && this.getMp() >= 3)
				this.Concentrate();
			else
				this.Meditate();
		}
		
		else if (this.Is_buffed(target) && Main.rnd.nextBoolean()){
			if (this.getMp() >= 10 && !this.isSealed())
				this.Dispel(target);
			else
				this.Drain(target);
		}
		
		else if (this.Is_debuffed(this) && Main.rnd.nextBoolean()){
			if (this.getMp() >= 10)
				this.Cleanse();
			else
				this.Drain(target);
		}
		
		else if (this.concentrated){
			if (this.getMp() >= 5)
				this.Fire(target);
			else
				this.Meditate();
		}
		
		else if(Main.Percent_of(this.getHp(), this.getMax_hp()) >= 60) {
			if (random <= 50 && !this.meditated)
				this.Meditate();
			else if (random <= 60)
				this.Fire(target);
			else if (random <= 90 && !this.isSealed())
				this.Concentrate();
			else
				this.Drain(target);
		}
		
		else if(Main.Percent_of(this.getHp(), this.getMax_hp()) >= 30) {
			if (random <= 30 && !target.isSealed()){
				if (this.getMp() >= 20 && !this.isSealed())
					this.Seal(target);
				else
					this.Drain(target);
			}
			else if (random <= 50)
				this.Drain(target);
			else if (random <= 90 && !this.isSealed())
				this.Concentrate();
			else
				this.Fire(target);
		}
		
		else{
			if (random <= 50 && !target.isSealed()){
				if(this.getMp() >= 20 && !this.isSealed())
					this.Seal(target);
				else
					this.Drain(target);
			}
			else if (random <= 70)
				this.Drain(target);
			else if (!this.isSealed())
				this.Concentrate();
			else
				this.Fire(target);
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
					if (!this.is_enough_MP(5))
						break;
					command_chosen = this.Fire(target);
					break;
				case 2:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(8))
						break;
					command_chosen = this.Drain(target);
					break;
				case 3:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(3))
						break;
					command_chosen = this.Concentrate();
					break;
				case 4:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(20))
						break;
					command_chosen = this.Seal(target);
					break;
				case 5:
					if (this.isSealed_message())
						break;
					else if (!this.is_enough_MP(10))
						break;
					command_chosen = this.Dispel(target);
					break;
				case 6:
					if (!this.is_enough_MP(15))
						break;
					command_chosen = this.Cleanse();
					break;
				case 7:
					command_chosen = this.Meditate();
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


	public boolean Fire (Character target){
		if (this.Is_player()){
			System.out.println("'Fire': The basics of any magic - a standart fireball, with a chance of critical hit (5 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " points his staff at " + target.getName() + " - he's about to throw a fireball!");
			Main.scn.nextLine();
		}
		
		this.setMp(-5);
		if (Is_miss(target));
		else{
			Damage(1.5, target, true);
		}
		return true;
	}
	
	
	public boolean Drain(Character target){
		if (this.Is_player()){
			System.out.println("'Drain': Drain 15 points of inner energy of your enemy - it can be either HP or MP (8 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " reaches his open palm to " + target.getName() + " and drains away the energy!");
			Main.scn.nextLine();
		}
		
		this.setMp(-8);
		boolean health = Main.rnd.nextBoolean();
		if (health) {
			if (this.Is_player())
				System.out.println("You feel the health of your enemy becoming yours");
			else
				System.out.println("It's a health!");
			if(target.getHp() <= 15) {
				this.setHp(target.getHp());
				if (this.Is_player())
					System.out.println("You drained " + target.getHp() + " HP");
				else
					System.out.println("He drained " + target.getHp() + " HP");
				target.setHp(-target.getHp());
			}
			else {
				this.setHp(15);
				if (this.Is_player())
					System.out.println("You drained 15 HP");
				else
					System.out.println("He drained 15 HP");
				target.setHp(-15);
			}
		}
		else {
			if (this.Is_player())
				System.out.println("You feel the magic of your enemy becoming yours");
			else
				System.out.println("It's a magic!");
			
			if (target.getMp() == 0){
				if (this.Is_player())
					System.out.println("Too bad he had none. You don't restore any MP.");
				else
					System.out.println("Joke's on him! Cause " + target.getName() + " got no MP to offer anyway");
			}
			else {
				if (target.getMp() <= 15) {
					this.setMp(target.getMp());
					if (this.Is_player())
						System.out.println("You drained " + target.getMp() + " MP");
					else
						System.out.println("He drained " + target.getMp() + " MP");
					target.setMp(-target.getMp());
				}
				else {
					this.setMp(15);
					if (this.Is_player())
						System.out.println("You drained 15 MP");
					else
						System.out.println("He drained 15 MP");
					target.setMp(-15);
				}
			}
		}
		return true;
	}
	
	
	public boolean Concentrate(){
		if (this.Is_player()){
			System.out.println("'Concentrate': Channel the energy within - next turn deal 2.5 damage with Fire (3 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " looks somewhere away from the enemy");
			Main.scn.nextLine();
		}
		this.setMp(-3);
		if (this.Is_player())
			System.out.println("You recall the most advanced fire magics you have ever learned and get ready to burn");
		else
			System.out.println("He seems to be concentrating for his next Fire attack");
		this.concentrated = true;
		this.concentrated_turns = 2;
		return true;
	}


	public boolean Seal(Character target){
		if (this.Is_player()){
			System.out.println("'Seal': Seal most of the commands your enemy has for 3 turns (20 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " lowers his head and starts mumbling something");
			Main.scn.nextLine();
		}
		
		this.setMp(-20);
		target.setSealed(true);
		target.setSealed_turns(4);
		if (this.Is_player())
			System.out.println("Now enemy feels confused - he has fewer battle options for the next 3 turns!");
		else
			System.out.println("Suddenly " + target.getName() + " feel dizzy. Somehow he forgot most of his skills");
		return true;
	}


	public boolean Dispel(Character target){
		if (this.Is_player()){
			System.out.println("'Dispel': Dispel all of the buffs your enemy has right now (10 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " smiles and waves his hand in " + target.getName() + "s direction");
			Main.scn.nextLine();
		}
		
		this.setMp(-10);
		if (!target.Clear_buffs(target))
			System.out.println("Well, that was pointless...");
		else {
			if (this.Is_player())
				System.out.println("The enemy had his buffs removed");
			else
				System.out.println("Suddenly " + target.getName() + " feels his buffs disappearing!");
		}
		return true;
	}


	public boolean Cleanse(){
		if (this.Is_player()){
			System.out.println("'Cleanse': Clean yourself from any debuff you have (15 MP)");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " raises his staff high above him");
			Main.scn.nextLine();
		}
		this.setMp(-15);
		if (this.Clear_debuffs()){
			if (this.Is_player())
				System.out.println("In a swift moment, all your debuffs go away");
			else
				System.out.println("He's clearing himself from the debuffs!");
		}
		else
			System.out.println("Well, that was pointless...");
		return true;
	}


	public boolean Meditate() {
		if (this.Is_player()){
			System.out.println("'Meditate': Channel your inner forces to restore some MP for the next 3 turns");
			if(!Main.Is_sure())
				return false;
		}
		else{
			System.out.println(this.getName() + " closes his eyes and stands still");
			Main.scn.nextLine();
		}
		if (this.Is_player())
			System.out.println("You close your eyes and distance yourself from the battle. You will restore MP for the next 3 turns");
		else
			System.out.println("He seems to be restoring his MP");
		this.meditated = true;
		this.meditated_turns = 4;
		return true;
	}
	
	
}
