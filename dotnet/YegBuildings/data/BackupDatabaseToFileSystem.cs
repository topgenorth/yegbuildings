using System;
using System.IO;
using Android.Util;
using Environment = Android.OS.Environment;

namespace net.opgenorth.yegbuildings.m4a.data
{
    public class BackupDatabaseToFileSystem
    {
        public virtual string GetBackupFile()
        {
            var timestamp = DateTime.Now.ToString("yyyyMMddHHmmss");
            var nameParts = Globals.DatabaseFileName.Split('.');
            var backupName = String.Concat(nameParts[0], "_", timestamp, "." + nameParts[1]);
            var path = Path.Combine(Environment.ExternalStorageDirectory.AbsolutePath, "backup", Globals.PackageName, backupName);
            return path;
        }

        public void Backup()
        {
            var externalStorageDirectory = Environment.ExternalStorageDirectory;
            if (!externalStorageDirectory.CanWrite())
            {
                Log.Error(Globals.LogTag, "Cannot write to the external storage at  " + Environment.ExternalStorageDirectory);
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