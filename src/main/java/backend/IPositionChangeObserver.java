package backend;

public interface IPositionChangeObserver {
    void positionChanged(Vector oldPosition, Vector newPosition);
    void positionChanged();
}
