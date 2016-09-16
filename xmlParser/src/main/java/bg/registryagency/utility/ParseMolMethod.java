package bg.registryagency.utility;

import bg.registryagency.schemas.deedv2.DeedType;

@FunctionalInterface
public interface ParseMolMethod {
    public String parseMolFromDeed(DeedType deedType);
}
