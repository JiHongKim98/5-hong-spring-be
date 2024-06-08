package com.example.community.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.util.StreamUtils;

public class CommunityStreamUtils {

	public static String toString(ByteArrayInputStream inputStream) {
		try {
			return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return "";
		}
	}
}
