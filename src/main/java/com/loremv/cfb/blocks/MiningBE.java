package com.loremv.cfb.blocks;

import com.loremv.cfb.CFB;
import com.loremv.cfb.OreUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MiningBE extends BlockEntity {
    private String minedOre = "empty";
    public MiningBE(BlockPos pos, BlockState state) {
        super(CFB.INFORMATION_BLOCK_ENTITY, pos, state);

    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putString("minedOre",minedOre);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        minedOre=nbt.getString("minedOre");
    }

    public void setMinedOre(String minedOre) {
        this.minedOre = minedOre;
        markDirty();
    }

    public static void tick(World world, BlockPos pos, BlockState state, MiningBE be)
    {
        if(world.getTimeOfDay()%995L==0L)
        {
            if(be.minedOre.equals("empty"))
            {

                String s = OreUtils.keys.get(world.random.nextInt(OreUtils.keys.size())).toLowerCase();
                be.setMinedOre(s);
                //System.out.println("new ore: "+s);
            }
        }
        if(world.getTimeOfDay()%1000L==0L)
        {

            //System.out.println("trying to place ore at "+pos.toShortString());

            if(world.getBlockEntity(pos.up()) instanceof Inventory inventory)
            {
                for (int i = 0; i < inventory.size(); i++) {
                    if(inventory.getStack(i).isOf(OreUtils.REGISTRY.get(be.minedOre)))
                    {
                        inventory.getStack(i).increment(1);
                        break;
                    }
                    else if(inventory.getStack(i).isEmpty())
                    {
                        inventory.setStack(i, new ItemStack(OreUtils.REGISTRY.get(be.minedOre)));
                        break;
                    }
                }
            }
        }
    }

    public String getMinedOre() {
        return minedOre;
    }
}
