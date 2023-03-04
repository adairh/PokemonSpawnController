package me.hyhon.leggyspawncontroller.leggyspawncontroller.serializers;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class BigDecimalSerializer implements TypeSerializer<BigDecimal> {

	@Override
	public BigDecimal deserialize(Type type, ConfigurationNode node) throws SerializationException {
		return new BigDecimal(node.getString());
	}

	@Override
	public void serialize(Type type, @Nullable BigDecimal decimal, ConfigurationNode node)
			throws SerializationException {
		node.set(decimal.toString());
	}

}