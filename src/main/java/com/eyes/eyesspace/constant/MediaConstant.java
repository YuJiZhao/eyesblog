package com.eyes.eyesspace.constant;

import java.util.ArrayList;
import java.util.List;

public class MediaConstant {
	private static final List<String> image = new ArrayList<>();

	public static final String DEFAULT_MEDIA_TYPE = ".png";

	static {
		image.add(DEFAULT_MEDIA_TYPE);
		image.add(".PNG");
		image.add(".jpg");
		image.add(".JPG");
		image.add(".jpeg");
		image.add(".JPEG");
	}

	public static boolean imgContain(String media) {
		return image.contains(media);
	}
}
