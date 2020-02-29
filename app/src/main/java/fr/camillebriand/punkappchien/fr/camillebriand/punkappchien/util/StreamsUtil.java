package fr.camillebriand.punkappchien.fr.camillebriand.punkappchien.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class StreamsUtil {
	private StreamsUtil() {}
	
	/**
	 * Reads the content of an {@link InputStream} and return its content as a {@link String}
	 * @param is {@link InputStream} to read into
	 * @return {@link String} content of {@code is}
	 * @throws IOException In case of an error while reading the stream
	 */
	public static String readStream(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			sb.append(line);
		}
		is.close();
		return sb.toString();
	}
}