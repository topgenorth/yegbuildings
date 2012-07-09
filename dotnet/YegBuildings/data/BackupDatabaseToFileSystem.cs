using System;
using System.IO;

using Android.Util;

using AndroidEnvironment = Android.OS.Environment;

namespace net.opgenorth.yegbuildings.m4a.data
{
    public class BackupDatabaseToFileSystem
    {
        public void Backup()
        {
            var externalStorageDirectory = AndroidEnvironment.ExternalStorageDirectory;
            if (!externalStorageDirectory.CanWrite())
            {
                Log.Error(Globals.LogTag, "Cannot write to the external storage at  " + AndroidEnvironment.ExternalStorageDirectory);
                return;
            }
            var dbFile = new FileInfo(Globals.DatabaseName);
            if (!dbFile.Exists)
            {
                Log.Warn(Globals.LogTag, "The database file {0} does not exist.", dbFile.FullName);
                return;
            }

            var backupFileName = GetBackupFile();

            try
            {
                EnsureBackupDirectoryExists(backupFileName);
                dbFile.CopyTo(backupFileName, true);
                Log.Verbose(Globals.LogTag, "Copied the file from {0} to {1}", dbFile.FullName, backupFileName);
            }
            catch (Exception e)
            {
                Log.Error(Globals.LogTag, "Could not copy the database from {0} to {1}.", dbFile.FullName, backupFileName, e);
            }
        }

        public virtual string GetBackupFile()
        {
            var timestamp = DateTime.Now.ToString("yyyyMMddHHmmss");
            var nameParts = Globals.DatabaseFileName.Split('.');
            var backupName = String.Concat(nameParts[0], "_", timestamp, "." + nameParts[1]);


            var path = Path.Combine(AndroidEnvironment.ExternalStorageDirectory.AbsolutePath, "backups", "apps", Globals.PackageName, backupName);
            return path;
        }

        private static void EnsureBackupDirectoryExists(string backupFileName)
        {
            var backupFile = new FileInfo(backupFileName);
            if (backupFile.Directory != null && !backupFile.Directory.Exists)
            {
                backupFile.Directory.Create();
                Log.Verbose(Globals.LogTag, "Created the directory " + backupFile.Directory.FullName);
            }
        }
    }
}
