package com.loremv.cfb.blocks;

import com.loremv.cfb.CFB;
import com.loremv.cfb.OreUtils;
import com.loremv.cfb.items.ItemWithChemical;
import com.smashingmods.chemlib.registry.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ProcessingBE extends BlockEntity {

    private NbtList processedOres;
    public ProcessingBE(BlockPos pos, BlockState state) {
        super(CFB.PROCESSING_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        if(processedOres==null)
        {
            processedOres=new NbtList();
        }
        nbt.put("processedOres",processedOres);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        processedOres= (NbtList) nbt.get("processedOres");
    }

    public NbtList getProcessedOres() {
        return processedOres;

    }

    public void setProcessedOres(NbtList processedOres) {
        this.processedOres = processedOres;
        markDirty();
    }

    public static void tick(World world, BlockPos pos, BlockState state, ProcessingBE be)
    {
        if(world.getTimeOfDay()%195L==0L)
        {
            if(be.getProcessedOres()==null || be.getProcessedOres().size()==0)
            {
                NbtList ores = new NbtList();
                for (int i = 0; i < world.random.nextBetween(1,5); i++) {
                    ores.add(NbtString.of(OreUtils.keys.get(world.random.nextInt(OreUtils.keys.size()))));
                }
                be.setProcessedOres(ores);
            }
        }
        if(world.getTimeOfDay()%200L==0L)
        {

            if(world.getBlockEntity(pos.up()) instanceof Inventory in)
            {
                if(world.getBlockEntity(pos.down()) instanceof Inventory out)
                {
                    for (int i = 0; i < in.size(); i++) {
                        if(in.getStack(i).getItem() instanceof ItemWithChemical chemical)
                        {
                            boolean found = false;
                            for (int l = 0; l < be.getProcessedOres().size(); l++) {
                                if(be.getProcessedOres().getString(l).equals(chemical.getOre()))
                                {

                                    found=true;
                                }
                            }
                            if(!found) break;

                            int[] atomics = chemical.getElements();
                            int take = world.random.nextInt(atomics.length)+1;
                            for (int j = 0; j < take; j++) {
                                Item output = ItemRegistry.getElementByAtomicNumber(atomics[j]).get();

                                for (int k = 0; k < out.size(); k++) {
                                    if(out.getStack(k).isOf(output))
                                    {
                                        in.getStack(i).decrement(1);
                                        out.getStack(k).increment(1);
                                        break;
                                    }
                                    else if(out.getStack(k).isEmpty())
                                    {
                                        in.getStack(i).decrement(1);
                                        out.setStack(k,new ItemStack(output));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
