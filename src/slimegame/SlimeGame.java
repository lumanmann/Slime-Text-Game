/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slimegame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/**
 *
 * @author wingyiu
 */
public class SlimeGame {
    public static final String FILE_NAME = "slime.txt";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // 請設計一遊戲主程式依照以下順序進行

        // 讀入item.txt創建出ArrayList<Item>

        // 讓使用者選擇選項 1.創建史萊姆 2.讀取史萊姆

        // 1. =>建立一個新的史萊姆實體

        // 2. =>讓使用者輸入.txt檔名來讀取舊的史萊姆

        // 讓使用者選擇選項 1.養成 2.對戰 3.退出

        // 1. =>如果史萊姆還有進食次數，列出Item列表讓使用者選擇並進食

        //    =>進食後回到選擇選項

        // 2. =>讓使用者輸入.txt檔名來讀取第二隻史萊姆

        //    =>隨機一隻史萊姆先進行攻擊

        //    =>利用attack方法讓兩隻史萊姆輪流攻擊直到其中一方血量歸零

        //    =>請印出戰鬥過程(A使用了"slash"對B造成了40點傷害) 然後秀出雙方當前血量/魔力

        //    =>顯示哪一隻史萊姆勝利，並回到選項

        // 3. 統一於退出時進行存檔 => 將史萊姆依照以下JSON格式進行存檔

        //    =>檔名: 史萊姆名稱.txt

        //    => {"name":"史萊姆名稱","type":"warrior","skill":"slash","maxHp":1000,"maxMp":500,"atk":100,"feedTime":4,"skillRate":5}
        Scanner scanner = new Scanner(System.in);
        ArrayList<Item> itemArray = new ArrayList();
        ArrayList<Slime> slimeArray = new ArrayList();
        Slime mySlime = null;
        
