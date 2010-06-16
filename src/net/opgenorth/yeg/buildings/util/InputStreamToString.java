package net.opgenorth.yeg.buildings.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamToString implements ITransmorgifier<InputStream, String> {
	@Override
	public String transmorgify(InputStream source) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(source));
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				source.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
}
