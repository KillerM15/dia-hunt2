package killerm.minecraft.helper;

import killerm.minecraft.communication.Message;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionDelimiter {
    public static <T> String delimit(T[] ts, Function<T, String> toOutput) {
        return Arrays.stream(ts)
                .map(toOutput)
                .collect(Collectors.joining(Message.COLLECTION_DELIMITER));
    }
}
