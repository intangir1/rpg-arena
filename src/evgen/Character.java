package evgen;


public class Character {
	private String name;
	private String job;
	private int max_hp;
	private int hp;
	private int max_mp;
	private int mp;
	private int str;
	private int def;
	private int spd;
	private int skl;
	private boolean is_player;
	
	private boolean poisoned = false;
	private int poisoned_turns = 0;
	private boolean charmed = false;
	private int charmed_turns = 0;
	private boolean sealed = false;
	private int sealed_turns = 0;
	private boolean str_broken = false;
	private int str_broken_turns = 0;
	private boolean def_broken = false;
	private int def_broken_turns = 0;
	private boolean spd_broken = false;
	private int spd_broken_turns = 0;
	private boolean skl_broken = false;
	private int skl_broken_turns = 0;
	
	
	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public int getMax_hp() {
		return max_hp;
	}
	
	
	public int getHp() {
		return hp;
	}


	public void setHp(int change) {
		if (this.hp + change >= this.max_hp)
			this.hp = this.max_hp;
		else if (this.hp + change <= 0)
			this.hp = 0;
		else
			this.hp += change;
	}


	public void setMax_hp(int max_hp) {
		this.max_hp = max_hp;
	}
	
	
	public void setHP_to_max(){
		this.hp = this.max_hp;
	}


	public int getMp() {
		return mp;
	}
	
	
	public boolean is_enough_MP(int cost){
		if (this.mp >= cost)
			return true;
		System.out.println("Not enough MP!\n");
		return false;
	}


	public void setMp(int change) {
		if (this.mp + change >= this.max_mp)
			this.mp = this.max_mp;
		else if (this.mp + change <= 0)
			this.mp = 0;
		else
			this.mp += change;
	}
	
	
	public void setMP_to_max(){
		this.mp = this.max_mp;
	}


	public int getMax_mp() {
		return max_mp;
	}


	public void setMax_mp(int max_mp) {
		this.max_mp = max_mp;
	}


	public int getStr() {
		return str;
	}


	public void setStr(int str) {
		this.str = str;
	}


	public int getDef() {
		return def;
	}


	public void setDef(int def) {
		this.def = def;
	}


	public int getSpd() {
		return spd;
	}


	public void setSpd(int spd) {
		this.spd = spd;
	}


	public int getSkl() {
		return skl;
	}


	public void setSkl(int skl) {
		this.skl = skl;
	}


	public boolean isPoisoned() {
		return poisoned;
	}


