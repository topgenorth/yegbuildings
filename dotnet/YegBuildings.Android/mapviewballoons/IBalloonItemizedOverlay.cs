namespace net.opgenorth.yegbuildings.m4a.mapviewballoons
{
    public interface IBalloonItemizedOverlay<Item>
    {
        /// <summary>
        /// Override this to handle a "tap" on a ballon.  By default does nothing and returns false.
        /// </summary>
        /// <param name="index"></param>
        /// <param name="item"></param>
        /// <returns>true if you handled the tap, false otherwise.</returns>
        bool OnBalloonTap(int index, Item item);
        int CurrentFocusedIndex { get; }
        Item CurrentFocusedItem { get; }
    }
}