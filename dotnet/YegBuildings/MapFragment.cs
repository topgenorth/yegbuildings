using Android.App;
using Android.GoogleMaps;
using Android.OS;
using Android.Views;
using Android.Widget;

namespace net.opgenorth.yegbuildings.m4a
{
    public class MapFragment : Fragment
    {
        private MapView map;

        public override void OnActivityCreated(Bundle savedInstanceState)
        {
            base.OnActivityCreated(savedInstanceState);
            InitializeMapView();
            ((ViewGroup) View).AddView(map);
        }

        private void InitializeMapView()
        {
            map = new MapView(Activity, "0U7qxP9sDmYSOxxQFKVQjWOn9XDSARAhf5Ouy3A") // todo This is the debug/development API key.
                      {
                          Clickable = true
                      };


            map.Controller.SetCenter(GetPoint(40.76793169992044, -73.98180484771729));
            map.Controller.SetZoom(17);
            map.SetBuiltInZoomControls(true);

            var marker = Resources.GetDrawable(Resource.Drawable.building_medium);
            marker.SetBounds(0, 0, marker.IntrinsicWidth, marker.IntrinsicHeight);
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return new FrameLayout(Activity);
        }

        private GeoPoint GetPoint(double lat, double lon)
        {
            return (new GeoPoint((int) (lat*1000000.0),
                                 (int) (lon*1000000.0)));
        }
    }
}