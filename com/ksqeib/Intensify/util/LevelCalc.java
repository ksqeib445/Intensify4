package ksqeib.Intensify.util;

import ksqeib.Intensify.enums.LocType;
import ksqeib.Intensify.enums.Sectype;
import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.store.Stone;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class LevelCalc {
    public static String cln = "cuilel";
    private final String[] num = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private final String[] fnum = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private final String[] hnum = {"蕶", "噎", "弍", "彡", "sī", "㈤", "⒍", "㈦", "仈", "氿"};
    private final String[] unit = {"星", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
    ConcurrentHashMap<LocType, HashMap<Sectype, Double>> loadedtypeadd = new ConcurrentHashMap<>();
    HashMap<String, Double> arms;
    HashMap<String, Double> helmet;
    HashMap<String, Double> chestplate;
    HashMap<String, Double> leggings;
    HashMap<String, Double> boots;

    public List<String> getLevelString(Sectype sectype) {
        if (sectype == null) return null;
        return Intensify.um.getTip().getMessageList(sectype + "_LORE");
    }

    public double getLelDouble(LocType type, Sectype sectype, int lel) {
        if (type == null || sectype == null) return 0;
        Double get = loadedtypeadd.get(type).get(sectype);
        return get * lel;
    }

    public void init(FileConfiguration yml) {
        for (LocType locType : LocType.values()) {
            loadedtypeadd.put(locType, loada(locType, yml));
        }
    }

    private HashMap<Sectype, Double> loada(LocType locType, FileConfiguration yml) {
        HashMap<Sectype, Double> hash = new HashMap<>();
        for (Sectype sectype : Sectype.values()) {
            hash.put(sectype, yml.getDouble(locType + "." + sectype));
        }
        return hash;
    }

    public List<String> makeLelStr(int i) {
        ArrayList<String> lo = new ArrayList<>();
        int r1 = i % 5;
        int r5 = (i - r1) / 5;
        String out = Intensify.um.getTip().getMessageList("lore_head").get(0);
        while (r5 > 1) {
            r5--;
            out += Intensify.dataer.r5;
        }
        while (r1 > 0) {
            r1--;
            out += Intensify.dataer.r1;
        }
        out += int2big(i);
        lo.add(out + Intensify.um.getTip().getMessageList("lore_end").get(0));
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

    public double calcStoneProvavility(Stone stone, int lel) {
        return Intensify.dataer.clchance.get(lel) + stone.getBasePro() - stone.getSharpStar() * lel;
    }
}