        try {
            Item.createItemList();
        } catch (JSONException ex) {
            Logger.getLogger(SlimeGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SlimeGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
        while (true){
            System.out.println("請選擇： 1.創建史萊姆 2.讀取史萊姆");
            int opt;
            
            try {
                opt = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("請輸入數字： 1.創建史萊姆 2.讀取史萊姆");
                opt = scanner.nextInt();
            }
            
            while (opt != 1 && opt != 2) {
                continue;
            }
            
            if (opt == 1) {
                System.out.println("請選擇史萊姆的種類： 1. 戰士 2. 法師  其他數字：普通");
                int type = scanner.nextInt();
                System.out.println("請輸入史萊姆的名字:");
                String name = scanner.next();
                System.out.println("請輸入史萊姆的最大血量:");
                int hp = scanner.nextInt();
                System.out.println("請輸入史萊姆的最大魔力:");
                int mp = scanner.nextInt();
                System.out.println("請輸入史萊姆的攻擊力:");
                int atk = scanner.nextInt();
                System.out.println("請輸入史萊姆的可進食次數:");
                int eatTime = scanner.nextInt();
                System.out.println("請輸入史萊姆的技能發動機率:");
                int skillRate = scanner.nextInt();
                
                if (type == 1) {
                    System.out.println("請選擇戰士史萊姆的技能： 1. buff 2. slash");
                    int skill = scanner.nextInt();
                    while (skill != 1 && skill != 2) {
                        System.out.println("請選擇戰士史萊姆的技能： 1. buff 2. slash");
                        skill = scanner.nextInt();
                    }
                    
                    if (skill == 1){
                        mySlime = new WarriorSlime(name, hp, mp, atk, eatTime, skillRate, WarriorSlime.buff);
                    } else {
                        mySlime = new WarriorSlime(name, hp, mp, atk, eatTime, skillRate, WarriorSlime.slash);
                    }
                    
                } else if (type == 2) {
                    System.out.println("請選擇法師史萊姆的技能： 1. fire 2. heal");
                    int skill = scanner.nextInt();
                    while (skill != 1 && skill != 2) {
                        continue;
                    }
                    
                    if (skill == 1){
                        mySlime = new MageSlime(name, hp, mp, atk, eatTime, skillRate, MageSlime.fire);
                    } else {
                        mySlime = new MageSlime(name, hp, mp, atk, eatTime, skillRate, MageSlime.heal);
                    }
                } else {
                     mySlime = new Slime(name, hp, mp, atk, eatTime, skillRate);
                }
                
                
                slimeArray.add(mySlime);
                
            
                try {
                    writeSlimeFile(mySlime);
                } catch (JSONException ex) {
                    Logger.getLogger(SlimeGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                break;
            }
            
            if (opt == 2) {
                // 2. =>讓使用者輸入.txt檔名來讀取舊的史萊姆
                System.out.println("請選擇史萊姆:");
                String name = scanner.next();
                mySlime = readFile(name); 
                slimeArray.add(mySlime);
                break;
             
            }
            
        }
         
        
        while (true) {
            
            System.out.println("請選擇： 1.養成 2.對戰 3.退出");
            int opt = scanner.nextInt();
            
            
            if (opt == 1) {
                // 1. =>如果史萊姆還有進食次數，列出Item列表讓使用者選擇並進食
                if (mySlime.getEatNum() < 0) {
                   System.out.println("你的史萊姆沒有進食次數了");
                }
                System.out.println("請選擇要吃的食物：");
                try {
                    itemArray = readFile(itemArray, Item.FILE_NAME);
                } catch (JSONException ex) {
                    Logger.getLogger(SlimeGame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SlimeGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                printArray(itemArray);
                int choice = scanner.nextInt();
                mySlime.eat(itemArray.get(choice-1));
                continue;  //    =>進食後回到選擇選項
            }
            
             if (opt == 2) {
                // 2. =>讓使用者輸入.txt檔名來讀取第二隻史萊姆
                System.out.println("請選擇要對戰的史萊姆：");
                String enemy = scanner.next();
                Slime enemySlime = readFile(enemy);
                slimeArray.add(enemySlime);
                
                while (true) {
//                    if (mySlime.getHp()<0 || enemySlime.getHp()<0 ){
//                        break;
//                    }
                    scanner.nextLine();
                    
                    mySlime.attack(enemySlime);
                    enemySlime.attack(mySlime);
                    
                    System.out.println("雙方當前血量/魔力:");
                    System.out.println(mySlime);
                    System.out.println(enemySlime);
                    
                    if (enemySlime.getHp()<=0 ){
                        System.out.println( mySlime.getName() + " 贏了");
                        break;
                    }
                    
                    if (mySlime.getHp()<=0){
                        System.out.println( enemySlime.getName()+ " 贏了");
                        break;
                    }
                    
                    
                }
                
                
            }
            
            
             if (opt == 3) {
                 for (int i=0; i< slimeArray.size(); i++) {
                     try {
                         writeSlimeFile(slimeArray.get(i));
                     } catch (JSONException ex) {
                         System.out.println("存檔JSONException");
                     }
                 }
                 break;
             }
            
        }
        
    }
    
    
    private static ArrayList readFile(ArrayList arrayList, String fileName) throws JSONException, FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while(br.ready()){

            JSONArray array = new JSONArray(br.readLine());

            
                for(int i=0;i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);
                //System.out.println(obj);
                Item tmp = new Item(obj.getString("name"), 
                        obj.getInt("hp"),
                        obj.getInt("mp"),
                        obj.getInt("atk"),
                        obj.getInt("skillRate")
                );
                //System.out.print(tmp);
                arrayList.add(tmp); 
                }
            

        }
        br.close();
        return arrayList;
    }
    
    private static Slime readFile(String name) {
        String fileName = name + ".txt";
        BufferedReader br = null;
        Slime tmp = null;
        
        try {
            br = new BufferedReader(new FileReader(fileName));
            
            while(br.ready()){

                JSONObject obj = new JSONObject(br.readLine());

                //System.out.println(obj);
                
                if (obj.getString("type").equals(Slime.TYPE_NOR)){
                    tmp = new Slime(obj.getString("name"),
                        obj.getInt("maxHp"),
                        obj.getInt("maxMp"),
                        obj.getInt("atk"),
                        obj.getInt("eatNum"),
                        obj.getInt("skillRate"));
                } else {
                    switch(obj.getString("skill")){
                        case "buff":
                            tmp = new WarriorSlime(obj.getString("name"),
                                obj.getInt("maxHp"),
                                obj.getInt("maxMp"),
                                obj.getInt("atk"),
                                obj.getInt("eatNum"),
                                obj.getInt("skillRate"), WarriorSlime.buff);
                        case "slash":
                            tmp = new WarriorSlime(obj.getString("name"),
                                obj.getInt("maxHp"),
                                obj.getInt("maxMp"),
                                obj.getInt("atk"),
                                obj.getInt("eatNum"),
                                obj.getInt("skillRate"), WarriorSlime.slash);
                        case "fire":
                            tmp = new MageSlime(obj.getString("name"),
                                obj.getInt("maxHp"),
                                obj.getInt("maxMp"),
                                obj.getInt("atk"),
                                obj.getInt("eatNum"),
                                obj.getInt("skillRate"), MageSlime.fire);
                        case "heal":
                            tmp = new MageSlime(obj.getString("name"),
                                obj.getInt("maxHp"),
                                obj.getInt("maxMp"),
                                obj.getInt("atk"),
                                obj.getInt("eatNum"),
                                obj.getInt("skillRate"), MageSlime.heal);
                    }
                }
                
                //System.out.print(tmp);
                
            }  
            
        } catch (FileNotFoundException ex) {
            System.out.println(name +" 不存在");
            Logger.getLogger(SlimeGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException | IOException ex) {
            System.out.println(name +" JSONException/IOException");
            Logger.getLogger(SlimeGame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {  
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(SlimeGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return tmp;
    }
    
    
    private static void writeSlimeFile(Slime slime) throws JSONException{
        String fileName = slime.getName() + ".txt";
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            JSONObject obj = (slime.toJobj());
            bw.append(obj.toString());
            bw.flush();
            bw.close();
        }catch (FileNotFoundException e){
            System.out.println("file not found");
        }catch (IOException e){
            System.out.println("IOException ");
        }
    }
    
    private static void printArray(ArrayList arrayList){ 
        for (int i = 0; i <arrayList.size(); i++) {
            System.out.println((i+1) + ". " + arrayList.get(i));
         }
            
    }   
    
    
}
