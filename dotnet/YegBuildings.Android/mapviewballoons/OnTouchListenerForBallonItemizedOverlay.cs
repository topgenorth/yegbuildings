using Android.Views;
using Java.Lang;
using Math = System.Math;

namespace net.opgenorth.yegbuildings.m4a.mapviewballoons
{
    public class OnTouchListenerForBallonItemizedOverlay<Item> : Object, View.IOnTouchListener
    {
        private float startX, startY;
        private readonly IBalloonItemizedOverlay<Item> _item;

        public OnTouchListenerForBallonItemizedOverlay(IBalloonItemizedOverlay<Item> item)
        {
            _item = item;
        }

        public bool OnTouch(View v, MotionEvent e)
        {
            var l = ((View) v.Parent).FindViewById<View>(Resource.Id.balloon_main_layout);
            var d = l.Background;
            if (e.Action == MotionEventActions.Down)
            {
                var states = new[] {Android.Resource.Attribute.StatePressed};
                if (d.SetState(states))
                {
                    d.InvalidateSelf();
                }
                startX = e.GetX();
                startY = e.GetY();
                return true;
            }
            if (e.Action == MotionEventActions.Up)
            {
                var newStates = new int[0];
                if (d.SetState(newStates))
                {
                    d.InvalidateSelf();
                }
                var xDiff = Math.Abs(startX - e.GetX());
                var yDiff = Math.Abs(startY - e.GetY());
                if ((xDiff < 40) && (yDiff < 40))
                {
                    _item.OnBalloonTap(_item.CurrentFocusedIndex, _item.CurrentFocusedItem);
                }
                return true;
            }
            return false;
        }
    }
}