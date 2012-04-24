using System.Collections.Generic;
using System.Linq;
using Android.GoogleMaps;
using Android.Graphics.Drawables;
using Android.OS;
using Android.Views;
using Java.Lang;

namespace net.opgenorth.yegbuildings.m4a.mapviewballoons
{
    /// <summary>
    ///   An abstract extension of ItemizedOverlay for displaying an information balloon upon a screen-tap of each marker overlay.
    /// </summary>
    /// <typeparam name="Item"> </typeparam>
    public abstract class BalloonItemizedOverlay<Item> : ItemizedOverlay where Item : OverlayItem, IBalloonItemizedOverlay<Item>
    {
        private const long BALLOON_INFLATION_TIME = 300;
        // ReSharper disable StaticFieldInGenericType
        private static readonly Handler _handler = new Handler();
        private static readonly Runnable _finishBalloonInflation = new Runnable(() => IsInflating = false);
        // ReSharper restore StaticFieldInGenericType
        private readonly MapController _mapController;
        private readonly MapView _mapView;
        private BalloonOverlayView<Item> _balloonView;
        private View _clickRegion;
        private View _closeRegion;
        private Item _currentFocusedItem;

        protected BalloonItemizedOverlay(Drawable defaultMarker, MapView mapView)
            : base(defaultMarker)
        {
            SnapToCenter = true;
            ShowClose = true;
            _mapView = mapView;
            BalloonBottomOffset = 0;
            _mapController = mapView.Controller;
        }

        public static bool IsInflating { get; private set; }
        public int CurrentFocusedIndex { get; private set; }
        public Item CurrentFocusedItem { get { return _currentFocusedItem; } }
        /// <summary>
        /// Set the horizontal distance between the marker and the bottom of the information balloon.
        /// The default is 0 which works well for center bounded markers. If your marker is
        /// center-bottom bounded, call this before adding overlay items to ensure the balloon
        /// hovers exactly above the marker.
        /// </summary>
        public int BalloonBottomOffset { get; set; }

        protected MapView MapView
        {
            get { return _mapView; }
        }

        public bool ShowClose { get; set; }
        public bool ShowDisclosure { get; set; }
        public bool SnapToCenter { get; set; }

        /// <summary>
        /// The item that currently has focus.
        /// </summary>
        public override Object Focus
        {
            get { return _currentFocusedItem; }
            set
            {
                base.Focus = value;
                CurrentFocusedIndex = LastFocusedIndex;
                _currentFocusedItem = value as Item;
                if (_currentFocusedItem == null)
                {
                    HideBalloon();
                }
                else
                {
                    CreateBalloonOverlayView();
                }
            }
        }

        /// <summary>
        /// Override this method to perform actions up an item being tapped before its
        /// balloon is displayed.
        /// </summary>
        /// <param name="index">The index of the item being tapped.</param>
        protected void OnBalloonOpen(int index) {}

        /// <summary>
        /// Override this to handle a "tap" on a ballon.  By default does nothing and returns false.
        /// </summary>
        /// <param name="index"></param>
        /// <param name="item"></param>
        public bool OnBalloonTap(int index, Item item)
        {
            return false;
        }
        protected override bool OnTap(int index)
        {
            _handler.RemoveCallbacks(_finishBalloonInflation);
            IsInflating = true;
            _handler.PostDelayed(_finishBalloonInflation, BALLOON_INFLATION_TIME);

            CurrentFocusedIndex = index;
            _currentFocusedItem = (Item) CreateItem(index);
            LastFocusedIndex = index;
            OnBalloonOpen(index);
            CreateAndDisplayBalloonOverlay();

            if (SnapToCenter)
            {
                _mapController.AnimateTo(_currentFocusedItem.Point);
            }

            return true;
        }

        /// <summary>
        /// Creates the balloon view. Override to create a sub-classed view that can populate
        /// additional sub-views.
        /// </summary>
        /// <returns></returns>
        protected virtual BalloonOverlayView<Item> CreateBalloonOverlayView()
        {
            return new BalloonOverlayView<Item>(_mapView.Context, BalloonBottomOffset);
        }

        /// <summary>
        /// Sets the visibility of this overlay's ballon view to GONE and unfocuses the item.
        /// </summary>
        public void HideBalloon()
        {
            if (_balloonView != null)
            {
                _balloonView.Visibility = ViewStates.Gone;
            }
            _currentFocusedItem = null;
        }

        /// <summary>
        /// Hides the balloon view for any other BalloonItemizedOverlay instances that might
        /// be present on the MapView.
        /// </summary>
        /// <param name="overlays">List of Overlays (including this) on the MapView.</param>
        private void HideOtherBalloons(IEnumerable<Overlay> overlays)
        {
            foreach (var overlay in overlays.Where(overlay => overlay is BalloonItemizedOverlay<Item> && (overlay != this)))
            {
                ((BalloonItemizedOverlay<Item>) overlay).HideBalloon();
            }
        }

        public void HideAllBalloons()
        {
            if (!IsInflating)
            {
                if (_mapView.Overlays.Any()) {}
                HideOtherBalloons(_mapView.Overlays);
            }
            HideBalloon();
        }

        /// <summary>
        /// Creates and displays the balloon overlay by recycling the current balloon or by
        /// inflating it from XML.
        /// </summary>
        /// <returns>true if the balloon was recycled, false otherwise</returns>
        private bool CreateAndDisplayBalloonOverlay()
        {
            bool isRecycled;
            if (_balloonView == null)
            {
                _balloonView = CreateBalloonOverlayView();
                _clickRegion = _balloonView.FindViewById<View>(Resource.Id.balloon_inner_layout);
                _clickRegion.SetOnTouchListener(new OnTouchListenerForBallonItemizedOverlay<Item>((IBalloonItemizedOverlay<Item>) this));
                _closeRegion = _balloonView.FindViewById<View>(Resource.Id.balloon_close);
                if (_closeRegion != null)
                {
                    if (!ShowClose)
                    {
                        _closeRegion.Visibility = ViewStates.Gone;
                    }
                    else
                    {
                        _closeRegion.Click += (sender, args) => HideBalloon();
                    }
                }
                if (ShowDisclosure && !ShowClose)
                {
                    var v = _balloonView.FindViewById<View>(Resource.Id.balloon_disclosure);
                    if (v != null)
                    {
                        v.Visibility = ViewStates.Visible;
                    }
                }
                isRecycled = false;
            }
            else
            {
                isRecycled = true;
            }
            _balloonView.Visibility = ViewStates.Gone;

            if (_mapView.Overlays.Any())
            {
                HideOtherBalloons(_mapView.Overlays);
            }

            if (_currentFocusedItem != null)
            {
                _balloonView.SetData(_currentFocusedItem);
            }
            var point = _currentFocusedItem.Point;
            var @params = new MapView.LayoutParams(ViewGroup.LayoutParams.WrapContent, ViewGroup.LayoutParams.WrapContent, point, MapView.LayoutParams.BottomCenter)
                              {
                                  Mode = MapView.LayoutParams.ModeMap
                              };

            _balloonView.Visibility = ViewStates.Visible;

            if (isRecycled)
            {
                _balloonView.LayoutParameters = @params;
            }
            else
            {
                _mapView.AddView(_balloonView, @params);
            }

            return isRecycled;
        }
    }
}