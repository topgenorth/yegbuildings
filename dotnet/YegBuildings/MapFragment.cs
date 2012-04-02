using Android.App;
using Android.GoogleMaps;
using Android.Graphics.Drawables;
using Android.OS;
using Android.Views;
using Android.Widget;

namespace net.opgenorth.yegbuildings.m4a
{
    public class MapFragment : Fragment
    {
        private MapView _map;
        private MyLocationOverlay _myLocationOverlay;
        private Drawable _buildingMarker;

        public override void OnActivityCreated(Bundle savedInstanceState)
        {
            base.OnActivityCreated(savedInstanceState);

            InitializeBuildingMarker();
            InitializeMapView();
            AddHistoricalBuildingsOverlay();
            AddMyLocationOverlay();

            ((ViewGroup) View).AddView(_map);
        }

        private void AddHistoricalBuildingsOverlay()
        {
            var loader = new LoadBuildingsFromAssets(Activity);
            var overlay = new YegBuildingsOverlayItems(_buildingMarker, loader.GetBuildings());
            _map.Overlays.Add(overlay);
        }

        private void AddMyLocationOverlay()
        {
            _myLocationOverlay = new MyLocationOverlay(Activity, _map);
            _myLocationOverlay.RunOnFirstFix(() => _map.Controller.AnimateTo(_myLocationOverlay.MyLocation));
            _map.Overlays.Add(_myLocationOverlay);
        }

        private void InitializeMapView()
        {
            var apiKey = Resources.GetString(Resource.String.google_maps_api_key);
            _map = new MapView(Activity, apiKey)
                       {
                           Clickable = true
                       };

            _map.Controller.SetZoom(17);
            _map.SetBuiltInZoomControls(true);
        }

        private void InitializeBuildingMarker()
        {
            _buildingMarker = Resources.GetDrawable(Resource.Drawable.building_medium);
            _buildingMarker.SetBounds(0, 0, _buildingMarker.IntrinsicWidth, _buildingMarker.IntrinsicHeight);
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return new FrameLayout(Activity);
        }

        public override void OnResume()
        {
            base.OnResume();
            _myLocationOverlay.EnableMyLocation();
        }

        public override void OnPause()
        {
            base.OnPause();
            _myLocationOverlay.DisableMyLocation();
        }
    }
}