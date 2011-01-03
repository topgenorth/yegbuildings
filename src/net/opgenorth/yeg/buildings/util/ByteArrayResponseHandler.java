package net.opgenorth.yeg.buildings.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ByteArrayResponseHandler implements ResponseHandler<byte[]> {
	@Override
	public byte[] handleResponse(HttpResponse httpResponse) throws IOException {
		StatusLine statusLine = httpResponse.getStatusLine();
		if (statusLine.getStatusCode() >= 300)
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());

		HttpEntity entity = httpResponse.getEntity();
		if (entity == null)
			return null;

		return EntityUtils.toByteArray(entity);
	}
}
