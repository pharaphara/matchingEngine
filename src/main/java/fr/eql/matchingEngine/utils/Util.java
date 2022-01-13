package fr.eql.matchingEngine.utils;

import java.io.IOException;
import java.util.UUID;

public final class Util {

	public static String generateUUid(String projectName) throws IOException {

		// Creating a random UUID (Universally unique identifier).
		UUID uuid = UUID.randomUUID();
		String randomUUIDString =  projectName + " -- " + uuid.toString();

		return randomUUIDString;
	}
}
