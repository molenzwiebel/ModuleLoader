
import nl.thijsmolendijk.Client.Modules.IModule;
import nl.thijsmolendijk.Client.Modules.UndercastModule;

@UndercastModule(name="Module 1", version="1.0.0")
public class SimpleMod1 implements IModule {

	@Override
	public boolean getState() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setState(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

}
