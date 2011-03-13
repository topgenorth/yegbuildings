namespace Net.Opgenorth.Yeg.Buildings
{
    /// <summary>
    /// Implemented by classes that will convert objects from one type to another.
    /// </summary>
    /// <typeparam name="TFrom"></typeparam>
    /// <typeparam name="TTo"></typeparam>
    public interface ITransmorgifier<in TFrom, out TTo>
    {
        TTo Transmorgify(TFrom source);
    }

}