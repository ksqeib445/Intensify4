package ksqeib.Intensify.util;

import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.store.Stone;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelCalc {
    HashMap<String, Double> arms;
    HashMap<String, Double> helmet;
    HashMap<String, Double> chestplate;
    HashMap<String, Double> leggings;
    HashMap<String, Double> boots;

    private  final String num[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private  final String fnum[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private  final String hnum[] = {"蕶","噎","弍","彡","sī","㈤","⒍","㈦","仈","氿"};
    private  final String unit[] = {"星","拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
    public ArrayList<String> types = new ArrayList<String>() {
        {
            add("arms");
            add("helmet");
            add("chestplate");
            add("leggings");
            add("boots");
        }
    };
    public ArrayList<String> sectypes = new ArrayList<String>() {
        {
            add("damage");
            add("bloodSuck");
            add("defense");
            add("reboundDamage");
            add("experience");
        }
    };
    public static String cln="cuilel";
    public List<String> getLevelString(String sectype) {
        if (!sectypes.contains(sectype)) return null;
        return Intensify.um.getTip().getMessageList(sectype + "_LORE");
    }

    public double getLelDouble(String type, String sectype, int lel) {
        if (!types.contains(type)) return 0;
        if (!sectypes.contains(sectype)) return 0;
        switch (type) {
            case "arms":
                return arms.get(sectype) * lel;
            case "helmet":
                return helmet.get(sectype) * lel;
            case "chestplate":
                return chestplate.get(sectype) * lel;
            case "leggings":
                return leggings.get(sectype) * lel;
            case "boots":
                return boots.get(sectype) * lel;
        }
        return 0;
    }

    public void init(FileConfiguration yml) {
        arms = loada("arms", yml);
        helmet = loada("helmet", yml);
        chestplate = loada("chestplate", yml);
        leggings = loada("leggings", yml);
        boots = loada("boots", yml);
    }

    private HashMap<String, Double> loada(String name, FileConfiguration yml) {
        HashMap<String, Double> hash = new HashMap<String, Double>();
        for (String sectype : sectypes) {
            hash.put(sectype, yml.getDouble(name + "." + sectype));
        }
        return hash;
    }

    public List<String> makeLelStr(int i){
        ArrayList<String> lo=new ArrayList<>();
        int r1 =i%5;
        int r5 =(i-r1)/5;
        String out=Intensify.um.getTip().getMessageList("lore_head").get(0);
        while(r5>1){
            r5--;
            out+=Intensify.dataer.r5;
        }
        while(r1>0){
            r1--;
            out+=Intensify.dataer.r1;
        }
        out+=int2big(i);
        lo.add(out+Intensify.um.getTip().getMessageList("lore_end").get(0));
        return lo;
    }

    private String int2big(int src) {
        String dst = "";
        int count = 0;
        while (src > 0) {
            dst = (fnum[src % 10] + unit[count]) + dst;
            src = src / 10;
            count++;
        }
        return dst.replaceAll("零[仟佰拾]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零", "");
    }

    public double calcStoneProvavility(Stone stone,int lel){
        return Intensify.dataer.clchance.get(lel)+stone.getBasePro()-stone.getSharpStar()*lel;
    }
}