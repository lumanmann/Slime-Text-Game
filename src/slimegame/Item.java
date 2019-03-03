/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slimegame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.*;

/**
 *
 * @author wingyiu
 */
public class Item {
    // => 具有屬性 道具名稱 增長血量 增長魔力 增長攻擊力 增長技能發動機率
    // => 請設計六種以上道具，並依照以下JSON格式存入txt檔
    // => [{"name":"t","hp":10,"mp":10,"atk":5,"skillRate":2},{"name":"t","hp":0,"mp":0,"atk":50,"skillRate":2}]
    public static final String FILE_NAME = "items.txt";
    
    private String name;
    private int hp;
    private int mp;
    private int atk;
    private int skillRate;
    
    
     public Item() {
         
     }
    
    public Item(String name, int hp, int mp, int atk, int skillRate) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.atk = atk;
        this.skillRate = skillRate;
    }
    
    
    public JSONObject toJobj() throws JSONException{
        JSONObject i = new JSONObject();
        i.put("name", this.getName());
        i.put("hp", this.getHp());
        i.put("mp", this.getMp());
        i.put("atk", this.getAtk());
        i.put("skillRate", this.getSkillRate());
        
        return i;
    }
    
    
    public static void createItemList() throws JSONException, IOException{ 
        JSONArray jsonItems = new JSONArray();
        ArrayList<Item> array = new ArrayList();
        array.add(new Item("Apple", 0, 0, 0, 50));
        array.add(new Item("Candy", 40, 0, 0, 10));
        array.add(new Item("Rice", 10, 40, 10, 20));
        array.add(new Item("Pasta", 50, 10, 0, 10));
        array.add(new Item("Beef", 20,20, 50, 0));
        array.add(new Item("Pie", 20,50, 0, 10));
      
        
        for (int i = 0; i <array.size(); i++) {
         Item item = array.get(i);
         JSONObject obj = (item.toJobj());
         jsonItems.put(obj);
        }
        
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
       
        bw.append(jsonItems.toString());
        bw.flush();
        bw.close();
   
    }
    
    @Override
    public String toString() {
        return this.name + ": 血量+" + this.hp + ", 魔力+" +this.mp 
                + ", 攻擊力+" + this.atk + ", 技能發動機率+"+ this.skillRate;
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

    public int getAtk() {
        return atk;
    }

    public int getSkillRate() {
        return skillRate;
    }
    
    
    
    
}
