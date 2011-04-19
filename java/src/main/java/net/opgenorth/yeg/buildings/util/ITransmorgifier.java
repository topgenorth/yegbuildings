package net.opgenorth.yeg.buildings.util;

public interface ITransmorgifier<FROM, TO> {
	TO transmorgify(FROM source);
}
