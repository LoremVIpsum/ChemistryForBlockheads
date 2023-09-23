package com.loremv.cfb.blocks;

import com.loremv.cfb.CFB;
import com.smashingmods.chemlib.api.Chemical;
import com.smashingmods.chemlib.api.MatterState;
import com.smashingmods.chemlib.common.fluids.ChemicalFluid;
import com.smashingmods.chemlib.registry.FluidRegistry;
import com.smashingmods.chemlib.registry.ItemRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ChemicalChuteBE extends BlockEntity {
    public ChemicalChuteBE(BlockPos pos, BlockState state) {
        super(CFB.CHEMICAL_CHUTE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ChemicalChuteBE be)
    {
        if(world.getTimeOfDay()%20L==0)
        {
            if(world.getBlockEntity(pos.up()) instanceof Inventory above)
            {
                if(world.getBlockEntity(pos.down()) instanceof Inventory below)
                {
                    for (int i = 0; i < above.size(); i++) {
                        if(above.getStack(i).getItem() instanceof Chemical chemical)
                        {
                            //System.out.println(chemical.getChemicalName());
                            if(chemical.getMatterState()==MatterState.SOLID)
                            {
                                Identifier chemId = Registry.ITEM.getId(above.getStack(i).getItem());
                                Item chemDust = Registry.ITEM.get(new Identifier(chemId.getNamespace(),chemId.getPath()+"_dust"));
                                //System.out.println("chem: "+chemDust);
                                if(chemDust!=null)
                                {
                                    for (int j = 0; j < below.size(); j++) {
                                        if(below.getStack(j)==null || below.getStack(j).isEmpty())
                                        {
                                            below.setStack(j,new ItemStack(chemDust));
                                            above.getStack(i).decrement(1);
                                            break;
                                        }
                                        if(below.getStack(j).isOf(chemDust) && below.getStack(j).getCount()<below.getMaxCountPerStack())
                                        {
                                            below.getStack(j).increment(1);
                                            above.getStack(i).decrement(1);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }

                /**else if(FluidStorage.SIDED.find(world,pos.down(), Direction.UP)!= null)
                {
                    Storage<FluidVariant> below = FluidStorage.SIDED.find(world,pos.down(), Direction.UP);
                    for (int i = 0; i < above.size(); i++) {
                        if(above.getStack(i).getItem() instanceof Chemical chemical)
                        {

                        }
                    }
                }**/
            }
        }

    }
}
