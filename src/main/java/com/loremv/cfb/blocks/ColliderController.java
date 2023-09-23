package com.loremv.cfb.blocks;

import com.smashingmods.chemlib.ChemLib;
import com.smashingmods.chemlib.api.Chemical;
import com.smashingmods.chemlib.registry.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ColliderController extends BlockWithEntity {
    public ColliderController(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        int ringCount = 0;
        BlockPos point = new BlockPos(pos.getX()+2,pos.getY(),pos.getZ()+1);
        BlockPos terminus = new BlockPos(pos.getX()+2,pos.getY(),pos.getZ());
        Direction direction = Direction.SOUTH;
        boolean circle = true;
        while (!point.equals(terminus))
        {
            if(world.getBlockState(point.offset(direction))==Blocks.RAW_COPPER_BLOCK.getDefaultState())
            {
                point=point.offset(direction);
                ringCount++;
            }
            else
            {
                if(world.getBlockState(point.offset(direction.rotateYCounterclockwise()))==Blocks.RAW_COPPER_BLOCK.getDefaultState())
                {
                    direction=direction.rotateYCounterclockwise();
                    point=point.offset(direction);

                    ringCount++;
                }
                else
                {
                    circle=false;
                    break;
                }
            }
        }
        System.out.println("is circle?: "+circle);
        System.out.println("rings: "+ringCount);

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(2,0,2,14,16,14);
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ColliderControllerBE(pos,state);
    }
}
