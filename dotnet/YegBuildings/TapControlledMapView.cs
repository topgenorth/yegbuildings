using Android.Content;
using Android.GoogleMaps;
using Android.Util;

namespace net.opgenorth.yegbuildings.m4a
{
    public class TapControlledMapView : MapView
    {
        public TapControlledMapView(Context context, IAttributeSet attrs) : base(context, attrs)
        {
            Initialize();
        }

        public TapControlledMapView(Context context, IAttributeSet attrs, int defStyle) : base(context, attrs, defStyle)
        {
            Initialize();
        }

        private void Initialize() {}
    }
}