using System;
using System.ComponentModel;
using System.Net;
using Android.App;
using Android.Content;
using Environment = Android.OS.Environment;

namespace net.opgenorth.yeg.buildings.Util
{
    // todo don't think we need this.
    public class HttpTextDownloader : IntentService
    {
        public static readonly string HISTORICAL_BUILDINGS_CSV_URL =
            @"http://data.edmonton.ca/DataBrowser/DownloadCsv?container=coe&entitySet=HistoricalBuildings&filter=NOFILTER";

        public HttpTextDownloader() : base(Constants.INTENT_SERVICE_HISTORICAL_BUILDING_DOWNLOAD)
        {
        }

        protected override void OnHandleIntent(Intent intent)
        {
            var source = new Uri(HISTORICAL_BUILDINGS_CSV_URL);
            string target = Environment.ExternalStorageDirectory.Path + @"\yegbuildings.csv";
            var wc = new WebClient();
            wc.DownloadFileCompleted += wc_DownloadFileCompleted;
            wc.DownloadFileAsync(source, target);
        }

        private void wc_DownloadFileCompleted(object sender, AsyncCompletedEventArgs e)
        {
            throw new NotImplementedException();
        }
    }
}