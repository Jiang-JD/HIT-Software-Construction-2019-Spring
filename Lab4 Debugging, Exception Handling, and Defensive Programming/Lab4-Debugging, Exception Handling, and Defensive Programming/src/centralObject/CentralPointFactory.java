package centralObject;

import physicalObject.ParamPackage;
import physicalObject.PhysicalObject;
import physicalObject.PhysicalObjectFactory;

public class CentralPointFactory extends PhysicalObjectFactory{
	
	/**
	 * 建抽象CentralPoint，输入名称name
	 * @param name 名称
	 * @return 新的PhysicalObject
	 */
	@Override
	public  CentralPoint create(String name) {
		return new CentralPoint(name);
	}

	@Override
	public PhysicalObject create(ParamPackage paramPackage) {
		return null;
	}
}
