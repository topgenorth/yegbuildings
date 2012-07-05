using System;
using System.IO;

namespace net.opgenorth.yegbuildings.m4a
{
    public class Globals
    {
        public static readonly string LogTag = "YegBuildings";

        public static readonly string DatabaseFileName = "yegbuildings.db";

        public static string PackageName { get; private set; }
        public static string DatabaseName { get; private set; }

        /// <summary>
        /// We as for a GPS update every 5 seconds.
        /// </summary>
        public static readonly int GpsUpdateTimeInterval = 5000;

        /// <summary>
        /// We as for a GPS update every 50 metres
        /// </summary>
        public static readonly int GpsUpdateDistanceInterval = 50;


        public static void Initialize(string packageName)
        {
            if (string.IsNullOrWhiteSpace(packageName))
            {
                throw new ArgumentNullException("packageName");
            }
            PackageName = packageName.Trim();
            DatabaseName = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.Personal), DatabaseFileName); ;
        }
        
    }
}