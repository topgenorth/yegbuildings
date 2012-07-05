namespace net.opgenorth.yegbuildings.m4a.data
{
    public interface IBuildingDatabase
    {
        void CreateDatabase();
        string DatabasePath { get; }
        
    }
}