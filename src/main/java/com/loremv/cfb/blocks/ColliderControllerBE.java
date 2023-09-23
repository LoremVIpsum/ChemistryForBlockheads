package com.loremv.cfb.blocks;

import com.loremv.cfb.CFB;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class ColliderControllerBE extends BlockEntity {

    public ColliderControllerBE(BlockPos pos, BlockState state) {
        super(CFB.COLLIDER_BLOCK_ENTITY, pos, state);
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(10000,0,500) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }
}
