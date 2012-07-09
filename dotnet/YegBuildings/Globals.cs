using System;
using System.IO;

namespace net.opgenorth.yegbuildings.m4a
{
    /// <summary>
    /// This is a holder for some global variables for the application.
    /// </summary>
    public class Globals
    {
        /// <summary>
        /// For use in Log calls.
        /// </summary>
        public static readonly string LogTag = "YegBuildings";

        /// <summary>
        ///   This is the file name of the database that will be used.
        /// </summary>
        public static readonly string DatabaseFileName = "yegbuildings.db";

        /// <summary>
        ///   We as for a GPS update every 50 metres.
        /// </summary>
        public static readonly int GpsUpdateDistanceInterval = 50;

        /// <summary>
        ///   We as for a GPS update every 5 seconds.
        /// </summary>
        public static readonly int GpsUpdateTimeInterval = 5000;


        /// <summary>
        ///   This is the path and file name of the database.
        /// </summary>
        public static string DatabaseName { get; private set; }

        /// <summary>
        /// Quick & easy way to figure out the package name of this application.
        /// </summary>
        public static string PackageName { get; private set; }

        public static void Initialize(string packageName)
        {
            if (string.IsNullOrWhiteSpace(packageName))
            {
                throw new ArgumentNullException("packageName");
            }
            PackageName = packageName.Trim();
            DatabaseName = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.Personal), DatabaseFileName);
            ;
        }
    }
}
