using Android.Content;
using Android.GoogleMaps;
using Android.Util;
using Android.Views;

namespace net.opgenorth.yegbuildings.m4a
{
    public class TapControlledMapView : MapView, GestureDetector.IOnGestureListener, GestureDetector.IOnDoubleTapListener
    {
        private GestureDetector _gestureDetector;
        private IOnSingleTapListener _singleTapListener;

        public TapControlledMapView(Context context, IAttributeSet attrs) : base(context, attrs)
        {
            Initialize();
        }

        public TapControlledMapView(Context context, IAttributeSet attrs, int defStyle) : base(context, attrs, defStyle)
        {
            Initialize();
        }

        private void Initialize()
        {
            _gestureDetector = new GestureDetector(this);
            _gestureDetector.SetOnDoubleTapListener(this);
        }

        public bool OnDown(MotionEvent e)
        {
            return false;
        }

        public bool OnFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            return false;
        }

        public void OnLongPress(MotionEvent e) {}

        public bool OnScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            return false;
        }

        public void OnShowPress(MotionEvent e) {}

        public bool OnSingleTapUp(MotionEvent e)
        {
            return false;
        }

        public bool OnDoubleTap(MotionEvent e)
        {
            Controller.ZoomInFixing( (int) e.GetX(), (int) e.GetY());
            return false;
        }

        public bool OnDoubleTapEvent(MotionEvent e)
        {
            return false;
        }

        public bool OnSingleTapConfirmed(MotionEvent e)
        {
            if (_singleTapListener == null)
            {
                return false;
            }
            return _singleTapListener.OnSingleTap(e);
        }

        public void SetOnSingleTapListener(IOnSingleTapListener listener)
        {
            _singleTapListener = listener;
        }
    }
}