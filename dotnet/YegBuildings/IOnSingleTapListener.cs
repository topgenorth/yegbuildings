using System;
using Android.Views;

namespace net.opgenorth.yegbuildings.m4a
{
    public interface IOnSingleTapListener
    {
        Boolean OnSingleTap(MotionEvent e);
    }
}