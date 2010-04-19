package net.opgenorth.yeg.historicalbuildings.util;

public interface ITransmorgifier<FROM, TO> {
	TO transmorgify(FROM source);
}
