package com.loremv.cfb;

import com.loremv.cfb.items.ItemWithChemical;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;

public class OreUtils {

    public static final HashMap<String, int[]> ORES = new HashMap<>();
    static
    {
        ORES.put("Bauxite",new int[]{13,13,13,31,23});
        ORES.put("Pyrite",new int[]{26,16,16});
        ORES.put("Galena",new int[]{26,47,16});
        ORES.put("Carnotite",new int[]{19,19,92,23});
        ORES.put("Malachite",new int[]{29,29,6});
        ORES.put("Smithsonite",new int[]{30,30,6,48});
        ORES.put("Magnesite",new int[]{12,6});
        ORES.put("Hemimorphite",new int[]{30,30,30,30,14,14});
        ORES.put("Monazite-Ce",new int[]{58,57,60,90});
        ORES.put("Monazite-Sm",new int[]{62,64,58,90});
        ORES.put("Halite",new int[]{11,17});
        ORES.put("Romanechite",new int[]{56,25});
        ORES.put("Pentlandite",new int[]{26,28,16});
        ORES.put("Chromite",new int[]{12,26,24});
        ORES.put("Stibnite",new int[]{51,51,16,16,16});
        ORES.put("Rutile",new int[]{22});
        ORES.put("Proustite",new int[]{47,47,47,33,16,16,16});
        ORES.put("Lepidolite",new int[]{19,3,13,14,37});
        ORES.put("Sphalerite",new int[]{30,26});
        ORES.put("Cassiterite",new int[]{50});
        ORES.put("Chalcopyrite",new int[]{26,29,16,16});
    }

    public static final List<String> keys = OreUtils.ORES.keySet().stream().toList();

    public static HashMap<String,Item> REGISTRY = new HashMap<>();
    public static void registerOres()
    {
        ORES.keySet().forEach(a->
        {
            Item item = new ItemWithChemical(new Item.Settings().group(ItemGroup.MATERIALS),ORES.get(a),a);
            Registry.register(Registry.ITEM,new Identifier("cfb",a.toLowerCase()),item);
            REGISTRY.put(a.toLowerCase(),item);
        });
    }

}