	public void setPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
	}


	public int getPoison_turns() {
		return poisoned_turns;
	}


	public void setPoison_turns(int poison_turns) {
		this.poisoned_turns = poison_turns;
	}


	public boolean isCharmed() {
		return charmed;
	}


	public void setCharmed(boolean charmed) {
		this.charmed = charmed;
	}


	public int getCharmed_turns() {
		return charmed_turns;
	}


	public void setCharmed_turns(int charm_turns) {
		this.charmed_turns = charm_turns;
	}


	public boolean isSealed() {
		return sealed;
	}
	
	
	public boolean isSealed_message(){
		if (this.sealed)
			System.out.println("This command is sealed!\n");
		return sealed;
	}
	
	
	public void Show_sealed_word(){
		if (this.sealed)
			System.out.println(" -SEALED-");
		else
			System.out.println();
	}


	public void setSealed(boolean sealed) {
		this.sealed = sealed;
	}


	public int getSealed_turns() {
		return sealed_turns;
	}


	public void setSealed_turns(int sealed_turns) {
		this.sealed_turns = sealed_turns;
	}


	public boolean isStr_broken() {
		return str_broken;
	}


	public void setStr_broken(boolean str_broken) {
		this.str_broken = str_broken;
	}


	public int getStr_broken_turns() {
		return str_broken_turns;
	}


	public void setStr_broken_turns(int str_broken_turns) {
		this.str_broken_turns = str_broken_turns;
	}


	public boolean isDef_broken() {
		return def_broken;
	}


	public void setDef_broken(boolean def_broken) {
		this.def_broken = def_broken;
	}


	public int getDef_broken_turns() {
		return def_broken_turns;
	}


	public void setDef_broken_turns(int def_broken_turns) {
		this.def_broken_turns = def_broken_turns;
	}


	public boolean isSpd_broken() {
		return spd_broken;
	}


	public void setSpd_broken(boolean spd_broken) {
		this.spd_broken = spd_broken;
	}


	public int getSpd_broken_turns() {
		return spd_broken_turns;
	}


	public void setSpd_broken_turns(int spd_broken_turns) {
		this.spd_broken_turns = spd_broken_turns;
	}


	public boolean isSkl_broken() {
		return skl_broken;
	}


	public void setSkl_broken(boolean skl_broken) {
		this.skl_broken = skl_broken;
	}


	public int getSkl_broken_turns() {
		return skl_broken_turns;
	}


	public void setSkl_broken_turns(int skl_broken_turns) {
		this.skl_broken_turns = skl_broken_turns;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public boolean Is_player() {
		return is_player;
	}


	public void setIs_player(boolean is_player) {
		this.is_player = is_player;
	}
	
	
	public void Action(Character target){
		this.Reduce_buffs();
		this.Reduce_debuffs();
		if (this.hp == 0){
			Main.scn.nextLine();
			return;
		}
		if (this.charmed){
			if (this.is_player)
				System.out.println("You're still charmed and skip the turn");
			else
				System.out.println(this.getName() + " is under charms and skips the turn");
		}
		else if (this.is_player){
			if (this instanceof Warrior)
				((Warrior)this).Commands(target);
			else if (this instanceof Thief)
				((Thief)this).Commands(target);
			else if (this instanceof Mage)
				((Mage)this).Commands(target);
			else if (this instanceof Creator)
				((Creator)this).Commands(target);
		}
		else{
			if (this instanceof Warrior)
				((Warrior)this).Commands_ai(target);
			else if (this instanceof Thief)
				((Thief)this).Commands_ai(target);
			else if (this instanceof Mage)
				((Mage)this).Commands_ai(target);
			else if (this instanceof Creator)
				((Creator)this).Commands_ai(target);
		}
		Main.scn.nextLine();
	}


	public boolean Clear_debuffs(){
		boolean found_debuff = false;
		if (this.poisoned){
			this.poisoned = false;
			this.poisoned_turns = 0;
			found_debuff = true;
		}
		if (this.charmed){
			this.charmed = false;
			this.charmed_turns = 0;
			found_debuff = true;
		}
		if (this.sealed){
			this.sealed = false;
			this.sealed_turns = 0;
			found_debuff = true;
		}
		if (this.str_broken){
			this.str_broken = false;
			this.str_broken_turns = 0;
			found_debuff = true;
		}
		if (this.def_broken){
			this.def_broken = false;
			this.def_broken_turns = 0;
			found_debuff = true;
		}
		if (this.spd_broken){
			this.spd_broken = false;
			this.spd_broken_turns = 0;
			found_debuff = true;
		}
		if (this.skl_broken){
			this.skl_broken = false;
			this.skl_broken_turns = 0;
			found_debuff = true;
		}
		return found_debuff;
	}
	
	
	public boolean Is_debuffed(Character target) {
		if (target.poisoned || target.charmed || target.sealed || target.str_broken ||
			target.def_broken	|| target.spd_broken || target.skl_broken)
			return true;
		return false;
	}
	
	
	public void Show_debuffs(Character target){
		if (target.poisoned)
			System.out.println("	'POISONED' (" + target.poisoned_turns + " turns)");
		if (target.charmed)
			System.out.println("	'CHARMED' (" + target.charmed_turns + " turns)");
		if (target.sealed)
			System.out.println("	'SEALED' (" + target.sealed_turns + " turns)");
		if (target.str_broken)
			System.out.println("	'STRENGTH-BROKEN' (" + target.str_broken_turns + " turns)");
		if (target.def_broken)
			System.out.println("	'DEFENCE-BROKEN' (" + target.def_broken_turns + " turns)");
		if (target.spd_broken)
			System.out.println("	'SPEED-BROKEN' (" + target.spd_broken_turns + " turns)");
		if (target.skl_broken)
			System.out.println("	'SKILL-BROKEN' (" + target.skl_broken_turns + " turns)");
	}
	
	
	public void Reduce_debuffs(){
		if (this.poisoned){
			this.poisoned_turns --;
			if (this.poisoned_turns == 0){
				System.out.println("'POISONED' expired");
				this.poisoned = false;
			}
			else{
				int random = Main.rnd.nextInt((8 - 4) + 1) + 4;
				this.setHp(-random);
				System.out.println("Lost " + random + " HP from the poison");
			}
		}
		if (this.charmed){
			this.charmed_turns --;
			if (this.charmed_turns == 0){
				System.out.println("'CHARMED' expired");
				this.charmed = false;
			}
		}
		if (this.sealed){
			this.sealed_turns --;
			if (this.sealed_turns == 0){
				System.out.println("'SEALED' expired");
				this.sealed = false;
			}
		}
		if (this.str_broken){
			this.str_broken_turns --;
			if (this.str_broken_turns == 0){
				System.out.println("'STRENGTH-BROKEN' expired");
				this.str_broken = false;
			}
		}
		if (this.def_broken){
			this.def_broken_turns --;
			if (this.def_broken_turns == 0){
				System.out.println("'DEFENCE-BROKEN' expired");
				this.def_broken = false;
			}
		}
		if (this.spd_broken){
			this.spd_broken_turns --;
			if (this.spd_broken_turns == 0){
				System.out.println("'SPEED-BROKEN' expired");
				this.spd_broken = false;
			}
		}
		if (this.skl_broken){
			this.skl_broken_turns --;
			if (this.skl_broken_turns == 0){
				System.out.println("'SKILL-BROKEN' expired");
				this.skl_broken = false;
			}
		}
	}
	
	
	public boolean Clear_buffs(Character target){
		boolean found_buff = false;
		if (target instanceof Warrior && ((Warrior)target).isBerserked()){
			((Warrior)target).setBerserked(false);
			((Warrior)target).setBerserked_turns(-((Warrior)target).getBerserked_turns());
			found_buff = true;
		}
		if (target instanceof Thief && ((Thief)target).isEnergized()){
			((Thief)target).setEnergized(false);
			((Thief)target).setEnergized_turns(-((Thief)target).getEnergized_turns());
			found_buff = true;
		}
		if (target instanceof Mage && ((Mage)target).isMeditated()){
			((Mage)target).setMeditated(false);
			((Mage)target).setMeditated_turns(-((Mage)target).getMeditated_turns());
			found_buff = true;
		}
		if (target instanceof Mage && ((Mage)target).isConcentrated()){
			((Mage)target).setConcentrated(false);
			((Mage)target).setConcentrated_turns(-((Mage)target).getConcentrated_turns());
			found_buff = true;
		}
		return found_buff;
	}
	
	
	public void Show_buffs(Character target){
		if (target instanceof Warrior && ((Warrior)target).isBerserked())
			System.out.println("	'BERSERKED' (" + ((Warrior)target).getBerserked_turns() + " turns)");
		if (target instanceof Thief && ((Thief)target).isEnergized())
			System.out.println("	'ENERGIZED' (" + ((Thief)target).getEnergized_turns() + " turns)");
		if (target instanceof Mage && ((Mage)target).isMeditated())
			System.out.println("	'MEDITATED' (" + ((Mage)target).getMeditated_turns() + " turns)");
		if (target instanceof Mage && ((Mage)target).isConcentrated())
			System.out.println("	'CONCENTRATED' (" + ((Mage)target).getConcentrated_turns() + " turns)");
	}
	
	
	public boolean Is_buffed(Character target){
		if (	(target instanceof Warrior && ((Warrior)target).isBerserked()) ||
				(target instanceof Thief && ((Thief)target).isEnergized())	||
				(target instanceof Mage && ((Mage)target).isMeditated()) ||
				(target instanceof Mage && ((Mage)target).isConcentrated())	)
			return true;
		return false;
	}
	
	
	public void Reduce_buffs(){
		if (this instanceof Warrior && ((Warrior)this).isBerserked()){
			((Warrior)this).setBerserked_turns(-1);
			if(((Warrior)this).getBerserked_turns() == 0){
				System.out.println("'BERSERKED' expired");
				((Warrior)this).setBerserked(false);
			}
		}
		if (this instanceof Warrior && ((Warrior)this).isDefended())
			((Warrior)this).setDefended(false);
		
		if (this instanceof Thief && ((Thief)this).isEnergized()){
			((Thief)this).setEnergized_turns(-1);
			if(((Thief)this).getEnergized_turns() == 0){
				System.out.println("'ENERGIZED' expired");
				((Thief)this).setEnergized(false);
			}
			else{
				int random = Main.rnd.nextInt((8 - 4) + 1) + 4;
				this.setHp(random);
				System.out.println("Restored " + random + " HP from the energy drink");
			}
		}
		if (this instanceof Thief && ((Thief)this).isReflexed())
			((Thief)this).setReflexed(false);
		
		if (this instanceof Mage && ((Mage)this).isMeditated()){
			((Mage)this).setMeditated_turns(-1);
			if(((Mage)this).getMeditated_turns() == 0){
				System.out.println("'MEDITATED' expired");
				((Mage)this).setMeditated(false);
			}
			else{
				int random = Main.rnd.nextInt((10 - 5) + 1) + 5;
				this.setMp(random);
				System.out.println("Restored " + random + " MP from the meditation");
			}
		}
		if (this instanceof Mage && ((Mage)this).isConcentrated()){
			((Mage)this).setConcentrated_turns(-1);
			if(((Mage)this).getConcentrated_turns() == 0){
				System.out.println("'CONCENTRATED' expired");
				((Mage)this).setConcentrated(false);
			}
		}
	}
	
	
	public boolean Is_double(int attacker_spdeed, int receiver_spdeed) {
		if (attacker_spdeed - receiver_spdeed >= 4)
			return true;
		return false;
	}
	
	
	public boolean Is_miss(Character target) {
		int evasion = target.spd * 4;
		if (target instanceof Thief && ((Thief) target).isReflexed())
			evasion *= 3;
		if (target.spd_broken)
			evasion = (int) (evasion / 2);
		
		int hit = this.skl * 2;
		if (this.skl_broken)
			hit = (int) (hit / 2);
		
		evasion -= hit;
		int percent = Main.rnd.nextInt(100) + 1;
		if (evasion > percent){
			if (this.is_player)
				System.out.println("Oh no! " + target.getName() + " dodged the attack!");
			else
				System.out.println("Yes! " +  target.getName() + " managed to dodge the attack!");
			return true;
		}
		return false;
	}
	
	
	public void Damage(double multiplier, Character target, boolean critical_allowed) {
		int damage = this.str;
		if (this instanceof Warrior && ((Warrior) this).isBerserked())
			damage = (int) (damage * 1.5);
		else if (this instanceof Mage && ((Mage) this).isConcentrated())
			damage = (int) (damage * 2.5);
		if (this.str_broken)
			damage = (int) (damage / 2);
		damage = (int) (damage * multiplier);
		
		int guard = target.def;
		if (target instanceof Warrior && ((Warrior) target).isDefended())
			guard *= 2;
		if (target.def_broken)
			guard = (int) (guard / 2);
		
		damage -= guard;
		if (damage > 0){
			if (critical_allowed && Is_critical())
				damage *= 2;
			System.out.println("The damage was " + damage + " HP");
			target.setHp(-damage);
		}
		else
			System.out.println("The attack was worthless");
	}
	
	
	public boolean Is_critical() {
		int crit = this.skl * 3;
		if (this.skl_broken)
			crit = (int) (crit / 2);
		
		int percent = Main.rnd.nextInt(100) + 1;
		if (crit > percent){
			System.out.print("It's a CRITICAL HIT! ");
			return true;
		}
		return false;
	}
	
	
	public void Show_information(Character target){
		System.out.println(target.name + "s status:\n-------------------------------------");
		System.out.println("HP: " + target.getHp() + "/" + target.getMax_hp());
		System.out.println("MP: " + target.getMp() + "/" + target.getMax_mp());
		if (target.Is_buffed(target)){
			System.out.println("BUFFS:");
			target.Show_buffs(target);
		}
		if (target.Is_debuffed(target)){
			System.out.println("DEBUFFS:");
			target.Show_debuffs(target);
		}
	}
	
	
	private void Add_points_command(int points){
		System.out.println("You have " + points + " bonus points to add\nChoose where:");
		System.out.println("	1) Max HP (+5)");
		System.out.println("	2) Max Mp (+5)");
		System.out.println("	3) Strength (+1)");
		System.out.println("	4) Defence (+1)");
		System.out.println("	5) Speed (+1)");
		System.out.println("	6) Skill (+1)");
		System.out.println("	7) Random (one point)");
		System.out.println("	8) Random (all points)");
		System.out.println("	9) See stats");
	}
	
	
	private boolean Is_maxed_stats(){
		if (this.max_hp == 100 && this.max_mp == 100 && 
			this.str == 15 && this.def == 15 &&
			this.spd == 15 && this.skl == 15){
			System.out.println(this.name + " reached the power that equals those of gods!");
			Main.scn.nextLine();
			return true;
		}
		return false;
	}
	
	
	public void Random_points(int points){
		int random;
		for (int i = 0; i < points; i++){
			if (this.Is_maxed_stats())
				return;
			random = Main.rnd.nextInt(6) + 1;
			if (random == 1){
				if (this.max_hp == 100)
					i--;
				else
					this.max_hp += 5;
			}
			else if (random == 2){
				if (this.max_mp == 100)
					i--;
				else
					this.max_mp += 5;
			}
			else if (random == 3){
				if (this.str == 15)
					i--;
				else
					this.str++;
			}
			else if (random == 4){
				if (this.def == 15)
					i--;
				else
					this.def++;
			}
			else if (random == 5){
				if (this.spd == 15)
					i--;
				else
					this.spd++;
			}
			else{
				if (this.skl == 15)
					i--;
				else
					this.skl++;
			}
		}
	}
	
	
	public void Show_stats(){
		System.out.println(this.name + "s stats:");
		System.out.print("	Max HP: " + this.getMax_hp());
		if (this.max_hp == 100){
			System.out.println("	(MAXED)");
		}
		else
			System.out.println();
		System.out.print("	Max MP: " + this.getMax_mp());
		if (this.max_mp == 100){
			System.out.println("	(MAXED)");
		}
		else
			System.out.println();
		System.out.print("	STRENGTH: " + this.str);
		if (this.str == 15){
			System.out.println("	(MAXED)");
		}
		else
			System.out.println();
		System.out.print("	DEFENCE: " + this.def);
		if (this.def == 15){
			System.out.println("	(MAXED)");
		}
		else
			System.out.println();
		System.out.print("	SPEED: " + this.spd);
		if (this.spd == 15){
			System.out.println("	(MAXED)");
		}
		else
			System.out.println();
		System.out.print("	SKILL: " + this.skl);
		if (this.skl == 15){
			System.out.println("	(MAXED)");
		}
		else
			System.out.println();
		Main.scn.nextLine();
	}
	
	
	public void Add_points(int points){
		this.Show_stats();
		while(points > 0){
			if (this.Is_maxed_stats()){
				System.out.print("Final ");
				this.Show_stats();
				return;
			}
			Add_points_command(points);
			int choice = Main.Get_integer();
			switch(choice){
				case 1:
					if (this.max_hp == 100)
						System.out.println("---Reached maximum Max HP---");
					else{
						this.max_hp += 5;
						points--;
					}
					break;
				case 2:
					if (this.max_mp == 100)
						System.out.println("---Reached maximum Max MP---");
					else{
						this.max_mp += 5;
						points--;
					}
					break;
				case 3:
					if (this.str == 15)
						System.out.println("---Reached maximum STRENGTH---");
					else{
						this.str++;
						points--;
					}
					break;
				case 4:
					if (this.def == 15)
						System.out.println("---Reached maximum DEFENCE---");
					else{
						this.def++;
						points--;
					}
					break;
				case 5:
					if (this.spd == 15)
						System.out.println("---Reached maximum SPEED---");
					else{
						this.spd++;
						points--;
					}
					break;
				case 6:
					if (this.skl == 15)
						System.out.println("---Reached maximum SKILL---");
					else{
						this.skl++;
						points--;
					}
					break;
				case 7:
					this.Random_points(1);
					points--;
					break;
				case 8:
					this.Random_points(points);
					points = 0;
					break;
				case 9:
					this.Show_stats();
					break;
				default:
					System.out.println("Wrong number input!\n");
			}
		}
		System.out.print("Final ");
		this.Show_stats();
	}
	
	
	public void Show_fighters(Character squall, Character seifer){
		System.out.println("		" + squall.name + " the " + squall.job + "	VS	" + seifer.name + " the " + seifer.job);
		System.out.println("HP:		" + squall.hp + "/" + squall.max_hp + "			" + seifer.hp + "/" + seifer.max_hp);
		System.out.println("MP:		" + squall.mp + "/" + squall.max_mp + "			" + seifer.mp + "/" + seifer.max_mp);
		System.out.println("STRENGTH:	" + squall.str + "			" + seifer.str);
		System.out.println("DEFENCE:	" + squall.def + "			" + seifer.def);
		System.out.println("SPEED:		" + squall.spd + "			" + seifer.spd);
		System.out.println("SKILL:		" + squall.skl + "			" + seifer.skl);
		Main.scn.nextLine();
	}
	
	
	public void Battle (Character seifer){
		Show_fighters(this, seifer);
		boolean player_turn = Main.rnd.nextBoolean();
		if (player_turn)
			System.out.println(this.name + " begins!");
		else
			System.out.println(seifer.name + " begins!");
		Main.scn.nextLine();
		while(this.hp > 0 && seifer.hp > 0){
			if (player_turn)
				this.Action(seifer);
			else
				seifer.Action(this);
			player_turn = !player_turn;
		}
		if (this.hp == 0)
			System.out.print(this.name);
		else
			System.out.print(seifer.name);
		System.out.println(" was defeated");
		Main.scn.nextLine();
	}
	
	
}
