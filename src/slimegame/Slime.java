/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slimegame;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author wingyiu
 */
public class Slime {
     // 撰寫一個類別Slime

        // => 具有屬性 名稱 最大血量 血量 最大魔力 魔力 攻擊力 進食次數(遞減int) 技能發動機率(可以使用int來設計,0~100表示機率) Skill

        // => 具有內部介面 Skill => 介面中具有skillAttack(Slime slime, Slime targetSlime)方法決定技能的內容=> slime=使用者,targetSlime=敵人

        // => 具有normalAttack(Slime slime)方法進行普通攻擊

        // => 具有attack(Slime slime)方法來通過技能發動機率決定進行的攻擊方式

        // => 具有eat(Item item)方法進食並且加強屬性
    public static final String TYPE_NOR = "Normal";
    public static final String TYPE_WARRIOR = "Warrior";
    public static final String TYPE_MAGE = "Mage";
    public String skillTag;
    private final int maxHp; // 最大血量 
    private final int maxMp; // 最大魔力
    private String name;
    private int hp;
    private int mp;
    private int atk; //攻擊力
    private int currentAtk;
    private int eatNum;// 進食次數
    private int skillRate; //技能發動機率
    
    protected SlimeSkill skill;
    protected String type;
    
    public interface SlimeSkill {
      
        public void skillAttack(Slime slime, Slime targetSlime);
    }
    
    
    public Slime(String name, int hp, int mp, int atk, int eatNum, int skillRate) {
        this.name = name;
        this.maxHp = hp;
        this.maxMp = mp;
        this.hp = hp;
        this.mp = mp;
        this.atk = atk;
        this.eatNum = eatNum;
        this.skillRate = skillRate;
        this.type = TYPE_NOR;
        this.currentAtk = atk;
      
    }
    
    public void eat(Item item) {
        this.hp += item.getHp();
        this.mp += item.getMp();
        this.atk += item.getAtk();
        this.skillRate += item.getSkillRate();
        
        if ( this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
        
        if ( this.mp > this.maxMp) {
            this.mp = this.maxMp;
        }
        eatNum--;
    }
    
    public void attack(Slime slime) {
        if (skill != null && this.mp > 0) {
            int rate = (int)(Math.random() * 101);
            
            if (rate < this.skillRate){
                this.skill.skillAttack(this, slime);
            } else {
                normalAttack(slime);
            }
        } else {
            normalAttack(slime);
        }
    }
    
    private void normalAttack(Slime slime) {
        int atk = this.getCurrentAtk();
        slime.decreaseHp(atk);
    
        System.out.println(this.getName() + "使用了normalAttack對" + slime.getName() +
                        "造成了" + atk +"點傷害");
    }
    
    public JSONObject toJobj() throws JSONException{
        JSONObject s = new JSONObject();
        s.put("name", this.name);
        s.put("maxHp", this.maxHp);
        s.put("maxMp", this.maxMp);
        s.put("type", this.type);
        s.put("atk", this.atk);
        s.put("eatNum", this.eatNum);
        s.put("skillRate", this.skillRate);
        s.put("skill", this.skillTag);
        
        return s;
    }

    @Override
    public String toString() {
        return this.name + "  目前血量: " + this.hp + ", 目前魔力:" +this.mp;
               // + ", 攻擊力+" + this.atk + ", 技能發動機率+"+ this.skillRate;
    }
  
    public int getMaxMp() {
        return maxMp;
    }

    public String getName() {
        return name;
    }

   
    public int getHp() {
        return hp;
    }

   
    public int getMp() {
        return mp;
    }

   
    public int getCurrentAtk() {
        return atk;
    }

   
    public int getEatNum() {
        return eatNum;
    }

    
    public int getSkillRate() {
        return skillRate;
    }

    
    public void decreaseHp(int hp) {
        this.hp -= hp;
        if (this.hp < 0){
            this.hp = 0;
        }
    }
    
    public void increaseHp(int hp) {
        this.hp += hp;
        if (this.hp > this.maxHp){
            this.hp = this.maxHp;
        }
    }

    public void decreaseMp(int mp) {
        this.mp -= mp;
        if (this.mp < 0){
            this.mp = 0;
        }
    }
    
    public void increaseMp(int mp) {
        this.mp += mp;
        if (this.mp > this.maxMp){
            this.mp = this.maxMp;
        }
    }

    public void setCurrentAtk(int atk) {
        this.currentAtk += atk;
    }
    
    public void setEatNum(int eatNum) {
        this.eatNum = eatNum;
    }

    
    public void setSkillRate(int skillRate) {
        this.skillRate = skillRate;
    }

    public void setSkill(SlimeSkill skill) {
        this.skill = skill;
    }
}
