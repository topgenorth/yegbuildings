package net.opgenorth.yeg.buildings.model;

import com.google.android.maps.GeoPoint;

import java.util.UUID;

public class Building {
	private UUID            _rowKey;
	private String          _name;
	private String          _address;
	private String          _neighbourHood;
	private String          _url;
	private String          _constructionDate;
	private LatLongLocation _location;
	private double          _latitude;
	private double          _longitude;


	public Long getId() {
		return _id;
	}

	public void setId(Long id) {
		this._id = id;
	}

	private Long _id;


	@Override
	public String toString() {
		return _name;
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

	public LatLongLocation getLocation() {
		return _location;
	}

	public void setLocation(double latitude, double longitude) {
		_location = new LatLongLocation(latitude, longitude);
		_latitude = latitude;
		_longitude = longitude;
	}

	public GeoPoint getGeoPoint() {
		int latE6 = (int) (_latitude * 1000000.0);
		int lonE6 = (int) (_longitude * 1000000.0);
		GeoPoint geopoint = new GeoPoint(latE6, lonE6);
		return geopoint;
	}

	public double getLatitude() {
		return _latitude;
	}

	public double getLongitude() {
		return _longitude;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Building that = (Building) o;

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
