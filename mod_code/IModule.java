package nl.thijsmolendijk.Client.Modules;

public interface IModule {
	/**
     * Set the enable/disable state of the module
     * 
     * @param state boolean enable=true disable=false
     */
    public void setState(boolean state);

    /**
     * Returns the boolean value of enable or disable
     * 
     * @return boolean enable=true disable=false
     */
    public boolean getState();
}
