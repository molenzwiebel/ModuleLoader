
import nl.thijsmolendijk.Client.Modules.IModule;
import nl.thijsmolendijk.Client.Modules.UndercastModule;

@UndercastModule(name="Module 2", version="1.2.4")
public class SimpleMod2 implements IModule {

	@Override
	public boolean getState() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setState(boolean arg0) {
	}


}
