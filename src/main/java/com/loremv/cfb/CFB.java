package com.loremv.cfb;

import com.loremv.cfb.blocks.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mixins;
import team.reborn.energy.api.EnergyStorage;

public class CFB implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("cfb");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		registerBlocks();
		registerItems();
		registerEnergyBEs();
		LOGGER.info("Hello Fabric world!");
	}

	public static final ChemicalChute CHEMICAL_CHUTE = new ChemicalChute(AbstractBlock.Settings.of(Material.GLASS).nonOpaque());
	public static final ColliderController COLLIDER_CONTROLLER = new ColliderController(AbstractBlock.Settings.of(Material.GLASS).nonOpaque().strength(-1.0F, 3600000.0F).dropsNothing());
	public static final MiningBlock INFORMATION_BLOCK = new MiningBlock(AbstractBlock.Settings.of(Material.METAL).strength(-1.0F, 3600000.0F).dropsNothing());
	public static final ProcessingBlock PROCESSING_BLOCK = new ProcessingBlock(AbstractBlock.Settings.of(Material.METAL).strength(-1.0F, 3600000.0F).dropsNothing());

	private void registerBlocks()
	{
		Registry.register(Registry.BLOCK,new Identifier("cfb","chemical_chute"),CHEMICAL_CHUTE);
		Registry.register(Registry.BLOCK,new Identifier("cfb","collider_controller"),COLLIDER_CONTROLLER);
		Registry.register(Registry.BLOCK,new Identifier("cfb","mining_block"),INFORMATION_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("cfb","processing_block"),PROCESSING_BLOCK);


	}

	public static BlockEntityType<ChemicalChuteBE> CHEMICAL_CHUTE_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier("cfb", "chemical_chute_block_entity"),
			FabricBlockEntityTypeBuilder.create(ChemicalChuteBE::new,CHEMICAL_CHUTE).build()
	);
	public static BlockEntityType<MiningBE> INFORMATION_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier("cfb", "mining_block_entity"),
			FabricBlockEntityTypeBuilder.create(MiningBE::new,INFORMATION_BLOCK).build()
	);

	public static BlockEntityType<ProcessingBE> PROCESSING_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier("cfb", "processing_block_entity"),
			FabricBlockEntityTypeBuilder.create(ProcessingBE::new,PROCESSING_BLOCK).build()
	);

	public static BlockEntityType<ColliderControllerBE> COLLIDER_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			new Identifier("cfb", "collider_block_entity"),
			FabricBlockEntityTypeBuilder.create(ColliderControllerBE::new,COLLIDER_CONTROLLER).build()
	);


	private void registerEnergyBEs()
	{
		EnergyStorage.SIDED.registerForBlockEntity((myBlockEntity, direction) -> myBlockEntity.energyStorage, COLLIDER_BLOCK_ENTITY);

	}


	private void registerItems()
	{
		OreUtils.registerOres();
		Registry.register(Registry.ITEM,new Identifier("cfb","chemical_chute"),new BlockItem(CHEMICAL_CHUTE,new Item.Settings()));
	}
}
