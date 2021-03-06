﻿using System.Collections.Generic;
using System.Linq;
using Android.GoogleMaps;
using Android.Graphics.Drawables;
using Java.Lang;
using net.opgenorth.yegbuildings.m4a.model;

namespace net.opgenorth.yegbuildings.m4a.views
{
    public class ItemizedYegBuildingOverlay : ItemizedOverlay
    {
        private readonly List<OverlayItem> _buildings;

        public ItemizedYegBuildingOverlay(Drawable defaultMarker, IEnumerable<Building> buildings) : base(defaultMarker)
        {
            _buildings = new List<OverlayItem>();
            foreach (var overlay in 
                from building in buildings
                let point = building.GetPoint()
                select new OverlayItem(point, building.Name, building.Address))
            {
                _buildings.Add(overlay);
            }
            BoundCenterBottom(defaultMarker);
            Populate();
        }

        protected override Object CreateItem(int i)
        {
            return _buildings[i];
        }

        public override int Size()
        {
            return _buildings.Count;
        }
    }
}