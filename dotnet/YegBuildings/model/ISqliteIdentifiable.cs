namespace net.opgenorth.yegbuildings.m4a.model
{
    // Implemented by things that have an SQLite ID column.
    public interface ISqliteIdentifiable
    {
        int Id { get; }
    }
}