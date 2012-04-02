using System.Reflection;
using System.Runtime.InteropServices;
using Android;
using Android.App;

[assembly: AssemblyTitle("YegBuildings")]
[assembly: AssemblyDescription("Historical Buildings in the Edmonton Area.")]
#if DEBUG
[assembly: AssemblyConfiguration("DEBUG")]
#endif

#if RELEASE
[assembly: AssemblyConfiguration("RELEASE")]
#endif

[assembly: AssemblyCompany("Tom Opgenorth")]
[assembly: AssemblyProduct("YegBuildings")]
[assembly: AssemblyCopyright("Copyright ©  2012")]
[assembly: AssemblyTrademark("")]
[assembly: AssemblyCulture("")]
[assembly: ComVisible(false)]
[assembly: Guid("a557ce8c-9dbe-4b93-8fc4-95ffc126cf14")]
[assembly: AssemblyVersion("2.0.0.0")]
[assembly: AssemblyFileVersion("2.0.0.0")]
[assembly: UsesPermission(Manifest.Permission.Internet)]
[assembly: UsesPermission(Manifest.Permission.AccessFineLocation)]
[assembly: UsesPermission(Manifest.Permission.AccessCoarseLocation)]