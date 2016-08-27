package de.nordakademie.eta.database;

public class DBException extends RuntimeException
{

  /**
   * 
   */
  private static final long serialVersionUID = -4180754848131415215L;
  
  public DBException( String message )
  {
    super( message );
  }

  public static final void throwFor( String message )
  {
    throw new DBException( message );
  }
  
}
