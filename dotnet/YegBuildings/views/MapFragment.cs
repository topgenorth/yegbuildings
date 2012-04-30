using Android.App;
using Android.GoogleMaps;
using Android.Graphics.Drawables;
using Android.OS;
using Android.Views;
using Android.Widget;
using net.opgenorth.yegbuildings.m4a.model;

namespace net.opgenorth.yegbuildings.m4a.views
{
    public class MapFragment : Fragment
    {
        private MapView _map;
        private MyLocationOverlay _myLocationOverlay;
        private Drawable _buildingMarker;
        private YegBuildingsOverlayItems _buildingsOverlay;

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
            _buildingsOverlay = new YegBuildingsOverlayItems(_buildingMarker, Activity.Buildings());
            _map.Overlays.Add(_buildingsOverlay);
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
            _map.DisplayZoomControls(true);
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

        public void AnimateTo(Building building)
        {
            if (_map == null)
            {
                return;
            }
            if (building == null)
            {
                return;
            }
            _map.Controller.AnimateTo(building.GetPoint());
        }
    }
}