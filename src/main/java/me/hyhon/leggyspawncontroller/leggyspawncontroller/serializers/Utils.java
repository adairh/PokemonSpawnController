package me.hyhon.leggyspawncontroller.leggyspawncontroller.serializers;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.RandomHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Utils {

	private static UserStorageService uss = null;

	public static Optional<String> getNameForUuid(UUID uuid) {
		if (uss == null)
			uss = Sponge.getServiceManager().provideUnchecked(UserStorageService.class);
		Optional<User> oUser = uss.get(uuid);

		if (oUser.isPresent()) {
			String name = oUser.get().getName();
			return Optional.of(name);
		} else {
			return Optional.empty();
		}
	}

	public static <T> T checkNotNull(@Nullable T value, @Nullable Object message) {
		if (value == null) {
			throw new NullPointerException(String.valueOf(message));
		} else {
			return value;
		}
	}

	public static <T> List<T> getRandomItems(List<T> items, int n) {
		List<T> result = Lists.newArrayList();
		if (items.size() == 0)
			return result;
		for (int i = 0; i < n; i++) {
			result.add(items.get(RandomHelper.rand.nextInt(items.size())));
		}
		return result;
	}

	public static <T> List<T> getRandomDistinctItems(List<T> items, int n) {
		List<T> result = Lists.newArrayList();
		List<T> clone = Lists.newArrayList(items);
		if (items.size() == 0 || items.size() < n)
			return result;

		for (int i = 0; i < n; i++) {
			int r = RandomHelper.rand.nextInt(clone.size());
			result.add(clone.get(r));
			clone.remove(r);
		}
		return result;
	}

	public static ITextComponent text(String str) {
		return new TextComponentString(toLegacy(str));
	}

	public static String toLegacy(String str) {
		return str.replace('&', '\u00a7');
	}

}