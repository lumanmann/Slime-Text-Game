/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slimegame;

/**
 *
 * @author wingyiu
 */
public class MageSlime extends Slime {
      // 法師史萊姆會習得任一技能 fire=> 造成攻擊力2.5倍傷害, heal=> 回復攻擊力2.5倍血量
    public static SlimeSkill fire = new SlimeSkill() {
            @Override
            public void skillAttack(Slime slime, Slime targetSlime) {
                int atk = (int)(slime.getCurrentAtk() * 2.5);
                targetSlime.decreaseHp(atk);
                slime.decreaseMp(100);
                System.out.println(slime.getName() + " 使用了fire對 " + targetSlime.getName() +
                        "造成了" + atk +"點傷害");
            }
            
            @Override
            public String toString() {
                return "fire";
            }
    };
    
    public static SlimeSkill heal = new SlimeSkill() {
            @Override
            public void skillAttack(Slime slime, Slime targetSlime) {
                int heal = (int)(slime.getCurrentAtk() * 2.5);
                slime.increaseHp(heal);
                slime.decreaseMp(100);
                System.out.println(slime.getName() + " 使用了heal回復了" + heal +"點生命值");
            }
    };
    
    public MageSlime(String name, int hp, int mp, int atk, int eatNum, int skillRate, MageSlime.SlimeSkill skill) {
        super(name, hp, mp, atk, eatNum, skillRate);
        this.skill = skill;
        this.type = TYPE_MAGE;
        if (skill == MageSlime.fire) {
            this.skillTag = "fire";
        } 
        
        if (skill == MageSlime.heal) {
            this.skillTag = "heal";
        }
    }
    
}
