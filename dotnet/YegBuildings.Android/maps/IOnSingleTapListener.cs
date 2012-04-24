using System;
using Android.Views;

namespace net.opgenorth.yegbuildings.m4a.maps
{
    public interface IOnSingleTapListener
    {
        Boolean OnSingleTap(MotionEvent e);
    }
}