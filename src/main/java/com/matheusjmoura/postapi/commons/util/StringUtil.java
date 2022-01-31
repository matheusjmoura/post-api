package com.matheusjmoura.postapi.commons.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    public static UUID uuidFromString(String id) {
        String pattern = "^([0-9a-f]{8})([0-9a-f]{4})([0-9a-f]{4})([0-9a-f]{4})([0-9a-f]{12})$";
        String uuidString = id.replaceAll(pattern, "$1-$2-$3-$4-$5");
        return UUID.fromString(uuidString);
    }

    public static UUID stringFromUUID(UUID uuid) {
        return UUID.fromString(uuid.toString().replace("-", ""));
    }

}
