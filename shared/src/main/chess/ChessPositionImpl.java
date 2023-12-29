package chess;

import java.util.Objects;

public class ChessPositionImpl implements ChessPosition{
  private int row = 0;
  private int column = 0;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChessPositionImpl position=(ChessPositionImpl) o;
    return row == position.row && column == position.column;
  }

  @Override
  public String toString() {
    String strRow = Integer.toString(row);
    String strCol = Integer.toString(column);
    return
            "row=" + strRow +
            ", column=" + strCol;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, column);
  }


  public ChessPositionImpl(int row, int column) {
    this.row = row;
    this.column = column;
  }

  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getColumn() {
    return column;
  }
}
