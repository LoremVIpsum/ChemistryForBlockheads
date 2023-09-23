package com.loremv.cfb.items;

import com.smashingmods.chemlib.registry.ItemRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWithChemical extends Item {
    private final int[] atomic;
    private final String ores;
    public ItemWithChemical(Settings settings, int[] atoms,String ore) {
        super(settings);
        atomic=atoms;
        ores=ore;
    }

    public String getOre() {
        return ores;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if(ItemRegistry.getElementByAtomicNumber(atomic[0]).isPresent())
        {
            for (int i = 0; i < atomic.length; i++) {
                tooltip.add(Text.of(ItemRegistry.getElementByAtomicNumber(atomic[i]).get().getChemicalName()));
            }
        }

    }

    public int[] getElements() {
        return atomic;
    }
}
