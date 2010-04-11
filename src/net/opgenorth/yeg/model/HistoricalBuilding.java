package net.opgenorth.yeg.model;

import android.location.Location;

import java.util.Date;
import java.util.UUID;

public class HistoricalBuilding {
	private UUID _rowKey;
	private UUID _entityId;
	private String _partitionKey;
	private String _name;
	private String _address;
	private String _neighbourHood;
	private String _url;
	private String _constructionDate;
	private LatLongLocation _location;
	private Date _timestamp;

	@Override
	public String toString() {
		return _name;
	}

	public String getPartitionKey() {
		return _partitionKey;
	}

	public void setPartitionKey(String partitionKey) {
		_partitionKey = partitionKey;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public String getNeighbourHood() {
		return _neighbourHood;
	}

	public void setNeighbourHood(String neighbourHood) {
		_neighbourHood = neighbourHood;
	}

	public String getAddress() {
		return _address;
	}

	public void setAddress(String address) {
		_address = address;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getConstructionDate() {
		return _constructionDate;
	}

	public void setConstructionDate(String constructionDate) {
		_constructionDate = constructionDate;
	}

	public UUID getRowKey() {
		return _rowKey;
	}

	public void setRowKey(UUID rowKey) {
		_rowKey = rowKey;
	}

	public UUID getEntityId() {
		return _entityId;
	}

	public void setEntityId(UUID entityId) {
		_entityId = entityId;
	}

	public LatLongLocation getLocation() {
		return _location;
	}

	public void setLocation(LatLongLocation location) {
		_location = location;
	}

	public void setLocation(double latitude, double longitude) {
		_location = new LatLongLocation(latitude, longitude);
	}

	public Date getTimestamp() {
		return _timestamp;
	}

	public void setTimestamp(Date timestamp) {
		_timestamp = timestamp;
	}

	public double setDistanceTo(Location location) {
		return this.getLocation().getDistanceTo(location);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		HistoricalBuilding that = (HistoricalBuilding) o;

		if (_location != null ? !_location.equals(that._location) : that._location != null) {
			return false;
		}
		if (_rowKey != null ? !_rowKey.equals(that._rowKey) : that._rowKey != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = _rowKey != null ? _rowKey.hashCode() : 0;
		result = 31 * result + (_location != null ? _location.hashCode() : 0);
		return result;
	}
}
