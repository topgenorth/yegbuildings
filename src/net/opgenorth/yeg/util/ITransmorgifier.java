package net.opgenorth.yeg.util;

public interface ITransmorgifier<FROM, TO> {
	TO transmorgify(FROM source);
}
