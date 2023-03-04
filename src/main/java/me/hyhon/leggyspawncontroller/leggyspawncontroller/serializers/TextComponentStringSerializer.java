package me.hyhon.leggyspawncontroller.leggyspawncontroller.serializers;

import net.minecraft.util.text.TextComponentString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class TextComponentStringSerializer implements TypeSerializer<TextComponentString> {

	@Override
	public TextComponentString deserialize(Type type, ConfigurationNode node) throws SerializationException {
		return new TextComponentString(Utils.toLegacy(node.getString()));
	}

	@Override
	public void serialize(Type type, @Nullable TextComponentString text, ConfigurationNode node) throws SerializationException {
		node.set(Utils.toLegacy(text.getText()));
	}

}