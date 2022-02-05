package backend;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition);
    void positionChanged();
}
