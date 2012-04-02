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
            var apiKey = Resources.GetString(Resource.String.google_maps_api_key);
            map = new MapView(Activity, apiKey)
                      {
                          Clickable = true
                      };

            var point = GetPoint( 53.54270, -113.49332);
            map.Controller.SetCenter(point);
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
            return (new GeoPoint((int) (lat*1000000.0), (int) (lon*1000000.0)));
        }
    }
}