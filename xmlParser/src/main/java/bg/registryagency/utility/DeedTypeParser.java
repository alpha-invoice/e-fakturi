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
        return deedType.getCompanyName() + " " + LegalFormUtils.getLegalFormInBulgarian(deedType.getLegalForm());
    }

    /**
     * TODO : to be extracted (complete refactoring to be done ). Based on the
     * legalForm extracts mol from the deedType.
     *
     * @return String mol name, or null if there is no present
     */
    public String getMol() {
        try {
            String mol = LegalFormUtils.getMol(deedType);
            if (mol.length() < 100) {
                return mol;
            }

            return null;
        } catch (Exception e) {
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
