package bg.registryagency.utility;

import bg.registryagency.schemas.deedv2.DeedType;
import bg.registryagency.schemas.deedv2.fields.AddressType;

public class DeedTypeParser {

    private DeedType deedType;

    public DeedTypeParser(DeedType deedType) {
        this.deedType = deedType;
    }

    public Long getEik() {
        return Long.parseLong(deedType.getUIC());
    }

    public String getName() {
        return deedType.getCompanyName() + " " + LegalFormsConverter.getLegalFormInBulgarian(deedType.getLegalForm());
    }

    public String getMol() {
        try {
            return deedType.getSubDeed().get(0).getManagers().get(0).getManager().get(0).getPerson().getName();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public String getAddress() {
        AddressType addressType;
        try {
            addressType = deedType.getSubDeed().get(0).getSeat().get(0).getAddress();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        AddressTypeParser addressTypeParser = new AddressTypeParser(addressType);
        return addressTypeParser.getFullAddress();
    }

}
