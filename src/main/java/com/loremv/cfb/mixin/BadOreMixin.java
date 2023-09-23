package com.loremv.cfb.mixin;

import com.loremv.cfb.CFB;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.Block.dropStacks;

@Mixin(Block.class)
public abstract class BadOreMixin {


    @Inject(at = @At("HEAD"), method = "afterBreak", cancellable = true)
    private void init(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo ci) {
        if(state.getBlock() instanceof OreBlock)
        {
            Block block = (Block) (Object)this;
            player.incrementStat(Stats.MINED.getOrCreateStat(block));
            player.addExhaustion(0.005F);
            //dropStacks(state, world, pos, blockEntity, player, new ItemStack(Items.IRON_NUGGET));
            ci.cancel();
        }
    }
}
