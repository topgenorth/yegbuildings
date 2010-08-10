package net.opgenorth.yeg.buildings.model;

import android.os.Bundle;

public interface IBundleModel<T>      {
    android.os.Bundle toBundle(T obj);
    T fromBundle(Bundle bundle);
}
