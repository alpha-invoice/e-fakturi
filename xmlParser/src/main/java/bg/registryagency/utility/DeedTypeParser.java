package bg.registryagency.utility;

import java.util.regex.Pattern;

import bg.registryagency.exception.InvalidDeedException;
import bg.registryagency.schemas.deedv2.DeedType;
import bg.registryagency.schemas.deedv2.fields.AddressType;

public class DeedTypeParser {

    private static Pattern eikRegex = Pattern.compile("[0-9]{9}");

    private DeedType deedType;

    public DeedTypeParser(DeedType deedType) {
        this.deedType = deedType;
    }

    public String getEik() throws InvalidDeedException {
        if (!eikRegex.matcher(deedType.getUIC()).matches()) {
            throw new InvalidDeedException("Invalid EIK for Deed.");
        }
        return deedType.getUIC();
    }

    public String getName() {
        return deedType.getCompanyName() + " " + LegalFormsConverter.getLegalFormInBulgarian(deedType.getLegalForm());
    }

    public String getMol() {
        try {
            if (deedType.getLegalForm().equals("ET")) {
                return deedType.getSubDeed().get(0).getPhysicalPersonTrader().get(0).getPerson().getName();
            } else {
                return deedType.getSubDeed().get(0).getManagers().get(0).getManager().get(0).getPerson().getName();
            }
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
