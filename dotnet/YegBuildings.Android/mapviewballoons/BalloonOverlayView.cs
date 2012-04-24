using System;
using Android.Content;
using Android.GoogleMaps;
using Android.Views;
using Android.Widget;

namespace net.opgenorth.yegbuildings.m4a.mapviewballoons
{
    public class BalloonOverlayView<T> : FrameLayout where T : OverlayItem
    {
        private readonly LinearLayout _layout;
        private TextView _title;
        private TextView _snippet;

        public BalloonOverlayView(Context context, int balloonBottomOffset) : base(context)
        {
            SetPadding(10, 0, 10, balloonBottomOffset);
            _layout = new LimitLinearLayout(context) {Visibility = ViewStates.Visible};

            SetupView(context, _layout);

            var parms = new LayoutParams(ViewGroup.LayoutParams.WrapContent, ViewGroup.LayoutParams.WrapContent);

            AddView(_layout, parms);
        }

        private void SetupView(Context context, ViewGroup parent)
        {
            var inflater = (LayoutInflater) context.GetSystemService(Context.LayoutInflaterService);
            var v = inflater.Inflate(Resource.Layout.balloon_overlay, parent);
            _title = v.FindViewById<TextView>(Resource.Id.balloon_item_title);
            _snippet = v.FindViewById<TextView>(Resource.Id.balloon_item_snippet);
        }

        public void SetData(T item)
        {
            _layout.Visibility = ViewStates.Visible;
            SetBalloonData(item);
        }

        private void SetBalloonData(T item)
        {
            if (!string.IsNullOrWhiteSpace(item.Title))
            {
                _title.Visibility = ViewStates.Visible;
                _title.Text = item.Title;
            }
            else
            {
                _title.Text = string.Empty;
                _title.Visibility = ViewStates.Gone;
            }

            if (!string.IsNullOrWhiteSpace(item.Snippet))
            {
                _snippet.Visibility = ViewStates.Visible;
                _snippet.Text = item.Snippet;
            }
            else
            {
                _snippet.Visibility = ViewStates.Gone;
                _snippet.Text = string.Empty;
            }
        }

        private class LimitLinearLayout : LinearLayout
        {
            private const int MAX_WIDTH_DP = 280;
            public LimitLinearLayout(Context context) : base(context) {}

            protected override void OnMeasure(int widthMeasureSpec, int heightMeasureSpec)
            {
                var SCALE = Context.Resources.DisplayMetrics.Density;

                var mode = MeasureSpec.GetMode(widthMeasureSpec);
                var measuredWidth = MeasureSpec.GetSize(widthMeasureSpec);
                var adjustedMaxWidth = (int) (MAX_WIDTH_DP*SCALE + 0.5f);
                var adjustedWidth = Math.Min(measuredWidth, adjustedMaxWidth);
                var adjustedWidthMeasureSpec = MeasureSpec.MakeMeasureSpec(adjustedWidth, mode);

                base.OnMeasure(adjustedWidthMeasureSpec, heightMeasureSpec);
            }
        }
    }
}