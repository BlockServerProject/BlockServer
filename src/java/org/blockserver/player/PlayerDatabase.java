package org.blockserver.player;

import java.util.Locale;

import org.blockserver.Server;

/**
 * <p>The parent class for databases containing player data.<br>
 * Subclasses should have a public nullary constructor (with no arguments) in order to
 *   avoid <code>Class&lt;? extends PlayerDatabase&gt;.newInstance()</code> throwing an
 *   <code>InstantiationException</code> due to the class having no nullary constructor
 *   or an <code>IllegalAccessException</code> due to the constructor not
 *   being <code>public</code>.<br>
 * Constructors of subclasses should only initialize fields and leave the processing of
 *   the actual database at <code>initialize()</code>.</p>
 */
public abstract class PlayerDatabase{
	private boolean initialized = false;
	private boolean closed = false;
	private Server server;

	/**
	 * <p>Initialize the database. Database-related methods are
	 *   operated when this method is called.<br>
	 * This method should only be called once, or an
	 *   <code>IllegalStateException</code> will be thrown.</p>
	 * 
	 * @param server an instance of the currently running server
	 * @throws IllegalStateException if this method is already called.
	 */
	public final void init(Server server){
		if(initialized){
			throw new IllegalStateException("Database is already initialized.");
		}
		initialized = true;
		this.server = server;
		initialize();
	}
	/**
	 * <p>Initializes the database.<br>
	 * Subclasses should implement this method to run the code that actually
	 *   modifies/creates/reads the database, not at the constructor.</p>
	 */
	protected abstract void initialize();

	/**
	 * <p>Load a player's data from the username and return a <code>PlayerData</code> instance
	 *   containing the information fetched about the player.<br>
	 * This method returns the default data for a new player if the player is
	 *   not in the database.</p>
	 * 
	 * @param name the name of the player (will be converted to lower case) to load
	 * @return a <code>PlayerData</code> instance containing information of the player
	 */
	public final PlayerData load(String name){
		validate();
		return loadPlayer(name.toLowerCase(Locale.US));
	}
	protected abstract PlayerData loadPlayer(String name);

	/**
	 * Save a player's data into the database.
	 * 
	 * @param data
	 * @throws IllegalStateException if <code>init(Server)</code> has not been called
	 *   or <code>close()</code> has already been called.
	 */
	public final void save(PlayerData data){
		validate();
		savePlayer(data);
	}
	protected abstract void savePlayer(PlayerData data);

	public boolean isAvailable(){
		return isValid();
	}

	/**
	 * <p>Close the database. This method should only be called once.<br>
	 * After calling this method, no more methods except <code>isAvailable()</code>,
	 *   <code>isValid()</code>, <code>isInitialized()</code> and <code>isClosed()</code>
	 *   should be called on this object.</p>
	 * 
	 * @throws IllegalStateException if <code>init(Server)</code> has
	 *   not been called, or this method has been called on this object more than once.
	 */
	public final void close(){
		validate();
		closed = true;
		closeDatabase();
	}
	protected void closeDatabase(){}

	/**
	 * <p>Check if the database is initialized and throws an <code>IllegalStateException</code>
	 *   if not initialized.
	 * 
	 * @throws IllegalStateException if <code>init(Server)</code> has not been called on the object
	 *   or <code>close()</code> is already called on the object.
	 */
	protected void validate(){
		if(!isValid()){
			throw new IllegalStateException("Database not initialized");
		}
	}

	/**
	 * <p>Check if the database is valid and in running state.
	 * 
	 * @return <code>true</code> if <code>init()</code> has been called and
	 *   <code>close()</code> has not been called, <code>false</code> otherwise.
	 */
	public boolean isValid(){
		return initialized && !closed;
	}
	public boolean isInitialized(){
		return initialized;
	}
	public boolean isClosed(){
		return closed;
	}
	public Server getServer(){
		return server;
	}
}
