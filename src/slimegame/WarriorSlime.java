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
public class WarriorSlime extends Slime {
    
    // 戰士史萊姆會習得任一技能 buff=> 提升攻擊力, slash=> 造成攻擊力1.5倍傷害
    public static SlimeSkill buff = new SlimeSkill() {
            @Override
            public void skillAttack(Slime slime, Slime targetSlime) {
                int atk = slime.getCurrentAtk() + 20;
                slime.setCurrentAtk(atk);
                slime.decreaseMp(100);
                System.out.println(slime.getName() + " 使用了buff對提升了20點攻擊力");
            }
    };
    
    public static SlimeSkill slash = new SlimeSkill() {
            @Override
            public void skillAttack(Slime slime, Slime targetSlime) {
                int atk = (int)(slime.getCurrentAtk() * 1.5);
                targetSlime.decreaseHp(atk);
                slime.decreaseMp(50);
                System.out.println(slime.getName() + " 使用了slash對 " + targetSlime.getName() +
                        " 造成了" + atk +"點傷害");
            }
    };
    
    public WarriorSlime(String name, int hp, int mp, int atk, int eatNum, int skillRate, WarriorSlime.SlimeSkill skill) {
        super(name, hp, mp, atk, eatNum, skillRate);
        this.skill = skill;
        this.type = TYPE_WARRIOR;
        if (skill == WarriorSlime.buff) {
            this.skillTag = "buff";
        } 
        
        if (skill == WarriorSlime.slash) {
            this.skillTag = "slash";
        }
        
    }
}
